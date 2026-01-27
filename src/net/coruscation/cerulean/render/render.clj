(ns net.coruscation.cerulean.render.render
  (:require
   [cheshire.core :as json]
   [clojure.string :as str]
   [hiccup.util :refer [raw-string]]
   [hiccup2.core :as h]
   [net.coruscation.cerulean.common.pages :as pages]
   [net.coruscation.cerulean.render.context :refer [with-new-context] :as render-context]
   [net.coruscation.cerulean.render.context-commons :refer [extra-script-global-this-name]]
   [net.coruscation.cerulean.server.user-config :refer [get-user-config]]
   [net.coruscation.cerulean.utils :refer [use-user-config]]
   [uix.core :refer [$]]
   [uix.dom.server :as dom.server]))

(defn template [inner serialized-assets
                & {:keys [ssr? server-url
                          cloudflare-analytics-token
                          description
                          title
                          extra-scripts]
                   :or {ssr? false
                        server-url "http://localhost:3001/"}}]
  (str
   "<!DOCTYPE html>"
   (h/html
       [:html.no-js {:lang "en"}
        [:head
         [:meta {:charset "utf-8"}]
         [:meta {:http-equiv "x-ua-compatible" :content "ie=edge"}]
         [:meta {:name "description" :content (if (seq description) description title)}]
         [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
         [:title (or title (use-user-config :title))]
         [:link {:rel "preconnect", :href "https://fonts.googleapis.com"}]
         [:link
          {:rel "preconnect",
           :href "https://fonts.gstatic.com",
           :crossorigin ""}]
         [:link
          {:href "https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600&display=swap",
           :rel "stylesheet"}]
         [:link
          {:rel "stylesheet",
           :href "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&icon_names=keyboard_arrow_down"}]
         [:link {:rel "stylesheet" :href (str "/main.css?v=" (System/currentTimeMillis))}]
         [:link
          {:rel "alternate",
           :type "application/rss+xml",
           :title (use-user-config :title),
           :href (str (use-user-config :root-url) "/atom.xml")}]
         [:link {:rel "modulepreload" :href "/js/main.js"}]
         (map (fn [{:keys [_ module]}]
                [:link {:rel "modulepreload"
                        :href (str "/js/" module)}])
              extra-scripts)]
        [:body
         [:div#root (raw-string inner)]
         [:script (raw-string (str "window.__server_path = \"" server-url \"))]
         (when ssr? (list [:script "window.__cerulean_rehydrate = true;"]
                          [:script (raw-string
                                    (str/join "\n"
                                              (map (fn [[key value]]
                                                     (str "globalThis[\"" key "\"] = "
                                                          (json/generate-string value)
                                                          ";"))
                                                   serialized-assets)))]))
         (map (fn [{:keys [id module]}]
                (list [:script {:src (str "/js/" module) :type "module"} ]
                      [:script {:type "module"}
                       (raw-string "import * as exps  from '/js/" module "';\n")
                       (raw-string "window['" (extra-script-global-this-name id) "'] = exps;")]))
              extra-scripts)
         [:script {:src "/js/main.js" :type "module" :defer true}]
         (when cloudflare-analytics-token
           [:script {:defer true
                     :src "https://static.cloudflareinsights.com/beacon.min.js"
                     :data-cf-beacon  (cheshire.core/generate-string {:token cloudflare-analytics-token})}])]])))

(def ^:dynamic *serialized-assets* (atom {}))

(defn spa-template []
  (template nil nil))

(defn render [path]
  (with-new-context
    (let [inner (->
                 ($ pages/app {:initial-route path})
                 (dom.server/render-to-string))
          {:keys [description title extra-scripts]} (render-context/values)]
      (template inner (render-context/assets)
                :ssr? true :server-url "/"
                :description description
                :title title
                :extra-scripts extra-scripts
                :cloudflare-analytics-token (:token (get-user-config
                                                     :cloudflare-analytics))))))

(defn dev-template [{:keys [uri http-roots http-config] :as req}]
  {:status 200
   :body (template nil nil)})
