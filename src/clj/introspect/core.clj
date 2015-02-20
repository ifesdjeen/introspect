(ns introspect.core
  (:gen-class)
  (:import [net.bytebuddy ByteBuddy]
           [net.bytebuddy.agent ByteBuddyAgent]
           [net.bytebuddy.matcher ElementMatchers]
           [net.bytebuddy.instrumentation MethodDelegation SuperMethodCall]

           [net.bytebuddy.dynamic ClassLoadingStrategy ClassLoadingStrategy$Default]
           [net.bytebuddy.dynamic.loading ClassReloadingStrategy]
           [introspect LoggingAFn IFnInterceptor MainTest FnImpl]
           [clojure.lang AFn]))

(defn asd
  []
  (+ 1 2))

(defn asd2
  [a b]
  (+ a b))

(defn a
  []
  (let [zz 1]
    (fn [a b] (+ a b zz))))

(def b (a))

(defn c [a b c] (str a b c))
;;
;; http://docs.oracle.com/javase/7/docs/api/java/lang/ClassLoader.html
;; (.getName (type b))

;; First, load:
;; (MainTest/initialize "introspect.core")
;; (compile 'introspect.core)

(defn -main [& args]
  (compile 'introspect.core)
  (MainTest/initialize "introspect.core")
  (compile 'introspect.core)
  (println (asd2 1 2)))
