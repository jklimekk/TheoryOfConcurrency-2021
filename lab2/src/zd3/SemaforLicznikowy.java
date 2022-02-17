package zd3;

public class SemaforLicznikowy {
    private int stan;
    private final SemaforBinarny dodatniStan; // informuje czy stan można w danym momencie zmiejszyć
    private final SemaforBinarny zmianaStanuDostepna; // pilnuje aby tylko 1 wątek naraz mógł zmieniać pole stan

    public SemaforLicznikowy(int stan) {
        if(stan < 0) {
            System.out.println("Bledna wartosc stanu semafora");
            System.exit(-1);
        }

        this.stan = stan;
        zmianaStanuDostepna = new SemaforBinarny(true);

        if(stan > 0) {
            dodatniStan = new SemaforBinarny(true);
        } else {
            dodatniStan = new SemaforBinarny(false);
        }

    }

    public void P() {
        dodatniStan.P();
        zmianaStanuDostepna.P();
        {
            stan--;
            if(stan > 0) {
                dodatniStan.V();
            }
        }
        zmianaStanuDostepna.V();
    }

    public void V() {
        zmianaStanuDostepna.P();
        {
            stan++;
            if(stan == 1) {
                dodatniStan.V();
            }
        }
        zmianaStanuDostepna.V();
    }

    public int getStan() {
        return stan;
    }
}
