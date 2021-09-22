;Write a function to determine the number of nodes on the level k from a n-tree represented as follows:
;(root list_nodes_subtree1 ... list_nodes_subtreen)
;Eg: tree is (a (b (c)) (d) (e (f))) and k=1 => 3 nodes

;level (l1...ln, k) = {
;	1, n = 1 and k = -1
;	0, n = 1
;	level(l2...ln, k) + level(l2...ln, k-1), otherwise


(defun level (tree k)
	(cond
		((and (atom tree) (= k -1)) 1)
		((atom tree) 0)
		(t (apply '+ (mapcar #' (lambda(v) (level v (- k 1))) tree)))
	)
)

(write (level '(a (b (c)) (d) (e (f))) 1))