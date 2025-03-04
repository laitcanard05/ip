package bob.function;
import bob.exceptions.*;

public class Parser {

    /**
     * Returns a Boolean value of whether the user input indicates the end of the chat
     * by comparing it with the string "bye".
     *
     * @return {@code true} if the input is "Bye" (ignoring case and spaces), otherwise {@code false}
     * @param userInput the user's input string to check
     */
    public Boolean isChatOver(String userInput) {
        return userInput.trim().equalsIgnoreCase("bye");
    }

    /**
     * Processes user input to execute commands on the given TaskList and
     * returns the corresponding instruction for the output message to be set in ui.
     * For the command {@code list}, the instruction contains the command.
     * For commands {@code mark}, {@code unmark}, {@code delete}, {@code todo}, {@code deadline}, {@code event},
     * the instruction contains the command and the string representation of the task to be displayed in the ui.
     * For the command {@code find}, the instruction contains the command and the string representation of the
     * indexes of all matching tasks in the task list.
     *
     * Supported commands:
     * <ul>
     *     <li>{@code list} - Displays all tasks.</li>
     *     <li>{@code mark <taskIndex>} - Marks a task as done.</li>
     *     <li>{@code unmark <taskIndex>} - Marks a task as not done.</li>
     *     <li>{@code todo <description>}, {@code deadline <description>}, {@code event <description>} - Adds a task to the task list.</li>
     *     <li>{@code delete <taskIndex>} - Removes a task.</li>
     *     <li>{@code find <keyword>} - Finds all matching tasks that contain the keyword in the task description </li>
     * </ul>
     *
     * @param userInput The user's input command string.
     * @param taskList The task list on which operations are performed.
     * @return An instruction message for how to set the output message in ui.
     * @throws MissingDescriptionException If the user input requires task description or task number but none is provided.
     * @throws UnknownCommandException If the command is not recognized.
     * @throws AlreadyDoneException If an attempt is made to mark an already completed task.
     * @throws AlreadyUndoneException If an attempt is made to unmark a task that is not completed.
     * @throws UnfoundTaskException If the specified task number does not exist.
     * @throws StringIndexOutOfBoundsException If string manipulation causes an out-of-bounds error due to missing dates or times in the task description when performing addTask().
     * @throws ArrayIndexOutOfBoundsException If an array indexing error occurs while parsing input due to null user input or user input consisting of only whitespaces.
     */

    public String processInput(String userInput, TaskList taskList) throws MissingDescriptionException, UnknownCommandException, AlreadyDoneException, AlreadyUndoneException, UnfoundTaskException, StringIndexOutOfBoundsException, ArrayIndexOutOfBoundsException, MissingSearchException {
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
            if (taskToDelete > taskList.getNumTasks()) {
                throw new UnfoundTaskException();
            }
            messageInstruction = "delete " + taskList.getTask(taskToDelete - 1).displayTask();
            taskList.deleteTask(taskToDelete-1);
            break;
        case "find":
            if (userInput.trim().split(" ").length < 2) {
                throw new MissingSearchException();
            }
            String keyword = userInput.substring(userInput.indexOf(" ")+1).toLowerCase();
            messageInstruction = "find ";
            for (int i = 0; i < taskList.getNumTasks(); i++) {
                if (taskList.getTask(i).getName().toLowerCase().contains(keyword)) {
                    messageInstruction += Integer.toString(i);
                }
            }
            break;
        default:
            throw new UnknownCommandException();
        }
        return messageInstruction;
    }



}