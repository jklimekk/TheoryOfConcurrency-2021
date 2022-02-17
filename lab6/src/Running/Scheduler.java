package Running;

import Productions.Utils.IProduction;

import java.util.ArrayList;

public class Scheduler {

    private final ArrayList<IProduction> currentProductions;

    public Scheduler() {
        this.currentProductions = new ArrayList<>();
    }

    public void addThread(IProduction production) {
        this.currentProductions.add(production);
    }

    public void startAll() throws InterruptedException {
        for(IProduction p : this.currentProductions) {
            p.start();
        }

        for(IProduction p : this.currentProductions) {
            p.join();
        }

        this.currentProductions.clear();
    }
}
