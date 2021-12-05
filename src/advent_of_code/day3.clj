(ns advent-of-code.day3
  (:require [clojure.string :as str]))

(def input-file (slurp "resources/day3-input.txt"))
(def input (->> input-file
                str/split-lines))

(defn log [x] (do (println (x))
                   x))

; Part 1
(defn mcb-bit [bits] (if (>= (count (filter #(= % \1) bits)) (count (filter #(= % \0) bits))) \1 \0))
(defn lcb-bit [bits] (if (< (count (filter #(= % \1) bits)) (count (filter #(= % \0) bits))) \1 \0))

(def hsize (count (first input)))
(def mcb-bits (mapv mcb-bit (map (fn [h] (str/join (map #(get % h) input))) (range hsize))))
(def lcb-bits (mapv lcb-bit (map (fn [h] (str/join (map #(get % h) input))) (range hsize))))
;(def lcb-bits (mapv #(if (= % \0) \1 \0) mcb-bits))         ; alternate way
(def gamma-rate (read-string (str "2r" (str/join mcb-bits))))
(def epsilon-rate (read-string (str "2r" (str/join lcb-bits))))
(def power-consumption (* gamma-rate epsilon-rate))
power-consumption

; Part 2
(defn filter-by-bits
  ([remains filter-bits] (filter-by-bits remains filter-bits 0))
  ([remains filter-bits i] (if (> (count remains) 1)
                             (filter-by-bits
                               (filterv #(= (get % i) (get filter-bits i)) remains)
                               filter-bits
                               (do (println (str "i: " i " filter-bit: " (get filter-bits i) " remains: " (count remains))) (inc i)))
                             remains)))

(def o2-reading (read-string (str "2r" (first (filter-by-bits input mcb-bits)))))
(def co2-reading (read-string (str "2r" (first (filter-by-bits input lcb-bits)))))
(def life-support-rating (* o2-reading co2-reading))
life-support-rating
