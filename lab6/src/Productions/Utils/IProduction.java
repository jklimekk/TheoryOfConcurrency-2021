package Productions.Utils;

public interface IProduction {

    void join() throws InterruptedException;

    void start();

    double getValue();

    double apply();
}
