import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        String line;
        String[] tasks = new String[100];
        int taskIndex = 0;
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
                for (int i = 0; i < taskIndex; i++) {
                    System.out.println((i+1) + ". " + tasks[i]);
                }
            } else{
                tasks[taskIndex] = line;
                taskIndex++;
                System.out.println("added: " + line);
            }
            line = in.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
    }

}
