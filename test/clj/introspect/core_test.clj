(ns introspect.core-test
  (:require [clojure.test :refer :all]
            [introspect.core :refer :all]

            [introspect.helpers.simple-functions :as h1]))

(deftest a-test
  (introspect-namespace 'introspect.helpers.simple-functions)

  (h1/constant-return-value-fn 1 1)
  (h1/constant-return-value-fn "1" "1")
  )
