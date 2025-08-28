package lucid;

public class Lucid {
    public void run() {
        Ui.introduction();
        Parser.parse();
    }
    public static void main(String[] args) {
        new Lucid().run();
    }

}
