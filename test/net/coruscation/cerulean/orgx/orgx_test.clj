(ns net.coruscation.cerulean.orgx.orgx-test
  (:require
   [cemerick.pomegranate :as pome]
   [clojure.java.io :as io]
   [clojure.test :refer [deftest is testing]]
   [clojure.tools.logging :as log]
   [hickory.core :as hc]
   [kaocha.classpath :as kaocha.classpath]
   [net.coruscation.cerulean.config :refer [join-workspace-path]]
   [net.coruscation.cerulean.orgx.orgx :as sut :refer [remove-extra-newline]]
   [net.coruscation.cerulean.server.utils :refer [add-to-classpath path-join]]
   [net.coruscation.cerulean.test-constants :refer [resources-dir]]
   [net.coruscation.cerulean.test-utils :refer [with-temp-workspace]]))

(defonce test-resources-dir (path-join resources-dir "orgx-test"))

(defn html->hiccup [htmlstr]
  (->> (hc/parse-fragment htmlstr)
       (mapv hc/as-hiccup)))

(deftest from-html-test
  (testing
      (let [[[ele] toplevels] (sut/from-html "<pre class=\"orgx\">  ($ :button {:on-click (fn [&amp; _] (js/alert &quot;clicked!&quot;))} &quot;Click Me&quot;) </pre>")]
        (is (= (str ele)
               "($ :button {:on-click (fn [& _] (js/alert \"clicked!\"))} \"Click Me\")"))
        (is (not (seq toplevels))))))

(deftest toplevesl-test
  (testing
      (let [[_ toplevels] (sut/from-html "<pre class=\"orgx\" data-toplevel> (defui demo []) </pre> ")]
        (is (= (str '((defui demo [])))
               (str toplevels))))))

(deftest load-orgx-test
  (testing
      (with-temp-workspace
        (let [cl (.getContextClassLoader (Thread/currentThread))]
          (.setContextClassLoader (Thread/currentThread) (clojure.lang.DynamicClassLoader. cl)))
        (spit (io/file (path-join (doto (io/file (sut/get-orgx-dest-dir))
                                    (.mkdirs))
                                  "demo.cljc"))
              (slurp (io/file (path-join test-resources-dir
                                         "demo_orgx.cljc"))))
        (add-to-classpath (join-workspace-path "./.cljc/"))
        (kaocha.classpath/add-classpath (join-workspace-path "./.cljc/"))
        (log/warn (pome/get-classpath))
        (require (symbol "orgx.demo") :reload)
        (is (= 10 (var-get (intern (find-ns (symbol "orgx.demo"))
                                   'a)))))))

(deftest remove-extra-newline-test
  (is (= [[:p {} [:b {} "hello"]]]
         (remove-extra-newline (html->hiccup "<p>
<b>hello</b>
</p>"))))
  (is (= [[:pre {} "\n" [:b {} "hello"] "\n"]]
         (remove-extra-newline (html->hiccup "<pre>

<b>hello</b>
</pre>")))))
