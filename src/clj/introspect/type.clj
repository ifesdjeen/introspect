(ns introspect.type
  (:require [clojure.set :as s]))

(defn same-arity?
  "Checks whether two given calls have same arity"
  [input1 input2]
  (= (count input1)
     (count input2)))

(def same-type? =) ;; Semantic alias

(defn subtype?
  "Checks if inst2 is a subtype of inst1"
  [inst1 inst2]
  (let [ancestors (ancestors inst2)]
    (not (nil? (get ancestors inst1)))))

(defn any-subtype?
  [inst1 inst2]
  (or (subtype? inst1 inst2)
      (subtype? inst2 inst1)))

(defn have-common-ancestor?
  "Checks if two classes have a common ancestor"
  [inst1 inst2]
  (let [ancestors1 (disj (ancestors inst1) Object java.io.Serializable)
        ancestors2 (disj (ancestors inst2) Object java.io.Serializable)]
    (not (empty? (s/intersection ancestors1 ancestors2)))))

(defn every-pair?
  [matcher coll1 coll2]
  (every? identity (map matcher coll1 coll2)))

(defn call-types-match?
  [current-call-args prev-calls]
  (some
   (fn [previous-call-types]
     (and (same-arity? current-call-args previous-call-types)
          (or (every-pair? same-type? current-call-args previous-call-types)
              (every-pair? subtype? current-call-args previous-call-types)
              (every-pair? any-subtype? current-call-args previous-call-types)
              (every-pair? have-common-ancestor? current-call-args previous-call-types))))
   prev-calls))
