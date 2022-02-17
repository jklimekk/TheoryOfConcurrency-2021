package Productions;

import Matrix.Matrix;
import Productions.Utils.AbstractProduction;

public class ProductionC extends AbstractProduction {

    private final int i;
    private final int j;
    private final int k;
    private final double cijk;

    public ProductionC(Matrix matrix, int i, int j, int k, double cijk) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.cijk = cijk;
        this.matrix = matrix;
    }

    @Override
    public synchronized double apply() {
        System.out.println("C" + i + j + k);
        double Mkj = this.matrix.getElement(k, j) - cijk;
        this.matrix.setElement(k, j, Mkj);
        return Mkj;
    }
}
