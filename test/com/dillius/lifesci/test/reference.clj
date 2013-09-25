(ns com.dillius.lifesci.test.reference
  (:use clojure.test
        ring.mock.request
        com.dillius.lifesci.reference))

(deftest test-app
  (testing "parse-int test"
    (let [result (parse-int "123wut")]
      (is (= 123 result)))
    )

  (testing "vector and keywordize units"
    (let [result (vectorKeyUnits "base gram")]
      (is (= [:base :gram] result)))
    )

  (testing "pounds to kilograms 1"
    (let [result (convertImperialValue [1.8144 [:pound]])])
    )
  )
