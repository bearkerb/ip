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
            } else if (userInput.split(" ")[0].equals("mark")) {
                String[] separatedInputs = userInput.split(" ");
                String taskIndex = "";
                for (int i = 1; i < separatedInputs.length; i++) {
                    taskIndex += separatedInputs[i];
                    if (i != separatedInputs.length - 1) {
                        taskIndex += " ";
                    }
                }
                taskList.completeTask(Integer.parseInt(taskIndex));
            } else if (userInput.split(" ")[0].equals("unmark")) {
                String[] separatedInputs = userInput.split(" ");
                String taskIndex = "";
                for (int i = 1; i < separatedInputs.length; i++) {
                    taskIndex += separatedInputs[i];
                    if (i != separatedInputs.length - 1) {
                        taskIndex += " ";
                    }
                }
                taskList.uncompleteTask(Integer.parseInt(taskIndex));
            } else {
                taskList.addTask(new Task(userInput));
            }
        }
    }
}
