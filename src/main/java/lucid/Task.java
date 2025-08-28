package lucid;

public class Task {
    private String name;
    private boolean isDone;

    public Task(String s) {
        this.name = s;
        this.isDone = false;
    }

    public String getName() {
        return this.name;
    }

    public boolean isComplete() {
        return isDone;
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.name;
    }

    public String toDataString() {
        return "placeholder";
    }

    public void complete() {
        if (isDone) {
            Ui.taskAlreadyCompletedMessage();
        } else {
            this.isDone = true;
        }
    }

    public void uncomplete() {
        if (!isDone) {
            Ui.taskNotCompletedYetMessage();
            return;
        } else {
            this.isDone = false;
        }
    }
}
