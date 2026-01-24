(ns net.coruscation.cerulean.server.server-test
  (:require
   [clojure.java.io :as io]
   [clojure.test :refer [deftest is use-fixtures]]
   [net.coruscation.cerulean.cli :refer [init-workspace]]
   [net.coruscation.cerulean.config :as config]
   [net.coruscation.cerulean.orgx.orgx :as orgx]
   [net.coruscation.cerulean.server.emacs-ipc :as eipc]
   [net.coruscation.cerulean.server.server :as subject]
   [net.coruscation.cerulean.server.utils :refer [path-join]]
   [net.coruscation.cerulean.test-utils :refer [ensure-dynamic-classloader-fixture
                                                with-temp-workspace]]))

(deftest ensure-orgx-watch!-test
  (with-temp-workspace
    (try (.mkdirs (io/file config/*blog-dir*))
         (eipc/init-emacs!)
         (init-workspace)
         (subject/ensure-orgx-watch! config/*blog-dir*)
         (.sleep java.util.concurrent.TimeUnit/SECONDS 1)
         (spit (io/file (path-join config/*blog-dir*
                                   "demo.org"))
               "#+ORGX: true")
         (.sleep java.util.concurrent.TimeUnit/SECONDS 1)
         (is (.exists (io/file (path-join (orgx/get-orgx-dest-dir)
                                          "demo.cljc"))))
         (finally
           (subject/stop-orgx-watch! config/*blog-dir*)))))

(use-fixtures :once ensure-dynamic-classloader-fixture)
