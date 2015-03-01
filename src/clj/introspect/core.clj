(ns introspect.core
  (:import [introspect IntrospectProfilingAgent Interceptor])
  (:require [introspect.stacktrace :as stacktrace]))


(def method-calls (atom {}))

(def format-signature #(str "(" (clojure.string/join " -> " %) ")"))
(def join-with-newline #(clojure.string/join "\n\t" %))

(defn get-type
  [a]
  (if (fn? a)
    clojure.lang.IFn
    (type a)))

(defn introspect-function
  [class-name args return-value]
  (let [current-call-types  (conj (map get-type args) (type return-value))
        previous-call-types (get @method-calls class-name)]
    ;; (println current-call-types)
    (when (and (not (empty? previous-call-types))
               (nil? (get previous-call-types current-call-types))
               ;; TODO: check for subclassing
               )
      (println
       (str
        "\nDetected unusual method call: "
        (class class-name)
        " was called with arguments \n\t"
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

(defn introspect-namespace
  [namespace-sym]
  (IntrospectProfilingAgent/initializeAgent (-> namespace-sym
                                                name
                                                (.replace "-" "_")))
  (compile namespace-sym)
  ;; v.ns.name.name.replace('.', '/').replace('-','_') + "$" + munge(v.sym.name);
  )

(Interceptor/setCallback introspect-function)
