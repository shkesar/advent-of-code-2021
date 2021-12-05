(ns advent-of-code.day3
  (:require [clojure.string :as str]))

(def input-file (slurp "resources/day3-input.txt"))
(def input (->> input-file
                str/split-lines))

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
(defn filter-by-bit
  ([remains criteria] (filter-by-bit remains criteria 0))
  ([remains criteria i] (if (> (count remains) 1)
                             (filter-by-bit
                               (filterv #(= (get % i) (criteria remains i)) remains)
                               criteria
                               (inc i))
                             remains)))
(defn bit-criteria [input i bitf] (bitf (str/join (map #(get % i) input))))
(defn mcb-bit-criteria [input i] (bit-criteria input i mcb-bit))
(defn lcb-bit-criteria [input i] (bit-criteria input i lcb-bit))

(def o2-reading (read-string (str "2r" (first (filter-by-bit input mcb-bit-criteria)))))
(def co2-reading (read-string (str "2r" (first (filter-by-bit input lcb-bit-criteria)))))
(def life-support-rating (* o2-reading co2-reading))
life-support-rating
