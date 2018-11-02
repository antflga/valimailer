(ns valimailer.config
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.pprint :refer [pprint]]))

(def ^{:private true} default-config
  {:db           {:dbtype   "mysql"
                  :dbname   "db name"
                  :host     "host"
                  :user     "user"
                  :password "pass"}
   :creds        {:key    "a-key-here"
                  :domain "foo.bar"}
   :email        "email@goes.here"
   :subject      "default subject"
   :ratelimit-ms 1000})

(defn- read-or-init-config []
  (try
    (let [config  (slurp "config.edn")
          content (edn/read-string config)]
      (if-not (= content default-config)
        content
        (do
          (println "Cannot use default config. Please edit config.edn")
          (System/exit 0))))
    (catch java.io.FileNotFoundException e
      (println "No config file detected, generating example config.edn.")
      (println "You need to edit this file to suit your needs.")
      (spit "config.edn" (with-out-str (pprint default-config)))
      (System/exit 0))))

(def config (read-or-init-config))

(def db (:db config))

(def creds (:creds config))

(def email (:email config))

(def subject (:subject config))

(def ratelimit (:ratelimit-ms config))
