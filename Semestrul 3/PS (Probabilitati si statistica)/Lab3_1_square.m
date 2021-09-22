#INPUT
n = input("Give n: ");

#a)
a = tcdf(0, n);
a_prime = 1-a;

#b)
b1 = tcdf(1,n);
b2 = tcdf(-1,n);
b = b1-b2;
b_prime = 1-b;

#c)
alpha = input("Give alpha: ");
c = tinv(alpha,n);

#d)
beta = input("Give beta: ");
d = tinv(1-beta,n);
