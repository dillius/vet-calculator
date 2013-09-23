(ns com.dillius.lifesci.test.reference
  (:use clojure.test
        ring.mock.request
        com.dillius.lifesci.reference))

(deftest test-app
  (testing "pounds to kilograms 1"
    (let [result (convertImperialValue [1.8144 [:pound]])])
    )
  )
