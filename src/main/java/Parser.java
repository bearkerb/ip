import java.util.Scanner;

public class Parser {
    public static void parse() {
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
