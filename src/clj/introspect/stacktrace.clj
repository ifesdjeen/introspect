(ns introspect.stacktrace)

(defn format-stack-frame
  [el max-class-name max-file-name]
  (let [class-name  (.getClassName el)
        file-name   (or (.getFileName el) "")
        line-number (.getLineNumber el)]
    (format (str "\t%"
                 max-class-name
                 "s\t%"
                 max-file-name
                 "s:\t%20d")
            class-name
            file-name
            line-number)))

(defn format-stack-trace
  [trace]
  (let [trace-els      trace
        max-class-name (->> trace-els
                            (map #(.getClassName %))
                            (map count)
                            (reduce max))
        max-file-name  (->> trace-els
                            (map #(or (.getFileName %) ""))
                            (map count)
                            (reduce max))]
    (->> trace-els
         (map #(format-stack-frame % max-class-name max-file-name))
         (clojure.string/join "\n"))))
