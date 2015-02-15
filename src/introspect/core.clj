(ns introspect.core
  (:import [net.bytebuddy ByteBuddy]
           [net.bytebuddy.agent ByteBuddyAgent]
           [net.bytebuddy.matcher ElementMatchers]
           [net.bytebuddy.instrumentation MethodDelegation]
           [net.bytebuddy.dynamic ClassLoadingStrategy ClassLoadingStrategy$Default]
           [net.bytebuddy.dynamic.loading ClassReloadingStrategy]
           [introspect LoggingAFn]
           [clojure.lang AFn]))

(defn asd
  []
  (+ 1 2 ))

(defn a
  []
  (fn [a b] (+ a b)))

(def b (a))
;;
(defn init-byte-buddy
  []
  (ByteBuddyAgent/installOnOpenJDK)
  (comment
    (let [byte-buddy (ByteBuddy.)]
      (-> byte-buddy
          (.redefine AFn)
          (.method (ElementMatchers/named "invoke"))
          (.intercept (MethodDelegation/to IFnInterceptor))
          (.make)
          (.load (.getClassLoader AFn) (ClassLoadingStrategy/fromInstalledAgent)) ;; (ClassLoadingStrategy$Default/INJECTION)
          )
      ))

  ;; It should be also possible to combine both redefinition and that one
  (let [byte-buddy (ByteBuddy.)]
    (-> byte-buddy
        (.redefine LoggingAFn)
        (.name (.getName AFn))
        (.make)
        ;; (.load (.getClassLoader AFn) (ClassReloadingStrategy/fromInstalledAgent))
        (.load (ClassLoader/getSystemClassLoader) (ClassReloadingStrategy/fromInstalledAgent))
        )
    )
  )


;; http://docs.oracle.com/javase/7/docs/api/java/lang/ClassLoader.html
;; (.getName (type b))
