(ns hospital.logic)

; TODO COLOCAR DESCRICAO DAS FUNCOES POR CIMA E ENSINAR ISSO

; usar
(def tamanho-maximo-da-fila 5)

; usa? nao
(defn tamanho-da-fila [hospital departamento]
  (->> hospital
       departamento
       count))

; usa? sim
(defn cabe-na-fila? [hospital departamento]
  (-> hospital
      departamento
      count
      (< ,,, tamanho-maximo-da-fila)))

; VERSAO 1
;(defn chega-em [hospital departamento pessoa]
;  (let [fila (get hospital departamento)
;        tamanho (count fila)
;        cabe? (< tamanho 5)]
;    (if cabe?
;      (update-in hospital [departamento] conj pessoa)
;      (throw (ex-info "não cabe mais ninguém" {})))
;    ))

; VERSAO 2
;(defn chega-em [hospital departamento pessoa]
;  (let [fila (get hospital departamento)]
;    (if (cabe-na-fila? hospital fila) ; TEM ALGO ERRADO AQUI
;      (update-in hospital [departamento] conj pessoa)
;      (throw (ex-info "não cabe mais ninguém" {})))
;    ))


; VERSAO 3
(defn chega-em [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update-in hospital [departamento] conj pessoa)
    (throw (ex-info "não cabe mais ninguém" {})))
  )


; VERSAO 1
(defn atende [hospital departamento]
  (update-in hospital [departamento] pop))

; AULA 1 SO ATE AQUI












; VERSAO COM PAUSA
(defn chega-em-pausado [hospital departamento pessoa]
  (println "Trying to add " pessoa)
  (Thread/sleep (* (rand) 3000))
  (let [fila (get hospital departamento)
        tamanho (count fila)
        cabe? (< tamanho 5)]
    (if cabe?
      (update-in hospital [departamento] conj pessoa)
      (throw (ex-info "não cabe mais ninguém" {})))
    ))


(defn chega-em-pausado-com-log [hospital departamento pessoa]
  (println "Trying to add " pessoa)
  (Thread/sleep (* (rand) 3000))
  (let [fila (get hospital departamento)
        tamanho (count fila)
        cabe? (< tamanho 5)]
    (if cabe?
      (do
        ;(Thread/sleep 2000)
        (update-in hospital [departamento] conj pessoa))
      (throw (ex-info "não cabe mais ninguém" {})))
    ))





; TRANSFERENCIA

(defn proxima [hospital departamento]
  (->> hospital
       departamento
       peek))

; VERSAO 1 com atende
; nao validei se a pessoa realmente estava em de... fica de exercicio
(defn transfere [hospital de para]
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        ; TODO ficou bizarro pq a garantia de que o peek e o rest eh igual eh por causa da atomicidade,
        ; TODO mas so fica bizarro pra quem nao entendeu imutabilidade. como eh imutavel, nao tem problema
        (chega-em para pessoa)
)))







; VERSAO 2
(defn atende2 [hospital departamento]
  (let [fila (get hospital departamento)
        [pessoa fila] ((juxt peek pop) fila)]
    [pessoa
     (assoc hospital departamento fila)]))


; JUXT com posicional ao inves de mapa. duvida: ao inves de let algum tipo de thread? principalmente pq pessoa pode vir nulo
(defn transfere2 [hospital de para]
  (let [[pessoa, hospital] (atende2 hospital de)]
    (chega-em hospital para pessoa)))


