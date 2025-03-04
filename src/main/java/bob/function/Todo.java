package bob.function;

public class Todo extends Task {

    /**
     * Constructs a Todo object with the specified name.
     * This constructor is called when the user input requires a new Todo task to be added.
     * The isDone attribute is set to a default value of false.
     *
     * @param name  the name of the task.
     */
    public Todo(String name) {
        super(name);
    }

    /**
     * Constructs a Todo object with the specified name and completion status.
     * This constructor is called when adding a Todo saved task where the completion status may be true or false.
     *
     * @param name  the name of the task.
     * @param isDone the completion status of the task.
     */
    public Todo(String name, Boolean isDone) {
        super(name, isDone);
    }

    /**
     * Displays the Todo task in a formatted string.
     * The format includes the task type, task name and completion status.
     *
     * @return a string representation of the event task.
     */
    public String displayTask() {
        return "[T]" + super.displayTask();
    }

}
