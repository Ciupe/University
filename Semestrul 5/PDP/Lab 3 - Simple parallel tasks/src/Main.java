import model.Matrix;
import model.threads.NormalThread;
import model.threads.ThreadPool;

public class Main {
    public static final String THREAD = "normal";
    public static final String TASK = "row";
    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 5;
    private static final int THREADS_COUNT = 5;

    public static void main(String[] args){
        Matrix matrix1 = new Matrix(ROW_COUNT, COLUMN_COUNT);
        Matrix matrix2 = new Matrix(ROW_COUNT, COLUMN_COUNT);

        matrix1.populate();
        matrix2.populate();

        System.out.println("First matrix:\n" + matrix1);
        System.out.println("Second matrix:\n" + matrix2);

        Matrix finalMatrix = new Matrix(ROW_COUNT, COLUMN_COUNT);
        finalMatrix.neutralizeMatrix();

        float start = System.nanoTime() / 1_000_000f;

        if (THREAD.equals("pool")) {
            ThreadPool.run(THREADS_COUNT, TASK, matrix1, matrix2, finalMatrix);
        } else if (THREAD.equals("normal")) {
            NormalThread.run(THREADS_COUNT, TASK, matrix1, matrix2, finalMatrix);
        }

        float end = System.nanoTime() / 1_000_000f;
        System.out.println("Time elapsed: " + (end - start) / 1000 + " seconds");
    }
}
