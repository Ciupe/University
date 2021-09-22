; 5.
; a) Write twice the n-th element of a linear list. 
; Example: for (10 20 30 40 50) and n=3 will produce (10 20 30 30 40 50).



;twice (l1...ln, n) = {
;	[], n = 0
;	l1 U l1...ln, n = 1
;	l1 U twice (l2...ln, n-1), otherwise
;	}


(defun twice (list n) 
    (cond
        ((null list) nil) ; if the list is empty, we return nil
        ((= n 1) (cons (car list) list)) ;if n = 1, we return the element concatenated with the remaining list (including that element)
        (T (cons (car list) (twice (cdr list) (- n 1)))) ; if none of the condition above were met, we append the current element and call 
                                                         ; the function twice with the remaining list and n-1
     )
)
(princ "a) ")
(write (twice `(10 20 30 40 50) `3))
(terpri)



; b) Write a function to return an association list with the two lists given as parameters.
; Example: (A B C) (X Y Z) --> ((A.X) (B.Y) (C.Z)).

;association (p1...pn, q1...qn) = {
;	[], n = 0 
;	(p1 q1) U association (p2...pn, q2...qn), otherwise
;	}	

(defun association (l1 l2)
    (cond
        ((null l1) nil) ; if the lists are null, return nil
        (T (cons (cons (car l1) (car l2)) (association (cdr l1) (cdr l2)))) ; while the lists still have elements, we just concatenate the first element of each list 
                                                                            ; and then call the function with the remaining elements
     )
)
(princ "b) ")
(write (association `(A B C) `(X Y Z)))
(terpri)



; c) Write a function to determine the number of all sublists of a given list, on any level.
; A sublist is either the list itself, or any element that is a list, at any level. Example:
; (1 2 (3 (4 5) (6 7)) 8 (9 10)) => 5 lists:

;sublists (l1...ln) = {
;	0, l is an atom
;	sublists(l2... ln), l1 is an atom	
;	1 + sublists(l2...ln), l1 is a list


(defun sublists (l)
    (cond 
        ((atom l) 0) ; return 0 l isn't a list
        ((listp l) (+ 1 (apply '+ (mapcar 'sublists l)))) ; if l is a list, return 1 (l is a list itself) + the number of number of sublists of l
     )
)
(princ "c) ")
(write (sublists `(1 2 (3 (4 5) (6 7)) 8 (9 10))))
