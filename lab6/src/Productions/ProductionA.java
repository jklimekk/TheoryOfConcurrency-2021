package Productions;

import Matrix.Matrix;
import Productions.Utils.AbstractProduction;

public class ProductionA extends AbstractProduction {

    private final int k;
    private final int i;

    public ProductionA(Matrix matrix, int k, int i) {
        this.k = k;
        this.i = i;
        this.matrix = matrix;
    }

    @Override
    public synchronized double apply() {
        System.out.println("A" + k + i);
        return this.matrix.getElement(k, i) / this.matrix.getElement(i, i); // mki
    }
}
