(ns net.coruscation.cerulean.common.commons
  (:require
   [cljc.java-time.format.date-time-formatter :as date-time-formatter]
   [cljc.java-time.local-date :as local-date]
   [cljc.java-time.local-date-time :as local-date-time]
   [cljc.java-time.local-time :as local-time]
   [cljc.java-time.zone-id :as zone-id]
   [cljc.java-time.zoned-date-time :as zoned-date-time]))

(defn index-by
  [f coll]
  (first (first (filter (fn [[index item]]
                          (f item))
                        (map-indexed vector coll)))))

(defn parse-iso8601 [timestr]
  (zoned-date-time/parse timestr date-time-formatter/iso-offset-date-time))

(defn parse-org-timestamp
  "Parse org timestamp inserted with `org-timestamp`, example: <2026-01-24 Sat>
  Throw an Exception if not supported"
  [timestr]
  (zoned-date-time/of
   (local-date-time/of
    (local-date/parse timestr
                      (date-time-formatter/of-pattern "<yyyy-MM-dd EEE>"))
    (local-time/of 0 0 0))
   (zone-id/system-default)))

(defn parse-timestamp
  "Supporting two format:
  1. org-mode timestamp inserted using `org-timestamp`, example: <2026-01-24 Sat>
  2. iso8601 timestamp"
  [timestr]
  (try
    (parse-iso8601 timestr)
    (catch #?(:clj java.lang.Throwable
              :cljs js/Error) _
      (try (parse-org-timestamp timestr)
           (catch #?(:clj java.lang.Throwable
                     :cljs js/Error) _
             (throw (ex-info "Can not parse timestamp: "
                             {:timestamp timestr})))))))

(defn to-iso8601 [zdt]
  (zoned-date-time/format zdt date-time-formatter/iso-offset-date-time))

(defn call-once! [f]
  (let [mem (atom {})]
    (fn [& args]
      (if-let [e (find @mem args)]
        (val e)
        (locking mem
          (if-let [e (find @mem args)]
            (val e)
            (let [ret (apply f args)]
              (swap! mem assoc args ret)
              ret)))))))
