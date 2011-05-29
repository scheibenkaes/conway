(ns conway.ui
  (:use conway.world)
  (:use [seesaw core color graphics]))

(def dead-color (color 200 0 0))

(def living-color (color 0 155 0))

(def buttons
  (horizontal-panel
    :items [(button :text "Start")
            (button :text "Stop")
            "Width" (text :text "10") "Height" (text :text "10")]))

(defn paint-world [c g] 
  (let [[world-w world-h] *bounds*
        w (.getWidth c)
        h (.getHeight c)]
    (doseq [[x y] (whole-world)]
      (draw g
            (rect (dec x) (dec y) 2 2)
            (style :foreground (color 120 0 0))))))

(def canvas-element
  (let [[w h] *bounds*
        c (canvas :id :canvas :paint paint-world)] 
    (vertical-panel :items [c])))

(def main-content 
  (border-panel
    :north buttons
    :center canvas-element))

(defn start-ui [] 
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
        :content main-content))))

