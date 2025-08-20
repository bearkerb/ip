import java.util.Scanner;

public class Parser {
    public static void parse() {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        TaskList taskList = new TaskList();
        while (true) {
            userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                System.out.println("Goodbye. Until next time!");
                return;
            } else if (userInput.equals("list")){
                taskList.printTasks();
            } else {
                taskList.addTask(new Task(userInput));
            }
        }
    }
}
