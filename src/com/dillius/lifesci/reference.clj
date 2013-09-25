(ns com.dillius.lifesci.reference
  (:require [clojure.string]))

(defn String->Number [str]
  (let [n (read-string str)]
       (if (number? n) n nil)))

(defn vectorKeyUnits
  [words]
  (apply vector (map keyword (clojure.string/split words #" "))))


; Imperial conversion

(def imperialRateAndMetricUnit
  (hash-map
   [:inch :3] [16.387M [:centi :meter :3]]
   [:foot :3] [0.0283M [:base :meter :3]]
   [:fluidounce] [28.413M [:milli :liter]]
   [:fluidounce :us] [29.574M [:milli :liter]]
   [:pint] [0.5683M [:base :liter]]
   [:pint :us] [0.4731M [:base :liter]]
   [:gallon] [4.5461M [:base :liter]]
   [:gallon :us] [3.7854M [:base :liter]]

   [:ounce] [28.35M [:base :gram]]
   [:pound] [0.4536M [:kilo :gram]]))

(defn conversionUnits
  [amount units]
  (let [impConv (imperialRateAndMetricUnit units)]
    (if (nil? impConv)
      (vector amount units)
      (vector (* amount (first impConv)) (second impConv)))))

(defn conversionDivisor
  [per]
  (if (nil? per)
    nil
    (let [impConv (imperialRateAndMetricUnit per)]
      (if (nil? impConv)
        (vector 1 per)
        (vector (first impConv) (second impConv))))))

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
