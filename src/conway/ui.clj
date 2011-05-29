(ns conway.ui
  (:use conway.world)
  (:use [seesaw core color graphics]))

(def running (atom false))

(def current-world (atom #{}))

(def tick-speed 500)

(declare canvas-element)

(defn tick-loop [] 
  (when @running
    (do
      (swap! current-world tick)
      (Thread/sleep tick-speed)
      (recur))))

(def dead-color (color 200 0 0))

(def living-color (color 0 155 0))

(def start-action 
  (action :name "Start"
          :handler (fn [_] 
                     (invoke-later
                       (do 
                         (reset! current-world (generate-rand-world))
                         (reset! running true)
                         (let [thread (Thread. tick-loop)]
                           (.start thread)))))))

(def stop-action 
  (action :name "Stop" :handler (fn [& _] (reset! running false))))

(def buttons
  (horizontal-panel
    :items [start-action
            stop-action
            "Width" (text :text "10") "Height" (text :text "10")]))

(def cell-size 5)

(defn paint-world [c g] 
  (let [[world-w world-h] *bounds*
        w (.getWidth c)
        h (.getHeight c)
        wrld @current-world]
    (doseq [[x y] (whole-world)]
      (let [living? (contains? wrld [x y])
            col (if living? living-color dead-color)
            c-x (* cell-size (dec x))
            c-y (* cell-size (dec y))]
        (draw g
              (rect c-x c-y cell-size cell-size)
              (style :background col))))))

(def canvas-element
  (canvas :id :canvas :paint paint-world)) 

(def main-content 
  (border-panel
    :north buttons
    :center canvas-element))

(defn start-ui [] 
  (let [t (timer (fn [e] (repaint! canvas-element)) :delay (/ tick-speed 2)) ]
    (do
      (native!)
      (invoke-later 
        (frame 
          :title "Conway" 
          :width 640
          :height 480
          :pack? false
          :on-close :exit
          :visible? true
          :content main-content)))))

