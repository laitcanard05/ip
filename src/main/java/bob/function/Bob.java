package bob.function;
import java.util.Scanner;
import bob.exceptions.*;

import java.io.IOException;
import java.time.format.DateTimeParseException;


public class Bob {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private Parser parser = new Parser();

    /**
     * Constructs a Bob instance with the specified file path for task storage.
     * Initializes the user interface, storage, and task list.
     * If an IOException occurs during task loading, an error message is displayed.
     *
     * @param filepath The file path of the file where tasks are stored.
     */
    public Bob(String filepath) {
        ui = new Ui();
        try {
            storage = new Storage(filepath);
            taskList = new TaskList(storage.loadTasks());
        } catch (IOException e) {
            ui.processError("IO Exception");
            ui.printMessage();
        }
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        while (!parser.isChatOver(userInput)) {
            try {
                ui.setMessage(parser.processInput(userInput, taskList), taskList);
                storage.saveTasks(taskList);
            } catch (UnknownCommandException | ArrayIndexOutOfBoundsException e) {
                ui.processError("Unknown Command");
            } catch (MissingDescriptionException e) {
                ui.processError("Missing Description");
            } catch (UnfoundTaskException e) {
                ui.processError("Task Does Not Exist");
            } catch (AlreadyDoneException e) {
                ui.processError("Already Marked As Done");
            } catch (AlreadyUndoneException e) {
                ui.processError("Already Marked As Not Done");
            } catch (StringIndexOutOfBoundsException e) {
                ui.processError("Missing Date Or Time");
            } catch (MissingSearchException e) {
                ui.processError("Missing Search Term");
            } catch (IOException e) {
                ui.processError("Save Failed");
            } catch (DateTimeParseException e) {
                ui.processError("Invalid Format for Date/Time");
            }
            ui.printMessage();
            userInput = in.nextLine();
        }
        ui.exit();
    }

    public static void main(String[] args) {
        new Bob("src/main/java/data/bob.txt").run();
    }
}