package bob.function;
import bob.exceptions.*;

public class Chatbot {
    protected String outputMessage;
    protected Task[] taskList;
    protected int numTasks;

    public Chatbot(String name) {
        outputMessage = "Hello! I'm " + name + ".\nWhat can I do for you?";
        taskList = new Task[100];
        numTasks = 0;
    }

    public void printMessage() {
        System.out.println(outputMessage);
    }

    public Boolean isChatOver(String userInput) {
        if (userInput.trim().equalsIgnoreCase("Bye")) {
            setMessage("bye");
            return true;
        } else {
            return false;
        }
    }

    public void setMessage(String command) {
        switch(command) {
        case "list":
            outputMessage = "Here are the tasks in your list:";
            for (int i = 0; i < numTasks; i++) {
                outputMessage += String.format("\n%d. %s", i+1, taskList[i].displayTask());
            }
            break;
        case "bye":
            outputMessage = "Bye! Hope to see you again soon!";
            break;
        default:
            break;
        }
    }

    public void setMessage(String command, int taskIndex) {
        switch(command) {
        case "mark":
            outputMessage = "Nice! I've marked this task as done:\n\t" + taskList[taskIndex].displayTask();
            break;
        case "unmark":
            outputMessage = "OK, I've marked this task as not done yet:\n\t" + taskList[taskIndex].displayTask();
            break;
        case "add task":
            outputMessage = String.format("Got it. I've added this task:\n\t%s\nNow you have %d tasks in the list.", taskList[taskIndex].displayTask(), numTasks);
            break;
        default:
            break;
        }
    }

    public void setErrorMessage(String errorType) {
        switch(errorType) {
        case "Unknown Command":
            outputMessage = "I am unable to process this command. The available commands are\n1.list\n2.todo\n3.deadline\n4.event\n5.mark\n6.unmark\n7.mark\n8.bye";
            break;
        case "Missing Description":
            outputMessage = "The description of the command is missing. Please try again.";
            break;
        case "bob.function.Task Does Not Exist":
            outputMessage = "The task is not found. Please try again.";
            break;
        case "Already Marked As Done":
            outputMessage = "This task is already marked as done.";
            break;
        case "Already Marked As Not Done":
            outputMessage = "This task is already marked as not done.";
            break;
        case "Missing Date Or Time":
            outputMessage = "There are missing dates or times in the description. Please try again.";
            break;
        }
    }

    public void processInput(String userInput) throws MissingDescriptionException, UnknownCommandException, AlreadyDoneException, AlreadyUndoneException, NullPointerException, StringIndexOutOfBoundsException {
        String command = userInput.split(" ")[0].trim().toLowerCase();
        if (command.equals("list")) {
            setMessage("list");
        } else if (command.equals("mark")) {
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            int taskToMark = Integer.parseInt(userInput.trim().split(" ")[1]);
            if (taskList[taskToMark - 1].getDone()) {
                throw new AlreadyDoneException();
            }
            taskList[taskToMark - 1].setDone(true);
            setMessage("mark", taskToMark-1);
        } else if (command.equals("unmark")) {
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            int taskToMark = Integer.parseInt(userInput.trim().split(" ")[1]);
            if (!taskList[taskToMark - 1].getDone()) {
                throw new AlreadyUndoneException();
            }
            taskList[taskToMark-1].setDone(false);
            setMessage("unmark", taskToMark-1);
        } else if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            addTask(userInput);
            setMessage("add task", numTasks-1);
        } else {
            throw new UnknownCommandException();
        }
    }

    public void addTask(String userInput) {
        Task newTask;
        if (userInput.trim().toLowerCase().startsWith("todo")) {
            String newTaskName = userInput.substring(userInput.indexOf(" ")+1);
            newTask = new Todo(newTaskName);
        } else if (userInput.trim().toLowerCase().startsWith("deadline")) {
            String newTaskName = userInput.substring(userInput.indexOf(" ")+1, userInput.indexOf("/")).trim();
            String dueDate = userInput.substring(userInput.indexOf("/by")+3).trim();
            newTask = new Deadline(newTaskName, dueDate);
        } else {
            String newTaskName = userInput.substring(userInput.indexOf(" ")+1, userInput.indexOf("/")).trim();
            String start = userInput.substring(userInput.indexOf("/from")+5, userInput.indexOf("/to")).trim();
            String end = userInput.substring(userInput.indexOf("/to")+3).trim();
            newTask = new Event(newTaskName, start, end);
        }
        taskList[numTasks] = newTask;
        numTasks++;
    }

}