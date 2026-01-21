(ns net.coruscation.cerulean.build-test
  (:require
   [babashka.process :as process]
   [build]
   [clojure.test :refer [deftest is testing]]
   [clojure.tools.logging :as logging]
   [net.coruscation.cerulean.config :as config]
   [net.coruscation.cerulean.test-utils :as test-utils]))


(deftest test-build-uberjar []
  (testing ""
    (build/uber {})
    (is true)))

(deftest ^:slow test-build-docker-image []
  (testing ""
    (process/shell "make docker-build-and-load"
                   {:err (logging/log-stream :info *ns*)
                    :out (logging/log-stream :info *ns*)})
    (is true)))


(deftest ^:slow test-using-docker-image []
  (testing ""
    (let [uid (.trim (:out (process/sh "id -u")))
          gid (.trim (:out (process/sh "id -g")))]
      (process/shell "make docker-build-and-load"
                     {:err (logging/log-stream :info *ns*)
                      :out (logging/log-stream :info *ns*)})
      (test-utils/with-temp-workspace
        (process/shell (str "docker run --rm --user " uid ":" gid " -v ./:/cerulean/workspace cerulean:latest init")
                       {:dir config/*workspace*
                        :err (logging/log-stream :error *ns*)
                        :out (logging/log-stream :info *ns*)})
        (process/shell (str "docker run --rm --user " uid ":" gid " -v ./:/cerulean/workspace cerulean:latest build")
                       {:dir config/*workspace*
                        :err (logging/log-stream :error *ns*)
                        :out (logging/log-stream :info *ns*)})
        (test-utils/verify-build-results config/*workspace*)))))
