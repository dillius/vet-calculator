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
      (is (= result ["2.000" [:milli :liter]]))))

  (testing "dosing calculation off-metric"
    (let [result
          (dosingCalc
           [1250 [:base :gram]]
           [20 [:milli :gram] [:kilo :gram]]
           [5 [:milli :gram] [:base :liter]]
           )]
      (is (= result ["5.000" [:base :liter]]))))

  (testing "dosing calculation weight in pounds"
    (let [result
          (dosingCalc
           [4 [:pound]]
           [10 [:milli :gram] [:kilo :gram]]
           [5 [:milli :gram] [:milli :liter]]
           )]
      (is (= result ["3.629" [:milli :liter]]))))

  (testing "dosing calculation decimals"
    (let [result
          (dosingCalc
           [3 [:kilo :gram]]
           [2.5 [:milli :gram] [:kilo :gram]]
           [1 [:milli :gram] [:milli :liter]]
           )]
      (is (= result ["7.500" [:milli :liter]]))))


  (testing "fluid rate easy" ; ml/hour
    (let [result
          (fluidRate
           [3 [:kilo :gram]]
           1
           [5 [:milli :liter] [:kilo :gram] [:hour]] ; per Hour
           )]
      (is (= result ["15.000" [:milli :liter] [:hour]]))))

    (testing "drip rate easy" ; drop/min
    (let [result
          (dripRate
           [15 [:milli :liter] [:hour]]
           [5 [:drop] [:milli :liter]] ; per Hour
           )]
      (is (= result ["1.250" [:drop] [:minute]]))))

  )
