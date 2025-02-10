package bob.function;

public class Deadline extends Task {
    protected String dueDate;

    Deadline(String name, String dueDate){
        super(name);
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String displayTask() {
        return "[D]" + super.displayTask() + " (by: " + dueDate + ")";
    }
}
