%11.
% a. Write a predicate to substitute an element from a list with another
% element in the list.
% substitute(L:List, E1:integer, E2:integer, R:List)
% substitute(i,i,i,o)
% substitute([L1,L2,...],E1,E2)=
% -[], if L is empty
% -E2 + substitute([L2,L3,...],E1,E2) L1=E1
% -L1 + substitute([L2,L3,...],E1,E2) otherwise

substitute([],_,_,[]).
substitute([H|T],E1,E2,[H|R]):- %add Lk to the list if Lk!=E1
    H\==E1, !,
    substitute(T,E1,E2,R).
substitute([H|T],E1,E2,[E2|R]):- %add E2 to the list if Lk = E1
    H==E1, !,
    substitute(T,E1,E2,R).




% b. Write a predicate to create the sublist (lm, …, ln) from the list
% (l1,…, lk).
%sublist(L:List, m:integer, n:integer, R:List)
%sublist(i,i,i,o)
%sublist([Lk1,Lk2,...],m,n)=
% -L1 + sublist([L2,L3,...],m-1,n-1), if m<=0 and n>0
% -sublist([L2,L3,...],m-1,n-1), if m>0
% -[], if n=0

sublist(_,_,0,[]). % n=0, so we stop
sublist([_|T],M,N,R):-
    M>1,
    M1 is M-1,
    N1 is N-1,
    sublist(T,M1,N1,R).
sublist([H|T],M,N,[H|R]):- % we add to the new list
    M=<1,
    N>0,
    M1 is M-1,
    N1 is N-1,
    sublist(T,M1,N1,R).



