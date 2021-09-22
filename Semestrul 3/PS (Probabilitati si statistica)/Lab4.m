pkg load statistics;

#INPUT
p = input("Give p:")
n = input("Give n:")

below = 0;
above = 0;

for i = 1:n
  x = rand();
  if (x < p)
    below = below + 1;
  else  
    above = above + 1;
  endif
endfor

below_p = below / n
aobve_p = above / n