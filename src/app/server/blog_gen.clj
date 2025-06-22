(ns app.server.blog-gen
  (:require
   [app.common.code-highlight :as code-highlight]
   [app.config :as config]
   [app.server.utils :as su]
   [cheshire.core :refer [parse-string]]
   [clojure.java.io :as io]
   [clojure.java.shell :refer [sh]]
   [clojure.string :as str]
   [clojure.zip :as zip]
   [hickory.core :as hk]
   [hickory.render :as hr]
   [hickory.select :as hs]
   [hickory.zip :as hz]
   [com.potetm.fusebox.fallback :as fallback]
   [com.potetm.fusebox.retry :as retry]) 
  (:import
   [com.google.common.io Files]
   [javax.imageio ImageIO]))

(defn get-code-code [hickory-tree]
  (let [class (as-> hickory-tree %
                (:attrs %)
                (:class %)
                (str/split % #" ")
                (filter (fn [class]
                          (str/starts-with? class "src-"))
                        %)
                (first %))]
    (and class (subs class 4))))


(defn wrap-in-pre-code [hickory-tree lang]
  {:type :element,
   :attrs {:class "cr-highlighted"},
   :tag :pre,
   :content
   [{:type :element,
     :attrs {:class (str "lang-" lang)},
     :tag :code,
     :content [hickory-tree]}]})

(defn html-code-highlight [hickory-tree]
  (let [select-fn (hs/or (hs/and (hs/tag :code)
                                 (hs/class :src))
                         (hs/and (hs/tag :pre)
                                 (hs/not (hs/class :cr-highlighted))))]
    (if-let [loc (hs/select-next-loc select-fn
                                     (hz/hickory-zip hickory-tree))]
      (loop [loc loc]
        (let [lang (get-code-code (zip/node loc))
              result (zip/replace loc
                                  (-> (code-highlight/highlight
                                       (-> loc
                                           zip/node
                                           :content
                                           first)
                                       lang)
                                      hk/parse
                                      hk/as-hickory
                                      ((fn [node]
                                         (if (= (:tag (zip/node loc))
                                                :pre)
                                           (wrap-in-pre-code node lang)
                                           node)))))]
          (if-let [loc (hs/select-next-loc
                        select-fn
                        (zip/next result))]
            (recur loc)
            (zip/root result))))
      hickory-tree)))

(defn wrap-in-self-reference [element]
  {:type :element,
   :attrs {:href (str "#" (-> element
                              :attrs
                              :id))}
   :tag :a,
   :content
   [(update-in element [:attrs :class] (fn [classes] (str "cr-self-reference " classes)))]})

(defn html-header-self-reference [hickory-tree]
  (let [select-fn (hs/and (apply hs/or (map hs/tag [:h1 :h2 :h3 :h4 :h5]))
                          (hs/not (hs/class :cr-self-reference)))]
    (if-let [loc (hs/select-next-loc select-fn
                                     (hz/hickory-zip hickory-tree))]
      (loop [loc loc]
        (let [result (zip/replace loc (wrap-in-self-reference (first loc)))]
          (if-let [new-loc (hs/select-next-loc select-fn
                                               result)]
	    (recur new-loc)
            (zip/root result))))
      hickory-tree)))

(defn relative-path? [path]
  (not (boolean (re-matches #"^(https?:/)?/.*$" path))))

(defonce image-cache-dir
  (future
    (let [cache-dir (str config/*cache* "/image-cache")]
      (.mkdirs (io/file cache-dir))
      cache-dir)))

(defn sanitize-filename [url]
  (java.net.URLEncoder/encode url))

(defn cache-image-if-remote [url-or-path]
  (if (re-matches #"^https?://.*$" url-or-path)
    (retry/with-retry {::retry/retry? (fn [n _ _]
                                        (< n 3))
                       ::retry/delay (constantly 1000)}
      (let [out-path (str @image-cache-dir "/" (sanitize-filename url-or-path))
            tmp-path (str out-path ".tmp")]
        (if (.exists (io/file out-path))
          out-path
          (do (with-open [out (io/output-stream tmp-path)
                          in (io/input-stream url-or-path)]
                (io/copy in out))
              (.renameTo (io/file tmp-path)
                         (io/file out-path))
              out-path))))
    url-or-path))

(defn get-img-dimension [url-or-path]
  (fallback/with-fallback {::fallback/fallback (constantly {})}
    (let [image-path (cache-image-if-remote url-or-path)]
      (with-open [image-stream (io/input-stream image-path)]
        (let [img (ImageIO/read image-stream)]
          (if (nil? img)
            {}
            {:height (.getHeight img)
             :width  (.getWidth img)}))))))

(defn set-img-dimension-if-not-exist [img dimension]
  (update-in img 
             [:attrs]
             (fn [m]
               (merge dimension m))))

(defn get-image-src [img]
  (let [tmp (-> img :attrs :src)]
    (if (relative-path? tmp)
      (-> (str config/*blog-dir*
               "/"
               tmp)
          io/file
          .getCanonicalPath)
      tmp)))

(defn optimize-img-tag [img]
  (let [image-path (get-image-src img)
        optimize? (fn [image-path]
                    (not (re-matches #".*\.svg$" image-path)))]
    
    (if (optimize? image-path)
      (let [{:keys [width height] } (get-img-dimension image-path)]
        (set-img-dimension-if-not-exist img (if width
                                              {:width (str width)
                                               :height (str height)}
                                              {})))
      img)))

(defn image-optimization [hickory-tree]
  (let [select-fn (hs/tag :img)]
    (if-let [loc (hz/hickory-zip hickory-tree)]
      (loop [loc loc]
        (if-let [candidate (hs/select-next-loc select-fn loc)]
          (recur (-> candidate
                     (zip/replace (optimize-img-tag (zip/node candidate)))
                     zip/next))
          (zip/root loc)))
      hickory-tree)))

(defn org-file->html [path]
  ;; TODO escape path
  (let [{:keys [content title category tags email language author] :as result}
        (-> (sh "emacs" "--batch" "-q" "-l" "init.el"
                "--eval" (str "(org->html-to-stdout \"" path "\")")
                :dir (System/getProperty "user.dir"))
            :out
            (parse-string true))

        hickory-blocks (->> content
                            hk/parse-fragment
                            (map (fn [block]
                                   (-> block
                                       hk/as-hickory
                                       image-optimization
                                       html-header-self-reference
                                       html-code-highlight))))]
    {:id (if title
           (str/lower-case (str/replace title #"[^\p{IsAlphabetic}]" "-"))
           (Files/getNameWithoutExtension path))

     :content (->> hickory-blocks
                   (map hr/hickory-to-html)
                   (str/join ""))
     :title title
     :category category
     :tags (if tags (str/split tags #" ") [])
     :email email
     :language language
     :author author}))

#_(def ^:dynamic *demo* (org-file->html "./blogs/demo.org"))
