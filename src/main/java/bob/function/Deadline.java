package bob.function;

public class Deadline extends Task {
    protected String deadline;

    /**
     * Constructs a Deadline object with the specified name and deadline.
     * This constructor is called when the user input requires a new Deadline task to be added.
     * The isDone attribute is set to a default value of false.
     *
     * @param name  the name of the task.
     * @param deadline the deadline of the task.
     */
    Deadline(String name, String deadline){
        super(name);
        this.deadline = deadline;
    }

    /**
     * Constructs a Deadline object with the specified name, deadline and completion status.
     * This constructor is called when adding a saved Deadline task where the completion status may be true or false.
     * The isDone attribute is set to a default value of false.
     *
     * @param name  the name of the task.
     * @param deadline the deadline of the task.
     * @param isDone the completion status of the task.
     */
    Deadline(String name, String deadline, Boolean isDone){
        super(name, isDone);
        this.deadline = deadline;
    }

    public String getDueDate() {
        return deadline;
    }

    public void setDueDate(String dueDate) {
        this.deadline = deadline;
    }

    /**
     * Displays the Deadline task in a formatted string.
     * The format includes the task type, task name, completion status, and deadline.
     *
     * @return a string representation of the event task.
     */
    public String displayTask() {
        return "[D]" + super.displayTask() + " (by: " + deadline + ")";
    }
}
