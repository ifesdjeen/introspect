(ns introspect.visitor
  (:require [introspect.stacktrace :as stacktrace])
  (:gen-class
   :name    introspect.Visitor
   :methods [#^{:static true} [methodEntered [String] void]
             #^{:static true} [methodEntered [String  Object] void]
             #^{:static true} [methodEntered [String  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object  Object  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object  Object  Object  Object  Object
                                              Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object] void]
             #^{:static true} [methodEntered [String  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object  Object  Object  Object  Object
                                              Object  Object  Object  Object  Object] void]

             #^{:static true} [methodLeft    [String Object] void]]))



(def method-calls (atom {}))

(def format-signature #(str "(" (clojure.string/join " " %) ")"))
(def join-with-newline #(clojure.string/join "\n\t" %))

(defn generic-method-entered
  [class-name args]
  (let [current-call-types  (map type args)
        previous-call-types (get @method-calls class-name)]
    (when (and (not (empty? previous-call-types))
               (nil? (get previous-call-types current-call-types)))
      (println

       (str
        "\nDetected unusual method call:"
        class-name
        "was called with arguments \n\t"
        (format-signature current-call-types)
        "\nprevious calls were with\n\t"
        (join-with-newline (map format-signature previous-call-types))
        "\nStacktrace:\n")
       (stacktrace/format-stack-trace (.getStackTrace (Thread/currentThread)))))

    (swap! method-calls
           update-in
           [class-name]
           #(conj (or % #{}) current-call-types))
    ))


(defn -methodEntered
  ([class-name]
     (generic-method-entered class-name []))
  ([class-name arg1]
     (generic-method-entered class-name [arg1]))
  ([class-name arg1 arg2]
     (generic-method-entered class-name [arg1 arg2]))
  ([class-name arg1 arg2 arg3]
     (generic-method-entered class-name [arg1 arg2 arg3]))
  ([class-name arg1 arg2 arg3 arg4]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4]))
  ([class-name arg1 arg2 arg3 arg4 arg5]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9 arg10 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9 arg10]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9 arg10 arg11 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9 arg10 arg11]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9 arg10 arg11 arg12]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9 arg10 arg11 arg12]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9 arg10 arg11 arg12 arg13 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9 arg10 arg11 arg12 arg13]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9 arg10 arg11 arg12 arg13 arg14 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9 arg10 arg11 arg12 arg13 arg14]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9 arg10 arg11 arg12 arg13 arg14 arg15 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9 arg10 arg11 arg12 arg13 arg14 arg15]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9 arg10 arg11 arg12 arg13 arg14 arg15
    arg16]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9 arg10 arg11 arg12 arg13 arg14 arg15
                                         arg16]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9 arg10 arg11 arg12 arg13 arg14 arg15
    arg16 arg17 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9 arg10 arg11 arg12 arg13 arg14 arg15
                                         arg16 arg17]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9 arg10 arg11 arg12 arg13 arg14 arg15
    arg16 arg17 arg18 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9 arg10 arg11 arg12 arg13 arg14 arg15
                                         arg16 arg17 arg18]))
  ([class-name arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
    arg9 arg10 arg11 arg12 arg13 arg14 arg15
    arg16 arg17 arg18 arg19 ]
     (generic-method-entered class-name [arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8
                                         arg9 arg10 arg11 arg12 arg13 arg14 arg15
                                         arg16 arg17 arg18 arg19]))
  )

(defn -methodLeft
  [class-name ret]
  ;; (println class-name "return value was" ret)
  )


;; (Runtime/getRuntime )



;; (println (format-stack-trace (.getStackTrace (Thread/currentThread))))
