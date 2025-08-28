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

    public static void taskAddedMessage(Task task) {
        System.out.println("Added the following task:");
        System.out.println("\t" + task.toString());
    }

    public static void taskDeletedMessage(Task task) {
        System.out.println("Got it! I've removed this task for you:");
        System.out.println("\t" + task.toString());
    }

    public static void numberOfTasksMessage(int index) {
        System.out.println("You now have " + index + (index == 1 ? " task" : " tasks") + " in your list.");
    }

    public static void taskCompletedMessage(Task task) {
        System.out.println("Alright. I've marked this task as completed for you:");
        System.out.println("\t" + task.toString());
    }

    public static void taskUncompletedMessage(Task task) {
        System.out.println("No problem, I've marked this task as uncompleted for you:");
        System.out.println("\t" + task.toString());
    }

    public static void invalidTaskIndexMessage() {
        System.out.println("I can't find the task you're talking about! Did you make a typo?");
    }

    public static void printTaskInList(int index, Task task) {
        System.out.println(index + ". " + task.toString());

    }
}
