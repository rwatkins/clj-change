(ns change.main
  (:require [clojure.math.numeric-tower :as math]))

(def c (atom {1 0 5 0 10 0 25 0}))

(defn to-num
  "Converts a string like '1.32' to a number."
  [s]
  (let [n (read-string s)]
    (if (number? n)
      n
      nil)))

(defn -main
  [amt]
  (if (< (to-num amt) 1.0)
    (loop [units (reverse (sort (keys @c)))
           unit (first units)
           leftover (* 100 (to-num amt))] ;; Multiply by 100 to avoid fractional division
      (swap! c assoc unit (int (math/floor (/ leftover unit))))
      (if (or (empty? (rest units))
              (zero? (mod leftover unit)))
        @c
        (recur (rest units)
               (first (rest units))
               (mod leftover unit))))
    nil))
