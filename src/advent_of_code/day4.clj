(ns advent-of-code.day4
  (:require [clojure.string :as str]))

(def input-file (slurp "resources/day4-input.txt"))
(def bingo-splits (str/split input-file #"\r?\n\r?\n"))
(def called-nums (map #(Integer/parseInt %) (str/split (first bingo-splits) #",")))
(def str-boards (map #(str/split % #"\n") (rest bingo-splits)))
(defn str-board->num-board [board]
  (->> board
       (map #(str/split (str/trim %) #"\s+"))
       flatten
       (map #(Integer/parseInt %))))

(def boards (map str-board->num-board str-boards))

(defn transpose [& xs] (apply map list xs))
(defn board-rows [board] (partition 5 board))
(defn board-columns [board] (apply transpose (partition 5 board)))
(defn board->check-seqs [board] (concat (board-rows board)
                                        (board-columns board)))

(defn winning-seq? [seq cnums] (every? #(some #{%} cnums) seq))
(defn winning-board? [board cnums] (some #{true} (map #(winning-seq? % cnums) (board->check-seqs board))))
(defn get-winning-board [boards cnums] (nth boards (.indexOf
                                                         (map
                                                           #(some #{true} %)
                                                           (map #(winning-board? % cnums) boards))
                                                         true)))
(defn board-value [board cnums]
  (* (last cnums)
     (reduce + (filter #(not (some #{%} cnums)) board))))

; Part 1

(def wboard '(30 93 48 17 33 67 7 5 0 69 54 76 52 1 87 99 73 50 25 16 13 20 41 77 62))
(def wcnums (take 26 called-nums))
(def first-winning-board-value (board-value wboard wcnums))

; Part 2

; fn to check how many numbers were called before the last board will win
;
(defn remaining-boards [boards cnums]
  (filter #(not (winning-board? % cnums)) boards))

(def last-won-board '(93 7 60 75 12 49 64 20 46 10 3 23 76 42 47 9 22 6 34 87 41 37 66 45 48))
(def lwcnums (take 86 called-nums))
(def last-winning-board-value (board-value last-won-board lwcnums))