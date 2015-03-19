(ns introspect.util
  (:require [clojure.walk :as walk]))

(def any-seq? #(or (seq? %)
                   (vector? %)
                   (set? %)))

(defn to-json-representation
  "Recursively transforms all map keys from strings to keywords."
  {:added "1.1"}
  [m]
  (let [to-s (fn to-s [i]
               (cond
                (= java.lang.Class (type i)) (.getName i)
                (string? i)                  (str "\"" i "\"")
                (any-seq? i)                 (mapv to-s i)
                (nil? i)                     "nil"
                :else                        (throw (RuntimeException. (str "Not sure how I would translate "
                                                                            i
                                                                            " that to json")))))
        f (fn [[k v]] [(to-s k) (to-s v)])]
    ;; only apply to maps
    (clojure.walk/postwalk (fn [x]
                             (cond
                              (map? x) (into {} (map f x))
                              :else    x))
                           m)))
