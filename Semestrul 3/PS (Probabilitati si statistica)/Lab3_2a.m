#INPUT 
p = input("p = "); #
n = input("n = ");

#for cycle( variable being n, step size for n, end point for n)
#inside, you plot the binomial probability density function
#after plotting, you use "pause", so the program stops for e.g. 1 second, so then 
#it resumes and shows how the plot is changing

for i = 1:n
  m = p*i;
  s_prime = p*i*(1-p);
  s = sqrt(s_prime);
  nrm = normpdf(m,s);
  x = 0:0.01:i
  plot(nrm);
  pause(1);
endfor
