(ns advent-of-code.day2
  (:require [clojure.string :as str]))

; Input
(def input-file (slurp "resources/day2-input.txt"))
(def input (->> input-file
                str/split-lines
                (map #(str/split % #" "))
                (map #(vector (keyword (first %)) (Integer/parseInt (second %))))))

; Part 1
(def init-hpos 0)
(def init-depth 0)

(def res
  (reduce (fn [[hpos depth] [direction x]] (case direction :forward [(+ hpos x) depth]
                                                           :up [hpos (- depth x)]
                                                           :down [hpos (+ depth x)]))
          [init-hpos init-depth] input))

(* (first res) (second res))

; Part 2
(def init-aim 0)

(def res
  (reduce (fn [[hpos depth aim] [direction x]] (case direction :forward [(+ hpos x) (+ depth (* aim x)) aim]
                                                           :up [hpos depth (- aim x)]
                                                           :down [hpos depth (+ aim x)]))
          [init-hpos init-depth init-aim] input))

(* (first res) (second res))