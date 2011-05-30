(defproject conway "1.0.0-SNAPSHOT"
  :description "Just conways game of life in clojure"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [seesaw "1.0.3"]]
  :dev-dependencies [[org.clojars.autre/lein-vimclojure "1.0.0"]
                     [lein-vim "1.0.1-SNAPSHOT"]]
  :aot [conway.applet]
  :main conway.core)
