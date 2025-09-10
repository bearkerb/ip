package lucid;

/**
 * Class with static fields and methods to handle the retrieval and interpretation of user inputs
 * Contains a TaskList to store information on existing tasks
 */
public class Parser {
    private static TaskList taskList = new TaskList();

    /**
     * Static method to retrieve user input and process it, calling appropriate function to handle it
     */
    public static String parse(String userInput) {

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
    public static String handleMarkCommand(String userInput) throws MarkUsageException {
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingInput = userInput.substring(firstSpaceIndex + 1).trim();
        if (remainingInput.equals("mark") || remainingInput.isEmpty()) {
            throw new MarkUsageException();
        }
        try {
            Integer.parseInt(remainingInput);
        } catch (NumberFormatException e) {
            throw new MarkUsageException();
        }
        return taskList.completeTask(Integer.parseInt(remainingInput));
    }

    /**
     * Marks appropriate indexed task as uncomplete
     *
     * @param userInput contains keyword "unmark" and index of task to mark as complete
     */
    public static String handleUnmarkCommand(String userInput) throws UnmarkUsageException {
        int firstSpaceIndex = userInput.indexOf(' ');
        String remainingInput = userInput.substring(firstSpaceIndex + 1).trim();
        if (remainingInput.equals("unmark") || remainingInput.isEmpty()) {
            throw new UnmarkUsageException();
        }
        try {
            Integer.parseInt(remainingInput);
        } catch (NumberFormatException e) {
            throw new UnmarkUsageException();
        }
        return taskList.uncompleteTask(Integer.parseInt(remainingInput));
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
        if (args.length == 0 || args.length == 1) {
            throw new DeadlineUsageException();
        }
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
        if (args.length < 2) {
            throw new EventUsageException();
        }

        String name = args[0].trim();
        if (name.isEmpty()) {
            throw new EventUsageException();
        }

        String[] times = args[1].split("/to");
        if (times.length < 2) {
            throw new EventUsageException();
        }
        String start = times[0].trim();
        String end = times[1].trim();
        if (!isCorrectDateTimeFormat(start) || !isCorrectDateTimeFormat(end)) {
            throw new EventUsageException();
        }
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
        String remainingWords = userInput.substring(firstSpaceIndex + 1).trim();
        if (remainingWords.equals("delete") || remainingWords.isEmpty()) {
            throw new DeleteUsageException();
        }
        try {
            Integer.parseInt(remainingWords);
        } catch (NumberFormatException e) {
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

    /**
     * Checks if a string is appropriately formatted for event and deadline creation
     * @param s String containing date and time information
     * @return true if proper format, false otherwise
     */
    public static boolean isCorrectDateTimeFormat(String s) {
        if (s.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return true;
        } else if (s.matches("\\d{4}-\\d{2}-\\d{2}-\\d{4}")) {
            return true;
        } else {
            return false;
        }
    }
}
