(ns conway.world
  (:use clojure.set))

(def *bounds* [100 100])

(defn whole-world [] 
  (let [[width height] *bounds*]
    (for [x (range 1 (inc width)) y (range 1 (inc height))] [x y])))

(defn rand-pos [] 
  [(rand-int (inc (*bounds* 0))) (rand-int (inc (*bounds* 1)))])

(defn n-unique-positions 
  ([n] (n-unique-positions n #{}))
  ([n poss] (if (= n (count poss)) poss (recur n (conj poss (rand-pos))))))

(defn generate-rand-world
  ([] (generate-rand-world *bounds* (inc (rand-int 50))))
  ([[width height] percentage]
   (let [num-cells (* width height)
         len (int (* percentage (/ num-cells 100)))]
     (n-unique-positions len))))

(def neighbors 
  (memoize (fn [[x y]]
             (let [left (dec x)
                   right (inc x)
                   above (dec y)
                   below (inc y)]
               (set 
                 [[left above] [x above] [right above]
                  [left y]     #_[x y]   [right y]
                  [left below] [x below] [right below]])))))

(defn in-bounds? [[x y] [width height]]
  (and
    (pos? x)
    (pos? y)
    (<= x width)
    (<= y height)))

(defn neighbors-in-bounds [pos] 
  (select #(in-bounds? % *bounds*) (neighbors pos)))

(defn tick-living [num-living-neighbors]
  (cond 
    (< num-living-neighbors 2) false
    (or (= num-living-neighbors 2) (= num-living-neighbors 3)) true
    (> num-living-neighbors 3) false))

(defn tick-dead [num-living-neighbors]
  (= num-living-neighbors 3))

(defn alive-next-round? [pos living]
  (let [living? (contains? living pos)
        neighbor-cells (neighbors-in-bounds pos)
        living-neighbors (intersection neighbor-cells living)
        num-living-neighbors (count living-neighbors)]
    (if living?
      (tick-living num-living-neighbors)
      (tick-dead num-living-neighbors))))

(defn tick
  "Given a set of living cells - represented by a vector of two numbers - 
  returns a set of the cells alive after a tick."
  [living] 
  (assert (set? living))
  (set 
    (filter
      #(alive-next-round? % living) (whole-world))))

