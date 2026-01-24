(ns net.coruscation.cerulean.orgx.orgx
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.zip :as zip]
   [hickory.core :refer [as-hiccup]]
   [net.coruscation.cerulean.common.components :refer [default-exports]]
   [net.coruscation.cerulean.config :refer [join-workspace-path]]
   [net.coruscation.cerulean.orgx.orgx-commons :refer :all]
   [net.coruscation.cerulean.server.utils :refer [add-to-classpath]]
   [uix.dev :refer [from-hiccup]])
  (:import
   [java.nio.file Path]
   [org.apache.commons.text StringEscapeUtils]))

(deftype CljCode [^String s]
  Object
  (^String toString [_] s)
  (^boolean equals [_ other]
   (and (instance? CljCode other)
        (= s  (.toString other)))))

(defmethod print-method CljCode [this writer]
  (.write writer (.toString this)))

(defn unescape-contents [hiccup]
  (loop [zip (zip/vector-zip hiccup)]
    (if (zip/end? zip)
      (zip/node zip)
      (recur (zip/next
              (zip/replace zip
                           (if (string? (zip/node zip))
                             (StringEscapeUtils/unescapeHtml4 (zip/node zip))
                             (zip/node zip))))))))

(declare unwrap-clj-code)

(defn use-comp [comp-name children]
  [`(~'$ ~(symbol comp-name) ~@children)])

(defn use-defui [comp-name children]
  [nil `(~'defui ~(symbol comp-name) [~'props] ~@children)])

(def registered-wrappers {:use-comp use-comp
                          :use-defui use-defui})

(defn expand-orgx [orgx-node]
  (let [children (drop 3 orgx-node)
        [children-inplace children-toplevel]
        (unwrap-clj-code `(~'$ :<> ~@children))
        {:keys [data-wrapper
                data-wrapper-data
                data-toplevel]}
        (nth orgx-node 2)]
    (cond
      data-wrapper

      (let [[inplace toplevel] (((keyword data-wrapper)
                                 registered-wrappers)
                                data-wrapper-data
                                (list children-inplace))]
        [inplace toplevel])

      :else (if data-toplevel
              [nil (CljCode. (str/trim (last orgx-node)))]
              [(CljCode. (str/trim (last orgx-node)))]))))

(defn unwrap-clj-code [uix-sexp]
  (loop [loc (zip/seq-zip uix-sexp)
         toplevels nil]
    (if (zip/end? loc)
      [(zip/node loc)
       (apply list (reverse toplevels))]
      (let [node (zip/node loc)]
        (if (and (seq? node)
                 (= (first node)
                    '$)
                 (or (= (second node)
                        :pre)
                     (= (second node)
                        :code))
                 (= (:class (nth node 2))
                    "orgx"))
          (let [[inplace toplevel] (expand-orgx node)]
            (recur
             (zip/next (zip/replace loc
                                    inplace))
             (if toplevel
               (cons toplevel toplevels)
               toplevels)))
          (recur (zip/next loc)
                 toplevels))))))

(defn remove-extra-newline [hiccup]
  (loop [loc (zip/vector-zip hiccup)]
    (if (zip/end? loc)
      (zip/node loc)
      (let [node (zip/node loc)]
        (cond
          ;; do not handle verbatim blocks
	      (and (vector? node)
               (or (= (first node)
                      :pre)
                   (= (first node)
                      :node)
                   (= (first node)
                      :samp)))
          (if (zip/right loc)
            (recur (zip/right loc))
            (zip/root loc))

          (and (string? node)
               (= (str/trim-newline node)
                  ""))
          (recur (zip/next (zip/remove loc)))

          :else
          (recur (zip/next loc)))))))

(defn from-html [html]
  (let [results (->> (hickory.core/parse-fragment html)
                     (map as-hiccup)
                     (into [])
                     remove-extra-newline
                     (map unescape-contents)
                     (map from-hiccup)
                     (map unwrap-clj-code))]
    [(remove nil? (map first results))
     (apply list (apply concat (map second results)))]))

(defn blog->cljc [{:blog/keys [id content orgx-require] :as blog-asset}]
  (let [[inplace toplevels] (from-html content)]
    (->> (concat
          `((~'ns ~(symbol (str orgx-base-ns "." id))
             (:require [uix.core :as ~'uix :refer
                        [~'defui ~'use-state ~'use-effect ~'use-context ~'$]]
                       [net.coruscation.cerulean.utils :refer [~'cljc-case]]
                       [net.coruscation.cerulean.common.components :refer [~@default-exports]]
                       ~@orgx-require)))
          `(~@toplevels)
          `((~'defui ~'_comp [~'post-props]
             (~'$ :<> ~@inplace)))
          `((~'defui ~(symbol orgx-default-component-name) [~'props]
             (~'$ ~'_comp (~'merge (~'quote ~blog-asset)
                           ~'props)))))
         (map str)
         (str/join "\n"))))

(defn get-orgx-dest-dir []
  (join-workspace-path "./.cljc/orgx"))

(defn generate-cljc-from-blog [{:blog/keys [id] :as blog}]
  (.mkdirs (io/file (get-orgx-dest-dir)))
  (add-to-classpath (join-workspace-path "./.cljc/"))
  (binding [*print-namespace-maps* false]
    (spit (.toString (Path/of (get-orgx-dest-dir)
                              (into-array String
                                          [(str (str/replace id "-" "_")
                                                ".cljc")])))
          (blog->cljc blog))))
