package lucid;

/**
 * Main class, entry point of the progrma
 */
public class Lucid {
    public void run() {
        Ui.introduction();
        Parser.parse();
    }
    public static void main(String[] args) {
        new Lucid().run();
    }

}
