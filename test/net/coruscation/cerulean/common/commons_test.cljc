(ns net.coruscation.cerulean.common.commons-test
  (:require
   [cljc.java-time.zoned-date-time :as zoned-date-time]
   [clojure.test :refer [are deftest is]]
   [net.coruscation.cerulean.common.commons :as subject])
  (:import
   [java.lang Throwable]))

(deftest parse-org-timestamp-test
  (let [zdt (subject/parse-org-timestamp "<2026-01-24 Sat>")]
    (are [x y] (= x y) (zoned-date-time/get-day-of-month zdt) 24
         (zoned-date-time/get-year zdt) 2026
         (zoned-date-time/get-month-value zdt) 1
         (zoned-date-time/get-hour zdt) 0))

  (is (thrown? Throwable (subject/parse-org-timestamp "broken"))))

(deftest parse-timestamp-test
  (let [zdt-with-org (subject/parse-timestamp "<2026-01-24 Sat>")
        zdt-with-iso (subject/parse-timestamp "2026-01-23T20:19:30Z")]
    (is (= zdt-with-org (subject/parse-org-timestamp  "<2026-01-24 Sat>")))
    (is (= (zoned-date-time/get-day-of-month zdt-with-iso)
           23))
    (is (thrown-with-msg? Throwable #"Can not parse timestamp:.*"
                          (subject/parse-timestamp "broken")))))

(deftest to-iso8601-test
  (is (= "2026-01-23T20:19:30Z"
         (subject/to-iso8601 (subject/parse-timestamp "2026-01-23T20:19:30Z"))) ))
