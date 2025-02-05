import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        String line;
        String[] tasks = new String[100];
        String taskName;
        Boolean[] taskIsDone = new Boolean[100];
        String taskType;
        char[] tasksType = new char[100];
        String dueDate, startDateTime, endDateTime;
        for (int i = 0; i < 100; i++) {
            taskIsDone[i] = false;
        }
        int taskIndex = 0;
        int markIndex = -1;
        int unmarkIndex = -1;
        Scanner in = new Scanner(System.in);
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);
        System.out.println("Hello! I'm Bob!\nWhat can I do for you?");
        line = in.nextLine();

        while (!line.equalsIgnoreCase("bye")) {
            if (line.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskIndex; i++) {
                    System.out.println((i + 1) + ". [" + tasksType[i]  + "]" + (taskIsDone[i] ? "[X] " : "[ ] ") + tasks[i]);
                }
            } else if (line.startsWith("unmark")) {
                String[] lineWords = line.split(" ");
                unmarkIndex = Integer.parseInt(lineWords[lineWords.length-1]) - 1;
                if (taskIsDone[unmarkIndex]) {
                    taskIsDone[unmarkIndex] = false;
                    System.out.println("Ok, I've marked this task as not done yet:\n[" + tasksType[markIndex] + "][ ] " + tasks[markIndex]);
                } else {
                    System.out.println("Task is already marked as not done.");
                }

            } else if (line.startsWith("mark")) {
                String[] lineWords = line.split(" ");
                markIndex = Integer.parseInt(lineWords[lineWords.length-1]) - 1;
                if (!taskIsDone[markIndex]) {
                    taskIsDone[markIndex] = true;
                    System.out.println("Nice! I've marked this task as done:\n[" + tasksType[markIndex] + "][X] " + tasks[markIndex]);
                } else {
                    System.out.println("Task is already marked as done.");
                }
            } else {
                taskType = line.split(" ")[0];
                taskName = line.substring(line.indexOf(" ")+1);
                if (taskType.equalsIgnoreCase("todo")) {
                    tasksType[taskIndex] = 'T';
                    tasks[taskIndex] = taskName;
                } else if (taskType.equalsIgnoreCase("deadline")) {
                    tasksType[taskIndex] = 'D';
                    dueDate = line.substring(line.indexOf("/")+4);
                    taskName = line.substring(0, line.indexOf("/")).trim();
                    taskName += " (by: " + dueDate + ")";
                    tasks[taskIndex] = taskName;
                } else if (taskType.equalsIgnoreCase("event")) {
                    tasksType[taskIndex] = 'E';
                    int indexOfWordFrom = (line.indexOf("/from"));
                    int indexOfWordTo = (line.indexOf("/to"));
                    startDateTime = line.substring(indexOfWordFrom+6, indexOfWordTo).trim();
                    endDateTime = line.substring(indexOfWordTo+4).trim();
                    taskName = line.substring(0, indexOfWordFrom).trim();
                    taskName += " (from: " + startDateTime + " to: " + endDateTime + ")";
                    tasks[taskIndex] = taskName;
                }
                System.out.println("Got it. I've added this task:\n\t[" + tasksType[taskIndex] + "][ ] " + tasks[taskIndex]);
                taskIndex++;
                System.out.println("Now you have " + taskIndex + " tasks in your list.");
            }
            line = in.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
    }

}
