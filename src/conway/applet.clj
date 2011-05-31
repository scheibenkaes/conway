(ns conway.applet
  (:use conway.ui seesaw.core)
  (:gen-class :extends javax.swing.JApplet))

(defn -init [this] 
  (let [content (doto (main-content) (.setVisible true))]
    (invoke-later (.. this getContentPane (add content)))))

