package lucid;

public class Ui {
    public static void introduction() {
        System.out.println("Hello! I'm Lucid.Lucid.\nHow can I help you?");
        System.out.println("Just to let you know, I hate the '|' character, so don't use it when talking to me!");
    }

    public static void farewell() {
        System.out.println("Goodbye. Until next time!");
    }

    public static void firstTimeUserMessage() {
        System.out.println("Looks like it's your first time here!\nLet me set up your data file for you.");
    }

    public static void invalidCharacterDetectedMessage() {
        System.out.println("Hey, I told you not to use the '|' character!");
    }

    public static void readDataErrorMessage() {
        System.out.println("Oh no! Something went wrong reading data...");
    }

    public static void taskAlreadyCompletedMessage() {
        System.out.println("You sure? This task is already complete!");
    }

    public static void taskNotCompletedYetMessage() {
        System.out.println("Try again! This task isn't even complete!");
    }

    /**
     * Notifies user of addition of task to the task list
     * @param task Task added to list
     */
    public static void taskAddedMessage(Task task) {
        System.out.println("Added the following task:");
        System.out.println("\t" + task.toString());
    }

    /**
     * Notifies user of deletion of task from the task list
     * @param task Task deleted from list
     */
    public static void taskDeletedMessage(Task task) {
        System.out.println("Got it! I've removed this task for you:");
        System.out.println("\t" + task.toString());
    }

    /**
     * Notifies user of number of tasks in the list
     * @param numOfTasks
     */
    public static void numberOfTasksMessage(int numOfTasks) {
        System.out.println("You now have " + numOfTasks + (numOfTasks == 1 ? " task" : " tasks") + " in your list.");
    }

    /**
     * Notifies user of successful marking of task as complete
     * @param task Task to mark as complete
     */
    public static void taskCompletedMessage(Task task) {
        System.out.println("Alright. I've marked this task as completed for you:");
        System.out.println("\t" + task.toString());
    }

    /**
     * Notifies user of successful marking of task as uncomplete
     * @param task Task to mark as uncomplete
     */
    public static void taskUncompletedMessage(Task task) {
        System.out.println("No problem, I've marked this task as uncompleted for you:");
        System.out.println("\t" + task.toString());
    }

    /**
     * Notifies user that they have entered an invalid task index
     */
    public static void invalidTaskIndexMessage() {
        System.out.println("I can't find the task you're talking about! Did you make a typo?");
    }

    public static void tasksFoundMessage() {
        System.out.println("Here are the tasks I've found that match what you're looking for:");
    }
    public static void printTaskInFoundList(int index, Task task) {
        System.out.println(index + ". " + task.toString());
    }
}
