(ns conway.world)

(def *bounds* [100 100])

(defn rand-pos [] 
  [(rand-int (inc (*bounds* 0))) (rand-int (inc (*bounds* 1)))])

(defn n-unique-positions 
  ([n] (n-unique-positions n #{}))
  ([n poss] (if (= n (count poss)) poss (n-unique-positions n (conj poss (rand-pos))))))

(defn generate-rand-world
  ([bounds] (generate-rand-world bounds (inc (rand-int 50))))
  ([[width height] percentage]
   (let [num-cells (* width height)
         len (int (* percentage (/ num-cells 100)))]
     (n-unique-positions len))))
