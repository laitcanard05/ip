package bob.function;

public class Task {

    protected String name;
    protected Boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    public Task(String name, Boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String displayTask() {
        return (getDone() ? "[X] " : "[ ] ") + name;
    };
}
