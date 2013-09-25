(ns com.dillius.lifesci.general
  (:require [com.dillius.lifesci.reference :refer :all]))


(defn normalizeToMetric
  [value units]
  (apply convertMetricNumber
         (convertImperialValue value)
         units))

(defn dosingCalc
  [weight dose form]
  [(with-precision
       5
     (/ (*
         (first (normalizeToMetric weight [:kilo]))
         (first (normalizeToMetric dose [:milli :kilo])))
        (first (normalizeToMetric (take 2 form) [:milli])) ; we don't normalize /denom to keep the per units from formulation
        ))
   (nth form 2)]
  )
