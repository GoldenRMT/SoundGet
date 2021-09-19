(ns soundfetch.utils-test
  (:require [clojure.test :refer :all]
            [soundfetch.utils :refer :all]))

(deftest not-nil-test
  (is (not-nil? :a))
  (is (not-nil? 1)
  (is (not-nil? "hallelujah")
  (is (not (not-nil? nil))))))
