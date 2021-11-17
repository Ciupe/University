package model.threads;

import model.Matrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class ThreadPool {
    public static void run(int threadsCount, String threadType, Matrix matrix1, Matrix matrix2, Matrix finalMatrix){
        ExecutorService service = Executors.newFixedThreadPool(threadsCount);

        switch (threadType) {
            case "row":
                for (int threadIndex = 0; threadIndex < threadsCount; threadIndex++) {
                    service.submit(ThreadCreation.createRowTask(threadsCount, threadIndex, matrix1, matrix2, finalMatrix));
                }
                break;
            case "column":
                for (int threadIndex = 0; threadIndex < threadsCount; threadIndex++) {
                    service.submit(ThreadCreation.createColumnTask(threadsCount, threadIndex, matrix1, matrix2, finalMatrix));
                }
                break;
            case "kth":
                for (int threadIndex = 0; threadIndex < threadsCount; threadIndex++) {
                    service.submit(ThreadCreation.createKthElementTask(threadsCount, threadIndex, matrix1, matrix2, finalMatrix));
                }
                break;
        }

        service.shutdown();
        try {
            if (!service.awaitTermination(300, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }

            System.out.println("Product matrix:\n" + finalMatrix.toString());
        } catch (InterruptedException ex) {
            service.shutdownNow();
            ex.printStackTrace();
        }
    }
}