public class EventUsageException extends LucidException {
    public EventUsageException() {
        super("Looks like you've used the event command incorrectly!\n"
                + "Proper usage: event <name> /from yyyy-mm-dd /to yyyy-mm-dd (or yyyy-mm-dd-xxxx to include a timing)");
    }
}
