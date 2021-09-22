#INPUT 
p = input("Give p: ");
n = input("Give n: ");
N = input("Give N: ");

for i = 1:N
    U = rand (n,1);
    X(i) = sum (U<p);
endfor

unique_values = unique(X);
unique_values_frequencies = histc(X, unique_values);
unique_values_probabilities = unique_values_frequencies / N;
