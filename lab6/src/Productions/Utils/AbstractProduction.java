package Productions.Utils;

import Matrix.Matrix;

public abstract class AbstractProduction implements IProduction {
    protected Matrix matrix;

    // dla produkcji A: mki
    // dla produkcji B: cijk
    // dla produkcji C: nowe Mkj
    protected double value;

    private final PThread thread = new PThread();

    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void join() throws InterruptedException {
        thread.join();
    }

    private class PThread extends Thread {

        @Override
        public void run() {
            value = apply();
        }
    }

}
