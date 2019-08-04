(ns hospital.aula4
  (:use [clojure pprint])
  (:require [hospital.logic :as logic]
            [hospital.model :as model]))


;; mostrar que inclusive a transferência de um lugar pra outro também funciona

;; discussao? poderia ser vargars, poderia genelraizar, mas pensa com carinho antes de complicar coisa que eh uma linha de complexidade 1
(defn transfere! [hospital de para]
  (swap! hospital logic/transfere de para))

(defn chega-em! [hospital departamento pessoa]
  (swap! hospital logic/chega-em departamento pessoa))

;; TODO para saber mais: ver a pessoa antes, dar somente um ensure. maximo fora do sawp, e o minimo (so ensure) dentro
;; TODO clicar  no ensure
;; clojure south: 31 de agosto... 1 de setembro

(defn testa-transferencias! []
  (def hospital-azevedo (atom (model/new-hospital)))
  (chega-em! hospital-azevedo :espera "guilherme")
  (chega-em! hospital-azevedo :espera "daniela")
  (chega-em! hospital-azevedo :espera "carlos")
  (transfere! hospital-azevedo :espera :laboratorio3)
  (pprint hospital-azevedo)
  )

;(testa-transferencias!)





; vARIACAO DA TRANSFERENCIA QUE EVITA AQUELE DUPLO


(defn transfere2! [hospital de para]
  (swap! hospital logic/transfere2 de para))

(defn testa-transferencias2! []
  (def hospital-azevedo (atom (model/new-hospital)))
  (chega-em! hospital-azevedo :espera "guilherme")
  (chega-em! hospital-azevedo :espera "daniela")
  (chega-em! hospital-azevedo :espera "carlos")
  (transfere2! hospital-azevedo :espera :laboratorio3)
  (pprint hospital-azevedo)
  )

(testa-transferencias2!)

;; (desafio exercicio fazer o thread multiplo)
