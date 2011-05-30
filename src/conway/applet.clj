(ns conway.applet
  (:use conway.ui seesaw.core)
  (:gen-class :extends javax.swing.JApplet))

(defn -init [this] 
  (invoke-later (.. this getContentPane (add main-content))))

