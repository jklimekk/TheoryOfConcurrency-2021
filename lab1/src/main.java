public class main {
    public static void main(String[] args) {
        Counter c = new Counter();

        System.out.println("start: " + c.counter);
        long start = System.nanoTime();

        Thread thread_inc = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                //c.increment();
                c.increment_syn();
            }
        });

        Thread thread_dec = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                //c.decrement();
                c.decrement_syn();
            }
        });

        thread_inc.start();
        thread_dec.start();

        try {
            thread_inc.join();
            thread_dec.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long finish = System.nanoTime();
        System.out.println("end: " + c.counter);

        System.out.println("time: " + (finish - start));

    }
}
