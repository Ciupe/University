package model.threads;

import model.Matrix;

import java.util.ArrayList;
import java.util.List;

public class NormalThread {
    public static void run(int threadsCount, String task, Matrix matrix1, Matrix matrix2, Matrix productMatrix) {
        List<Thread> threads = new ArrayList<>();

        switch (task) {
            case "row":
                for (int threadIndex = 0; threadIndex < threadsCount; threadIndex++) {
                        threads.add(ThreadCreation.createRowTask(threadsCount, threadIndex, matrix1, matrix2, productMatrix));
                }
                break;
            case "column":
                for (int threadIndex = 0; threadIndex < threadsCount; threadIndex++) {
                    threads.add(ThreadCreation.createColumnTask(threadsCount, threadIndex, matrix1, matrix2, productMatrix));
                }
                break;
            case "kth":
                for (int threadIndex = 0; threadIndex < threadsCount; threadIndex++) {
                    threads.add(ThreadCreation.createKthElementTask(threadsCount, threadIndex, matrix1, matrix2, productMatrix));
                }
                break;
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Product matrix:\n" + productMatrix.toString());
    }
}
