package lucid;

public class FindUsageException extends LucidException {
    public FindUsageException() {
        super("Something went wrong! Try using it like this: find <name>");
    }
}
