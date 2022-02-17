public class Counter {
    public int counter = 0;

    public void increment_syn(){
        synchronized (this) {
            counter++;
        }
    }

    public void decrement_syn(){
        synchronized (this) {
            counter--;
        }
    }

    public void increment(){
        counter++;
    }

    public void decrement(){
        counter--;
    }

    public Counter() {
    }
}
