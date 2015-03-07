(defproject introspect "1.0.0-SNAPSHOT"
  :description       "FIXME: write description"
  :url               "http://example.com/FIXME"
  :license           {:name "Eclipse Public License"
                        :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :test-paths        ["test/clj" "test/java"]
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java" "test/java"]
  :aot               [introspect.core]

  :manifest          {"Premain-class"           "introspect.Premain"
                      "Agent-class"             "introspect.Premain"
                      "Can-Redefine-Classes"    "true"
                      "Can-Retransform-Classes" "true"}

  :jvm-opts          ["-noverify"]

  :profiles          {:test {:dependencies [[org.clojure/tools.nrepl "0.2.7"]]
                             :jvm-opts     ["-noverify"
                                            ~(str
                                              "-javaagent:"
                                              (System/getProperty "user.dir")
                                              "/target/introspect-1.0.0-SNAPSHOT-standalone.jar=introspect.helpers.simple-functions")]
                               }}
  :dependencies      [[org.clojure/clojure            "1.6.0"]
                      [net.bytebuddy/byte-buddy-agent "0.5.3"]
                      [net.bytebuddy/byte-buddy       "0.5.3"]]
)
