(ns com.dillius.lifesci.handler
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer :all]
            [ring.util.response :as resp]
            [clojure.walk :refer :all]
            [cheshire.core :refer :all]
            [com.dillius.lifesci.reference :refer [String->Number vectorKeyUnits]]
            [com.dillius.lifesci.general]))


(defn dosageCalc
  [params]
  (generate-string
   {:result
    (com.dillius.lifesci.general/dosingCalc
     [(String->Number (:weight params))
      (vectorKeyUnits (:weightUnit params))]
     [(String->Number (:dose params))
      (vectorKeyUnits (:doseUnit params))
      (vectorKeyUnits (:doseUnitPer params))]
     [(String->Number (:formulation params))
      (vectorKeyUnits (:formulationUnit params))
      (vectorKeyUnits (:formulationUnitPer params))]
     )}))

(defn flowRates
  [params]
  (let [fluidRate (com.dillius.lifesci.general/fluidRate
                   [(String->Number (:weight params))
                    (vectorKeyUnits (:weightUnit params))
                    ]
                   (String->Number (:multiplier params))
                   [(String->Number (:maintenance params))
                    [:milli :liter]
                    (vectorKeyUnits (:maintenanceUnitPer params))
                    (vectorKeyUnits (:maintenanceUnitPerPer params))
                    ]
                   (String->Number (:additionalAmount params))
                   )
        dripRate (com.dillius.lifesci.general/dripRate
                  [(String->Number (first fluidRate))
                   (second fluidRate)
                   (nth fluidRate 2 nil)]
                  [(String->Number (:givingSet params))
                   [:drop]
                   [:milli :liter]]
                  )
        ]
    (generate-string
     {:resultFluidRate fluidRate
      :resultDripRate dripRate
      })))

(defroutes app-routes
  (GET "/" [] (resp/resource-response "index.html" {:root "public"}))

  (GET "/api/dosageCalc" {:keys [params]} (dosageCalc params))
  (GET "/api/flowRates" {:keys [params]} (flowRates params))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes)
)
