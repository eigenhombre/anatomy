(defproject anatomy "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.4"]]
  :main ^:skip-aot anatomy.core
  :target-path "target/%s"
  :uberjar-name "muscles.jar"
  :profiles {:uberjar {:aot :all}})
