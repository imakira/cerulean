(ns app.client
  (:require
   [cljs.spec.alpha :as s]
   [clojure.edn :as edn]
   [uix.core :as uix :refer [defui $ use-state use-effect]]
   [uix.dom :as dom]
   [app.pages :as pages]
   [clojure.core.async :as a]
   [stylefy.core :as stylefy]
   [stylefy.generic-dom :as stylefy-generic-dom]))

(defonce root
  (delay (uix.dom/create-root (js/document.getElementById "root"))))

(defn render []
  (uix.dom/render-root ($ pages/app) @root))

(defn ^:export init []
  (stylefy/init  {:dom (stylefy-generic-dom/init)})
  (if js/window.__cerulean_rehydrate
    (dom/hydrate-root (js/document.getElementById "root")
                      ($ pages/app {:initial-route js/location.pathname}))
    (render)))
