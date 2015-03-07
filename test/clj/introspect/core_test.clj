(ns introspect.core-test
  (:import [introspect.helpers FooChildA FooChildB ParentClass ChildClass])
  (:require [clojure.test :refer :all]
            [introspect.core :refer :all]
            [introspect.helpers.simple-functions :as h1]))

(deftest t-test
  (h1/constant-return-value-fn 1 1)
  (h1/constant-return-value-fn "1" "1")

  (let [types (set
               (clojure.string/split (with-out-str
                                       (t h1/constant-return-value-fn))
                                     #"\n"))]
    (not (nil? (get types  "(java.lang.Long -> java.lang.Long -> java.lang.Long)")))
    (not (nil? (get types  "(java.lang.Long -> java.lang.String -> java.lang.String)")))))


(deftest protocols-test
  (println (h1/somemethod (h1/make-some-type) 1 1))
  (println (h1/somemethod (h1/make-some-type) "1" "1"))

  )
