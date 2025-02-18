package bob.function;

public class Todo extends Task {

    public Todo(String name) {
        super(name);
    }

    public Todo(String name, Boolean isDone) {
        super(name, isDone);
    }

    public String displayTask() {
        return "[T]" + super.displayTask();
    }

}
