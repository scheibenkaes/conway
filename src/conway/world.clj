(ns conway.world
  (:use clojure.set))

(def *bounds* [100 100])

(defn whole-world [] 
  (let [[width height] *bounds*]
    (set (for [x (range 1 (inc width)) y (range 1 (inc height))] [x y]))))

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

(defn dead-next-round? [living-neighbors]
  (let [cnt-living-neighbors (count living-neighbors)]
    (cond  
      (< cnt-living-neighbors 2) true
      (> cnt-living-neighbors 3) true
      :default false)))

(defn stays-alive? [living-neighbors]
  (let [cnt-living-neighbors (count living-neighbors)] 
    (if (or (= 2 cnt-living-neighbors) (= 3 cnt-living-neighbors)) true false)))

(defn alive-next-round? [pos living]
  (let [living? (contains? living pos)
        neighbor-cells (neighbors-in-bounds pos)
        living-neighbors (intersection neighbor-cells living)]
    (if living?
      (not (dead-next-round? living-neighbors))
      (stays-alive? living-neighbors))))


