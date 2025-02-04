import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        String line;
        String[] tasks = new String[100];
        int[] taskIsDone = new int[100];
        for (int i = 0; i < 100; i++) {
            taskIsDone[i] = 0;
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
                    if (taskIsDone[i] == 0) {
                        System.out.println((i + 1) + ". [ ] " + tasks[i]);
                    } else {
                        System.out.println((i + 1) + ". [X] " + tasks[i]);
                    }
                }
            } else if (line.contains("unmark")) {
                String[] lineWords = line.split(" ");
                unmarkIndex = Integer.parseInt(lineWords[lineWords.length-1]) - 1;
                if (taskIsDone[unmarkIndex] == 1) {
                    taskIsDone[unmarkIndex] = 0;
                    System.out.println("Ok, I've marked this task as not done yet:\n[ ] " + tasks[markIndex]);
                } else {
                    System.out.println("Task is already marked as not done.");
                }

            } else if (line.contains("mark")) {
                String[] lineWords = line.split(" ");
                markIndex = Integer.parseInt(lineWords[lineWords.length-1]) - 1;
                if (taskIsDone[markIndex] == 0) {
                    taskIsDone[markIndex] = 1;
                    System.out.println("Nice! I've marked this task as done:\n[X] " + tasks[markIndex]);
                } else {
                    System.out.println("Task is already marked as done.");
                }
            } else {
                tasks[taskIndex] = line;
                taskIndex++;
                System.out.println("added: " + line);
            }
            line = in.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
    }

}
