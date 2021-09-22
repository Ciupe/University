#INPUT
m = input("Give m: ");
s = input("Give s: ");

#a)
a = fcdf(0,m,s);
a_prime = 1-a;

#b)
b1 = fcdf(1,m,s);
b2 = fcdf(-1,m,s);
b = b1-b2;
b_prime = 1 - b;

#c)
alpha = input("Give alpha: ");
c = finv(alpha,m,s);

#d)
beta = input("Give beta: ");
d = finv(1-beta,m,s);
