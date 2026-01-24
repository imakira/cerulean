(ns net.coruscation.cerulean.render.context
  (:require
   [clojure.edn :as edn]
   [net.coruscation.cerulean.render.context-commons :refer :all]))

(def ^:dynamic *context* (atom {}))

(defn put-value! [k v]
  (swap! *context* assoc k v))

(defn get-value [k]
  (get @*context* k))

(defn add-assets! [k v]
  (swap! *context*
         assoc-in [:assets k]
         v))

;; TODO: unify put-edn! and add-asset!
(defn put-edn! [k v]
  (add-assets! k (prn-str v)))

(defn get-edn [k]
  (edn/read-string (get @*context* k)))

(defn add-extra-script! [id module]
  (swap! *context* update-in
         [:extra-scripts]
         conj
         {:id id
          :module module}))

(defn extra-scripts []
  (:extra-scripts *context*))

(defmacro with-new-context [& body]
  `(binding [~'net.coruscation.cerulean.render.context/*context*
             (atom {})]
     ~@body))

(defn assets []
  (get @*context* :assets))

(defn values []
  @*context*)
