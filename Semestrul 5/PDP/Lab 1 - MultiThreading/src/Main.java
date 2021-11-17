/*
Tests based on threads, 10000 records, with full locking:

10 threads -> 0.016 seconds
100 threads -> 0.048 seconds
500 threads -> 0.176 seconds
1000 threads -> 0.432 seconds

Tests based on threads, 10000 records, only quantity resource locking:

10 threads -> 0.016 seconds
100 threads -> 0.048 seconds
500 threads -> 0.160 seconds
1000 threads -> 0.384 seconds

Tests based on threads, 10000 records, without locking:

10 threads -> 0.000 seconds
100 threads -> 0.016 seconds
500 threads -> 0.064 seconds
1000 threads -> 0.112 seconds
*/

public class Main {
    public static void main(String[] args){
        Simulation simulation = new Simulation();
        simulation.run();
    }
}
