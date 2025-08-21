import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task t) {
        tasks.add(t);
        System.out.println("Added the following task:");
        System.out.println("\t" + t.toString());
        System.out.println("You have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks") + " in your list.");
        return;
    }

    public void printTasks() {
        int size = this.tasks.size();
        for (int i = 1; i <= size; i++) {
            System.out.println(i + ". " + this.tasks.get(i - 1).toString());
        }
    }

    public void completeTask(int index) {
        this.tasks.get(index - 1).complete();
        System.out.println("Alright. I've marked this task as completed for you:");
        System.out.println("\t" + this.tasks.get(index - 1).toString());
    }

    public void uncompleteTask(int index) {
        this.tasks.get(index - 1).uncomplete();
        System.out.println("No problem! I've marked this task as uncompleted:");
        System.out.println("\t" + this.tasks.get(index - 1).toString());
    }
}
