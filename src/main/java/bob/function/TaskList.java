package bob.function;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskList {
    protected ArrayList<Task> taskList;
    protected int numTasks;

    /**
     * Constructs a TaskList object and initializes it with saved tasks loaded from a text file.
     *
     * @param savedTasks a string containing saved tasks, each task separated by a newline character.
     *                   If the string is empty or only contains whitespace, the task list will be initialized as empty.
     */
    public TaskList(String savedTasks) {
        taskList = new ArrayList<>(100);
        numTasks = 0;
        if (!savedTasks.trim().isEmpty()) {
            String[] savedTasksArray = savedTasks.split("\n");
            for (int i = 0; i < savedTasksArray.length; i++) {
                addSavedTask(savedTasksArray[i]);
            }
        }
    }

    /**
     * Returns the current number of tasks in the task list
     *
     * @return numTasks the number of tasks in the task list
     */
    public int getNumTasks() {
        return numTasks;
    }

    /**
     * Adds a new task to the task list. The type of task to be added is determined by the provided user input.
     * The user input can specify a "todo", "deadline", or "event" task.
     *
     * @param userInput a string containing the task details. The first word should be the task type
     *                  (e.g., "todo", "deadline", "event"), followed by the task description.
     *                  For Deadline tasks, "/by" is followed by the due date.
     *                  For Event tasks, "/from" is followed by the start time, and "/to" is followed by the end time.
     */
    public void addTask(String userInput) throws DateTimeParseException {
        Task newTask;
        String command = userInput.split(" ")[0].trim().toLowerCase();
        switch (command) {
        case "todo":
            String newTaskName = userInput.substring(userInput.indexOf(" ")+1);
            newTask = new Todo(newTaskName);
            break;
        case "deadline":
            newTaskName = userInput.substring(userInput.indexOf(" ")+1, userInput.indexOf("/")).trim();
            String deadline = userInput.substring(userInput.indexOf("/by")+3).trim();
            if (deadline.contains("-") && deadline.contains(":")) {
                LocalDateTime dateTime = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                deadline = dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));
            }
            else if (deadline.contains("-")) {
                LocalDate date = LocalDate.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                deadline = date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
            }
            newTask = new Deadline(newTaskName, deadline);
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

    /**
     * Returns the task at the specified index from the task list.
     *
     * @param index the index of the task to retrieve.
     * @return the task at the specified index.
     */
    public Task getTask(int index) {
        return taskList.get(index);
    }

    /**
     * Adds a saved task to the task list based on the provided string representation of the saved task.
     * The string representation of the saved task contains task details separated by a pipe character '|'.
     * The task type (T for Todo, D for Deadline, E for Event) is the first element of the string.
     * The second element indicates whether the task is done ('X' for done, otherwise not done).
     * The third element contains the task name.
     * For Deadline tasks, the fourth element is the deadline.
     * For Event tasks, the fourth and fifth elements are the start and end times respectively.
     *
     * @param savedTask a string containing the details of the saved task.
     */
    public void addSavedTask(String savedTask) {
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
            String savedTaskDeadline = savedTaskInArray[3].trim();
            newTask = new Deadline(savedTaskName, savedTaskDeadline, savedTaskIsDone);
            break;
        case "E":
            String savedTaskStart = savedTaskInArray[3].trim();
            String savedTaskEnd = savedTaskInArray[4].trim();
            newTask = new Event(savedTaskName, savedTaskStart, savedTaskEnd, savedTaskIsDone);
            break;
        default:
            newTask = new Task(savedTaskName, savedTaskIsDone);
            break;
        }
        taskList.add(numTasks, newTask);
        numTasks++;
    }

    /**
     * Deletes the task at the specified index from the task list.
     *
     * @param taskToDelete the index of the task to be deleted.
     */
    public void deleteTask(int taskToDelete) {
            taskList.remove(taskToDelete);
            numTasks--;
    }



}
