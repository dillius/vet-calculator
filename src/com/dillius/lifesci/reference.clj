(ns com.dillius.lifesci.reference)

(def imperialConversion
  (hash-map
   [:inch :3] (fn [value] (identity [(* value 16.387) [:centi :meter :3]]))
   [:foot :3] (fn [value] (identity [(* value 0.0283) [:base :meter :3]]))
   [:fluidounce] (fn [value] (identity[(* value 28.413) [:milli :liter]]))
   [:fluidounce :us] (fn [value] (identity[(* value 29.574) [:milli :liter]]))
   [:pint] (fn [value] (identity [(* value 0.5683) [:base :liter]]))
   [:pint :us] (fn [value] (identity [(* value 0.4731) [:base :liter]]))
   [:gallon] (fn [value] (identity [(* value 4.5461) [:base :liter]]))
   [:gallon :us] (fn [value] (identity [(* value 3.7854) [:base :liter]]))

   [:ounce] (fn [value] (vector (* value 28.35) [:base :gram]))
   [:pound] (fn [value] (vector (* value 0.4536) [:kilo :gram]))))

(defn conversionUnits
  [amount units]
  (if (nil? (imperialConversion units))
      (vector amount units)
      ((imperialConversion units) amount)))

(defn conversionDivisor
  [per]
  (if (nil? per)
    nil
    (if (nil? (imperialConversion per))
      (vector 1 per)
      ((imperialConversion per) 1))))

(defn convertImperialValue
  [value]
  (let [amount (first value)
        units (second value)
        per (nth value 2 nil)
        upper (conversionUnits amount units)
        lower (conversionDivisor per)
        ]
    (if (nil? lower)
      upper
      (vector (/ (first upper) (first lower)) (second upper) (second lower)))))

(comment (convertImperialValue [4 [:fluidounce] [:pound]]))

(def metricRates
  (hash-map
   :kilo 1000
   :hecto 100
   :deca 10
   :base 1
   :deci 0.1
   :centi 0.01
   :milli 0.001
   :micro 0.000001
   :nano 0.000000001
   :pico 0.000000000001
   ))

(defn changeMetricPrefix
  [amount curr next]
  (* amount (/ (metricRates curr) (metricRates next))))

(defn convertMetricNumber
  ([value over]
     (filter identity
             (vector
              (changeMetricPrefix (first value) (first (second value)) over)
              (vector over (second (second value)))
              (nth value 2 nil))))
  ([value over under]
     (vector
      (/
       (changeMetricPrefix (first value) (first (second value)) over)
       (changeMetricPrefix 1 (first (nth value 2)) under))
      (vector over (second (second value)))
      (vector under (second (nth value 2)))
      ))
   )
