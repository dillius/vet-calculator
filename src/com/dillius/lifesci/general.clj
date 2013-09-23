(ns com.dillius.lifesci.general
  (:require [com.dillius.lifesci.reference :refer :all]))


(defn normalizeKilo
  "Converts a value to kilo-metric if possible."
  [value]
  (-> value convertImperialValue (convertMetricNumber :kilo)))

(defn normalizeMilliPerKilo
  [value]
  (-> value convertImperialValue (convertMetricNumber :milli :kilo)))

(defn normalizeMilliPerMilli
  [value]
  (-> value convertImperialValue (convertMetricNumber :milli :milli)))

(defn dosingCalc
  [weight dose form]
  [(with-precision
       5
     (/ (*
         (first (normalizeKilo weight))
         (first (normalizeMilliPerKilo dose)))
        (first (normalizeMilliPerMilli form))))
   [:milli :liter]]
  )
