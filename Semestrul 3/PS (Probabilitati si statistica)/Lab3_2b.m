#INPUT
#n = input("n = ");
#p = input("p = ");
 n = 35
 p = 0.03
lambda = n*p;
x = 0:0.01:n
ans = poisspdf(lambda);
plot(x,ans);