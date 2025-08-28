package lucid;

public class ToDo extends Task {
    public ToDo(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toDataString() {
        return "T | " + (this.isComplete() ? "DONE" : "NOT DONE") + " | " + this.getName() + "\n";
    }
}
