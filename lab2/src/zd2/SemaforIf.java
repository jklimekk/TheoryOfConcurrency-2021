package zd2;

public class SemaforIf {
    private boolean stan = true;

    public SemaforIf () {
    }

    public synchronized void P() {
        if(!stan) {
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
        if(stan) {
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
