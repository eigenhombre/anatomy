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
                            (apply hash-map))))
        pfmt (comp println format)]
    (doseq [{:keys [origin insertion overlaps name function overlapped-by]}
            muscle-maps]
      (pfmt "Function of %s\t%s" name function)
      (pfmt "Muscle w/ function %s\t%s" function name)
      (pfmt "Origin of %s\t%s" name origin)
      (pfmt "Muscle with origin '%s'\t%s" origin name)
      (pfmt "Insertion of %s\t%s" name insertion)
      (pfmt "Muscle with insertion '%s'\t%s" insertion name)
      (when (not= overlaps "None")
        (pfmt "%s overlaps...\t%s" name overlaps)
        (pfmt "%s are overlapped by...\t%s" overlaps name))
      (when (not= overlapped-by "None")
        (pfmt "%s is overlapped by...\t%s" name overlapped-by)
        (pfmt "%s overlap...\t%s" overlapped-by name)))))
