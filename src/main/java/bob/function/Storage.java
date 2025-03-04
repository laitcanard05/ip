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

    /**
     * Constructs a Storage instance with the specified file path.
     * Initializes the file for storing tasks. If the file does not exist,
     * it attempts to create the necessary directories and the file.
     *
     * @param filepath The file path of the file where tasks will be stored.
     * @throws IOException If an I/O error occurs while creating the file or directories.
     */
    public Storage(String filepath) throws IOException {
        this.filepath = filepath;
        savedTasks = new File(filepath);
        if (!savedTasks.exists()) {
            Files.createDirectories(Paths.get(filepath));
            Files.createFile(Paths.get(filepath));
        }
    }

    /**
     * Returns a single string representation of the tasks loaded from the saved file,
     * with each task on a new line.
     * Each task is read line by line and appended to the resulting string.
     *
     * @return string representation of loaded tasks
     * @throws FileNotFoundException If the saved tasks file does not exist.
     */
    public String loadTasks() throws FileNotFoundException {
        Scanner reader = new Scanner(savedTasks);
        String savedTasksAsString = "";
        while (reader.hasNextLine()) {
            savedTasksAsString += reader.nextLine() + "\n";
        }
        return savedTasksAsString;
    }

    /**
     * Returns a formatted string representation of the task list,
     * with each task on a new line, to allow the current task list to
     * be saved in a .txt file.
     *
     * @return the formatted string representation of the task list
     * @param taskList The TaskList to be converted into text format.
     */
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

    /**
     * Saves the given TaskList to a file by writing its text representation.
     * The existing content of the file is overwritten.
     *
     * @param taskList The TaskList to be saved.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void saveTasks(TaskList taskList) throws IOException {
        FileWriter fw = new FileWriter(filepath);
        fw.write(getTaskListAsText(taskList));
        fw.close();
    }
}
