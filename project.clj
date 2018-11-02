(defproject valimailer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [honeysql "0.9.4"]
                 [nilenso/mailgun "0.2.3"]
                 [org.clojure/tools.cli "0.4.1"]]
  :main ^:skip-aot valimailer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
