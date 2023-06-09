(ns codewarkata.best-travel-test
  (:require [clojure.test :refer [deftest is testing]]))

(defn combinations [coll size]
  (cond
    (= size 0) '(())
    (empty? coll) '()
    :else (concat (map (fn [x] (cons (first coll) x))
                       (combinations (rest coll) (dec size)))
                  (combinations (rest coll) size))))

(defn choose-best-sum [max-distance town-count ls]
  (let [plans (combinations ls town-count)
        available-plans (filter #(>= max-distance (apply + %)) plans)]
    (if (empty? available-plans)
      nil
      (apply max (map #(apply + %) available-plans)))))

(deftest a-test1
  (testing "Basic tests"
    (is (= (choose-best-sum 163, 3, [50, 55, 56, 57, 58]) 163))
    (is (= (choose-best-sum 163, 3, [50]) nil))))
