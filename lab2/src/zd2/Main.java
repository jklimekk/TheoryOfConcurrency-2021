package zd2;

public class Main {
    public static void main(String[] args) {
        SemaforIf semaforIf = new SemaforIf();
        Wyscig wyscig = new Wyscig(semaforIf);

        wyscig.wyscig();
    }
}

/*
Program w folderze zd1 wykorzystujący pętlę while działa bez zarzutów przy każdym uruchomieniu.
Analogiczny program w folderze zd2 ale wykorzystujący instrukcję if zamiast pętli, wręcz przeciwnie.
Przy kilkukrotnym uruchomieniu programu zdarzyły się zarówno deadlocki jak i zakończenie programu z błędnym wynikiem.

Zgodnie z dokumentacją funkcji wait() może się zdarzyć, że oczekujący wątek samoistnie się wybudzi bez otrzymania nofity().
Pętla while zapewnia nam, że po ewentualnym wybudzeniu warunek kontynuacji działania zostanie sprawdzony ponownie i
w przypadku niespełnienia warunku wątek ponownie wejdzie w stan uśpienia.
W przypadku if'a warunek nie zostanie sprawdzony po takim wybudzeniu, w efekcie kilka wątków może wejść jednocześnie do
sekcji krytycznej i ostatecznie dać zły wynik.
 */
