package zd3;

/*
Tak, semafor binarny jest szczególnym przypadkiem semafora ogólnego.
W przypadku semafora ogólnego stan może przyjmowac wartości naturalne od 0 do ustalonej liczby nieujemnej n,
przez co blokuje chwilowo działanie wątków próbujących zmienić wartość semafora na liczby spoza tego zakresu.
Wystarczy przyjąć n = 1 i otrzymujemy semafor binarny o analogicznym działaniu.
 */

public class Main {
    public static void main(String[] args) {

        // tworzę semafor o zerowym stanie początkowym
        SemaforLicznikowy semaforLicznikowy = new SemaforLicznikowy(0);

        // tworzę wątek dekrementujący 1000000 razy semafor - powinien czekać aż stan semafora wzrosie ponad 0
        Thread dekrementujacy = new Thread(() -> {
            for (int i = 1; i < 1000001; i++) {
                semaforLicznikowy.P();

                if(i % 10000 == 0) {
                    System.out.println("Dec: wykonalem -10000 (iteracja: " + i + ")");
                }
            }
            System.out.println("Dec: koncze prace");
        });

        System.out.println("Start dec");
        dekrementujacy.start();

        // tworzę wątek inkrementujący 1000000 razy semafor
        Thread inkrementujacy = new Thread(() -> {
            for (int i = 1; i < 1000001; i++) {
                semaforLicznikowy.V();

                if(i % 5000 == 0) {
                    System.out.println("Inc: wykonalem +5000 (iteracja: " + i + ")");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Inc: koncze prace");
        });

        System.out.println("Start inc");
        inkrementujacy.start();

        try {
            inkrementujacy.join();
            dekrementujacy.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Oczekiwany stan semafora: 0");
        System.out.println("Otrzymany stan semafora: " + semaforLicznikowy.getStan());
    }
}
