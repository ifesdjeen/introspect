(ns introspect.core
  (:import [introspect IntrospectProfilingAgent Interceptor]
           [java.lang.instrument Instrumentation])
  (:require [introspect.stacktrace :as stacktrace]
            [introspect.type       :as itype]
            [clojure.set           :as s]))

(defn format-signature
  [types]
  (str "("
       (->> types
            (map #(.getName %))
            (clojure.string/join " -> "))
       ")"))

(def join-with-newline #(clojure.string/join "\n\t" %))

;; (def method-calls (atom {}))

(def ^:dynamic *print-stacktraces* false)

(let [method-calls (atom {})]
  (defn introspect-function
    [class-name args return-value]
    (let [current-call-types  (conj (map type args) (type return-value))
          previous-call-types (get @method-calls class-name)]
      ;; (println current-call-types)
      (when (and (not (empty? previous-call-types))
                 (nil? (get previous-call-types current-call-types))
                 ;; TODO: check for subclassing
                 )
        (println
         (str
          "\nDetected unusual method call: "
          (.getName (class class-name))
          " was called with arguments \n\t"
          (format-signature current-call-types)
          "\nprevious calls were with\n\t"
          (join-with-newline (map format-signature previous-call-types))))
        (when *print-stacktraces*
          (println "\nStacktrace:\n"
                   (stacktrace/format-stack-trace (.getStackTrace (Thread/currentThread))))))

      (swap! method-calls
             update-in
             [class-name]
             #(conj (or % #{}) current-call-types))))

  (defn get-method-calls
    []
    @method-calls)

  (defn t
    [f]
    (doseq [call (get @method-calls f)]
      (println (format-signature call))))

  (Interceptor/setCallback introspect-function)
  )

(defonce introspected-namespaces (atom #{}))
(defn introspect-namespace
  [namespace-sym]
  (when (not (get introspected-namespaces namespace-sym))
    (println (str "Introspecting " (name namespace-sym)))
    (swap! introspected-namespaces conj namespace-sym)

    (IntrospectProfilingAgent/initializeAgent (-> namespace-sym
                                                  name
                                                  (.replace "-" "_")))
    (compile namespace-sym))
  ;; v.ns.name.name.replace('.', '/').replace('-','_') + "$" + munge(v.sym.name);
  )


(gen-class
 :name    introspect.Premain
 :methods [#^{:static true} [premain [String java.lang.instrument.Instrumentation] void]])

(defn -premain
  [agent-args instrumentation]
  (println " _       _                                 _ ")
  (println "(_)_ __ | |_ _ __ ___  ___ _ __   ___  ___| |_")
  (println "| | '_ \\| __| '__/ _ \\/ __| '_ \\ / _ \\/ __| __|")
  (println "| | | | | |_| | | (_) \\__ \\ |_) |  __/ (__| |_")
  (println "|_|_| |_|\\__|_|  \\___/|___/ .__/ \\___|\\___|\\__|")
  (println "                          |_|                  ")

  (binding [*compile-path* (str (System/getProperty "user.dir") "/target/classes")]
    (doseq [n (clojure.string/split agent-args #";")]
      (introspect-namespace (symbol n)))))
