package wariantZMozliwosciaZaglodzenia;

import java.util.concurrent.Semaphore;

public class Filozof extends Thread {
    private final int index;
    private final int liczbaPosilkow;
    private final Widelec[] sztucce;
    private final Semaphore dostepDoSztuccy;
    private long calkowityCzasOczekiwania = 0;

    public Filozof(int index, int liczbaPosilkow, Widelec[] sztucce, Semaphore dostepDoSztuccy) {
        this.index = index;
        this.liczbaPosilkow = liczbaPosilkow;
        this.sztucce = sztucce;
        this.dostepDoSztuccy = dostepDoSztuccy;
    }

    @Override
    public void run() {
        for(int i = 0; i < liczbaPosilkow; i++) {
            try {
                jedz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void jedz() throws InterruptedException {
        boolean zjadlem = false;

        Widelec lewyWidelec = sztucce[index];
        Widelec prawyWidelec = sztucce[(index + 1) % sztucce.length];

        long startPomiaru = System.currentTimeMillis();

        while(!zjadlem) {
            Thread.sleep(100);

            dostepDoSztuccy.acquire();

            if(!lewyWidelec.czyZajety() && !prawyWidelec.czyZajety()) {
                lewyWidelec.zajmij();
                prawyWidelec.zajmij();
                zjadlem = true;
                //System.out.println(index + " zjadl");
            }

            dostepDoSztuccy.release();
        }

        long koniecPomiaru = System.currentTimeMillis();

        calkowityCzasOczekiwania += (koniecPomiaru - startPomiaru);

        // po jedzeniu

        dostepDoSztuccy.acquire();

        lewyWidelec.odloz();
        prawyWidelec.odloz();

        dostepDoSztuccy.release();
    }

    public double sredniCzasOczekiwania() {
        return calkowityCzasOczekiwania / (double) liczbaPosilkow;
    }

    public int getIndex() {
        return index;
    }
}
