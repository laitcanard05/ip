package bob.function;

public class Deadline extends Task {
    protected String dueDate;

    Deadline(String name, String dueDate){
        super(name);
        this.dueDate = dueDate;
    }

    Deadline(String name, String dueDate, Boolean isDone){
        super(name, isDone);
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
