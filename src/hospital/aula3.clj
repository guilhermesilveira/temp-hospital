(ns hospital.aula3
  (:use [clojure pprint])
  (:require [hospital.logic :as logic]
            [hospital.model :as model]))


;; Entao agora temos como limpar
;
(defn chega-em! [hospital departamento pessoa]
  (println departamento pessoa)
  (swap! hospital logic/chega-em departamento pessoa))

(defn testa-atomo-real! []
  (def hospital-azevedo (atom (model/new-hospital)))
  (doseq [pessoa (range 1 6)]
    (.start (Thread. (fn [] (chega-em! hospital-azevedo :laboratorio3 pessoa)))))
  (.start (Thread. (fn []
                     (Thread/sleep 4000)
                     (pprint hospital-azevedo))))
  )

;(testa-atomo-real!)

(defn testa-atomo-real! []
  (def hospital-azevedo (atom (model/new-hospital)))
  (dotimes [pessoa 6]
    (.start (Thread. (fn [] (chega-em! hospital-azevedo :laboratorio3 pessoa)))))
  (.start (Thread. (fn []
                     (Thread/sleep 4000)
                     (pprint hospital-azevedo))))
  )

;(testa-atomo-real!)



