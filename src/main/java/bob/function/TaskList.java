package bob.function;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TaskList {
    protected ArrayList<Task> taskList;
    protected int numTasks;

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

    public int getNumTasks() {
        return numTasks;
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

    public Task getTask(int index) {
        return taskList.get(index);
    }

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
            break;
        }
        taskList.add(numTasks, newTask);
        numTasks++;
    }

    public void deleteTask(int taskToDelete) {
            taskList.remove(taskToDelete);
            numTasks--;
    }



}
