package wariantZArbitrem;

public class Filozof extends Thread {
    private final int index;
    private final int liczbaPosilkow;
    private final StolZArbitrem stol;
    private long calkowityCzasOczekiwania = 0;

    public Filozof(int index, int liczbaPosilkow, StolZArbitrem stol) {
        this.index = index;
        this.liczbaPosilkow = liczbaPosilkow;
        this.stol = stol;
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
        Widelec lewyWidelec = stol.getSztucce()[index];
        Widelec prawyWidelec = stol.getSztucce()[(index + 1) % stol.getSztucce().length];

        long startPomiaru = System.currentTimeMillis();

        stol.zajmijMiejsce(this.index);

        lewyWidelec.zajmij(this.index);
        prawyWidelec.zajmij(this.index);

        long koniecPomiaru = System.currentTimeMillis();

        calkowityCzasOczekiwania += (koniecPomiaru - startPomiaru);

        // po jedzeniu

        lewyWidelec.odloz();
        prawyWidelec.odloz();

        stol.odejdzOdStolu(this.index);
    }

    public double sredniCzasOczekiwania() {
        return calkowityCzasOczekiwania / (double) liczbaPosilkow;
    }

    public int getIndex() {
        return index;
    }
}
