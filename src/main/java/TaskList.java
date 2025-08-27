import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        Storage data = new Storage();
        this.tasks = data.readData();
    }

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Added the following task:");
        System.out.println("\t" + task.toString());
        System.out.println("You have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks") + " in your list.");
        Storage data = new Storage();
        data.appendTaskData(task);
    }

    public void printTasks() {
        int size = this.tasks.size();
        for (int i = 1; i <= size; i++) {
            System.out.println(i + ". " + this.tasks.get(i - 1).toString());
        }
    }

    public void completeTask(int index) {
        Task task = this.tasks.get(index - 1);
        if (task.isComplete()) {
            System.out.println("You've already completed this task!");
        } else {
            Storage data = new Storage();
            task.complete();
            data.completeTaskData(index);
            System.out.println("Alright. I've marked this task as completed for you:");
            System.out.println("\t" + task.toString());
        }
    }

    public void uncompleteTask(int index) {
        Task task = this.tasks.get(index - 1);
        if (!task.isComplete()) {
            System.out.println("You haven't even completed this task yet!");
        } else {
            Storage data = new Storage();
            task.uncomplete();
            data.uncompleteTaskData(index);
            System.out.println("No problem, I've marked this task as uncompleted for you:");
            System.out.println("\t" + task.toString());
        }
    }

    public void deleteTask(int index) {
        System.out.println("Got it! I've removed this task for you:");
        System.out.println("\t" + this.tasks.get(index - 1).toString());
        this.tasks.remove(index - 1);
        Storage data = new Storage();
        data.deleteTaskData(index);
        System.out.println("You have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks")
                            + " left in your list.");
    }
}
