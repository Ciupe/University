import java.util.concurrent.locks.ReentrantLock;

public class Utils {
    public int[] vector1 = {1, 2, 4, 4, 3, 1};
    public int[] vector2 = {5, 5, 3, 4, 1, 5};

    public int[] productVector = new int [vector1.length];
    public int totalSum = 0;

    ReentrantLock mutex = new ReentrantLock();
    boolean calculatedProduct = false;
}