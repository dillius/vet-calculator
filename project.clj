(defproject life-science-ref "0.1.0-SNAPSHOT"
  :description "Veterinary Calculator"
  :url "http://www.vetcalculator.com"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [cheshire "5.0.2"]
                 [clj-time "0.6.0"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.slf4j/slf4j-log4j12 "1.6.6"]
                 [ring/ring-jetty-adapter "1.2.0"]]
  :aot [com.dillius.lifesci.main]
  :main com.dillius.lifesci.main
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler com.dillius.lifesci.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}}
  :uberjar-name "vet-calculator.jar"
  )
