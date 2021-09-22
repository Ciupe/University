x = 0:3;
n = 3;
p = 0.5;

coin = binopdf(x,n,p);
plot (x,coin,"--") : title ("Coin toss"); 