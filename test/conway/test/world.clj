(ns conway.test.world
  (:use conway.world)
  (:use clojure.test))

(deftest rand-pos-returns-a-vec-of-2-ints 
         (let [pos (rand-pos)]
           (is (every? integer? pos))))

(deftest n-unique-positions-len
         (is (= 40 (count (n-unique-positions 40)))))

(deftest generate-rand-world-len
         (is (= (count (generate-rand-world [10 10] 10)) 10)))
