package lucid;

import java.util.ArrayList;

/**
 * Represents a list of Task objects, implemented with an ArrayList
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Initialises the tasklist according to a data file. If no data file is present, creates a new empty task list
     */
    public TaskList() {
        Storage data = new Storage();
        this.tasks = data.readData();
    }

    /**
     * Adds a task to the TaskList
     * @param task Task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
        Ui.taskAddedMessage(task);
        Ui.numberOfTasksMessage(tasks.size());

        Storage data = new Storage();
        data.appendTaskData(task);
    }

    /**
     * Prints all tasks currently in the list
     */
    public void printTasks() {
        int size = this.tasks.size();
        for (int i = 1; i <= size; i++) {
            Ui.printTaskInFoundList(i, this.tasks.get(i - 1));
        }
    }

    /**
     * Completes a task in the list
     * @param index Index of task to complete
     */
    public void completeTask(int index) {
        if (isInvalidIndex(index)) {
            return;
        }
        Task task = this.tasks.get(index - 1);
        if (task.isComplete()) {
            Ui.taskAlreadyCompletedMessage();
        } else {
            Storage data = new Storage();
            task.complete();
            data.completeTaskData(index);
            Ui.taskCompletedMessage(task);
        }
    }

    /**
     * Uncompletes a task in the list
     * @param index Index of task to uncomplete
     */
    public void uncompleteTask(int index) {
        if (isInvalidIndex(index)) {
            return;
        }
        Task task = this.tasks.get(index - 1);
        if (!task.isComplete()) {
            Ui.taskNotCompletedYetMessage();
        } else {
            Storage data = new Storage();
            task.uncomplete();
            data.uncompleteTaskData(index);
            Ui.taskUncompletedMessage(task);
        }
    }

    /**
     * Deletes a task from the list
     * @param index Index of task to delete
     */
    public void deleteTask(int index) {
        if (isInvalidIndex(index)) {
            return;
        }
        Ui.taskDeletedMessage(this.tasks.get(index - 1));
        this.tasks.remove(index - 1);
        Storage data = new Storage();
        data.deleteTaskData(index);
        Ui.numberOfTasksMessage(tasks.size());
    }

    /**
     * Checks if the given index is valid for the current number of existing tasks
     * @param index Index to check for validity
     * @return false if index < 0 or index > TaskList size, true otherwise
     */
    public boolean isInvalidIndex(int index) {
        if (index > tasks.size() || index <= 0) {
            Ui.invalidTaskIndexMessage();
            return true;
        }
        return false;
    }

    /**
     * Finds task names containing a substring and prints them
     * @param s Substring to search for in task names
     */
    public void findAndPrintTasks(String s) {
        int count = 1;
        Ui.tasksFoundMessage();
        for (Task t : tasks) {
            if (t.getName().contains(s)) {
                Ui.printTaskInFoundList(count, t);
                count++;
            }
        }
    }
}
