package zd1;

public class Wyscig {
    private int licznik = 0;
    private final SemaforBinarny semafor;

    public Wyscig(SemaforBinarny semafor) {
        this.semafor = semafor;
    }

    public void wyscig() {
        System.out.println("Start wyscigu!");

        Thread inkrementujacy = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                semafor.P();
                licznik++;
                semafor.V();
            }
        });

        Thread dekrementujacy = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                semafor.P();
                licznik--;
                semafor.V();
            }
        });

        inkrementujacy.start();
        dekrementujacy.start();

        try {
            inkrementujacy.join();
            dekrementujacy.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Koniec wyścigu - stan licznika: " + licznik);

    }
}
