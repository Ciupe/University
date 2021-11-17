import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Utils memory = new Utils();
        Producer producer = new Producer(memory);
        Consumer consumer = new Consumer(memory);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(consumer::run);
        executor.submit(producer::run);

        executor.shutdown();
        try {
            if (!executor.awaitTermination(5000, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}