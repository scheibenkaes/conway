(ns conway.test.ui
  (:use clojure.test)
  (:use conway.ui))

(deftest start-ui-test
         (is (instance? javax.swing.JFrame (start-ui))))
