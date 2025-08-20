import java.util.Scanner;

public class Lucid {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Lucid.\nHow can I help you?");
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        while (true) {
            userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                System.out.println("Goodbye. Until next time!");
                return;
            }
            System.out.println(userInput);
        }
    }
}
