import java.util.Scanner;

public class Parser {
    public static void parse() {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        TaskList taskList = new TaskList();
        while (true) {
            try {
                userInput = scanner.nextLine();
                String firstWord = userInput.split(" ")[0];
                int firstSpaceIndex = userInput.indexOf(' ');
                String remainingWords = userInput.substring(firstSpaceIndex + 1);

                if (userInput.equals("bye")) {
                    System.out.println("Goodbye. Until next time!");
                    return;
                } else if (userInput.equals("list")) {
                    taskList.printTasks();
                } else if (firstWord.equals("mark")) {
                    String[] separatedInputs = userInput.split(" ");
                    String taskIndex = "";
                    for (int i = 1; i < separatedInputs.length; i++) {
                        taskIndex += separatedInputs[i];
                        if (i != separatedInputs.length - 1) {
                            taskIndex += " ";
                        }
                    }
                    taskList.completeTask(Integer.parseInt(taskIndex));
                } else if (firstWord.equals("unmark")) {
                    String[] separatedInputs = userInput.split(" ");
                    String taskIndex = "";

                    for (int i = 1; i < separatedInputs.length; i++) {
                        taskIndex += separatedInputs[i];
                        if (i != separatedInputs.length - 1) {
                            taskIndex += " ";
                        }
                    }
                    taskList.uncompleteTask(Integer.parseInt(taskIndex));
                } else if (firstWord.equals("todo")) {
                    if (remainingWords.equals("todo") || remainingWords.isEmpty()) {
                        throw new ToDoEmptyException("You can't do that! A todo can't have an empty description!");
                    }
                    taskList.addTask(new ToDo(remainingWords));
                } else if (firstWord.equals("deadline")) {
                    String[] args = remainingWords.split("/by");
                    String name = args[0].trim();
                    String due = args[1].trim();

                    taskList.addTask(new Deadline(name, due));
                } else if (firstWord.equals("event")) {
                    String[] args = remainingWords.split("/from");
                    String name = args[0].trim();
                    String[] times = args[1].split("/to");
                    String start = times[0].trim();
                    String end = times[1].trim();

                    taskList.addTask((new Event(name, start, end)));
                } else {
                    throw new UnknownCommandException("Sorry, I don't get what you are saying.");
                }
            } catch (LucidException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
