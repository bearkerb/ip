public class Event extends Task {
    private String start;
    private String end;

    public Event(String name, String start, String end) {
        super(name);
        this.start = start;
        this.end = end;

    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }

    @Override
    public String toDataString() {
        return "E | " + (this.isComplete() ? "DONE" : "NOT DONE") + " | " + this.getName()
                + " | " + this.start + " | " + this.end + "\n";
    }
}
