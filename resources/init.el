;; init.el --- Description -*- lexical-binding: t; -*-
(require 'org)
(require 'json)
(require 'backtrace)
(require 'url-util)

(setq org-html-head ""
      org-html-head-extra ""
      org-html-head-include-default-style nil
      org-html-head-include-scripts nil
      org-html-preamble nil
      org-html-postamble nil
      org-html-use-infojs nil
      org-export-body-only t
      org-export-with-toc nil
      org-export-with-latex t
      org-html-html5-fancy t
      org-html-doctype "html5"
      org-html-toplevel-hlevel 2
      org-export-with-section-numbers nil
      org-export-headline-levels 6
      org-latex-compiler "xelatex"
      org-preview-latex-default-process 'dvisvgm)

(defun eval-string (str)
  (eval (car (read-from-string str))))

(defun hash (&rest args)
  (let ((result (make-hash-table :test #'equal)))
    (cl-loop for (key value) on args by #'cddr while value
             do (puthash key value result))
    result))

(defun org->html (file)
  (save-window-excursion
    (find-file file)
    (let ((blog-buffer (current-buffer)))
      (unwind-protect
          (let* ((keywords '("title" "category"
                             "tags" "email"
                             "language"
                             "author" "description"
                             "orgx_require" "orgx"
                             "unlisted" "date" "published_date" "modified_date"))
                 (kvs (org-collect-keywords keywords)))
            (with-current-buffer blog-buffer

              (let ((ids (org-map-entries (lambda ()
                                            (org-entry-get nil "custom_id")))))
                (org-map-entries (lambda ()
                                   (if (not (org-entry-get nil "custom_id"))
                                       (let* ((candidate-id (concat
                                                             (url-encode-url
                                                              (string-replace " " "-"
                                                                              (downcase
                                                                               (nth 4
                                                                                    (org-heading-components)))))))
                                              (duplicates (cl-count candidate-id ids :test #'equal)))
                                         (org-entry-put nil "custom_id" (concat candidate-id
                                                                                (if (= duplicates 0)
                                                                                    ""
                                                                                  (prin1-to-string (+ 1 duplicates)))))
                                         (setq ids (cons candidate-id
                                                         ids))))
                                   (setq ids (cons (org-entry-get nil "custom_id")
                                                   ids))))))
            (append
             (mapcar (lambda (kv)
                       (cons (intern (downcase (car kv)))
                             (cadr kv)))
                     kvs)
             `((content .
                        ,(progn
                           (org-html-export-as-html nil nil nil t)
                           (buffer-string))))))
        (kill-buffer blog-buffer)
        (kill-buffer (current-buffer))))))

(defun cerulean--export-uix-advice (oldfun special-block contents info)
  (let ((block-type (upcase (org-element-property :type special-block))))
    (cond ((string= block-type "ORGX")
           (format "<pre class=\"orgx\" %s>\n%s\n</pre>"
                   (if (org-element-property :attr_orgx_toplevel special-block)
                       "data-toplevel"
                     "")
                   (buffer-substring (org-element-property :contents-begin special-block)
                                     (org-element-property :contents-end special-block))))
          ((string-match-p "\\`ORGX_[^z-a]"
                           block-type)
           (if (org-element-property :attr_orgx_defui special-block)
               (format "<pre class=\"orgx\" data-wrapper=\"use-defui\" data-wrapper-data=\"%s\">\n%s\n</pre>"
                       (substring (downcase block-type)
                                  5)
                       contents)
             (format "<pre class=\"orgx\" data-wrapper=\"use-comp\" data-wrapper-data=\"%s\">\n%s\n</pre>"
                     (substring (downcase block-type)
                                5)
                     contents)))
          (t (funcall oldfun special-block contents info)))))

(defun cerulean--export-snippet-uix-advice (oldfun export-snippet _contents _info)
  (if (eq (org-export-snippet-backend export-snippet) 'orgx)
	  (format "<code class=\"orgx\">\n%s\n</code>"
              (org-element-property :value export-snippet))
    (funcall oldfun export-snippet _contents _info)))

(advice-add 'org-html-special-block :around #'cerulean--export-uix-advice)
(advice-add 'org-html-export-snippet :around #'cerulean--export-snippet-uix-advice)

(defun main ()
  (cl-loop
   (let ((message (read-string "")))
     (condition-case e
         (let* ((request (json-parse-string message  :array-type 'list))
                (jsonrpc (gethash "jsonrpc" request))
                (method (gethash "method" request))
                (params (gethash "params" request))
                (id (gethash "id" request)))
           (let ((result
                  (cl-block 'exec
                    (handler-bind ((error
                                    (lambda (e)
                                      (cl-return-from 'exec
                                        (list 'error
                                              (list 'message (error-message-string e)
                                                    'backtrace (backtrace-to-string (backtrace-get-frames 'backtrace))))))))
                      (list 'result (apply (symbol-function (intern method))
                                           params))))))
             (princ (json-serialize
                     (list 'jsonrpc "2.0"
                           'result result
                           'id id)))
             (princ "\n")))
       (error (message (prin1-to-string e) )
              (message "\n"))))))

(defun cerulean--reload-init ()
  "For develop purpose only"
  (find-file (concat (getenv "PWD") "/init.el"))
  (unwind-protect
      (eval-buffer (current-buffer))
    (kill-buffer (current-buffer))))
;; (org->html-to-stdout (concat default-directory "blogs/demo.org"))
