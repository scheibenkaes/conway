(ns conway.test.world
  (:use conway.world)
  (:use clojure.test))

(deftest rand-pos-returns-a-vec-of-2-ints 
         (let [pos (rand-pos)]
           (is (every? integer? pos))))
