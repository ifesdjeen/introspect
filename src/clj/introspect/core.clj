(ns introspect.core
  (:import [introspect IntrospectProfilingAgent Interceptor]
           [java.lang.instrument Instrumentation])
  (:require [introspect.stacktrace :as stacktrace]
            [introspect.type       :as itype]
            [clojure.set           :as s]
            [introspect.util       :as util]))

(defn format-signature
  [types]
  (str "("
       (->> types
            (filter identity)
            (map #(.getName %))
            (clojure.string/join " -> "))
       ")"))

(def join-with-newline #(clojure.string/join "\n\t" %))

;; (def method-calls (atom {}))

(def ^:dynamic *print-stacktraces* false)

(alter-var-root #'*out* (constantly *out*))

(let [method-calls (atom {})]
  (defn introspect-function
    [caller method args return-value]
    (let [descriptor          [(-> caller .getClass) (.getName method)]
          current-call-types  (conj (mapv type args) (type return-value))
          previous-call-types (get @method-calls descriptor)]

      (if (or (empty? previous-call-types)
              (not (itype/call-types-match? current-call-types previous-call-types)))
        (swap! method-calls
               update-in
               [descriptor]
               #(conj (or % #{}) current-call-types)))))


  (defn get-method-calls
    []
    @method-calls)

  (defn format-report
    "Formats multivariate function call report"
    []
    (->> @method-calls
         (filter #(> (count (second %)) 1))
         (map (fn [[[klass method-name] argument-calls]]
                (format "Multivariate Function Calls: `%s` \n\t\t%s\n"
                        (if (= "invoke" method-name)
                          klass
                          (format "%s$%s" klass method-name))
                        (clojure.string/join "\n\t\t"
                                             (map format-signature argument-calls)))))
         (clojure.string/join "\n")))

  (defn dump-path
    []
    (str (System/getProperty "user.dir") "/types.dump"))

  (defn dump-report!
    []
    (with-open [stream (clojure.java.io/writer (java.io.File. (dump-path)))]
      (binding [*out*       stream
                *print-dup* true]
        (prn @method-calls))))

  (defn restore-report!
    []
    (with-open [r (java.io.PushbackReader. (java.io.FileReader. (dump-path)))]
      (reset! method-calls (read r))))


  (defn t
    ([f-name]
       (t (.getClass f-name) "invoke"))
    ([klass method]
       (when (empty? @method-calls)
         (println "No calls logged, please make sure you've turned on the agent..."))

       (let [calls (get @method-calls [klass method])]
         (if (not (empty? calls))
           (doseq [call calls]
             (println (format-signature call)))
           (println "No calls found"))
         ))))

(Interceptor/setCallback introspect-function)


(defonce introspected-namespaces (atom #{}))
(defn introspect-namespace
  [namespace]
  (when (not (get introspected-namespaces namespace))
    (println (str "Introspecting " namespace))
    (swap! introspected-namespaces conj namespace)

    (IntrospectProfilingAgent/initializeAgent (-> namespace
                                                  name
                                                  (.replace "-" "_")))
    (let [namespace-sym (symbol namespace)]
      (compile namespace-sym)))
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
      (introspect-namespace n)))

  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. #(do
                                (dump-report!)
                                (println (format-report))))))
