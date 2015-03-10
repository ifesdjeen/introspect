(ns introspect.core-test
  (:import [introspect.helpers FooChildA FooChildB ParentClass ChildClass])
  (:require [clojure.test :refer :all]
            [introspect.core :refer :all]
            [testing.helpers.simple-functions :as h1]))

(comment
  (deftest t-test
    (is (= 2 (h1/constant-return-value-fn 1 1)))
    (is (= 2) (h1/constant-return-value-fn "1" "1"))

    (let [types (set
                 (clojure.string/split (with-out-str
                                         (t h1/constant-return-value-fn))
                                       #"\n"))]
      (is (not (nil? (get types  "(java.lang.Long -> java.lang.Long -> java.lang.Long)"))))
      (is (not (nil? (get types  "(java.lang.Long -> java.lang.String -> java.lang.String)")))))))


(deftest protocols-test
  (let [t (h1/make-some-type)]
    (println (h1/somemethod t (java.util.Date.) (java.util.Date.)))
    (println (h1/somemethod2 t 1.121 1.0123 1.123123))
    )
  ;; (h1/somemethod (h1/make-some-type) 1 1)
  ;; (println (h1/somemethod (h1/make-some-type) "1" "1"))
  (println (get-method-calls)) ;; (t h1/somemethod)
  )
