package wariantZMozliwosciaZaglodzenia;

public class Widelec {
    private boolean zajety = false;

    public Widelec() {
    }

    public boolean czyZajety() {
        return zajety;
    }

    public void zajmij() {
        this.zajety = true;
    }

    public void odloz() {
        this.zajety = false;
    }
}
