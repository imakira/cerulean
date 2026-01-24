((nil . ((cider-clojure-cli-global-options . "-A:dev:uberjar")
         (cider-preferred-build-tool       . clojure-cli)
         (cider-default-cljs-repl          . custom)
         (cider-test-default-exclude-selectors . ("integration" "flakey" "slow"))
         (cider-custom-cljs-repl-init-form . "(do (require 'net.coruscation.cerulean.shadow-dev) (net.coruscation.cerulean.shadow-dev/cljs-repl))")
         (eval . (progn
                   (make-variable-buffer-local 'cider-jack-in-nrepl-middlewares)
                   (add-to-list 'cider-jack-in-nrepl-middlewares "shadow.cljs.devtools.server.nrepl/middleware"))))))
