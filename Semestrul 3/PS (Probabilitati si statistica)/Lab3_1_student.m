#INPUT
DF = input("Give DF: ");

#a)
a = tcdf(0, DF);
a_prime = 1-a;

#b)
b1 = tcdf(1,DF);
b2 = tcdf(-1,DF);
b = b1-b2;
b_prime = 1-b;

#c)
alpha = input("Give alpha: ");
c = tinv(alpha,DF);

#d)
beta = input("Give beta: ");
d = tinv(1-beta,DF);
