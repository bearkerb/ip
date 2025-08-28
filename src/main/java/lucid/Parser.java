package lucid;

import java.util.Scanner;

public class Parser {
    private static TaskList taskList = new TaskList();

    public static void parse() {
        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (true) {
            try {
                userInput = scanner.nextLine();
                if (userInput.contains("|")) {
                    Ui.invalidCharacterDetectedMessage();
                    continue;
                }
                String firstWord = userInput.split(" ")[0];

                if (userInput.equals("bye")) {
                    Ui.farewell();
                    return;
                } else if (userInput.equals("list")) {
                    taskList.printTasks();
                } else if (firstWord.equals("mark")) {
                    handleMarkCommand(userInput);
                } else if (firstWord.equals("unmark")) {
                    handleUnmarkCommand(userInput);
                } else if (firstWord.equals("todo")) {
                    handleToDoCommand(userInput);
                } else if (firstWord.equals("deadline")) {
                    handleDeadlineCommand(userInput);
                } else if (firstWord.equals("event")) {
                    handleEventCommand(userInput);
                } else if (firstWord.equals("delete")) {
                    handleDeleteCommand(userInput);
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

    public static void handleMarkCommand(String userInput) {
        String[] separatedInputs = userInput.split(" ");
        String taskIndex = "";
        for (int i = 1; i < separatedInputs.length; i++) {
            taskIndex += separatedInputs[i];
            if (i != separatedInputs.length - 1) {
                taskIndex += " ";
            }
        }
        taskList.completeTask(Integer.parseInt(taskIndex));
    }

    public static void handleUnmarkCommand(String userInput) {
        String[] separatedInputs = userInput.split(" ");
        String taskIndex = "";
        for (int i = 1; i < separatedInputs.length; i++) {
            taskIndex += separatedInputs[i];
            if (i != separatedInputs.length - 1) {
                taskIndex += " ";
            }
        }
        taskList.uncompleteTask(Integer.parseInt(taskIndex));
    }

    public static void handleToDoCommand(String userInput) throws ToDoEmptyException {
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingInput = userInput.substring(firstSpaceIndex + 1);
        if (remainingInput.equals("todo") || remainingInput.isEmpty()) {
            throw new ToDoEmptyException();
        }
        taskList.addTask(new ToDo(remainingInput));
    }

    public static void handleDeadlineCommand(String userInput) throws DeadlineUsageException {
        if (!userInput.contains("/by")) {
            throw new DeadlineUsageException();
        }
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingInput = userInput.substring(firstSpaceIndex + 1);

        String[] args = remainingInput.split("/by");
        String name = args[0].trim();
        String due = args[1].trim();

        if (due.matches("\\d{4}-\\d{2}-\\d{2}")) {
            taskList.addTask(new Deadline(name, due));
        } else if (due.matches("\\d{4}-\\d{2}-\\d{2}-\\d{4}")) {
            taskList.addTask((new Deadline(name, due.substring(0, 10), due.substring(11))));
        } else {
            throw new DeadlineUsageException();
        }
    }

    public static void handleEventCommand(String userInput) throws EventUsageException {
        if (!userInput.contains("/from") || !userInput.contains("/to")) {
            throw new EventUsageException();
        }
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingInput = userInput.substring(firstSpaceIndex + 1);

        String[] args = remainingInput.split("/from");
        String name = args[0].trim();

        String[] times = args[1].split("/to");
        String start = times[0].trim();
        String end = times[1].trim();

        taskList.addTask((new Event(name, start, end)));
    }

    public static void handleDeleteCommand(String userInput) throws DeleteUsageException {
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingWords = userInput.substring(firstSpaceIndex + 1);
        if (remainingWords.equals("delete") || remainingWords.isEmpty()) {
            throw new DeleteUsageException();
        }
        taskList.deleteTask(Integer.parseInt(remainingWords));
    }
}