(ns net.coruscation.cerulean.common.pages
  (:require
   #?@(:clj [[net.coruscation.cerulean.server.assets :as assets]
             [hickory.core :as hc]
             [hickory.select :as hs]
             [net.coruscation.cerulean.server.utils :as su]])
   #?@(:cljs [["@js-joda/locale_en-us" :as js-joda-locale]])
   [cljc.java-time.format.date-time-formatter :as date-time-formatter]
   [clojure.string :as str]
   [net.coruscation.cerulean.common.commons :as commons]
   [net.coruscation.cerulean.components :refer [btn-wrapper]]
   [net.coruscation.cerulean.router :as router]
   [net.coruscation.cerulean.utils :refer
    [#?@(:cljs [obj->clj]) use-asset use-context use-orgx
     use-precalculated use-user-config] :as utils]
   [uix.core :as uix :refer
    [$ #?@(:cljs [lazy suspense]) defui use-effect use-memo
     use-ref use-state]]))


(defn date-time-to-readable-string [zoned-date-time]
  (.format zoned-date-time (-> "LLL dd, yyyy"
                               (date-time-formatter/of-pattern)
                               (date-time-formatter/with-locale
                                 #?(:cljs js-joda-locale/Locale.US
                                    :clj java.util.Locale/US)))))
(defui _github [] ($ :svg {:xmlns "http://www.w3.org/2000/svg", :viewBox "0 0 496 512", :width 40, :height 32} "<!--! Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free (Icons: CC BY 4.0, Fonts: SIL OFL 1.1, Code: MIT License) Copyright 2024 Fonticons, Inc. -->" ($ :path {:d "M165.9 397.4c0 2-2.3 3.6-5.2 3.6-3.3.3-5.6-1.3-5.6-3.6 0-2 2.3-3.6 5.2-3.6 3-.3 5.6 1.3 5.6 3.6zm-31.1-4.5c-.7 2 1.3 4.3 4.3 4.9 2.6 1 5.6 0 6.2-2s-1.3-4.3-4.3-5.2c-2.6-.7-5.5.3-6.2 2.3zm44.2-1.7c-2.9.7-4.9 2.6-4.6 4.9.3 2 2.9 3.3 5.9 2.6 2.9-.7 4.9-2.6 4.6-4.6-.3-1.9-3-3.2-5.9-2.9zM244.8 8C106.1 8 0 113.3 0 252c0 110.9 69.8 205.8 169.5 239.2 12.8 2.3 17.3-5.6 17.3-12.1 0-6.2-.3-40.4-.3-61.4 0 0-70 15-84.7-29.8 0 0-11.4-29.1-27.8-36.6 0 0-22.9-15.7 1.6-15.4 0 0 24.9 2 38.6 25.8 21.9 38.6 58.6 27.5 72.9 20.9 2.3-16 8.8-27.1 16-33.7-55.9-6.2-112.3-14.3-112.3-110.5 0-27.5 7.6-41.3 23.6-58.9-2.6-6.5-11.1-33.3 2.6-67.9 20.9-6.5 69 27 69 27 20-5.6 41.5-8.5 62.8-8.5s42.8 2.9 62.8 8.5c0 0 48.1-33.6 69-27 13.7 34.7 5.2 61.4 2.6 67.9 16 17.7 25.8 31.5 25.8 58.9 0 96.5-58.9 104.2-114.8 110.5 9.2 7.9 17 22.9 17 46.4 0 33.7-.3 75.4-.3 83.6 0 6.5 4.6 14.4 17.3 12.1C428.2 457.8 496 362.9 496 252 496 113.3 383.5 8 244.8 8zM97.2 352.9c-1.3 1-1 3.3.7 5.2 1.6 1.6 3.9 2.3 5.2 1 1.3-1 1-3.3-.7-5.2-1.6-1.6-3.9-2.3-5.2-1zm-10.8-8.1c-.7 1.3.3 2.9 2.3 3.9 1.6 1 3.6.7 4.3-.7.7-1.3-.3-2.9-2.3-3.9-2-.6-3.6-.3-4.3.7zm32.4 35.6c-1.6 1.3-1 4.3 1.3 6.2 2.3 2.3 5.2 2.6 6.5 1 1.3-1.3.7-4.3-1.3-6.2-2.2-2.3-5.2-2.6-6.5-1zm-11.4-14.7c-1.6 1-1.6 3.6 0 5.9 1.6 2.3 4.3 3.3 5.6 2.3 1.6-1.3 1.6-3.9 0-6.2-1.4-2.3-4-3.3-5.6-2z"})))

(defui _hacker-news [] ($ :svg {:xmlns "http://www.w3.org/2000/svg", :viewBox "0 0 448 512", :width 40, :height 32} "<!--! Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free (Icons: CC BY 4.0, Fonts: SIL OFL 1.1, Code: MIT License) Copyright 2024 Fonticons, Inc. -->" ($ :path {:d "M0 32v448h448V32H0zm21.2 197.2H21c.1-.1.2-.3.3-.4 0 .1 0 .3-.1.4zm218 53.9V384h-31.4V281.3L128 128h37.3c52.5 98.3 49.2 101.2 59.3 125.6 12.3-27 5.8-24.4 60.6-125.6H320l-80.8 155.1z"})))

(defui _linkedin [] ($ :svg {:xmlns "http://www.w3.org/2000/svg", :viewBox "0 0 448 512", :width 40, :height 32} "<!--! Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free (Icons: CC BY 4.0, Fonts: SIL OFL 1.1, Code: MIT License) Copyright 2024 Fonticons, Inc. -->" ($ :path {:d "M416 32H31.9C14.3 32 0 46.5 0 64.3v383.4C0 465.5 14.3 480 31.9 480H416c17.6 0 32-14.5 32-32.3V64.3c0-17.8-14.4-32.3-32-32.3zM135.4 416H69V202.2h66.5V416zm-33.2-243c-21.3 0-38.5-17.3-38.5-38.5S80.9 96 102.2 96c21.2 0 38.5 17.3 38.5 38.5 0 21.3-17.2 38.5-38.5 38.5zm282.1 243h-66.4V312c0-24.8-.5-56.7-34.5-56.7-34.6 0-39.9 27-39.9 54.9V416h-66.4V202.2h63.7v29.2h.9c8.9-16.8 30.6-34.5 62.9-34.5 67.2 0 79.7 44.3 79.7 101.9V416z"})))

(defui _square-rss [] ($ :svg {:xmlns "http://www.w3.org/2000/svg", :viewBox "0 0 448 512", :width 40, :height 32} "<!--! Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free (Icons: CC BY 4.0, Fonts: SIL OFL 1.1, Code: MIT License) Copyright 2024 Fonticons, Inc. -->" ($ :path {:d "M64 32C28.7 32 0 60.7 0 96L0 416c0 35.3 28.7 64 64 64l320 0c35.3 0 64-28.7 64-64l0-320c0-35.3-28.7-64-64-64L64 32zM96 136c0-13.3 10.7-24 24-24c137 0 248 111 248 248c0 13.3-10.7 24-24 24s-24-10.7-24-24c0-110.5-89.5-200-200-200c-13.3 0-24-10.7-24-24zm0 96c0-13.3 10.7-24 24-24c83.9 0 152 68.1 152 152c0 13.3-10.7 24-24 24s-24-10.7-24-24c0-57.4-46.6-104-104-104c-13.3 0-24-10.7-24-24zm0 120a32 32 0 1 1 64 0 32 32 0 1 1 -64 0z"})))

(defui _square-envelope [] ($ :svg {:xmlns "http://www.w3.org/2000/svg", :viewBox "0 0 448 512", :width 40, :height 32} "<!--! Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free (Icons: CC BY 4.0, Fonts: SIL OFL 1.1, Code: MIT License) Copyright 2024 Fonticons, Inc. -->" ($ :path {:d "M64 32C28.7 32 0 60.7 0 96L0 416c0 35.3 28.7 64 64 64l320 0c35.3 0 64-28.7 64-64l0-320c0-35.3-28.7-64-64-64L64 32zM218 271.7L64.2 172.4C66 156.4 79.5 144 96 144l256 0c16.5 0 30 12.4 31.8 28.4L230 271.7c-1.8 1.2-3.9 1.8-6 1.8s-4.2-.6-6-1.8zm29.4 26.9L384 210.4 384 336c0 17.7-14.3 32-32 32L96 368c-17.7 0-32-14.3-32-32l0-125.6 136.6 88.2c7 4.5 15.1 6.9 23.4 6.9s16.4-2.4 23.4-6.9z"})))


(def icons {:github _github
            :linkedin _linkedin
            :rss _square-rss
            :email _square-envelope})

(defn- header-to-level [tag]
  (case tag
    :h2 1
    :h3 2
    :h4 3
    :h5 4
    (throw (ex-info (str "tag " tag " isn't suported")
                    {}))))



#?(:clj
   (defn- generate-toc-from-html-string [raw-html]
     (let [headers (->> raw-html
                        hc/parse
                        hc/as-hickory
                        (hs/select (hs/or (hs/tag :h2)
                                          (hs/tag :h3)
                                          (hs/tag :h4)
                                          (hs/tag :h5))))]
       (into [] (for [header headers]
                  {:level (header-to-level (:tag header))
                   :content (su/extract-string header)
                   :id (:id (:attrs header))})))))

#?(:cljs (defn- generate-toc-from-element [element]
           (let [headers (array-seq (.querySelectorAll element "h2,h3,h4,h5"))]
             (into [] (for [header headers]
                        {:level (header-to-level (keyword
                                                  (str/lower-case (.-tagName header))))
                         :content (.-innerText header)
                         :id (.-id header)})))))

(defui toc-mobile [{:keys [toc-content current-header-id]}]
  (let [toc-item-active-classes "text-[#0260B3]"
        toc-header-ref (use-ref nil)
        toc-mobile-control (use-ref nil)
        toc-mobile-content (use-ref nil)
        toc-mobile-label (use-ref nil)
        [toc-stuck? set-toc-stuck!] (use-state false)
        current-header-index (if (and current-header-id
                                      toc-content)
                               (commons/index-by (fn [item]
                                                   (= current-header-id
                                                      (:id item)))
                                                 toc-content))
        escape-string (fn [s]
                        (str "\""
                             (-> s
                                 (str/replace "\\" "\\\\")
                                 (str/replace "\"" "\\\""))
                             "\""))
        [toc-open? set-toc-open!] (use-state false)]

    #?(:cljs (use-effect (fn []
                           (let [callback (fn [[e]]
                                            (set-toc-stuck! (not (.-isIntersecting e))))
                                 observer (js/IntersectionObserver. callback
                                                                    #js {:rootMargin "-1px 0px 0px 0px"
                                                                         :threshold #js [1]})]
                             (if @toc-header-ref (.observe observer @toc-header-ref))
                             (fn []
                               (if @toc-header-ref (.unobserve observer @toc-header-ref)))))
                         []))
    #?(:cljs (use-effect (fn []
                           (if toc-stuck?
                             (utils/set-css-variable! "--toc-mobile-offset"
                                                      (str "-"
                                                           (* 2 (+ 1 current-header-index))
                                                           "rem")))
                           (fn []
                             (utils/set-css-variable! "--toc-mobile-offset" "initial")))
                         [current-header-index toc-stuck?]))

    #?(:cljs (use-effect (fn []
                           (if @toc-mobile-control
                             (set! (.-checked @toc-mobile-control)
                                   toc-open?))
                           (fn []))
                         [toc-open?]))

    #?(:cljs
       (use-effect (fn []
                     (let [callback
                           (fn [e]
                             (if (and toc-open?
                                      @toc-mobile-content
                                      @toc-mobile-label
                                      (not (.contains @toc-mobile-label
                                                      (.-srcElement e)))
                                      (not (.contains @toc-mobile-content
                                                      (.-srcElement e))))
                               (set-toc-open! false)))]
                       (js/addEventListener "click" callback)
                       (fn []
                         (js/removeEventListener "click" callback))))
                   [toc-open?]))


    ($ :<>
       ($ :div {:ref toc-header-ref})
       ($ :input.hidden {:type "checkbox"
                         :id "toc-mobile-control"
                         :ref toc-mobile-control})
       ($ :label
          {:for "toc-mobile-control"
           :class (str " toc-header cursor-pointer select-none block text-sky-700 border-1 pl-2 "
                       " flex justify-between items-center mt-3 bg-white sticky top-0 h-8 pr-1 "
           	           " md:hidden ml-[1px] relative ")
           :on-click (fn [e]
                       (.preventDefault e)
                       (set-toc-open! (not toc-open?)))
           :ref toc-mobile-label}
          ($ :div.relative.h-full.w-full.overflow-hidden
             ($ :div.text-lg.toc-header-text.relative
                {:class (str " w-full transition-all ")}
                ($ :div.h-8.w-96 {:key "TOC"} "Table Of Content")
                (for [toc-item toc-content]
                  ($ :div.h-8.w-96.overflow-hidden {:key (:id toc-item)}
                     (:content toc-item)))))
          ($ :span.material-symbols-outlined.checked:rotate-180.transition-all.dropdown-icon
             "keyboard_arrow_down"))
       ($ :div.toc-mobile-wrapper2.h-1.sticky.top-8
          ($ :div.grid.toc-mobile-wrapper.transition-all.border-neutral-400.border-l-1.bg-white
             {:class  "md:hidden ml-[1px] "}
             ($ :ul.toc-mobile-content
                {:class "text-[1.05rem] 2xl:text-lg "
                 :ref toc-mobile-content}
                (for [{:keys [content id level] :as toc-item} toc-content]
                  ($ :li.my-1.list-inside.text-neutral-600
                     {:key id}
                     ;; to make tailwindcss-cli happy
                     ;; "pl-0 pl-4 pl-8 pl-12 pl-16 pl-20"
                     ($ btn-wrapper
                        ($ :div.group.w-full.h-full
                           ($ :a.h-12.transition-all.w-full.h-full.inline-block.flex.flex-col.justify-center
                              {:class (str "group-hover:text-[#0260B3] "
                                           (str "pl-" (* 4 level))
                                           (if (= current-header-id id)
                                             (str " " toc-item-active-classes)
                                             ""))
                               :href (str "#" id)
                               :on-click (fn [_] (set-toc-open! false))}
                              ($ :span content))))))))))))

(defui toc [{:keys [toc-content class current-header-id]}]
  (let [toc-item-active-classes "text-[#0260B3]"]
    ($ :div.border-neutral-400.border-l-2 {:class (str "text-[1.05rem] 2xl:text-lg " class)}
       ($ :ul
          (for [{:keys [content id level] :as toc-item} toc-content]
            ($ :li.my-1.list-inside.text-neutral-600  {:key id}
               ($ btn-wrapper
                  ($ :div.group.w-full.h-full
                     ($ :a.h-12.transition-all.w-full.h-full.inline-block.flex.flex-col.justify-center
                        ;; to make tailwindcss-cli happy
                        #_"pl-0 pl-4 pl-8 pl-12 pl-16 pl-20"
                        {:class (str "group-hover:text-[#0260B3] "
                                     (str "pl-" (* 4 level))
                                     (if (= current-header-id id)
                                       (str " " toc-item-active-classes)
                                       ""))
                         :href (str "#" id)}
                        ($ :span content))))))))))


(defui blog-content [{:blog/keys [id orgx content] doc-ref :doc-ref}]
  (let [orgx-comp (use-orgx id orgx)]
    (if (not orgx)
      ($ :div.cr-document {:class "md:mt-1"
                           :ref doc-ref
                           :dangerouslySetInnerHTML {:__html content}})
      (when orgx-comp
        ($ :div.cr-document {:class "md:mt-1"
                             :ref doc-ref}
           ($ orgx-comp))))))

(defui blog [{{:blog/keys [id]} :path-params :as data}]
  (let [{:blog/keys [content title show-toc? category
                     tags modified-date
                     published-date description
                     orgx] :as blog-asset}
        (use-asset (str "blog/" id))
		toc-content (use-precalculated id
                      ;; In some case, `generate-toc-from-html-string` (in clj) and
                      ;;   `generate-toc-from-element` in cljs produces slightly different results.
                      ;; The following code will choose clj, if server rendered, and cljs version if client rendered.
                      ;; Thus workaround the problem.
                      (fn [] #?(:clj (generate-toc-from-html-string content)))
                      (fn [] #?(:cljs (if show-toc? (let [dummy (.createElement js/document "html")]
                                                      (set! (.-innerHTML dummy)
                                                            content)
                                                      (generate-toc-from-element dummy))
                                          nil))))
        doc-ref (use-ref nil)
        [current-header-id set-current-header-id!] (use-state nil)]
    (utils/set-title! (str title " | " (use-user-config :title)))
    (utils/set-description! description)
    #?(:cljs
       (use-effect (fn []
                     (let [scroll-event
                           (fn [_]
                             (let [headers (.querySelectorAll @doc-ref "h1,h2,h3,h4,h5")

                                   current-header (or (some->> headers
                                                               (take-while
                                                                (fn [header]
                                                                  (< (.-top (.getBoundingClientRect header))
                                                                     2)))
                                                               last)
                                                      (first headers))]
                               (when current-header
                                 (set-current-header-id! (.-id current-header)))))]
                       (js/addEventListener "scroll" scroll-event)
                       (fn []
                         (js/removeEventListener "scroll" scroll-event))))
                   []))
    (when (seq blog-asset)
      ($ :div.px-4.relative
         ($ :div
            ($ :h1.font-medium.text-neutral-700.leading-tight {:class "text-2xl sm:text-[2rem]"} title)
            ($ :hr.border-t-1.border-slate-500.w-full {:class "my-3"})
            ($ :div.text-base.grid.items-center.text-gray-700 {:class "pl-[1px] grid-cols-[7rem_1fr]"}
               ($ :div.text-sky-700.border-l-2.pl-2.text-lg
                  (str category))
               ($ :span.flex.items-center.gap-4
                  ($ :span.flex.items-center.gap-2
                     ($ :span.text-gray-600 "Pub.")
                     ($ :span.text-base.text-gray-700
                        (date-time-to-readable-string (commons/parse-iso8601 published-date))))
                  (when (not (= published-date modified-date))
                    ($ :span.flex.items-center.gap-2
                       ($ :span.text-gray-600 "Upd.")
                       ($ :span.text-base.text-gray-700
                          (date-time-to-readable-string (commons/parse-iso8601 modified-date))))))))
         (when show-toc?
           ($ toc-mobile {:class ""
                          :toc-content toc-content
                          :current-header-id current-header-id}))

         ($ :div.gap-8.w-full.h-full {:class
	                                  (str "md:grid md:grid-cols-[minmax(0px,7fr)_minmax(17rem,17rem)] "
                                           "2xl:grid-cols-[minmax(0px,7fr)_minmax(20rem,20rem)]")}

            ($ blog-content (merge blog-asset {:doc-ref doc-ref}))

            ($ :div.mt-4
               (when show-toc?
                 ($ toc {:class (str "w-78 fixed right-0 top-[13.5rem] z-200 max-h-[80vh] "
                                     "overflow-y-auto select-none hidden md:block xl:top-36  "
                                     "2xl:w-96")
                         :toc-content toc-content
                         :current-header-id current-header-id}))))))))


(defui blog-item [{:keys [preview onclick class]}]
  (let [{:blog/keys [title id tags published-date modified-date author category]} preview]
    ($ :div.blog-item {:class class}
       ($ :div.relative.group
          ($ :div
             ($ :div
                ($ :span.text-lg.text-slate-600
                   (date-time-to-readable-string (commons/parse-iso8601 published-date)))
                ($ :span " / ")
                ($ :span.text-lg.text-slate-600
                   category))
             ($ :div {:class ""}
                ($ router/link {:href (str "blogs/" id ".html")
                                :class "text-2xl text-sky-950 hover:text-[#0260B3] transition-all duration-300"} ($ :h1 title))))))))



(defui home [_]
  (let [blogs  (use-asset "blogs")
        landscape-ref (use-ref nil)]
    (use-effect
     (fn []
       #?(:cljs
          (let [scroll-listener
                (fn [_]
                  (let [{:keys [bottom] :as rect}
                        (obj->clj (.getBoundingClientRect @landscape-ref)
                                  :keywordize-keys true)]))]
            (js/addEventListener "scroll" scroll-listener)
            (fn []
              (js/removeEventListener "scroll" scroll-listener)))))
     [])
    (utils/set-title! (str "HOME | " (use-user-config :title)))
    (utils/set-description! "")
    ($ :div.flex.flex-col
       ($ :div.flex.flex-col.justify-center.items-center {:class "xl:w-[1100px]"}
          ($ :div.w-full
             ($ :div.flex.justify-center.flex-col.gap-6.bg-opacity-100.w-full
                (for [blog blogs]
                  ($ :div.w-full {:key (:blog/id blog)}
                     ($ blog-item {:preview blog
                                   :key (:blog/id blog)
                                   :class "w-full"})
                     ($ :hr {:class "mt-2 border-gray-500"})))))))))


(defui email [{:keys [path domain]}]
  (let [[email-href set-href!] (use-state "")]
    (use-effect (fn []
                  #?(:cljs (js/setTimeout (fn []
                                            (set-href! (str "mailto:" path "@" domain)))
                                          0))
                  (fn [])))
    ($ router/link {:href email-href :external? true}
       ($ (:email icons)))))

(defui header []
  (let [{:keys [component] :as ctx} (use-context router/*router*)
        home-page? (= component home)]
    ($ :<>
       ($ :div.bg-opacity-100.px-4.relative
          {:class (str "bg-[#0260B3] sm:flex sm:justify-between block gap-4 "
                       (if home-page?
                         "md:grid md:grid-cols-[2fr_3fr] md:h-dvh md:fixed md:w-[20rem]"
                         "xl:grid xl:grid-cols-[2fr_3fr] xl:h-dvh xl:fixed xl:w-[20rem]"))}
          ($ router/link {:href "/" :class "flex justify-center block"}
             ($ :span.text-3xl.text-cyan-50.pt-4
                {:class (str "sm:py-6 "
                             (if home-page?
                               "md:sideways-lr md:text-6xl md:pb-8"
                               "xl:sideways-lr xl:text-6xl xl:pb-8"))}
                (use-user-config :title)))

          ($ :div.flex.justify-center
             ($ :div.flex.flex-col.items-center.justify-end.py-1.relative
                {:class (str "left-[0.275rem] sm:items-end "
                             (if home-page?
                               "md:justify-between"
                               "xl:justify-between"))}
                ($ :div.navigator-bar.gap-3.text-base.text-slate-50.opacity-80.flex.px-5.my-2
                   {:class (if home-page?
                             "md:text-2xl md:flex-col md:pt-6"
                             "xl:text-2xl xl:flex-col xl:pt-6")}
                   (for [[key {:keys [text href]}] (use-user-config :navigation)]
                     ($ :div.px-1.group-hover:text-cyan-50 {:key key :class "text-lg"}
                        ($ router/link {:href href}
                           text))))

                ($ :div.justify-center.w-full.mb-8.relative.hidden
                   {:class  (if home-page?
                              "md:flex"
                              "xl:flex")}
                   ($ :div.flex.relative
                      (for [[key {:keys [href external?] :as link-config}] (use-user-config :links)]
                        (cond
                          (= key :email)
                          ($ email (merge link-config {:key :email}))

                          :else
                          ($ router/link {:href href
                                          :key key
                                          :external? external?}
                             ($ (key icons)))))))))))))

(def routes
  ["" {:controllers [{:identity identity
                      :start (fn [_])
                      :stop (fn [_])}]}
   ["/index.html" {:component home}]
   ["/home.html" {:component home}]
   ["/" {:component home
         :controllers [{:identity identity
                        :start (fn [{{:keys [routing-state set-routing-state!] :as data}:data}]
                                 (set-routing-state! (assoc-in routing-state [:home-page?] true)))
                        :stop (fn [{{:keys [routing-state set-routing-state!]} :data}]
                                (set-routing-state! (assoc-in routing-state [:home-page?] false)))}]}]

   ;; ["/blogs/demonstration.html" {:component #?(:cljs (shadow.lazy/loadable orgx.demonstration/compnent)
   ;;                                        :clj orgx.demonstration/compnent)}]
   ["/blogs/{blog/id}.html" {:component blog
                        	 :name ::blog
                             :depends #?(:clj {:route :assets/blogs
                                               :params-list-fn
                                               assets/fetch-blog-ids}
                                         :cljs nil)}]
   ;; TODO: make this configurable
   ;; ["/about.html" {:component blog
   ;;                 :extra-data {:path-params {:blog/id "about.html"}}}]
   ])

(defui main [_]
  (let [{:keys [component] :as ctx} (use-context router/*router*)
        home-page? (= component home)]
    ($ :div.app.h-dvh.w-full
       {:class (if home-page?
                 "md:grid md:grid-cols-[20rem_9fr]"
                 "xl:grid xl:grid-cols-[20rem_9fr]")}
       ;; ($ :div.w-full.h-full.fixed.-z-50.bg-cyan-50.fixed.top-0)
       ($ :<>
          ($ :div.transition-all
             ($ header))
          ($ :main.pt-5.px-2 {:class "sm:px-4 xl:pt-7 xl:px-8"}
             ($ router/router-outlet))))))

(defui app [{:keys [initial-route]}]
  ($ router/router {:routes routes :initial-route initial-route}
     ($ main {})))
