package model.threads;

import model.Matrix;
import model.tasks.ColumnTask;
import model.tasks.KthElementTask;
import model.tasks.MatrixTask;
import model.tasks.RowTask;

public class ThreadCreation {

    public static RowTask createRowTask(int threadsCount, int threadIndex,
                                        Matrix matrix1, Matrix matrix2, Matrix finalMatrix) {
        int totalPositionsCount = finalMatrix.rows * finalMatrix.columns;
        int taskPositionsCount = totalPositionsCount / threadsCount;

        int rowStartIndex = taskPositionsCount * threadIndex / finalMatrix.rows;
        int columnStartIndex = taskPositionsCount * threadIndex % finalMatrix.columns;

        // if it's the last thread, also get the remaining positions
        if (threadIndex == threadsCount - 1) {
            taskPositionsCount += totalPositionsCount % threadsCount;
        }

        return new RowTask(rowStartIndex, columnStartIndex, taskPositionsCount, matrix1, matrix2, finalMatrix);
    }

    public static MatrixTask createColumnTask(int threadsCount, int threadIndex,
                                                    Matrix matrix1, Matrix matrix2, Matrix finalMatrix) {
        int totalPositionsCount = finalMatrix.rows * finalMatrix.columns;
        int taskPositionsCount = totalPositionsCount / threadsCount;

        int rowStartIndex = taskPositionsCount * threadIndex % finalMatrix.rows;
        int columnStartIndex = taskPositionsCount * threadIndex / finalMatrix.columns;

        // if it's the last thread, also get the remaining positions
        if (threadIndex == threadsCount - 1) {
            taskPositionsCount += totalPositionsCount % threadsCount;
        }

        return new ColumnTask(rowStartIndex, columnStartIndex, taskPositionsCount, matrix1, matrix2, finalMatrix);
    }

    public static MatrixTask createKthElementTask(int threadsCount, int threadIndex,
                                                 Matrix matrix1, Matrix matrix2, Matrix finalMatrix) {
        int totalPositionsCount = finalMatrix.rows * finalMatrix.columns;
        int k = threadsCount;

        if (totalPositionsCount % k != 0) {
            try {
                throw new Exception("Matrix elements count " + totalPositionsCount +
                        " not divisible with threads count (k) " + threadsCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int taskPositionsCount = totalPositionsCount / threadsCount;

        int rowStartIndex = threadIndex / finalMatrix.columns;
        int columnStartIndex = threadIndex % finalMatrix.columns;

        return new KthElementTask(rowStartIndex, columnStartIndex, taskPositionsCount, matrix1, matrix2, finalMatrix, k);
    }
}
