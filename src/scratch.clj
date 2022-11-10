(ns scratch
  (:require [datasplash.api :as ds])
  (:gen-class))

(def local-pipeline {:runner "DirectRunner"})

(defn alloc-price-coercion [alloc]
  (-> alloc
      (update :suppliers (fn [suppliers] (mapv #(update % :price bigdec) suppliers)))
      (update-in [:allocation :preferred] (fn [allocs] (mapv #(update % :price bigdec) allocs)))
      (update-in [:allocation :backup] (fn [allocs] (mapv #(update % :price bigdec) allocs)))
      (update-in [:allocation :stock] (fn [allocs] (mapv #(update % :price bigdec) allocs)))
      (update-in [:allocation :closed] (fn [allocs] (mapv #(update % :price bigdec) allocs)))))

(defn -main
  []
  (let [pipeline (ds/make-pipeline local-pipeline)
        data (ds/generate-input [{:suppliers  [{:price 1}] :allocation {:preferred [{:price 2}]}}] pipeline)]
    (->> (ds/map alloc-price-coercion data)
         (ds/write-edn-file "out.edn"))
    (ds/run-pipeline pipeline)))
