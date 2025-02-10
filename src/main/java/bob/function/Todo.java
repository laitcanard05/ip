package bob.function;

public class Todo extends Task {

    public Todo(String name) {
        super(name);
    }

    public String displayTask() {
        return "[T]" + super.displayTask();
    }
}
