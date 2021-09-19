(defproject soundfetch "0.1.0-SNAPSHOT"
  :description "Automatically download Soundcloud tracks as you like them"
  :url "http://github.com/goldsmith/soundfetch"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[javax.servlet/servlet-api "2.5"]
                 [org.clojure/clojure "1.5.1"]
                 [compojure "1.3.1"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-devel "1.3.2"]
                 [http-kit "2.1.18"]
                 [cheshire "5.4.0"]]
  :main ^:skip-aot soundfetch.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
