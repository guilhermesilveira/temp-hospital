(ns hospital.aula1
  (:use [clojure pprint])
  (:require [hospital.logic :as logic]
            [hospital.model :as model]))

;; O HORROR DOS REDEFS!!!
(defn simula-um-dia []

  (def hospital (model/new-hospital))

  ; sempre do namespace! (global mas no namespace, multithread por padrao, root binding)
  (println "dia1")
  (def hospital (logic/chega-em hospital :laboratorio2 "222"))
  (def hospital (logic/chega-em hospital :laboratorio2 "444"))
  (def hospital (logic/chega-em hospital :laboratorio2 "555"))
  (def hospital (logic/chega-em hospital :espera "1"))
  (def hospital (logic/chega-em hospital :laboratorio1 "333"))
  (pprint hospital)

  (println "dia2")
  (def hospital (logic/atende hospital :laboratorio1))
  (def hospital (logic/atende hospital :laboratorio2))
  (pprint hospital)

  (println "dia3")
  (def hospital (logic/chega-em hospital :espera "2"))
  (def hospital (logic/chega-em hospital :espera "3"))
  (def hospital (logic/chega-em hospital :espera "4"))
  (def hospital (logic/chega-em hospital :espera "5"))
  (pprint hospital)

  (println "dia4")
  (def hospital (logic/chega-em hospital :espera "6"))
  (pprint hospital)

  )

;(simula-um-dia)




; MUKLTI THREAD AGORA ZOADO

(defn entra [pessoa]
  (def hospital (logic/chega-em-pausado hospital :laboratorio3 pessoa))
  (println "Inserted" pessoa)
  )

(defn paused-simulation []
  (pprint "Starting a new simulation")
  (def hospital (model/new-hospital))

  (.start (Thread. (fn [] (entra 1))))
  (.start (Thread. (fn [] (entra 2))))
  (.start (Thread. (fn [] (entra 3))))
  (.start (Thread. (fn [] (entra 4))))
  (.start (Thread. (fn [] (entra 5))))
  (.start (Thread. (fn [] (entra 6))))
  (.start (Thread. (fn []
                     (Thread/sleep 4000)
                     (pprint hospital))))
  (println (pprint hospital))

  )
;(paused-simulation)




