(ns conway.ui
  (:use conway.world)
  (:use [seesaw core]))

(def buttons
  (horizontal-panel
    :items [(button :text "Start")
            (button :text "Stop")
            "Width" (text :text "10") "Height" (text :text "10")]))

(def canvas-element
  (vertical-panel :items [(canvas :id :canvas :paint nil)]))

(def main-content 
  (vertical-panel
    :items [buttons canvas-element]))

(defn start-ui [] 
  (do
    (native!)
    (invoke-later 
      (frame 
        :title "Conway" 
        :width 640
        :height 480
        :pack? true
        :on-close :exit
        :visible? true
        :content main-content))))

