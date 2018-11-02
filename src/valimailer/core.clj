(ns valimailer.core
  (:require [clojure.java.io :as io]
            [valimailer.db :as db]
            [valimailer.api :as api]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-a" "--all" "Send to all users"]
   ["-u" "--user-id ID" "Send to specific user by their user_id"
    :parse-fn #(Integer/parseInt %)
    :validate [#(some? (db/get-user-by-id %))]]
   ["-e" "--email EMAIL" "Send to a specific email"]
   ["-h" "--help"]])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{options    :options
         [template] :arguments
         summary    :summary
         errors     :errors} (parse-opts args cli-options)
        {all     :all
         user-id :user-id
         email   :email
         help    :help}  options]
    (if (or (not= nil help) (= nil all user-id email))
      (println summary)
      (if (and (some? template) (.exists (io/as-file template)))
        (cond
          (not= nil user-id)    (api/send-email (:email (db/get-user-by-id user-id)) template)
          (not= nil email)      (api/send-email email template)
          (not= nil all)        (api/burst-email-all template 0)
          (not (empty? errors)) (doseq [e errors] (println e)))
        (println "No such file exists.")))))
