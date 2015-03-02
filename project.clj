(defproject introspect "0.1.0-SNAPSHOT"
  :description       "FIXME: write description"
  :url               "http://example.com/FIXME"
  :license           {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :test-paths        ["test/clj"]
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java"]

  :dependencies      [[org.clojure/clojure "1.6.0"]
                      [net.bytebuddy/byte-buddy-agent "0.6-SNAPSHOT"]
                      [net.bytebuddy/byte-buddy "0.6-SNAPSHOT"]])
