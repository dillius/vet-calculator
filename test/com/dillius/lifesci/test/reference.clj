(ns com.dillius.lifesci.test.reference
  (:use clojure.test
        ring.mock.request
        com.dillius.lifesci.reference))

(deftest test-app
  (testing "number parse test"
    (let [result (String->Number "12.33")]
      (is (= 12.33 result)))
    )

  (testing "vector and keywordize units"
    (let [result (vectorKeyUnits "base gram")]
      (is (= [:base :gram] result)))
    )

  (testing "pounds to kilograms 1"
    (let [result (convertImperialValue [1.8144 [:pound]])])
    )
  )
