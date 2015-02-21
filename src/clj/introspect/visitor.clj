(ns introspect.visitor
  (:gen-class
   :name    introspect.Visitor
   :methods [#^{:static true} [methodEntered [String] void]
             #^{:static true} [methodEntered [String Object] void]
             #^{:static true} [methodEntered [String Object Object] void]
             #^{:static true} [methodEntered [String Object Object Object] void]
             #^{:static true} [methodLeft    [String Object] void]]))


(def time-stack (atom ()))

(def spaces (atom 0))

(defn -methodEntered
  ([class-name]
     (println "no args" class-name))
  ([class-name arg1]
     (println arg1 class-name))
  ([class-name arg1 arg2]
     (println arg1 arg2 class-name))
  ([class-name arg1 arg2 arg3]
     (println arg1 arg2 arg3 class-name))
  )

(defn -methodLeft
  [class-name a1]
  (println "RETR")
  (println "retr:" a1))
