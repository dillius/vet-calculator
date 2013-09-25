(ns com.dillius.lifesci.test.general
  (:use clojure.test
        ring.mock.request
        com.dillius.lifesci.general))

(deftest test-app
  (testing "dosing calculation easy"
    (let [result
          (dosingCalc
           [1 [:kilo :gram]]
           [10 [:milli :gram] [:kilo :gram]]
           [5 [:milli :gram] [:milli :liter]]
           )]
      (is (= result [2M [:milli :liter]]))))

  (testing "dosing calculation off-metric"
    (let [result
          (dosingCalc
           [1250 [:base :gram]]
           [20 [:milli :gram] [:kilo :gram]]
           [5 [:milli :gram] [:base :liter]]
           )]
      (is (= result [5.000M [:base :liter]]))))

  (testing "dosing calculation weight in pounds"
    (let [result
          (dosingCalc
           [4 [:pound]]
           [10 [:milli :gram] [:kilo :gram]]
           [5 [:milli :gram] [:milli :liter]]
           )]
      (is (= result [3.6288M [:milli :liter]]))))

  (testing "dosing calculation decimals"
    (let [result
          (dosingCalc
           [3 [:kilo :gram]]
           [2.5 [:milli :gram] [:kilo :gram]]
           [1 [:milli :gram] [:milli :liter]]
           )]
      (is (= result [7.5 [:milli :liter]]))))

  )
