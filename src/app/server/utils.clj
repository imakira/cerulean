(ns app.server.utils
  (:require [clojure.string :as str]))

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