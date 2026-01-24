(ns net.coruscation.cerulean.common.commons-test
  (:require
   [cljc.java-time.zoned-date-time :as zoned-date-time]
   [clojure.core :refer [future]]
   [clojure.test :refer [are deftest is testing]]
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

(deftest call-once!-test
  (testing "simpel-test"
    (let [counter  (atom 0)
          inc-once! (subject/call-once!
                     (fn []
                       (swap! counter inc)))]
      (is (= 0 @counter))
      (inc-once!)
      (is (= 1 @counter))
      (inc-once!)
      (is (= 1 @counter))))

  (testing "multithread-test"
    (let [counter (atom 0)
          inc! (subject/call-once!
                (fn [n]
                  (swap! counter + n)))
          numbers (atom [(map (fn [_] (rand-int 1000)) (range 0 1000000)) (list)])
          futures (for [_ (range 0 1000)]
                    (future
                      (while (not (empty? (first @numbers)))
                        (let [[_ [next-val & _]] (swap! numbers
                                                        (fn [[rst taken]]
                                                          (if (empty? rst)
                                                            [rst taken]
                                                            [(rest rst)
                                                             (conj taken
                                                                   (first rst))])))]
                          (inc! next-val)))))]
      (doseq [f futures]
        @f)
      (is (= @counter
             (apply + (-> @numbers
                          second
                          sort
                          dedupe)))))))
