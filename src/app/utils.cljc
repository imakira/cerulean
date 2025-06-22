(ns app.utils
  (:require [uix.dom.server :as dom.server]
            [clojure.core.async :as a]
            [malli.core :as m]
            [malli.generator :as mg]
            [clojure.walk :as walk]
            #?@(:cljs [["axios" :as axios]
                       [uix.core :refer [use-state use-effect]]
                       [goog.object :as goog.object]])
            #?@(:clj [[app.server.assets :as assets]
                      [reitit.core :as r]
                      [hickory.zip :as hzip]])
            [clojure.zip :as zip])
  #?(:cljs (:require-macros [app.utils :refer [use-context context-binding defcontext if-cljs]]))
  #?(:cljs (:import [goog.async Throttle Debouncer])))


#?(:cljs (def SERVER_PATH js/window.__server_path))

(defn fetch-asset [path]
  (let [chan (a/chan 1)]
    (a/go #?(:clj (->>
                   (let [routing (r/match-by-path
                                  (r/router assets/json-assets-route)
                                  (str "/assets/" path))]
                     ((-> routing
                          :data
                          :handler)
                      routing))
                   (a/>! chan))
             :cljs (-> (axios/get (str SERVER_PATH "assets/" path))
                       (.then (fn [resp]
                                (a/go (a/>! chan (:data (js->clj resp :keywordize-keys true))))))
                       (.catch (fn [error]
                                 (a/go (a/>! chan (js->clj error :keywordize-keys)))))))
          (a/<! chan))))

(defn use-asset [path]
  #?(:cljs
     (or (js->clj (aget js/globalThis path)
                  :keywordize-keys true)
         (let [[asset set-asset!]
               (use-state {})]
           (use-effect (fn []
                         (a/go (set-asset! (a/<! (fetch-asset path)))))
                       [path])
           asset))
     :clj
     (let [asset (a/<!! (fetch-asset path))]

       (swap! (var-get (resolve 'app.server.render/*serialized-assets*) ) conj
              [path asset])
       asset)))

;; (defmacro case [& {:keys [cljd cljs clj]}]
;;   (cond
;;     (contains? &env '&env)
;;     `(cond (:ns ~'&env) ~cljs (:nses ~'&env) ~cljd :else ~clj)
;;     #?(:clj (:ns &env) :cljs true) cljs
;;     #?(:clj (:nses &env) :cljd true) cljd
;;     :else clj))

(defn cljs-env?
  "Take the &env from a macro, and tell whether we are expanding into cljs."
  [env]
  (boolean (:ns env)))

#?(:clj (defmacro if-cljs
          "Return then if we are generating cljs code and else for Clojure code.

  https://groups.google.com/d/msg/clojurescript/iBY5HaQda4A/w1lAQi9_AwsJ"
          [then else]
          (if (cljs-env? &env) then else)))

#?(:clj (defmacro defcontext
          [name default-value]
          (if (cljs-env? &env)
            ;; setting as dynamic just for suppressing the naming warning
            `(def ~(with-meta name {:dynamic true}) (uix.core/create-context ~default-value))
            `(def ~(with-meta name {:dynamic true})  ~default-value))))

(defn- do-cljs-bindings [bindings body]
  (if (empty? bindings)
    body
    (let [binding (first bindings)]
      `(uix.core/$ (.-Provider ~(first binding)) {:value ~(second binding)}
                   ~@(if (empty? (rest bindings))
                       body
                       (list (do-cljs-bindings (rest bindings) body)))))))

(defn- do-clj-bindings [bindings body]
  `(letfn [(wrap-fn# [f#]
             (fn [& args#]
               (binding ~bindings
                 (loop [node# (hzip/hiccup-zip (apply f# args#))]
                   (if (zip/end? node#)
                     (zip/root node#)
                     (if (fn? (first (zip/node node#)))
                       (recur (zip/next (zip/replace node# (assoc (zip/node node#) 0 (wrap-fn# (first (zip/node node#)))))))
                       (recur (zip/next node#)))))
                 #_(walk/postwalk (fn [item#]
                                    (if (fn? item#)
                                      (wrap-fn# item#)
                                      item#))
                                  ))))]
     ((wrap-fn#
       (fn []
         (do ~@body))))))


;; TODO: limitation: it could only have one child
#?(:clj (defmacro context-binding [bindings & body]
          (if (cljs-env? &env)
            (do-cljs-bindings (partition 2 bindings) body)
            (do-clj-bindings bindings body)
            #_`(binding ~bindings ~@body))))

#?(:clj (defmacro use-context [context]
          (if (cljs-env? &env)
            `(uix.core/use-context ~context)
            `~context)))

#?(:cljs (defn disposable->function [disposable listener interval]
           (let [disposable-instance (disposable. listener interval)]
             (fn [& args]
               (.apply (.-fire disposable-instance) disposable-instance (to-array args))))))

#?(:cljs (defn throttle [listener interval]
           (disposable->function Throttle listener interval)))

#?(:cljs (defn debounce [listener interval]
           (disposable->function Debouncer listener interval)))


#?(:cljs (defn recur-obj->clj
           [obj & {:keys [keywordize-keys max-level]
                   :or {keywordize-keys true}}]
           (cond
             (and (not (nil? max-level))
                  (= max-level 0))
             obj

             (js/Array.isArray obj)
             (into [] (map (fn [item]
                             (recur-obj->clj item
                                             :keywordize-keys keywordize-keys
                                             :max-level (if max-level
                                                          (- max-level 1)
                                                          nil)))
                           obj))

             (goog.isObject obj)
             (into {} (-> (fn [result key]
                            (let [v (goog.object/get obj key)]
                              (cond (= "function" (goog/typeOf v))
                                    result

                                    :else
                                    (assoc result
                                           (if keywordize-keys
                                             (keyword key)
                                             key)
                                           (recur-obj->clj v
                                                           :keywordize-keys keywordize-keys
                                                           :max-level (if max-level
                                                                        (- max-level 1)
                                                                        nil))))))
                          (reduce {} (goog.object/getKeys obj))))
             :else
             obj)))

#?(:cljs (defn obj->clj [obj & {:keys [keywordize-keys]
                                :or {keywordize-keys true}}]
           (recur-obj->clj obj :max-level 1)))


#?(:cljs (defn set-css-variable! [var value & [element]]
           (-> (or element js/document)
               (.-documentElement)
               (.-style)
               (.setProperty var value))))



