package bob.function;
import bob.exceptions.*;

public class Parser {

    public Boolean isChatOver(String userInput) {
        if (userInput.trim().equalsIgnoreCase("Bye")) {
            return true;
        } else {
            return false;
        }
    }


    public String processInput(String userInput, TaskList taskList) throws MissingDescriptionException, UnknownCommandException, AlreadyDoneException, AlreadyUndoneException, UnfoundTaskException, StringIndexOutOfBoundsException, ArrayIndexOutOfBoundsException {
        String command = userInput.split(" ")[0].trim().toLowerCase();
        String messageInstruction;
        switch (command) {
        case "list":
            messageInstruction = "list";
            break;
        case "mark":
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            int taskToMark = Integer.parseInt(userInput.trim().split(" ")[1]);
            if (taskToMark > taskList.getNumTasks()) {
                throw new UnfoundTaskException();
            }
            if (taskList.getTask(taskToMark - 1).getDone()) {
                throw new AlreadyDoneException();
            }
            taskList.getTask(taskToMark - 1).setDone(true);
            messageInstruction = "mark " + taskList.getTask(taskToMark - 1).displayTask();
            break;
        case "unmark":
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            taskToMark = Integer.parseInt(userInput.trim().split(" ")[1]);
            if (taskToMark > taskList.getNumTasks()) {
                throw new UnfoundTaskException();
            }
            if (!taskList.getTask(taskToMark - 1).getDone()) {
                throw new AlreadyUndoneException();
            }
            taskList.getTask(taskToMark-1).setDone(false);
            messageInstruction = "unmark " + taskList.getTask(taskToMark-1).displayTask();
            break;
        case "todo":
        case "deadline":
        case "event":
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            taskList.addTask(userInput);
            messageInstruction = "add " + taskList.getTask(taskList.getNumTasks() - 1).displayTask();
            break;
        case "delete":
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            int taskToDelete = Integer.parseInt(userInput.trim().split(" ")[1]);
            messageInstruction = "delete " + taskList.getTask(taskToDelete - 1).displayTask();
            taskList.deleteTask(taskToDelete-1);
            break;
        default:
            throw new UnknownCommandException();
        }
        return messageInstruction;
    }



}