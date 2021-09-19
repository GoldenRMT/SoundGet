(ns soundfetch.fetcher
  (:require [org.httpkit.client :as http]
            [cheshire.core :as json])
  (:use [soundfetch.utils]))

(def soundcloud-client-id "79147fc595b1b8b227e5440c6bb6a6a3")
(def download-to "~/Music/")

(defn get-song-info [song-id]
  (let [url (str "http://api.soundcloud.com/tracks/" song-id ".json")
        options {:query-params {:client_id soundcloud-client-id}}]

    (let [{:keys [status headers body error] :as resp} @(http/get url options)]
      (if error
        (println "Failed, exception is " error)
        (do
          (println "Synchronous HTTP GET: " status url)
          (json/parse-string body))))))

(defn download-song [song-info path]
  (let [url (get song-info "stream_url")
        options {:query-params {:client_id soundcloud-client-id}
                 :as :byte-array}]

    (let [{:keys [status headers body error opts] :as resp} @(http/get url options)]
      (if error
        (println "Failed, exception is " error)
        (do
          (println "Downloaded song from: " url status headers error)
          (write-to-file! path body))))))

(defn- permalink [song-info]
  (str (get (get song-info "user") "permalink") "/" (get song-info "permalink")))

(defn- song-file-name [song-info]
  (clojure.string/replace (permalink song-info) #"/" "-"))

(defn fetch-song [song-id]
  (let [song-info (get-song-info song-id)
        song-path (str download-to (song-file-name song-info) ".mp3")]

    (download-song song-info song-path)))
