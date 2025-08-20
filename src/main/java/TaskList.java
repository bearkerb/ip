import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task t) {
        tasks.add(t);
        System.out.println("added: " + t.toString());
        return;
    }

    public void printTasks() {
        int size = this.tasks.size();
        for (int i = 1; i <= size; i++) {
            System.out.println(i + ". " + this.tasks.get(i - 1).toString());
        }
    }
}
