(ns chart-api.api.default
  (:require [chart-api.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn login-post-with-http-info
  "
  Allow users to log in and get a JWT token"
  ([] (login-post-with-http-info nil))
  ([{:keys [body ]}]
   (call-api "/login" :post
             {:path-params   {}
              :header-params {}
              :query-params  {}
              :form-params   {}
              :body-param    body
              :content-types ["application/json"]
              :accepts       ["application/json"]
              :auth-names    []})))

(defn login-post
  "
  Allow users to log in and get a JWT token"
  ([] (login-post nil))
  ([optional-params]
   (:data (login-post-with-http-info optional-params))))

(defn outcomes-id-report-get-with-http-info
  "Outcome reports"
  ([id ] (outcomes-id-report-get-with-http-info id nil))
  ([id {:keys [date-range ]}]
   (check-required-params id)
   (call-api "/outcomes/{id}/report" :get
             {:path-params   {"id" id }
              :header-params {}
              :query-params  {"dateRange" date-range }
              :form-params   {}
              :content-types ["application/json"]
              :accepts       ["application/json"]
              :auth-names    ["JWT"]})))

(defn outcomes-id-report-get
  "Outcome reports"
  ([id ] (outcomes-id-report-get id nil))
  ([id optional-params]
   (:data (outcomes-id-report-get-with-http-info id optional-params))))

(defn root-get-with-http-info
  "Overview of available links"
  []
  (call-api "/" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types ["application/json"]
             :accepts       ["application/json"]
             :auth-names    []}))

(defn root-get
  "Overview of available links"
  []
  (:data (root-get-with-http-info)))

