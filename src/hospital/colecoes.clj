(ns hospital.colecoes
  (:use [clojure pprint]))

(defn testa-vetor []
  (def pre-consulta ["111"])
  (println (conj pre-consulta "222"))
  (println (conj pre-consulta "111")))

(testa-vetor)

(defn testa-conjunto []
  (def pre-consulta #{"111"})
  (println (conj pre-consulta "222"))
  (println (disj pre-consulta "111")))
; OPERACAO CONJ IDEMPOTENTE EM UM SET, SE TENTAR DUAS VEZES NO MESMO ESTADO NAO TEM DIFERENCA

(testa-conjunto)


(defn testa-fila []
  (def pre-consulta (conj clojure.lang.PersistentQueue/EMPTY "111" "222" "333"))
  (pprint pre-consulta)
  (println (peek pre-consulta))
  (println (rest pre-consulta))
  (println (seq (pop pre-consulta)))
  (pprint (pop pre-consulta))
  )
(testa-fila)
