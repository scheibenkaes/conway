(ns conway.world)

(def *bounds* [100 100])

(defn rand-pos [] 
  [(rand-int (inc (*bounds* 0))) (rand-int (inc (*bounds* 1)))])
