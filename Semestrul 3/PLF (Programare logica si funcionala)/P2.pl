% is_power2(1) = true
% is_power2(N) =
%         is_power2(N/2), if N%2 = 0
%         false, otherwise
%         true, if N=1

is_power2(1) :- !.
is_power2(N) :- N mod 2 =:= 0,
                NN is N div 2,
                is_power2(NN).

% add_power2(L:list, P:integer, E:integer, R:list)
% add_power(i,i,i,o)
% add_power2([L1,L2,...],P,E) = [], if L = []
%              L1 + E + add_power2([L2, L3, ...], P+1, E), if P%2=0
%              L + add_power2([L2, L3, ...], P+1, E), if P%2=1

add_power2([], _, _, []).
add_power2([H|T], P, E, R) :- PP is P + 1,
                              is_power2(PP), !,
                              add_power2(T, PP, E, R1),
                              R = [H, E|R1].
add_power2([H|T], P, E, [H|R]) :- PP is P + 1,
                                  add_power2(T, PP, E, R).

add_elem(L, E, R) :- add_power2(L, 1, E, R).


%update_sublist(L:list, Prev:integer, R:list)
%update_sublist(i,i,o)
%update_sublist([L1, L2, ...], Prev) = [], if L = []
%               add_elem(L1,Prev)+update_sublist([L2,L3,...], L1), if
%               L1=list L1+update_sublist([L2,L3,...], L1), if L1!=list
%               Eg.: [1, [2, 3], 7, [4, 1, 4], 3, 6, [7, 5, 1, 3, 9, 8,
%               2, 7], 5] => [1, [2, 1, 3], 7, [4, 7, 1, 4, 7], 3, 6,
%               [7, 6, 5, 1, 6, 3, 9, 8, 2, 6, 7], 5].
%
update_sublist([], _, []) :- !.
update_sublist([H|T], Prev, R) :- is_list(H), !,
                                  update_sublist(T, H, R1),
                                  add_elem(H, Prev, L),
                                  R = [L|R1].
update_sublist([H|T], _, R) :- update_sublist(T, H, R1),
                               R = [H|R1].

process_list([], []) :- !.
process_list([H|T], R) :- update_sublist(T, H, R1),
                          R = [H|R1].
