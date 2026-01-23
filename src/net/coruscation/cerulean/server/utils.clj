(ns net.coruscation.cerulean.server.utils
  (:require
   [cemerick.pomegranate :as pome]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [net.coruscation.cerulean.common.commons :refer [call-once!]])
  (:import
   (java.nio.file Path)))

(defn extract-string [hickory-elements & {:keys [spacer]
                                          :or {spacer " "}}]
  (letfn [(inner [hickory-elements]
            (->> (cond
                   (string? hickory-elements) hickory-elements
                   (map? hickory-elements) (inner (:content hickory-elements))
                   true    (map inner hickory-elements))))]
    (->> (inner hickory-elements)
         (conj [])
         flatten
         (remove #(= "" (str/trim %)))
         (str/join spacer)
         str/trim)))

(defn path-join [& paths]
  (let [paths (mapv (fn [path]
                      (cond (instance? java.nio.file.Path path)
                            (.toString path)
		                    (instance? java.io.File path)
                            (.toString path)
                            :else
                            path))
                    paths)]
    (->> (rest paths)
         (into-array String)
         (java.nio.file.Path/of (first paths))
         (.normalize)
         (.toAbsolutePath)
         (.toString))))

(def add-to-classpath
  (call-once!
   (fn [path]
     (let [path (-> (Path/of path (into-array String []))
                    (.normalize)
                    (.toAbsolutePath)
                    (.toString))
           shadow-property-name "java.class.path"]
       (pome/add-classpath path)
       (when (find-ns 'kaocha.classpath)
         ((intern 'kaocha.classpath
                  'add-classpath)
          path))
       (if (nil? (System/getProperty shadow-property-name))
         (System/setProperty shadow-property-name path)
         (System/setProperty shadow-property-name
                             (str path ":" (System/getProperty shadow-property-name))))))))

(defn take-until
  [pred coll]
  (lazy-seq
   (when-let [[f & r] (seq coll)]
     (if (pred f)
       (cons f (take-until pred r))
       [f]))))

(defn assert-file-exist [path]
  (assert (.exists (io/file path)) (str "File " path " doesn't exist"))
  (assert (.isFile (io/file path)) (str "Path " path " is a directory")))
