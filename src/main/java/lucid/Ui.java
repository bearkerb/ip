package lucid;

/**
 * Class to handle text output to user
 */
public class Ui {
    /**
     * Opening message that greets user when launching the program
     * @return String to print to greet user
     */
    public static String introduction() {
        System.out.println("Hello! I'm Lucid.\nHow can I help you?");
        System.out.println("Just to let you know, I hate the '|' character, so don't use it when talking to me!");
        String out = "Hello! I'm Lucid.\nHow can I help you?\n"
                + "Just to let you know, I hate the '|' character, so don't use it when talking to me!";
        return out;
    }

    /**
     * Farewell message for user exiting program
     * @return String to print for farewell
     */
    public static String farewell() {
        System.out.println("Goodbye. Until next time!");
        return "Goodbye. Until next time!";
    }

    /**
     * Message to notify user of data file creation
     * @return String to print for file creation
     */
    public static String firstTimeUserMessage() {
        System.out.println("Looks like it's your first time here!\nLet me set up your data file for you.");
        return "Looks like it's your first time here!\nLet me set up your data file for you.";
    }

    /**
     * Message to notify user of invalid character usage
     * @return String to print for invalid character detection
     */
    public static String invalidCharacterDetectedMessage() {
        System.out.println("Hey, I told you not to use the '|' character!");
        return "Hey, I told you not to use the '|' character!";
    }

    /**
     * Message to notify user of error reading data
     * @return String to print for data reading error
     */
    public static String readDataErrorMessage() {
        System.out.println("Oh no! Something went wrong reading data...");
        return "Oh no! Something went wrong reading data...";
    }

    /**
     * Message when user tries completing an already complete task
     * @return String to print
     */
    public static String taskAlreadyCompletedMessage() {
        System.out.println("You sure? This task is already complete!");
        return "You sure? This task is already complete!";
    }

    /**
     * Message when user tries unmarking a task not yet complete
     * @return
     */
    public static String taskNotCompletedYetMessage() {
        System.out.println("Try again! This task isn't even complete!");
        return "Try again! This task isn't even complete!";
    }

    /**
     * Notifies user of addition of task to the task list
     * @param task Task added to list
     * @return String for task addition
     */
    public static String taskAddedMessage(Task task) {
        System.out.println("Added the following task:");
        System.out.println("\t" + task.toString());
        String out = "Added the following task:\n" + "\t" + task.toString();
        return out;
    }

    /**
     * Notifies user of deletion of task from the task list
     * @param task Task deleted from list
     * @return String for task deletion
     */
    public static String taskDeletedMessage(Task task) {
        System.out.println("Got it! I've removed this task for you:");
        System.out.println("\t" + task.toString());
        String out = "Got it! I've removed this task for you:\n" + "\t" + task.toString();
        return out;
    }

    /**
     * Notifies user of number of tasks in the list
     * @param numOfTasks
     * @return String for number of tasks
     */
    public static String numberOfTasksMessage(int numOfTasks) {
        System.out.println("You now have " + numOfTasks + (numOfTasks == 1 ? " task" : " tasks") + " in your list.");
        return "\nYou now have " + numOfTasks + (numOfTasks == 1 ? " task" : " tasks") + " in your list.";
    }

    /**
     * Notifies user of successful marking of task as complete
     * @param task Task to mark as complete
     * @return String for task completion
     */
    public static String taskCompletedMessage(Task task) {
        System.out.println("Alright. I've marked this task as completed for you:");
        System.out.println("\t" + task.toString());
        String out = "Alright. I've marked this task as completed for you:\n" + "\t" + task.toString();
        return out;
    }
    /**
     * Notifies user of successful marking of task as uncomplete
     * @param task Task to mark as uncomplete
     * @return String for task uncompletion
     */
    public static String taskUncompletedMessage(Task task) {
        System.out.println("No problem, I've marked this task as uncompleted for you:");
        System.out.println("\t" + task.toString());
        String out = "No problem, I've marked this task as uncompleted for you:\n" + "\t" + task.toString();
        return out;
    }

    /**
     * Notifies user that they have entered an invalid task index
     * @return String for usage of invalid task index
     */
    public static String invalidTaskIndexMessage() {
        System.out.println("I can't find the task you're talking about! Did you make a typo?");
        return "I can't find the task you're talking about! Did you make a typo?";
    }

    /**
     * Message to inform user of tasks they have searched for
     * @return String representing tasks found
     */
    public static String tasksFoundMessage() {
        System.out.println("Here are the tasks I've found that match what you're looking for:");
        return "Here are the tasks I've found that match what you're looking for:";
    }

    /**
     * Prints found task
     * @param index Position of task
     * @param task Task to print
     * @return String specifying task position in found tasks, and task information
     */
    public static String printTaskInFoundList(int index, Task task) {
        System.out.println(index + ". " + task.toString());
        String out = index + ". " + task.toString();
        return out;
    }
}
