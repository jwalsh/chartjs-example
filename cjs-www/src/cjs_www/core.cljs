(ns cjs-www.core
  (:require [reagent.core :as r]
            [cljsjs.chartjs]
            [schema.core :as s :include-macros true]
            [secretary.core :as secretary :refer-macros [defroute]]
            ))

(enable-console-print!)

(println "src/cjs-www/core.cljs")

;; State

(def app-state (r/atom
                {
                 :password nil
                 :text "Charts and DataSets"
                 }
                ))

(defonce title (r/atom "Charts and DataSets"))

(def click-count (r/atom 0))

;; Utilities

(defn password-valid?
  "Valid if password is greater than 5 characters"
  [password]
  (> (count password) 5))

(defn password-color [password]
  (let [valid-color "green"
        invalid-color "red"]
    (if (password-valid? password)
      valid-color
      invalid-color)))

;; Components

(defn plain-component []
  [:p "Login: " @title])

(defn header-component []
  (r/create-class {:reagent-render (fn []
                                     [:div
                                      [:h2 title]])}))

(defn simple-component []
  [:div
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn profile-component []
  [:div
   [:h2 "Profile"]])

(defn password-component []
  [:input {:type "password"
           :on-change #(swap! app-state assoc :password (-> % .-target .-value))}])

(defn home-component []
  [:div {:style {:margin-top "30px"}}
   "Please enter a password greater than 5 characters. "
   [:span {:style {:padding "20px"
                   :background-color (password-color (@app-state :password))}}
    [password-component]
    ]])

(defn counting-component []
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button" :value "Click me!"
            :on-click #(swap! click-count inc)}]])
(defn app []
  [:div {:style {:padding "40px"}}
   [:h1 (:text @app-state)]
   [:h3 "Login"]
   [:form {:action "http://localhost:3000/login"
           :method :put
           :style {:width "480px" :padding "40px" :border "1px solid #5555"}}
    [:label "Username"]
    [:input.form-control {:type :text :id :username}]
    [:label "Password"]
    [:input.form-control {:type :text :id :password}]
    [:hr]
    [:input {:type :submit}]]
   [simple-component]
   [counting-component]
   ])

(r/render-component [app]
                    (. js/document (getElementById "app")))

(defn show-revenue-chart
  []
  (let [context (.getContext (.getElementById js/document "rev-chartjs") "2d")
        chart-data {:type "bar"
                    :data {:labels ["2012" "2013" "2014" "2015" "2016"]
                           :datasets [{:data [5 10 15 20 25]
                                       :label "Rev in MM"
                                       :backgroundColor "#90EE90"}
                                      {:data [3 6 9 12 15]
                                       :label "Cost in MM"
                                       :backgroundColor "#F08080"}]}}]
    (js/Chart. context (clj->js chart-data))))

(show-revenue-chart)

(defn rev-chartjs-component
  []
  (r/create-class
   {:component-did-mount #(show-revenue-chart)
    :display-name        "chartjs-component"
    :reagent-render      (fn []
                           [:canvas {:id "rev-chartjs" :width "640" :height "480"}])}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
