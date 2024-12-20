(ns app.server.static-generator
  (:require [app.server.assets :as assets]
            [app.common.pages :as pages]
            [reitit.core :as r]
            [app.server.render :as render]
            [app.config :as config]
            [clojure.java.io :as io]
            [cheshire.core :as json]
            [babashka.fs :as fs]))

(defn- derive-routes [routes]
  (let [router (r/router routes)
        routes (r/routes router)]
    (loop [routes routes
           res []]
      (if (empty? routes)
        (into [] res)
        (recur
         (rest routes)
         (let [[path arguments] (first routes)
               {name :name {depend-route :route
                            params-list-fn :params-list-fn}
                :depends} arguments]
           (if (nil? depend-route)
             (conj res (assoc-in (first routes)
                                 [1 :match]
                                 (r/match-by-path router (first (first routes)))))
             (do
               (assert name (str "This route " path " must have a name."))
               (let [params-list (params-list-fn)]
                 (concat res
                         (for [params params-list]
                           (let [match (r/match-by-name router
                                                        name
                                                        params)]
                             [(:path match)
                              (-> arguments
                                  (dissoc :depends)
                                  (assoc :match match))]))))))))))))

(defn- render-wrapper [{path :path :as match}]
  (render/render path))

(defn- get-all-routes []
  (derive-routes [""
                  assets/assets-route
                  ["" {:handler render-wrapper} pages/routes]]))

(defn generate []
  (let [out-dir (io/file config/*output*)]
    (when (.exists out-dir)
      (fs/delete-tree out-dir)
      (.mkdirs out-dir))
    (when (and (.exists out-dir)
               (.isFile out-dir))
      (throw (AssertionError. (str out-dir "shouldn't be a file")))))
  (fs/copy-tree (str (System/getProperty "user.dir")
                     "/public")
                (str (System/getProperty "user.dir")
                     "/out/"))
  (for [[path {handler :handler match :match}] (get-all-routes)]
    (let [file (io/file (str config/*output*  (if (= path "/")
                                                "/index.html"
                                                path)))]
      (.mkdirs (.getParentFile file))
      (when (.exists file)
        (fs/delete file))
      (with-open [file-writer (io/writer file)]
        (.write file-writer (let [result (handler match)]
                              (if (string? result)
                                result
                                (json/generate-string result))))))))

