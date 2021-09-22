% 7. A player wants to choose the predictions for 4 games.
% The predictions can be 1, X, 2.
% Write a predicate to generate all possible variants considering that:
% last prediction can’t be 2 and no more than two possible predictions X.

prediction(1).
prediction(2).
prediction(3). % 3 = X


% all_cases(I:number, L:list, P:number, R:list)
% all_cases(i, i, i, o)
% all_cases(I, L, P) =
%	L, I = 4
%	all_cases(I + 1, prediction(X) + L, P * X), P * X % 27 != 0
%
all_cases(4,R,_,R):-!.
all_cases(I,L,P,R):-
    prediction(X),
    I1 is I+1,
    P1 is P*X,
    P1 mod 27 =\= 0,
    all_cases(I1,[X|L],P1,R).

%rez(R:list)
%rez(o)

rez(R):-all_cases(1,[1],3,R).
rez(R):-all_cases(1,[3],3,R).

% allsolutions(R:list)
% allsolutions(o)

allsolutions(R) :-
    findall(RPartial, rez(RPartial), R).


