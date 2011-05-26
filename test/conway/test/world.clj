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

(deftest neighbors-test
         (is (= 8 (count (neighbors [1 1])))))

(deftest neighbors-test-for-positions 
         (let [expected [[0 0] [1 0] [2 0]
                         [0 1] #_[1 1] [2 1]
                         [0 2] [1 2] [2 2]]]
           (is (= expected (neighbors [1 1])))))
