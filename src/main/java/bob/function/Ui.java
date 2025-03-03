package bob.function;

import java.util.ArrayList;
import java.util.Arrays;

public class Ui {
    protected String outputMessage;

    public Ui() {
        outputMessage = "Hello! I'm Bob" + ".\nWhat can I do for you?";
        printMessage();
    }

    public void setMessage(String messageInstruction, TaskList taskList) {
        int numTasks = taskList.getNumTasks();
        String command = messageInstruction.trim().split(" ")[0];
        String taskDisplay = "";
        if (!(command.equals("list") || command.equals("bye"))) {
            taskDisplay = messageInstruction.trim().substring(messageInstruction.indexOf(" ") + 1);
        }
        switch(command) {
        case "list":
            outputMessage = "Here are the tasks in your list:";
            for (int i = 0; i < numTasks; i++) {
                outputMessage += String.format("\n%d. %s", i+1, taskList.getTask(i).displayTask());
            }
            break;
        case "mark":
            outputMessage = "Nice! I've marked this task as done:\n\t" + taskDisplay;
            break;
        case "unmark":
            outputMessage = "OK, I've marked this task as not done yet:\n\t" + taskDisplay;
            break;
        case "add":
            outputMessage = String.format("Got it. I've added this task:\n\t%s\nNow you have %d tasks in the list.", taskDisplay, numTasks);
            break;
        case "delete":
            outputMessage = String.format("Noted. I've removed this task:\n\t%s\nNow you have %d tasks in the list.", taskDisplay, numTasks);
            break;
        case "find":
            String[] matchingTasksIndex = taskDisplay.split("");
            outputMessage = "Here are the matching tasks in your list:";
            int count = 1;
            for (int i = 0; i < matchingTasksIndex.length; i++) {
                int matchingTaskIndex = Integer.parseInt(matchingTasksIndex[i]);
                outputMessage += String.format("\n%d. %s", count, taskList.getTask(matchingTaskIndex).displayTask());
                count++;
            }
            break;
        default:
            break;
        }
    }

    public void processError(String errorType) {
        switch (errorType) {
        case "Unknown Command":
            outputMessage = "I am unable to process this command. The available commands are\n1.list\n2.todo\n3.deadline\n4.event\n5.mark\n6.unmark\n7.mark\n8.bye\n9.delete\n10.find";
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
        case "IO Exception":
            outputMessage = "There was some problems opening the file. Please try again.";
            break;
        case "Missing Search Term":
            outputMessage = "The keyword to search for is missing. Please try again.";
        default:
            break;
        }
    }

    public void printMessage() {
        System.out.println(outputMessage);
    }

    public void exit() {
        System.out.println("Bye! Hope to see you again soon!");
    }
}
