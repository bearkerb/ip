package lucid;

import java.util.Scanner;

/**
 * Main class, entry point of the progrma
 */
public class Lucid {
    /**
     * Method that starts the chatbot
     */
//    public void run() {
//        Ui.introduction();
//        Parser.parse();
//    }

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userInput = scanner.nextLine();
            String reply = Parser.parse2(userInput);
            System.out.println(reply);
            if (userInput.equals("bye")) {
                return;
            }
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return Parser.parse2(input);
    }

    public static void main(String[] args) {
        new Lucid().start();
    }

}
