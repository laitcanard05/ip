package bob.function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class Storage {
    File savedTasks;
    String filepath;

    public Storage(String filepath) throws IOException {
        this.filepath = filepath;
        savedTasks = new File(filepath);
        if (!savedTasks.exists()) {
            Files.createDirectories(Paths.get(filepath));
            Files.createFile(Paths.get(filepath));
        }
    }

    public String loadTasks() throws FileNotFoundException {
        Scanner reader = new Scanner(savedTasks);
        String savedTasksAsString = "";
        while (reader.hasNextLine()) {
            savedTasksAsString += reader.nextLine() + "\n";
        }
        return savedTasksAsString;
    }

    public String getTaskListAsText(TaskList taskList) {
        String taskListAsText = "";
        int numTasks = taskList.getNumTasks();
        for (int i = 0; i < numTasks; i++) {
            if (taskList.getTask(i) instanceof Todo) {
                taskListAsText += "T | " + (taskList.getTask(i).getDone() ? "X | " : "O | ") + taskList.getTask(i).getName() + "\n";
            } else if (taskList.getTask(i) instanceof Deadline) {
                taskListAsText += "D | " + (taskList.getTask(i).getDone() ? "X | " : "O | ") + taskList.getTask(i).getName() + " | " + ((Deadline) taskList.getTask(i)).getDueDate() + "\n";
            } else if (taskList.getTask(i) instanceof Event) {
                taskListAsText += "E | " + (taskList.getTask(i).getDone() ? "X | " : "O | ") + taskList.getTask(i).getName() + " | " + ((Event) taskList.getTask(i)).getStart() + " | " + ((Event) taskList.getTask(i)).getEnd() + "\n";
            }
        }
        return taskListAsText;
    }

    public void saveTasks(TaskList taskList) throws IOException {
        FileWriter fw = new FileWriter(filepath);
        fw.write(getTaskListAsText(taskList));
        fw.close();
    }
}
