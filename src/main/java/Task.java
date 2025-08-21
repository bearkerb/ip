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

    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.name;
    }

    public void complete() {
        if (isDone) {
            System.out.println("You sure? This task is already complete!");
            return;
        } else {
            this.isDone = true;
        }
    }

    public void uncomplete() {
        if (!isDone) {
            System.out.println("Try again! This task isn't even complete!");
            return;
        } else {
            this.isDone = false;
        }
    }
}
