package bob.function;
import bob.exceptions.*;
import java.util.ArrayList;

public class Chatbot {
    protected String outputMessage;
    protected ArrayList<Task> taskList;
    protected int numTasks;

    public Chatbot(String name) {
        outputMessage = "Hello! I'm " + name + ".\nWhat can I do for you?";
        taskList = new ArrayList<>(100);
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
                outputMessage += String.format("\n%d. %s", i+1, taskList.get(i).displayTask());
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
            outputMessage = "Nice! I've marked this task as done:\n\t" + taskList.get(taskIndex).displayTask();
            break;
        case "unmark":
            outputMessage = "OK, I've marked this task as not done yet:\n\t" + taskList.get(taskIndex).displayTask();
            break;
        case "add task":
            outputMessage = String.format("Got it. I've added this task:\n\t%s\nNow you have %d tasks in the list.", taskList.get(taskIndex).displayTask(), numTasks);
            break;
        case "delete":
            outputMessage = String.format("Noted. I've removed this task:\n\t%s\nNow you have %d tasks in the list.", taskList.get(taskIndex).displayTask(), numTasks);
            deleteTask(taskIndex, true);
            break;
        default:
            break;
        }
    }

    public String getTaskListAsText() {
        String taskListAsText = "";
        for (int i = 0; i < numTasks; i++) {
            if (taskList.get(i) instanceof Todo) {
                taskListAsText += "T | " + (taskList.get(i).getDone() ? "X | " : "O | ") + taskList.get(i).getName() + "\n";
            } else if (taskList.get(i) instanceof Deadline) {
                taskListAsText += "D | " + (taskList.get(i).getDone() ? "X | " : "O | ") + taskList.get(i).getName() + " | " + ((Deadline) taskList.get(i)).getDueDate() + "\n";
            } else if (taskList.get(i) instanceof Event) {
                taskListAsText += "E | " + (taskList.get(i).getDone() ? "X | " : "O | ") + taskList.get(i).getName() + " | " + ((Event) taskList.get(i)).getStart() + " | " + ((Event) taskList.get(i)).getEnd() + "\n";
            }
        }
        return taskListAsText;
    }

    public void setErrorMessage(String errorType) {
        switch(errorType) {
        case "Unknown Command":
            outputMessage = "I am unable to process this command. The available commands are\n1.list\n2.todo\n3.deadline\n4.event\n5.mark\n6.unmark\n7.mark\n8.bye\n9.delete";
            break;
        case "Missing Description":
            outputMessage = "The description of the command is missing. Please try again.";
            break;
        case "Task Does Not Exist":
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
        case "Save Failed":
            outputMessage = "There were some problems saving the updated list. Please try again.";
            break;
        default:
            break;
        }
    }

    public void processInput(String userInput) throws MissingDescriptionException, UnknownCommandException, AlreadyDoneException, AlreadyUndoneException, NullPointerException, StringIndexOutOfBoundsException, ArrayIndexOutOfBoundsException {
        String command = userInput.split(" ")[0].trim().toLowerCase();
        switch (command) {
        case "list":
            setMessage("list");
            break;
        case "mark":
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            int taskToMark = Integer.parseInt(userInput.trim().split(" ")[1]);
            if (taskList.get(taskToMark - 1).getDone()) {
                throw new AlreadyDoneException();
            }
            taskList.get(taskToMark - 1).setDone(true);
            setMessage("mark", taskToMark - 1);
            break;
        case "unmark":
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            taskToMark = Integer.parseInt(userInput.trim().split(" ")[1]);
            if (!taskList.get(taskToMark - 1).getDone()) {
                throw new AlreadyUndoneException();
            }
            taskList.get(taskToMark-1).setDone(false);
            setMessage("unmark", taskToMark-1);
            break;
        case "todo":
        case "deadline":
        case "event":
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            addTask(userInput);
            setMessage("add task", numTasks - 1);
            break;
        case "delete":
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingDescriptionException();
            }
            int taskToDelete = Integer.parseInt(userInput.trim().split(" ")[1]);
            deleteTask(taskToDelete-1, false);
            break;
        default:
            throw new UnknownCommandException();
            //Fallthrough
        }
    }

    public void addTask(String userInput) {
        Task newTask;
        String command = userInput.split(" ")[0].trim().toLowerCase();
        switch (command) {
        case "todo":
            String newTaskName = userInput.substring(userInput.indexOf(" ")+1);
            newTask = new Todo(newTaskName);
            break;
        case "deadline":
            newTaskName = userInput.substring(userInput.indexOf(" ")+1, userInput.indexOf("/")).trim();
            String dueDate = userInput.substring(userInput.indexOf("/by")+3).trim();
            newTask = new Deadline(newTaskName, dueDate);
            break;
        case "event":
            newTaskName = userInput.substring(userInput.indexOf(" ")+1, userInput.indexOf("/")).trim();
            String start = userInput.substring(userInput.indexOf("/from")+5, userInput.indexOf("/to")).trim();
            String end = userInput.substring(userInput.indexOf("/to")+3).trim();
            newTask = new Event(newTaskName, start, end);
            break;
        default:
            newTaskName = userInput.substring(userInput.indexOf(" ")+1);
            newTask = new Task(newTaskName);
            break;
        }
        taskList.add(numTasks, newTask);
        numTasks++;
    }

    public void addSavedTasks(String savedTask) {
        String[] savedTaskInArray = savedTask.split("\\|");
        String savedTaskType = savedTaskInArray[0].trim();
        Boolean savedTaskIsDone = savedTaskInArray[1].trim().equals("X");
        String savedTaskName = savedTaskInArray[2].trim();
        Task newTask;
        switch (savedTaskType) {
        case "T":
            newTask = new Todo(savedTaskName, savedTaskIsDone);
            break;
        case "D":
            String savedTaskDueDate = savedTaskInArray[3].trim();
            newTask = new Deadline(savedTaskName, savedTaskDueDate, savedTaskIsDone);
            break;
        case "E":
            String savedTaskStart = savedTaskInArray[3].trim();
            String savedTaskEnd = savedTaskInArray[4].trim();
            newTask = new Event(savedTaskName, savedTaskStart, savedTaskEnd, savedTaskIsDone);
            break;
        default:
            newTask = new Task(savedTaskName, savedTaskIsDone);
        }
        taskList.add(numTasks, newTask);
        numTasks++;
    }

    public void deleteTask(int taskToDelete, Boolean displayed) {
        if (!displayed) {
            numTasks--;
            setMessage("delete", taskToDelete);
        } else {
            taskList.remove(taskToDelete);
        }
    }

}