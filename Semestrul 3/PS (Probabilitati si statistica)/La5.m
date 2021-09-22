#B 1. a)
pkg load statistics

x = [ 7, 7, 4, 5, 9, 9,
      4, 12, 8, 1, 8, 7,
      3, 13, 2, 1, 17, 7,
      12, 5, 6, 2, 1, 13,
      14, 10, 2, 4, 9, 11,
      3, 5, 12, 6, 10, 7 ]

sigma = 5;
n = columns(x)*rows(x);
x_mean = mean(mean(x)); #used mean twice because its a matrix, not a vector
alpha = 0.05;
left = x_mean - (sigma /sqrt(n)) * norminv(1 - alpha/2, 0, 1);
right = x_mean - (sigma /sqrt(n)) * norminv(alpha/2, 0, 1);
fprintf("Confidence interval is: (%f, %f)\n", left, right);


#B 1. b)

x = [ 7, 7, 4, 5, 9, 9,
      4, 12, 8, 1, 8, 7,
      3, 13, 2, 1, 17, 7,
      12, 5, 6, 2, 1, 13,
      14, 10, 2, 4, 9, 11,
      3, 5, 12, 6, 10, 7 ]

n = columns(x)*rows(x);
x_mean = mean(mean(x)); #used mean twice because its a matrix, not a vector
alpha = 0.05;
left = (n-1)*std(x)*std(x)
right = x_mean - (sigma /sqrt(n)) * norminv(alpha/2, 0, 1);
fprintf("Confidence interval is: (%f, %f)\n", left, right);