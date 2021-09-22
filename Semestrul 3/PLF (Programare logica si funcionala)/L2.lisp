;7. Return the level of a node X in a tree of type (1). The level of the root element is 0.

(defun parcurg_st(arb nv nm)
    (cond
        ((null arb) nil)
        ((= nv (+ 1 nm)) nil)
        (t (cons (car arb) (cons (cadr arb) (parcurg_st (cddr arb) (+ 1 nv) (+ (cadr arb) nm)))))
    )
)

(defun parcurg_dr(arb nv nm)
    (cond
        ((null arb) nil)
        ((= nv (+ 1 nm)) arb )
        ((parcurg_dr (cddr arb) (+ 1 nv) (+ (cadr arb) nm)))
    )
)

(defun stanga(arb)
    (parcurg_st (cddr arb) 0 0)
)

(defun dreapta(arb)
    (parcurg_dr (cddr arb) 0 0)
)

;check (l1...ln, x) = {
;	0, n = 0
;	1, l1 = x
;	check(l2...ln, x), othewise

(defun check(l x) 
    (cond
        ((null l) 0)
        ((string= (car(car l)) x) 1)
        (t (check (cdr l) x))
    )
)

;parcurgere (l1...ln, x, level) = {
;	[], if l is empty list
;	level, check(l1...ln, x) = 1
;	stanga(l1) U dreapta(l1) U parcurgere(l2...ln, x, level)	

(defun parcurgere(l x level)
   (cond
        ((null l) nil)
        ((= (check l x) 1) level)
        (t (cons (stanga (car l)) (cons (dreapta (car l)) (parcurgere (cdr l) x level))))
    )
)

;executare (l1...ln, x, level) = {
;	null, l is empty list
;	l1, l1 is a number
;	null, l is null
;	executare (parcurgere (l x level), x, level + 1), otherwise

(defun executare(l x level)
    (cond
        ((null l) nil)
        ((numberp l) l)
        ((null (car l)) nil)
        (t (executare (parcurgere l x level) x (+ level 1)))
    )
)

; a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0    
(write (executare (list '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0)) "C" 0))



