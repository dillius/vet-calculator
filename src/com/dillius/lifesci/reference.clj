(ns com.dillius.lifesci.reference)

; Imperial conversion

(def imperialConversion
  (hash-map
   [:inch :3] (fn [value] (identity [(* value 16.387M) [:centi :meter :3]]))
   [:foot :3] (fn [value] (identity [(* value 0.0283M) [:base :meter :3]]))
   [:fluidounce] (fn [value] (identity[(* value 28.413M) [:milli :liter]]))
   [:fluidounce :us] (fn [value] (identity[(* value 29.574M) [:milli :liter]]))
   [:pint] (fn [value] (identity [(* value 0.5683M) [:base :liter]]))
   [:pint :us] (fn [value] (identity [(* value 0.4731M) [:base :liter]]))
   [:gallon] (fn [value] (identity [(* value 4.5461M) [:base :liter]]))
   [:gallon :us] (fn [value] (identity [(* value 3.7854M) [:base :liter]]))

   [:ounce] (fn [value] (vector (* value 28.35M) [:base :gram]))
   [:pound] (fn [value] (vector (* value 0.4536M) [:kilo :gram]))))

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

; Metric conversion

(def metricRates
  (hash-map
   :kilo 1000M
   :hecto 100M
   :deca 10M
   :base 1M
   :deci 0.1M
   :centi 0.01M
   :milli 0.001M
   :micro 0.000001M
   :nano 0.000000001M
   :pico 0.000000000001M
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
