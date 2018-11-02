(ns valimailer.api
  (:require [mailgun.mail :as mail]
            [valimailer.db :as db]
            [valimailer.config :as config]))

(defn send-email
  [to template-path]
  (let [html (slurp template-path)]
    (println "Emailing" to)
    (mail/send-mail config/creds {:from config/email
                                  :to to
                                  :subject config/subject
                                  :html html})))

(defn burst-email-all
  [template-path page]
  (when (< page (db/get-page-count))
    (let [user-page (db/get-users-paginated page)]
      (println (str "Emailing page " page " of " (db/get-page-count)))
      (doseq [user user-page]
        (send-email (:email user) template-path)
        (Thread/sleep config/ratelimit))
      (recur template-path (inc page)))))
