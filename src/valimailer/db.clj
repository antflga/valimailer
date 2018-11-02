(ns valimailer.db
  (:require [honeysql.core :as sql]
            [clojure.java.jdbc :as jdbc]
            [valimailer.config :refer [db]]))

(defn get-all-users []
  (let [sqlmap {:select [:*]
                :from [:xf_user]}
        query (sql/format sqlmap)]
    (jdbc/query db query)))

(defn get-user-by-id [id]
  (let [sqlmap {:select [:*]
                :from [:xf_user]
                :where [:= :user_id id]}
        query (sql/format sqlmap)]
    (first (jdbc/query db query))))

(defn get-all-user-ids []
  (let [sqlmap {:select [:user_id]
                :from [:xf_user]}
        query (sql/format sqlmap)]
    (map :user_id (jdbc/query db query))))

(defn count-users []
  (let [sqlmap    {:select [:%count.*]
                   :from   [:xf_user]}
        query     (sql/format sqlmap)
        [_ count] (-> (jdbc/query db query)
                      first
                      vec
                      first)]
    count))

(defn get-page-count []
  (/ (count-users) 10))

(defn get-users-paginated [page]
  (let [pages (get-page-count)]
    (if (and (> pages 0) (> page pages))
      (get-users-paginated pages)
      (let [offset (* 10 page)
            sqlmap {:select [:*]
                    :from   [:xf_user]
                    :limit  10
                    :offset offset}
            query  (sql/format sqlmap)]
        (jdbc/query db query)))))
