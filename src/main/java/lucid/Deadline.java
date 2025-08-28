package lucid;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A task with a deadline, represented by a LocalDate and LocalTime (if needed)
 */
public class Deadline extends Task {
    private LocalDate dueDate;
    private LocalTime dueTime;

    public Deadline(String name, String dueDate) {
        super(name);
        this.dueDate = LocalDate.parse(dueDate);
    }

    public Deadline(String name, String dueDate, String dueTime) {
        super(name);
        this.dueDate = LocalDate.parse(dueDate);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        this.dueTime = LocalTime.parse(dueTime, timeFormatter);
    }

    /**
     * Returns a string representing the deadline
     * @return String representation including type, completion status, name and due date (and time if it exists)
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.dueDate.getMonth() + " " + this.dueDate.getDayOfMonth()
                + " " + this.dueDate.getYear() + (this.dueTime == null ? "" : " " + this.dueTime) + ")";
    }

    /**
     * Returns a string representing the deadline suitable for input to a data file
     * @return String including type, completion status, name, due date (and time if it exists)
     */
    @Override
    public String toDataString() {
        return "D | " + (this.isComplete() ? "DONE" : "NOT DONE") + " | " + this.getName() + " | " + this.dueDate
                + (this.dueTime == null ? "" : "-" + this.dueTime.format(DateTimeFormatter.ofPattern("HHmm"))) + "\n";
    }
}
