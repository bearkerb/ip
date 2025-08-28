package lucid;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        Storage data = new Storage();
        this.tasks = data.readData();
    }

    public void addTask(Task task) {
        tasks.add(task);
        Ui.taskAddedMessage(task);
        Ui.numberOfTasksMessage(tasks.size());
        Storage data = new Storage();
        data.appendTaskData(task);
    }

    public void printTasks() {
        int size = this.tasks.size();
        for (int i = 1; i <= size; i++) {
            Ui.printTaskInList(i, this.tasks.get(i - 1));
        }
    }

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

    public boolean isInvalidIndex(int index) {
        if (index > tasks.size() || index <= 0) {
            Ui.invalidTaskIndexMessage();
            return true;
        }
        return false;
    }
}
