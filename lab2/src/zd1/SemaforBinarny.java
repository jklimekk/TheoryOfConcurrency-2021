package zd1;

public class SemaforBinarny {
    private boolean stan = true;

    public SemaforBinarny () {
    }

    public synchronized void P() {
        while(!stan) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stan = false;
        this.notifyAll();
    }

    public synchronized void V() {
        while(stan) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stan = true;
        this.notifyAll();
    }
}
