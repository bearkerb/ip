public class Deadline extends Task {
    private String dueDate;

    public Deadline(String name, String due) {
        super(name);
        this.dueDate = due;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.dueDate + ")";
    }

    @Override
    public String toDataString() {
        return "E | " + (this.isComplete() ? "DONE" : "NOT DONE") + " | " + this.getName()
                + " | " + this.dueDate + "\n";
    }
}
