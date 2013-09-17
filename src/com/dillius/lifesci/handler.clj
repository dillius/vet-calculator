(ns com.dillius.lifesci.handler
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer :all]))


(defn mainPage
  [name]
  (str "Hello World, " name))

(defroutes app-routes
  (GET "/" [] (mainPage "asshole"))
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
