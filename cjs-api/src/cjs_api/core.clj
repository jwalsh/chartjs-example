(ns cjs-api.core
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [cheshire.core :as json]
            [compojure.core :refer [defroutes ANY]]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})


(defroutes app
  (ANY "/" [] (resource :available-media-types ["text/html"]
                        :handle-ok (fn [ctx]
                                     (format "%d"
                                             (System/currentTimeMillis)))))
 (ANY "/reports/:id" [id] (resource  id)))


(def handler
  (-> app
      wrap-params))
