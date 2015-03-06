(ns introspect.core-test
  (:import [introspect.helpers FooChildA FooChildB ParentClass ChildClass])
  (:require [clojure.test :refer :all]
            [introspect.core :refer :all]
            [introspect.helpers.simple-functions :as h1]))

(deftest a-test
  (h1/constant-return-value-fn 1 1)
  (h1/constant-return-value-fn "1" "1")

  (is (= "(java.lang.Long -> java.lang.String -> java.lang.String)
(java.lang.Long -> java.lang.Long -> java.lang.Long)\n"
         (with-out-str
           (t h1/constant-return-value-fn)))))
