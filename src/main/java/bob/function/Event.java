package bob.function;

public class Event extends Task {
    protected String start;
    protected String end;

    /**
     * Constructs an Event object with the specified name, start time, and end time.
     * This constructor is called when the user input requires a new Event task to be added.
     * The isDone attribute is set to a default value of false.
     *
     * @param name  the name of the event.
     * @param start the start time of the event.
     * @param end   the end time of the event.
     */
    public Event(String name, String start, String end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs an Event object with the specified name, start time, end time, and completion status.
     * This constructor is called when adding a Event saved task where the completion status may be true or false.
     *
     * @param name   the name of the event.
     * @param start  the start time of the event.
     * @param end    the end time of the event.
     * @param isDone the completion status of the event.
     */
    public Event(String name, String start, String end, Boolean isDone) {
        super(name, isDone);
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Displays the Event task in a formatted string.
     * The format includes the task type, task name, completion status, start time, and end time.
     *
     * @return a string representation of the event task.
     */
    public String displayTask() {
        return "[E]" + super.displayTask() + " (from: " + start + " to: " + end + ")";
    }
}
