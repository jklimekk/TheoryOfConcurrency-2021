package wariantZArbitrem;

import java.util.concurrent.Semaphore;

public class StolZArbitrem {
    private final int liczbaMiejsc;
    private final Widelec[] sztucce;
    private final boolean[] filozofowie;
    private final Semaphore dostepStol = new Semaphore(1, true);
    private final Semaphore dostepSiedzenia = new Semaphore(1);

    public StolZArbitrem(int liczbaMiejsc) {
        this.liczbaMiejsc = liczbaMiejsc;
        sztucce = new Widelec[liczbaMiejsc];
        filozofowie = new boolean[liczbaMiejsc];

        for(int i = 0; i < liczbaMiejsc; i++) {
            sztucce[i] = new Widelec(i);
        }
    }

    public Widelec[] getSztucce() {
        return sztucce;
    }

    public boolean wolneMiejsca() {
//        int licznikSiedzacych = 0;
//
//        for(boolean f : filozofowie) {
//            if(f) {
//                licznikSiedzacych++;
//
//                if(licznikSiedzacych >= liczbaMiejsc - 1) {
//                    return false;
//                }
//            }
//        }
//
//        return true;

        int licznikSiedzacych = 0;

        for(boolean f : filozofowie) {
            if(f) {
                licznikSiedzacych++;

                if((liczbaMiejsc % 2 == 0 && licznikSiedzacych * 2 == liczbaMiejsc) ||
                        (liczbaMiejsc % 2 != 0 && licznikSiedzacych * 2 == liczbaMiejsc - 1)) {
                    return false;
                }
            }
        }

        return true;

//        boolean ostatniSprawdzanySiedzi = filozofowie[liczbaMiejsc - 1];
//        boolean saSasiedzi = false;
//        int iluSiedzi = 0;
//
//        for (int i = 0; i < liczbaMiejsc; i++) {
//            if (filozofowie[i]) {
//                iluSiedzi++;
//                if (ostatniSprawdzanySiedzi)
//                    saSasiedzi = true;
//                ostatniSprawdzanySiedzi = true;
//            }
//            else
//                ostatniSprawdzanySiedzi = false;
//        }
//
//        return iluSiedzi < 2 || (iluSiedzi == 2 && saSasiedzi);
    }

    public void zajmijMiejsce(int filozof) throws InterruptedException {
        dostepStol.acquire();
        dostepSiedzenia.acquire();

        filozofowie[filozof] = true;
        System.out.println("Filozof " + filozof + " zajmuje miejsce");

        if(wolneMiejsca()) {
            dostepStol.release();
        }

        dostepSiedzenia.release();
    }

    public void odejdzOdStolu(int filozof) throws InterruptedException {
        dostepSiedzenia.acquire();

        filozofowie[filozof] = false;
        System.out.println("Filozof " + filozof + " odchodzi od stolu");

        if(wolneMiejsca()) {
            dostepStol.release();
        }

        dostepSiedzenia.release();

    }
}
