(ns net.coruscation.cerulean.test-utils
  (:require
   [babashka.fs :as fs]
   [cemerick.pomegranate :as pome]
   [cheshire.core :as cheshire]
   [clojure.java.io :as io]
   [clojure.test :refer [is]]
   [clojure.tools.logging :as logging]
   [net.coruscation.cerulean.config :refer [*blog-dir* *cache* *output*
                                            with-workspace]]
   [net.coruscation.cerulean.server.utils :refer [path-join]]))

(defmacro with-temp-workspace [& children]
  `(let [~'tmp-workspace (.toString (java.nio.file.Files/createTempDirectory "cerulean-tmp-workspace" (into-array java.nio.file.attribute.FileAttribute [])))]
     (try
       (with-workspace (io/file ~'tmp-workspace)
         (logging/info "working on tmp workspace" {:workspace ~'tmp-workspace})
         (.mkdirs (io/file *cache*))
         (.mkdirs (io/file *blog-dir*))
         (.mkdirs (io/file *output*))
         ~@children)
       (finally
         (logging/info "quit tmp workspace" {:workspace ~'tmp-workspace})
         (fs/delete-tree ~'tmp-workspace)))))

(defn temp-workspace-fixture [test-run]
  (with-temp-workspace
    (test-run)))

(defn ensure-dynamic-classloader-fixture [test-run]
  (when (empty? (filter pome/modifiable-classloader? (pome/classloader-hierarchy (.getContextClassLoader (Thread/currentThread)))))
    (let [cl (.getContextClassLoader (Thread/currentThread))]
      (.setContextClassLoader (Thread/currentThread) (clojure.lang.DynamicClassLoader. cl))))
  (test-run))

(defn verify-build-results [workspace]
  (with-workspace workspace
    (is (.exists (io/file (path-join workspace
                                     "docs"
                                     "index.html"))))
    (is (= "example.com" (slurp (io/file (path-join workspace
                                                    "docs"
                                                    "CNAME")))))
    (is (.exists (io/file (path-join *output*
                                     "js/main.js"))))
    (is (.exists (io/file (path-join *output*
                                     "assets/blogs"))))
    (is (.exists (io/file (path-join *output*
                                     "atom.xml"))))
    (is (= "example.com"
           (slurp (io/file (path-join *output*
                                      "CNAME")))))
    (is (.exists (io/file (path-join *output*
                                     "sitemap.xml"))))
    (is (.exists (io/file (path-join *output*
                                     "main.css"))))
    (is (.exists (io/file (path-join *output*
                                     "blogs/demo.html"))))
    (is (= "Demo"
           (-> (cheshire/parse-string (slurp
                                       (io/file
                                        (path-join *output*
                                                   "assets/blogs")))
                                      keyword)
               first
               :blog/title)))))
