(defproject introspect "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :java-source-paths ["src/java"]

  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]

  :manifest {"Premain-class" "introspect.Premain"
             "Agent-class" "introspect.Premain"
             "Can-Redefine-Classes" "true"
             "Can-Retransform-Classes" "true"}

  :dependencies [[net.bytebuddy/byte-buddy-agent "0.5.2"]
                 [net.bytebuddy/byte-buddy "0.5.2"]])
