(ns com.dillius.lifesci.general
  (:require [com.dillius.lifesci.reference :refer :all]))


(defn normalizeToMetric
  [value units]
  (apply convertMetricNumber
         (convertImperialValue value)
         units))

(defn dosingCalc
  [weight dose form]
  [(format "%.3f" (with-precision
                      20
                    (/ (with-precision 20 (*
                                           (bigdec (first (normalizeToMetric weight [:kilo])))
                                           (bigdec (first (normalizeToMetric dose [:milli :kilo])))))
                       (bigdec (first (normalizeToMetric (take 2 form) [:milli]))) ; we don't normalize /denom to keep the per units from formulation
                       )))
   (nth form 2)]
  )

(defn fluidRate
  [weight multi maintenance]
  (let [timeMulti (if (= :day (first (nth maintenance 3 nil))) 24M 1M)]
    [(format "%.3f"
             (with-precision 20
               (*
                (bigdec multi)
                (/ 1M (bigdec timeMulti))
                (with-precision 20 (*
                                    (bigdec (first (normalizeToMetric weight [:kilo])))
                                    (bigdec (first (normalizeToMetric maintenance [:milli :kilo])))
                                    )))))
     [:milli :liter] [:hour]])
  )

(defn dripRate
  [fluidRate givingSet] ; ml/hour drops/ml
  [(format "%.1f"
           (with-precision 20
             (/
              (with-precision 20 (*
                                  (bigdec (first fluidRate))
                                  (bigdec (first givingSet))
                                  ))
              60M ; hour -> minute
              )))
   [:drop] [:minute]]
  )
