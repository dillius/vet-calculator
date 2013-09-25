(ns com.dillius.lifesci.main
  (:use ring.adapter.jetty)
  (:require [com.dillius.lifesci.handler :as handler])
  (:gen-class))

(defn -main [& [port]]
  (let [port (Integer. (or port (System/getenv "PORT") 3010))]
    (run-jetty
     #'handler/app
     {:port port :join? false})))
