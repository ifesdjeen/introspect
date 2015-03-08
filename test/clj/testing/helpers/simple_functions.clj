(ns testing.helpers.simple-functions)


(defprotocol SomeProtocol
  (somemethod [this a b]))

(deftype SomeType []
  SomeProtocol
  (somemethod [this a b]
    2))

(defn make-some-type
  [] (SomeType.))

(defn constant-return-value-fn
  [input-1 input-2]
  2)
