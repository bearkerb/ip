package lucid;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public Event(String name, String start, String end) {
        super(name);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        try {
            String[] startDateTime = Parser.parseDateTimeString(start);
            String[] endDateTime = Parser.parseDateTimeString(end);
            this.startDate = LocalDate.parse(startDateTime[0]);
            this.endDate = LocalDate.parse(endDateTime[0]);
            if (startDateTime[1] != null) {
                this.startTime = LocalTime.parse(startDateTime[1], timeFormatter);
            }
            if (endDateTime[1] != null) {
                this.endTime = LocalTime.parse(endDateTime[1], timeFormatter);
            }

        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + dateTimeToString(startDate, startTime) + " to: "
                + dateTimeToString(endDate, endTime) + ")";
    }

    @Override
    public String toDataString() {
        return "E | " + (this.isComplete() ? "DONE" : "NOT DONE") + " | " + this.getName() + " | " + this.startDate
                + (this.startTime == null ? "" : "-" + this.startTime.format(DateTimeFormatter.ofPattern("HHmm")))
                + " | " + this.endDate + (this.endTime == null ? "" : "-"
                + this.endTime.format(DateTimeFormatter.ofPattern("HHmm"))) + "\n";
    }

    public String dateTimeToString(LocalDate date, LocalTime time) {
        return date.getMonth() + " " + date.getDayOfMonth() + " " + date.getYear() + (time == null ? "" : " " + time);
    }
}
