package Productions;

import Matrix.Matrix;
import Productions.Utils.AbstractProduction;

public class ProductionB extends AbstractProduction {

    private final int i;
    private final int j;
    private final int k;
    private final double mki;

    public ProductionB(Matrix matrix, int i, int j, int k, double mki) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.mki = mki;
        this.matrix = matrix;
    }

    @Override
    public synchronized double apply() {
        System.out.println("B" + i + j + k);
        return this.matrix.getElement(i, j) * mki; // cijk
    }

}
