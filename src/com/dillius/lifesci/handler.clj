(ns com.dillius.lifesci.handler
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer :all]
            [ring.util.response :as resp]
            [clojure.walk :refer :all]
            [cheshire.core :refer :all]
            [com.dillius.lifesci.reference :refer [parse-int vectorKeyUnits]]
            [com.dillius.lifesci.general]))


(defn dosageCalc
  [params]
  (generate-string
   {:result
    (com.dillius.lifesci.general/dosingCalc
     [(parse-int (:weight params))
      (vectorKeyUnits (:weightUnit params))]
     [(parse-int (:dose params))
      (vectorKeyUnits (:doseUnit params))
      (vectorKeyUnits (:doseUnitPer params))]
     [(parse-int (:formulation params))
      (vectorKeyUnits (:formulationUnit params))
      (vectorKeyUnits (:formulationUnitPer params))]
     )}))

(defroutes app-routes
  (GET "/" [] (resp/resource-response "index.html" {:root "public"}))

  (GET "/api/dosageCalc" {:keys [params]} (dosageCalc params))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes)
)
