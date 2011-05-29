(ns conway.test.world
  (:use clojure.contrib.trace)
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

(deftest whole-world-test 
         (let [ww (whole-world)]
           (is (= (count ww) (* (*bounds* 0) (*bounds* 1))))))

(deftest neighbors-test-for-positions 
         (let [expected [[0 0] [1 0] [2 0]
                         [0 1] #_[1 1] [2 1]
                         [0 2] [1 2] [2 2]]]
           (is (= (set expected) (neighbors [1 1])))))

(deftest in-bounds?-test 
         (are [exp defs] (exp (apply in-bounds? defs))
              true? [[1 1] [10 10]]
              true? [[10 10] [10 10]]
              false? [[0 1] [10 10]]
              false? [[11 10] [10 10]]))

(deftest neighbors-in-bounds-test
         (are [cnt pos] (= cnt (count (neighbors-in-bounds pos)))
              3 [1 1]
              5 [2 1]
              5 [1 2]
              8 [5 5]))

(deftest alive-next-round?-test-with-a-dead-cell
         (are [exp values] (exp (apply alive-next-round? values))
              true? [[2 2] #{[1 3] [2 3] [3 3]}]
              false? [[2 2] #{[1 1]}]
              true? [[2 2] #{[1 2] [2 3] [3 3]}]
              false? [[2 2] #{[1 2] [2 3] [3 3] [1 3]}]))

(deftest alive-next-round?-test-with-a-living-cell
         (are [exp values] (exp (apply alive-next-round? values))
              true? [[2 2] #{[1 3] [2 2] [2 3] [3 3]}]
              false? [[2 2] #{[1 1] [2 2]}]
              true? [[2 2] #{[1 2] [2 2] [3 3]}]
              true? [[2 2] #{[1 2] [2 2] [2 3][3 3]}]
              false? [[2 2] #{[1 2] [2 2] [2 3][3 3] [1 3]}]))

(deftest tick-test 
         (binding [*bounds* [3 3]]
           (let [living #{[1 1] [2 1] [1 2] [2 2]}
                 after-tick (tick living)]
             (is (= living after-tick)))))

(deftest tick-test-static
         (binding [*bounds* [3 3]]
           (let [living #{[2 1] [1 2] [3 2] [2 3]}
                 after-tick (tick living)]
             (is (= living after-tick)))))

(deftest tick-test-blinker
         (binding [*bounds* [3 3]]
           (let [living #{[2 1] [2 2] [2 3]}
                 expected #{[1 2] [2 2] [3 2]}
                 after-tick (tick living)]
             (is (= expected after-tick)))))

(deftest tick-asserts-a-set
         (is (thrown? AssertionError (tick []))))
