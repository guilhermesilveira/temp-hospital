(ns hospital.aula5
  (:use [clojure pprint])
  (:require [hospital.logic :as logic]
            [hospital.model :as model]))


;; citar que travamos o hospital inteiro toda vez que tem uma transferencia, usamos entao varios atomos, vantagem/desvantagem
;; isto é, pessoas  estao sendo deslocadas e re-deslocadas (retry) inclusive com busy-retry ao inves de busy-wait e com timeout
;
;
;; TODO citar que podemos ter fine tuning com ref... e o perigo de sincronizar na unha. vantagem e perigo
;


(def hospital-song {:espera       (ref model/empty_queue)
                    :laboratorio2 (ref model/empty_queue)
                    :laboratorio1 (ref model/empty_queue)
                    :laboratorio3 (ref model/empty_queue)
                    })

(defn chega-em [fila pessoa]
  (conj fila pessoa))


(defn chega-em-ref! [fila pessoa]
  (swap! fila chega-em pessoa))

(defn chega-em-ref! [fila pessoa]
  (ref-set fila (chega-em @fila pessoa)))

(defn testa-ref! [hospital]
  (let [fila (get hospital :espera)]
    (chega-em-ref! fila "guilherme"))
  )

;(testa-ref! hospital-song)
;(pprint hospital-song)

(defn chega-em-ref! [fila pessoa]
  (dosync
    (ref-set fila (chega-em @fila pessoa))))

;(testa-ref! hospital-song)
;(pprint hospital-song)

(defn chega-em-ref! [fila pessoa]
  (dosync
    (alter fila chega-em pessoa)))

;(testa-ref! hospital-song)
;(pprint hospital-song)




; FUTURES
(defn chega-em-ref-certo! [fila pessoa]
  (let [tamanho (count @fila)
        cabe? (< tamanho 5)]
    (if cabe?
      (alter fila conj pessoa)
      (throw (ex-info "não cabe mais ninguém" {})))
    ))

;(testa-ref! hospital-song)
;(pprint hospital-song)

(defn async-ref! [hospital i]
  (future
    (Thread/sleep (* 1000 (rand-int 10)))
    (dosync
      (println "trying " i)
      ;(ensure (get hospital :espera))
      (chega-em-ref-certo! (get hospital :espera) i)
      )))

(defn start-async-ref! [i]
  (async-ref! hospital-song i))

; (use 'hospital.aula5)
(def futures (map start-async-ref! (range 1 7)))




