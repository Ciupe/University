n = 250;
p = 0.4;

v = 0:1:n;
w = 0:0.01:n;


a = binopdf(v,n,p);
plot (v,a,".") : title ("PDF and CDF");

hold on 

b = binocdf(w,n,p);
plot (w,b,".");
