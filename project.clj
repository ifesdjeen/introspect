(defproject introspect "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj"]
  :java-source-paths ["src/java"]
  :aot [introspect.visitor]
  ;; :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]

  :manifest {"Premain-class"           "introspect.IntrospectProfilingAgent"
             "Agent-class"             "introspect.IntrospectProfilingAgent"
             "Can-Redefine-Classes"    "true"
             "Can-Retransform-Classes" "true"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.ow2.asm/asm     "5.0"]
])
