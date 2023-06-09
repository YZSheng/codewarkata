(ns codewarkata.street-fighter-character-selection-test
  (:require [clojure.test :refer [deftest is]]))

(defn street-fighter-selection [fighters position moves]
  (loop [position position
         moves moves
         result []]
    (if (seq moves)
      (let [length (count (first fighters))
            [x y] position
            move (first moves)
            [updated-x updated-y] (case move
                                    "left" [(mod (dec x) length) y]
                                    "right" [(mod (inc x) length) y]
                                    "up" [x (max 0 (dec y))]
                                    "down" [x (min 1 (inc y))])]
        (recur [updated-x updated-y]
               (rest moves)
               (conj result (get-in fighters [updated-y updated-x]))))
      result)))

(def fighters [["Ryu" "E.Honda" "Blanka" "Guile" "Balrog" "Vega"]
               ["Ken" "Chun Li" "Zangief" "Dhalsim" "Sagat" "M.Bison"]])

(deftest street-fighter-selection-test
  (let [position [0 0] moves [] solution []]
    (is (= (street-fighter-selection fighters position moves) solution) "no cursor moves"))
  (let [position [0 0]
        moves (vec (repeat 8 "left"))
        solution ["Vega" "Balrog" "Guile" "Blanka" "E.Honda" "Ryu" "Vega" "Balrog"]]
    (is (= (street-fighter-selection fighters position moves) solution) "left 8 times"))
  (let [position [0 0]
        moves (vec (repeat 8 "right"))
        solution ["E.Honda" "Blanka" "Guile" "Balrog" "Vega" "Ryu" "E.Honda" "Blanka"]]
    (is (= (street-fighter-selection fighters position moves) solution) "right 8 times"))
  (let [position [0 0]
        moves (vec (repeat 4 "up"))
        solution ["Ryu" "Ryu" "Ryu" "Ryu"]]
    (is (= (street-fighter-selection fighters position moves) solution) "up 4 times"))
  (let [position [0 0]
        moves (vec (repeat 4 "down"))
        solution ["Ken" "Ken" "Ken" "Ken"]]
    (is (= (street-fighter-selection fighters position moves) solution) "down 4 times"))
  (let [position [0 0]
        moves ["down" "right" "up" "left" "down" "right" "up" "left"]
        solution ["Ken" "Chun Li" "E.Honda" "Ryu" "Ken" "Chun Li" "E.Honda" "Ryu"]]
    (is (= (street-fighter-selection fighters position moves) solution) "counter-clockwise"))
  (let [position [0 0]
        moves (vec ["up" "left" "down" "right" "up" "left" "down" "right"])
        solution ["Ryu" "Vega" "M.Bison" "Ken" "Ryu" "Vega" "M.Bison" "Ken"]]
    (is (= (street-fighter-selection fighters position moves) solution) "clockwise"))
  (let [position [0 0]
        moves (vec (concat ["up"] (repeat 6 "left") ["down"] (repeat 6 "right")))
        solution ["Ryu" "Vega" "Balrog" "Guile" "Blanka" "E.Honda" "Ryu" "Ken" "Chun Li" "Zangief" "Dhalsim" "Sagat" "M.Bison" "Ken"]]
    (is (= (street-fighter-selection fighters position moves) solution) "all characters")))