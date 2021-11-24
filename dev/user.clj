(ns user
  (:require [clojure.java.io :as io]
            [clojure.tools.namespace.repl :as repl]))

(comment
  (repl/set-refresh-dirs (io/file "src") (io/file "dev"))
  (repl/refresh)
  (repl/clear)

  )
