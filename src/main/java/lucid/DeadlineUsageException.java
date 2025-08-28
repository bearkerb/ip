package lucid;

public class DeadlineUsageException extends LucidException {
    public DeadlineUsageException() {
        super("Looks like you've used the deadline command incorrectly!\n"
                + "Proper usage: deadline <name> /by yyyy-mm-dd (or yyyy-mm-dd-xxxx to include a timing)");
    }
}
