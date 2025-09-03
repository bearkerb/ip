package lucid;

import java.util.Scanner;

/**
 * Class with static fields and methods to handle the retrieval and interpretation of user inputs
 * Contains a TaskList to store information on existing tasks
 */
public class Parser {
    private static TaskList taskList = new TaskList();

    /**
     * Static method to retrieve user input and process it, calling appropriate function to handle it
     */
//    public static void parse() {
//        Scanner scanner = new Scanner(System.in);
//        String userInput;
//
//        while (true) {
//            try {
//                userInput = scanner.nextLine();
//                if (userInput.contains("|")) {
//                    Ui.invalidCharacterDetectedMessage();
//                    continue;
//                }
//                String firstWord = userInput.split(" ")[0];
//
//                if (userInput.equals("bye")) {
//                    Ui.farewell();
//                    return;
//                } else if (userInput.equals("list")) {
//                    taskList.printTasks();
//                } else if (firstWord.equals("mark")) {
//                    handleMarkCommand(userInput);
//                } else if (firstWord.equals("unmark")) {
//                    handleUnmarkCommand(userInput);
//                } else if (firstWord.equals("find")) {
//                    handleFindCommand(userInput);
//                } else if (firstWord.equals("todo")) {
//                    handleToDoCommand(userInput);
//                } else if (firstWord.equals("deadline")) {
//                    handleDeadlineCommand(userInput);
//                } else if (firstWord.equals("event")) {
//                    handleEventCommand(userInput);
//                } else if (firstWord.equals("delete")) {
//                    handleDeleteCommand(userInput);
//                } else {
//                    throw new UnknownCommandException();
//                }
//            } catch (LucidException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }

    public static String parse2(String userInput) {

        try {
            if (userInput.contains("|")) {
                return Ui.invalidCharacterDetectedMessage();
            }
            String firstWord = userInput.split(" ")[0];

            if (userInput.equals("bye")) {
                Ui.farewell();
                return Ui.farewell();
            } else if (userInput.equals("list")) {
                return taskList.printTasks();
            } else if (firstWord.equals("mark")) {
                return handleMarkCommand(userInput);
            } else if (firstWord.equals("unmark")) {
                return handleUnmarkCommand(userInput);
            } else if (firstWord.equals("find")) {
                return handleFindCommand(userInput);
            } else if (firstWord.equals("todo")) {
                return handleToDoCommand(userInput);
            } else if (firstWord.equals("deadline")) {
                return handleDeadlineCommand(userInput);
            } else if (firstWord.equals("event")) {
                return handleEventCommand(userInput);
            } else if (firstWord.equals("delete")) {
                return handleDeleteCommand(userInput);
            } else {
                throw new UnknownCommandException();
            }
        } catch (LucidException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

    }

    /**
     * Returns a String array containing separated date and time string representations of the input string
     * If input string has no time information included, index 1 of returned array is null
     *
     * @param dateTimeString String containing date and time information retrieved from data file
     *                       Has form yyyy-mm-dd (or yyyy-mm-dd-xxxx to include time)
     * @return String array where index 0 contains date string, index 1 contains time string if any
     * @throws DateTimeParseException if input string does not adhere to expected format
     */
    public static String[] parseDateTimeString(String dateTimeString) throws DateTimeParseException {
        String[] dateAndTime;
        if (dateTimeString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            dateAndTime = new String[]{dateTimeString, null};
        } else if (dateTimeString.matches("\\d{4}-\\d{2}-\\d{2}-\\d{4}")) {
            dateAndTime = new String[]{dateTimeString.substring(0, 10), dateTimeString.substring(11)};
        } else {
            throw new DateTimeParseException();
        }
        return dateAndTime;
    }

    /**
     * Marks appropriate indexed task as complete
     *
     * @param userInput contains keyword "mark" and index of task to mark as complete
     */
    public static String handleMarkCommand(String userInput) {
        String[] separatedInputs = userInput.split(" ");
        String taskIndex = "";
        for (int i = 1; i < separatedInputs.length; i++) {
            taskIndex += separatedInputs[i];
            if (i != separatedInputs.length - 1) {
                taskIndex += " ";
            }
        }
        return taskList.completeTask(Integer.parseInt(taskIndex));
    }

    /**
     * Marks appropriate indexed task as uncomplete
     *
     * @param userInput contains keyword "unmark" and index of task to mark as complete
     */
    public static String handleUnmarkCommand(String userInput) {
        String[] separatedInputs = userInput.split(" ");
        String taskIndex = "";
        for (int i = 1; i < separatedInputs.length; i++) {
            taskIndex += separatedInputs[i];
            if (i != separatedInputs.length - 1) {
                taskIndex += " ";
            }
        }
        return taskList.uncompleteTask(Integer.parseInt(taskIndex));
    }

    /**
     * Adds a todo to list of tasks
     *
     * @param userInput String containing command and name of task
     * @throws ToDoEmptyException Exception resulting from incorrect string format
     */
    public static String handleToDoCommand(String userInput) throws ToDoEmptyException {
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingInput = userInput.substring(firstSpaceIndex + 1).trim();
        if (remainingInput.equals("todo") || remainingInput.isEmpty()) {
            throw new ToDoEmptyException();
        }
        return taskList.addTask(new ToDo(remainingInput));
    }

    /**
     * Adds a deadline to list of tasks
     *
     * @param userInput String containing command and information of deadline task
     * @throws DeadlineUsageException Exception resulting from incorrect string format
     */
    public static String handleDeadlineCommand(String userInput) throws DeadlineUsageException {
        if (!userInput.contains("/by")) {
            throw new DeadlineUsageException();
        }
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingInput = userInput.substring(firstSpaceIndex + 1);

        String[] args = remainingInput.split("/by");
        String name = args[0].trim();
        if (name.isEmpty()) {
            throw new DeadlineUsageException();
        }
        String due = args[1].trim();

        if (due.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return taskList.addTask(new Deadline(name, due));
        } else if (due.matches("\\d{4}-\\d{2}-\\d{2}-\\d{4}")) {
            return taskList.addTask((new Deadline(name, due.substring(0, 10), due.substring(11))));
        } else {
            throw new DeadlineUsageException();
        }
    }

    /**
     * Adds an event to list of tasks
     *
     * @param userInput String containing command and information of event task
     * @throws EventUsageException Exception resulting from incorrect string format
     */
    public static String handleEventCommand(String userInput) throws EventUsageException {
        if (!userInput.contains("/from") || !userInput.contains("/to")) {
            throw new EventUsageException();
        }
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingInput = userInput.substring(firstSpaceIndex + 1);

        String[] args = remainingInput.split("/from");
        String name = args[0].trim();
        if (name.isEmpty()) {
            throw new EventUsageException();
        }

        String[] times = args[1].split("/to");
        String start = times[0].trim();
        String end = times[1].trim();

        return taskList.addTask((new Event(name, start, end)));
    }

    /**
     * Deletes a task from the list of tasks
     *
     * @param userInput index of task (according to list) to delete
     * @throws DeleteUsageException Exception resulting from incorrect usage
     */
    public static String handleDeleteCommand(String userInput) throws DeleteUsageException {
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingWords = userInput.substring(firstSpaceIndex + 1);
        if (remainingWords.equals("delete") || remainingWords.isEmpty()) {
            throw new DeleteUsageException();
        }
        return taskList.deleteTask(Integer.parseInt(remainingWords));
    }

    /**
     * Searches and prints tasks that suit the user input
     *
     * @param userInput String containing command and name substring to search for
     * @throws FindUsageException
     */
    public static String handleFindCommand(String userInput) throws FindUsageException {
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingInput = userInput.substring(firstSpaceIndex + 1).trim();
        if (remainingInput.equals("find") || remainingInput.isEmpty()) {
            throw new FindUsageException();
        }
        return taskList.findAndPrintTasks(remainingInput.trim());
    }
}
