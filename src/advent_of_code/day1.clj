(ns advent-of-code.day1
  (:require [clojure.string :as str]))

; Input
(def input-file (slurp "resources/day1-input-p1.txt"))
(def input (map #(Integer/parseInt %) (str/split-lines input-file)))

; Part 1
(def depth-steps (map #(- %1 %2) (rest input) (butlast input)))
(def depth-increases (count (filter pos-int? depth-steps))) ; 1342

; Part 2
(def window-1 (drop-last 2 input))
(def window-2 (drop-last 1 (drop 1 input)))
(def window-3 (drop 2 input))

(def depth-sum (map #(+ %1 %2 %3) window-1 window-2 window-3))
(def depth-steps (map #(- %1 %2) (rest depth-sum) (butlast depth-sum)))
(def depth-increases (count (filter pos-int? depth-steps))) ; 1378