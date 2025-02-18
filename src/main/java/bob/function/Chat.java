package bob.function;
import java.nio.file.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import bob.exceptions.*;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Chat {
    public static void main(String[] args) {
        Chatbot Bob = new Chatbot("Bob");
        Bob.printMessage();
        File savedTasks = new File("./src/main/java/data/bob.txt");
        Scanner BobDataReader = null;
        try {
            if (!savedTasks.exists()) {
                Files.createDirectories(Paths.get("./src/main/java/data"));
                Files.createFile(Paths.get("./src/main/java/data/bob.txt"));
            }
            BobDataReader = new Scanner(savedTasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (BobDataReader.hasNextLine()) {
            Bob.addSavedTasks(BobDataReader.nextLine());
        }
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        while (!Bob.isChatOver(userInput)) {
            try {
                Bob.processInput(userInput);
                FileWriter fw = new FileWriter("./src/main/java/data/bob.txt");
                fw.write(Bob.getTaskListAsText());
                fw.close();
            } catch (UnknownCommandException | ArrayIndexOutOfBoundsException e) {
                Bob.setErrorMessage("Unknown Command");
            } catch (MissingDescriptionException e) {
                Bob.setErrorMessage("Missing Description");
            } catch (NullPointerException e) {
                Bob.setErrorMessage("Task Does Not Exist");
            } catch (AlreadyDoneException e) {
                Bob.setErrorMessage("Already Marked As Done");
            } catch (AlreadyUndoneException e) {
                Bob.setErrorMessage("Already Marked As Not Done");
            } catch (StringIndexOutOfBoundsException e) {
                Bob.setErrorMessage("Missing Date Or Time");
            } catch (IOException e) {
                Bob.setErrorMessage("Save Failed");
            }
            Bob.printMessage();
            userInput = in.nextLine();
        }
        Bob.printMessage();
    }
}