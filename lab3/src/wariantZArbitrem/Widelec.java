package wariantZArbitrem;

import java.util.concurrent.Semaphore;

public class Widelec {
    private final int index;
    private boolean zajety = false;
    private final Semaphore dostep = new Semaphore(1);

    public Widelec(int index) {
        this.index = index;
    }

    public void zajmij(int filozof) throws InterruptedException {
        boolean zjadl = false;

        while(!zjadl) {
            Thread.sleep(100);

            dostep.acquire();

            if(!zajety) {
                zajety = true;
                zjadl = true;
                System.out.println("Filozof " + filozof + " zajal "
                                    + this.index + " widelec");
            }

            dostep.release();
        }
    }

    public void odloz() throws InterruptedException {
        dostep.acquire();
        this.zajety = false;
        dostep.release();
    }
}
