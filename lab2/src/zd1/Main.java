package zd1;

public class Main {
    public static void main(String[] args) {
        SemaforBinarny semaforBinarny = new SemaforBinarny();
        Wyscig wyscig = new Wyscig(semaforBinarny);

        wyscig.wyscig();
    }
}
