package Running;

import Matrix.Matrix;
import Productions.ProductionA;
import Productions.ProductionB;
import Productions.ProductionC;

import java.util.ArrayList;

public class Executor {

    private final Matrix matrix;
    private final Scheduler scheduler;

    public Executor(Matrix matrix, Scheduler scheduler) {
        this.matrix = matrix;
        this.scheduler = scheduler;
    }

    public void gaussianElimination() throws InterruptedException {

        for(int i = 0; i < this.matrix.getRows() - 1; i++) {

            ArrayList<ProductionA> productionsA = new ArrayList<>();
            ArrayList<ProductionB> productionsB = new ArrayList<>();

            // FAi
            for(int k = i + 1; k < matrix.getRows(); k++) {
                ProductionA productionA = new ProductionA(matrix, i, k);
                scheduler.addThread(productionA);
                productionsA.add(productionA);
            }

            this.scheduler.startAll();

            // FBi
            int index = 0;
            for(int k = i + 1; k < matrix.getRows(); k++) {

                double mki = productionsA.get(index).getValue();

                for(int j = i; j < matrix.getColumns(); j++) {
                    ProductionB productionB = new ProductionB(matrix, i, j, k, mki);
                    scheduler.addThread(productionB);
                    productionsB.add(productionB);
                }
                index++;
            }

            this.scheduler.startAll();

            // FCi
            index = 0;
            for(int k = i + 1; k < matrix.getRows(); k++) {
                for(int j = i; j < matrix.getColumns(); j++) {
                    double cijk = productionsB.get(index).getValue();
                    ProductionC productionC = new ProductionC(matrix, i, j, k, cijk);
                    scheduler.addThread(productionC);
                    index++;
                }
            }

            this.scheduler.startAll();

            System.out.println(matrix);
        }

        matrix.solve();
    }
}
