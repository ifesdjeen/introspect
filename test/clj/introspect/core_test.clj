(ns introspect.core-test
  (:import [introspect.helpers FooChildA FooChildB ParentClass ChildClass])
  (:require [clojure.test :refer :all]
            [introspect.core :refer :all]
            [testing.helpers.simple-functions :as h1]))

(deftest t-test
  (is (= 2 (h1/constant-return-value-fn 1 1)))
  (is (= 2 (h1/constant-return-value-fn "1" "1")))

  (let [types (set
               (clojure.string/split (with-out-str
                                       (t h1/constant-return-value-fn))
                                     #"\n"))]
    (is (not (nil? (get types  "(java.lang.Long -> java.lang.Long -> java.lang.Long)"))))
    (is (not (nil? (get types  "(java.lang.String -> java.lang.String -> java.lang.Long)"))))))


(deftest protocols-test
  (let [inst (h1/make-some-type)]
    (is (= 2 (h1/somemethod inst (java.util.Date.) (java.util.Date.))))
    (is (= "str2" (h1/somemethod2 inst 1.121 1.0123 1.123123)))


    (is (= "(java.util.Date -> java.util.Date -> java.lang.Long)\n"
           (with-out-str
             (t (class inst) "somemethod"))))

    (is (= "(java.lang.Double -> java.lang.Double -> java.lang.Double -> java.lang.String)\n"
           (with-out-str
             (t (class inst) "somemethod2"))))))
