(ns testing.helpers.simple-functions)


(defprotocol SomeProtocol
  (somemethod [this a b])
  (somemethod2 [this a b c])
  )

(deftype SomeType []
  SomeProtocol
  (somemethod [this a b]
    2)

  (somemethod2 [this a b c]
    "str2")
  )

(defn make-some-type
  [] (SomeType.))

(defn constant-return-value-fn
  [input-1 input-2]
  2)
