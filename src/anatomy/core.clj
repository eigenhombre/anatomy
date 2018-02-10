(ns anatomy.core
  (:require [clojure.data.csv :as csv]
            [clojure.java.io]
            [clojure.walk])
  (:gen-class))



(defn -main [& _]
  (let [[hdr & rows]
        (->> "muscle-structure.csv"
             clojure.java.io/resource
             slurp
             csv/read-csv)
        hdr (->> hdr
                 (map (comp #(.toLowerCase %)
                            #(clojure.string/replace % #"\s+" "-"))))
        muscle-maps (clojure.walk/keywordize-keys
                     (for [r rows]
                       (->> r
                            (interleave hdr)
                            (apply hash-map))))]
    (doseq [{:keys [origin insertion overlaps name function overlapped-by]}
            muscle-maps]
      (println (format "Function of %s\t%s" name function))
      (println (format "Muscle w/ function %s\t%s" function name))
      (println (format "Origin of %s\t%s" name origin))
      (println (format "Muscle with origin '%s'\t%s" origin name))
      (println (format "Insertion of %s\t%s" name insertion))
      (println (format "Muscle with insertion '%s'\t%s" insertion name))
      (when (not= overlaps "None")
        (println (format "%s overlaps...\t%s" name overlaps))
        (println (format "%s are overlapped by...\t%s" overlaps name)))
      (when (not= overlapped-by "None")
        (println (format "%s is overlapped by...\t%s" name overlapped-by))
        (println (format "%s overlap...\t%s" overlapped-by name))))))
