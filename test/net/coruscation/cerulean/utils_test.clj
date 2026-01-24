(ns net.coruscation.cerulean.utils-test
  (:require
   [clojure.edn :as edn]
   [clojure.test :refer [deftest is]]
   [net.coruscation.cerulean.render.context :as render-context]
   [net.coruscation.cerulean.utils :as subject]))

(deftest use-precalculated-test
  (render-context/with-new-context
    (subject/use-precalculated "abc"
      (fn [] (+ 1 1))
      (fn []))
    (let [assets (:assets @render-context/*context*)
          [key value] (first assets)]
      (is (= 1 (count assets)))
      (is (.endsWith key "abc"))
      (is (= 2 (edn/read-string value))))))
