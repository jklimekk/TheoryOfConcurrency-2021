import Matrix.Matrix;
import Running.Executor;
import Running.Scheduler;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Matrix matrix = new Matrix("in.txt");
        System.out.println(matrix);

        Executor executor = new Executor(matrix, new Scheduler());
        executor.gaussianElimination();

        System.out.println(matrix);
        matrix.save("out.txt");

    }
}
