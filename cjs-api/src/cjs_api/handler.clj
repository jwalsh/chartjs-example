(ns cjs-api.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [ring.util.http-status :as http-status]
            [chart-api.core :as c]
            [schema.core :as s]))

(s/defschema Token
  {:token s/Str})

(s/defschema Login
  {
   :username s/Str
   :password s/Str
   })

(s/defschema User
  {
   :username s/Str
   :name s/Str
   })

(def users (atom {}))


(let [ids (atom 0)]
  (defn update-user! [maybe-id maybe-user]
    (let [id (or maybe-id (swap! ids inc))]
      (if maybe-user
        (swap! users assoc id (assoc maybe-user :id id))
        (swap! users dissoc id))
      (@users id))))


(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Chart API"
                    :description "Chart API "}
             :tags [{:name "api", :description "DataFrame and Charts"}]}}}

    (context "/users" []
             (resource
              {:tags ["user"]
               :get {:summary "get users"
                     :description "get all users"
                     :responses {http-status/ok {:schema [User]}}
                     :handler (fn [_] (ok (vals @users)))}
               :post {:summary "adds a user"
                      :parameters {:body-params User}
                      :responses {http-status/created {:schema User
                                                       :description "the created user"
                                                       :headers {"Location" s/Str}}}
                      :handler (fn [{body :body-params}]
                                 (let [{:keys [id] :as user} (update-user! nil body)]
                                   (created (path-for ::user {:id id}) user)))}}))

    (context "/users/:id" []
      :path-params [id :- s/Int]

      (resource
        {:tags ["user"]
         :get {:x-name ::user
               :summary "gets a user"
               :responses {http-status/ok {:schema User}}
               :handler (fn [_]
                          (if-let [user (@users id)]
                            (ok user)
                            (not-found)))}
         :put {:summary "updates a user"
               :parameters {:body-params User}
               :responses {http-status/ok {:schema User}}
               :handler (fn [{body :body-params}]
                          (if (@users id)
                            (ok (update-user! id body))
                            (not-found)))}
         :delete {:summary "deletes a user"
                  :handler (fn [_]
                             (update-user! id nil)
                             (no-content))}}))
    (context "/api" []
      :tags ["api"]

      (GET "/plus" []
        :return {:result Long}
        :query-params [x :- Long, y :- Long]
        :summary "adds two numbers together"
        (ok {:result (+ x y)}))

      (GET "/" []
        :return {}
        :summary "Links"
        (ok {:result ({})}))

      (GET "/report" []
        :return {}
        :summary "Links"
        (ok {:result ({})}))

      (POST "/login" []
        :return Token
        :body [login Login]
        :summary "JWT Token"
        (ok login))
     )))
