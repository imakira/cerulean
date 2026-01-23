(ns net.coruscation.cerulean.server.utils-test
  (:require
   [clojure.test :refer [deftest is use-fixtures]]
   [net.coruscation.cerulean.server.utils :as subject]
   [net.coruscation.cerulean.test-utils :refer [ensure-dynamic-classloader-fixture]])
  (:import
   [java.io File]
   [java.nio.file Files]))

(defn check-dynamic-load []
  (let [tmp-dir (.toFile (Files/createTempDirectory "classpath-demo" (into-array java.nio.file.attribute.FileAttribute [])))
        tmp-clj (File/createTempFile "demo" ".clj" tmp-dir)
        tmp-name (subs (.getName tmp-clj)
                       0
                       (.lastIndexOf (.getName tmp-clj)
                                     "."))]
    ;; Add `tmp-dir` to `classpath` using builtin `add-classpath`.
    (subject/add-to-classpath (.getPath tmp-dir))

    ;; Put a Clojure file under the directory
    (spit tmp-clj
          (str "(ns " tmp-name ") (def a 1)"))
                                        ;
    ;; `require` the Clojure file, and resolve the variable
    (is (= 1 (var-get (requiring-resolve (symbol tmp-name
                                                 "a")))))

    ;; Update the Clojure file
    (spit tmp-clj
          (str "(ns " tmp-name ") (def a 2)"))

    ;; Reload the Clojure file
    (require (symbol tmp-name)
             :reload-all)

    ;; We can read the new value
    (is (= 2 (var-get (requiring-resolve (symbol tmp-name
                                                 "a")))))
    (println "success")))

(deftest add-to-classpath-test
  (check-dynamic-load))

(deftest idempotency-test
  (subject/add-to-classpath "/tmp/nonexistence")
  (subject/add-to-classpath "/tmp/nonexistence")
  (subject/add-to-classpath "/tmp/nonexistence")
  (is  (= 1 (count (filter (fn [x] (= x  "/tmp/nonexistence"))
                           (.split (System/getProperty "java.class.path")
                                   ":") )))))

(use-fixtures :once ensure-dynamic-classloader-fixture)
