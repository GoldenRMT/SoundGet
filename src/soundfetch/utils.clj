(ns soundfetch.utils)

(def not-nil? (complement nil?))

(defn abs-path [path]
  (let [home (System/getProperty "user.home")]
    (clojure.string/replace path #"^~" home)))

(defn write-to-file! [path content]
  (with-open [w (clojure.java.io/output-stream (abs-path path))]
    (.write w content)))
