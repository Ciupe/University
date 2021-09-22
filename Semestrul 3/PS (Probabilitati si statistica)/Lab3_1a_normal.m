#INPUT
m = input("Give m: ");
s = input("Give s: ");

#a)
a = normcdf(0,m,s);
a_prime = 1-a;

#b)
b1 = normcdf(1,m,s);
b2 = normcdf(-1,m,s);
b = b1-b2;
b_prime = 1 - b;

#c)
alpha = input("Give alpha: ");
c = norminv(alpha,m,s);

#d)
beta = input("Give beta: ");
d = norminv(1-beta,m,s);
