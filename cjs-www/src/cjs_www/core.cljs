(ns cjs-www.core
  (:require [reagent.core :as r :refer [atom]]
            [cljsjs.chartjs]
            ))

(enable-console-print!)

(println "src/cjs-www/core.cljs")
(println c)
;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Charts and DataSets"}))

(defonce title (r/atom "Charts and DataSets"))

(defn plain-component []
  [:p "Login: " @title])

(defn app []
  [:div
   [:h1 (:text @app-state)]
   [:h3 "Login"]
   [:label "Username"]
   [:input.form-control {:field :text :id :username}]
   [:label "Password"]
   [:input.form-control {:field :text :id :password}]
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

(defn rev-chartjs-component
  []
  (r/create-class
    {:component-did-mount #(show-revenue-chart)
     :display-name        "chartjs-component"
     :reagent-render      (fn []
                            [:canvas {:id "rev-chartjs" :width "700" :height "380"}])}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
