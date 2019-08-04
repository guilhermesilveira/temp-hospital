(ns hospital.aula2
  (:use [clojure pprint])
  (:require [hospital.logic :as logic]
            [hospital.model :as model]))


; atomão

(defn testa-atomo []
  (def hospital-silveira (atom {}))
  (println "silveira" hospital-silveira)
  (println "silveira" (deref hospital-silveira))
  (println "silveira" @hospital-silveira)

  (swap! hospital-silveira assoc :laboratorio1 model/empty_queue)
  (println "silveira" @hospital-silveira)

  (swap! hospital-silveira assoc :espera model/empty_queue :laboratorio1 model/empty_queue)
  (println "silveira" @hospital-silveira)

  (swap! hospital-silveira update-in [:espera] conj "guilherme")
  (println "silveira" @hospital-silveira)
  (println (pprint @hospital-silveira))
  )

;(testa-atomo)





; mostrar agora um atomão vai controlar isso


; TODO controller!!! (sugestao). ou db que é so transacao no banco:
; chega-em-pausado! db... testa-atomo-pausado --> controller. mas db chamar logica eh bizarro. cuidado. por isso
(defn chega-em-pausado! [hospital departamento pessoa]
  (swap! hospital logic/chega-em-pausado-com-log departamento pessoa))

(defn entra! [hospital pessoa]
  (chega-em-pausado! hospital :laboratorio3 pessoa)
  (println "Inserted" pessoa)
  )




(defn testa-atomo-pausado []
  (def hospital-azevedo (atom (model/new-hospital)))
  (.start (Thread. (fn [] (entra! hospital-azevedo 1))))
  (.start (Thread. (fn [] (entra! hospital-azevedo 2))))
  (.start (Thread. (fn [] (entra! hospital-azevedo 3))))
  (.start (Thread. (fn [] (entra! hospital-azevedo 4))))
  (.start (Thread. (fn [] (entra! hospital-azevedo 5))))
  (.start (Thread. (fn [] (entra! hospital-azevedo 6))))
  (.start (Thread. (fn []
                     (Thread/sleep 4000)
                     (pprint hospital-azevedo))))
  )

;(testa-atomo-pausado)

