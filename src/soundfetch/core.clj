(ns soundfetch.core
  (:gen-class)
  (:use [compojure.route :only [not-found]]
        [compojure.handler :only [site]]
        [compojure.core :only [defroutes GET POST]]
        soundfetch.utils
        org.httpkit.server)
  (:require [soundfetch.fetcher :as fetcher]
            [ring.middleware.reload :as reload]))


(defn like-handler [req]
  "Takes a request map, returns a response map"

  (let [song-id (:songId (:params req))]
    (when (not-nil? song-id)
      (fetcher/fetch-song song-id)))

  "Thanks!")

(defroutes all-routes
  (GET "/like" [] like-handler)
  (not-found "<p>Page not found</p>"))


;; server stuff

(def dev? true)
(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn -main [& args]
  (let [handler (if dev?
                  (reload/wrap-reload (site #'all-routes)) ;; only reload when dev
                  (site all-routes))]
    (println "running server on port 8080...")
    (reset! server (run-server handler {:port 8080}))))
