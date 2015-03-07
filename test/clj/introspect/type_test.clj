(ns introspect.type-test
  (:import [introspect.helpers FooChildA FooChildB ParentClass ChildClass])
  (:require [clojure.test :refer :all]
            [introspect.type :refer :all]))

(deftest have-common-ancestor-test
  (is (have-common-ancestor? (type (FooChildA.))
                             (type (FooChildB.)))))

(deftest subtype-test
  (is (subtype? (type (ParentClass.))
                (type (ChildClass.))))
  (is (any-subtype? (type (ParentClass.))
                    (type (ChildClass.))))
  (is (any-subtype? (type (ChildClass.))
                    (type (ParentClass.))))
  )

(deftest call-types-match-test
  (is (call-types-match? [Integer Integer Integer]
                         #{[Integer Integer Integer]}))

  (is (not (call-types-match? [Integer Integer]
                              #{[Integer Integer Integer]})))

  (is (call-types-match? [(type (fn []))]
                         #{[(type (fn []))]}))

  (is (not (call-types-match? [(type (fn []))]
                              #{[Integer]})))

  (is (call-types-match? [(type (fn []))]
                         #{[(type (fn []))]}))

  (is (call-types-match? [(type (fn []))]
                         #{[(type (fn []))]}))

  (is (call-types-match? [(type (ParentClass.)) (type (ParentClass.))]
                         #{[(type (ChildClass.)) (type (ChildClass.))]}
                         ))

  (is (call-types-match? [(type (ChildClass.)) (type (ChildClass.))]
                         #{[(type (ParentClass.)) (type (ParentClass.))]}
                         ))


  (is (call-types-match? [(type (ParentClass.)) (type (ChildClass.))]
                         #{[(type (ParentClass.)) (type (ChildClass.))]}
                         )))
