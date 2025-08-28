import java.util.Scanner;

public class Parser {
    public static void parse() {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        TaskList taskList = new TaskList();

        while (true) {
            try {
                userInput = scanner.nextLine();
                if (userInput.contains("|")) {
                    System.out.println("Sorry, you're not allowed to use the '|' character!");
                    continue;
                }
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
                        throw new ToDoEmptyException();
                    }
                    taskList.addTask(new ToDo(remainingWords));
                } else if (firstWord.equals("deadline")) {
                    if (!userInput.contains("/by")) {
                        throw new DeadlineUsageException();
                    }
                    String[] args = remainingWords.split("/by");
                    String name = args[0].trim();
                    String due = args[1].trim();

                    if (due.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        taskList.addTask(new Deadline(name, due));
                    } else if (due.matches("\\d{4}-\\d{2}-\\d{2}-\\d{4}")) {
                        taskList.addTask((new Deadline(name, due.substring(0, 10), due.substring(11))));
                    } else {
                        throw new DeadlineUsageException();
                    }


                } else if (firstWord.equals("event")) {
                    if (!userInput.contains("/from") || !userInput.contains("/to")) {
                        throw new EventUsageException();
                    }

                    String[] args = remainingWords.split("/from");
                    String name = args[0].trim();
                    String[] times = args[1].split("/to");
                    String start = times[0].trim();
                    String end = times[1].trim();


                    taskList.addTask((new Event(name, start, end)));
                } else if (firstWord.equals("delete")) {
                    if (remainingWords.equals("delete") || remainingWords.isEmpty()) {
                        throw new DeleteUsageException();
                    }
                    taskList.deleteTask(Integer.parseInt(remainingWords));
                } else {
                    throw new UnknownCommandException();
                }
            } catch (LucidException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String[] parseDateTimeString(String dateTimeString) throws DateTimeParseException {
        String[] dateAndTime;
        if (dateTimeString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            dateAndTime = new String[] {dateTimeString, null};
        } else if (dateTimeString.matches("\\d{4}-\\d{2}-\\d{2}-\\d{4}")) {
            dateAndTime = new String[] {dateTimeString.substring(0, 10), dateTimeString.substring(11)};
        } else {
            throw new DateTimeParseException();
        }
        return dateAndTime;
    }
}