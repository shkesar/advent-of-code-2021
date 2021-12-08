(ns advent-of-code.day5
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]))

(def input-file (slurp "resources/day5-input.txt"))

(defn str->line [s]
  (->> (str/split s #" -> ")
       (map #(->> (str/split % #",")
                  (map (fn [v] (Integer/parseInt v)))))))

(defn is-horz-or-vert? [[[x1 y1] [x2 y2]]]
  (or (= x1 x2)
      (= y1 y2)))

(defn line->vertices [[[x1 y1] [x2 y2]]]
  (combo/cartesian-product (range (min x1 x2)
                                  (inc (max x1 x2)))
                           (range (min y1 y2)
                                  (inc (max y1 y2)))))

(def input (->> input-file
                str/split-lines
                (map str->line)))

(->> input
     (filter is-horz-or-vert?)
     (map line->vertices)
     (apply concat)
     frequencies
     (filter #(> (second %) 1))
     count)

; Part 2

(defn abs [n] (max n (- n)))

(defn is-diagonal? [[[x1 y1] [x2 y2]]]
  (= (abs (- (max y1 y2) (min y1 y2)))
     (abs (- (max x1 x2) (min x1 x2)))))

(defn inclusive-range [start end]
  (range start
         (if (> end start) (inc end) (dec end))
         (if (> end start) 1 -1)))                          ;step

(defn diag->vertices [[[x1 y1] [x2 y2]]]
  (zipmap (inclusive-range x1 x2)
          (inclusive-range y1 y2)))

(def straight-line-map (->> input
                            (filter is-horz-or-vert?)
                            (map line->vertices)
                            (apply concat)))

(def diag-map (->> input
                   (filter is-diagonal?)
                   (map diag->vertices)
                   (apply concat)))

(->> (concat straight-line-map diag-map)
     frequencies
     (filter #(> (second %) 1))
     count)