package wariantZMozliwosciaZaglodzenia;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int[] liczbaFilozofow = {5, 10, 15};
        int[] liczbaPosilkow = {5, 10, 15};

        for(int f : liczbaFilozofow) {
            for(int p : liczbaPosilkow) {
                System.out.println("Uczta - " + f + " filozofow, " + p + " posilkow");
                uczta(f, p);
                System.out.println("-----------------------------------------------");
            }
        }
    }

    public static void uczta(int liczbaFilozofow, int liczbaPosilkow) throws InterruptedException {

        // "stol"
        Widelec[] sztucce = new Widelec[liczbaFilozofow];

        for(int i = 0; i < liczbaFilozofow; i++) {
            sztucce[i] = new Widelec();
        }

        Semaphore dostepDoSztuccy = new Semaphore(1);

        // filozofowie
        Filozof[] filozofowie = new Filozof[liczbaFilozofow];

        for(int i = 0; i < liczbaFilozofow; i++) {
            filozofowie[i] = new Filozof(i, liczbaPosilkow, sztucce, dostepDoSztuccy);
        }

        // start uczty
        for(Filozof filozof: filozofowie) {
            filozof.start();
        }

        for(Filozof filozof: filozofowie) {
            filozof.join();
        }

        for(Filozof filozof: filozofowie) {
            System.out.println("\tFilozof " + filozof.getIndex()
                                + " oczekiwal srednio: " + filozof.sredniCzasOczekiwania());
        }
    }
}
