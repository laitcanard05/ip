import java.util.Scanner;

public class Chat {
    public static void main(String[] args) {
        Chatbot Bob = new Chatbot("Bob");
        Bob.printMessage();
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        while (!Bob.isChatOver(userInput)) {
            try {
                Bob.processInput(userInput);
            }
            catch (UnknownCommandException e) {
                Bob.setErrorMessage("Unknown Command");
            }
            catch (MissingDescriptionException e) {
                Bob.setErrorMessage("Missing Description");
            }
            catch (NullPointerException e) {
                Bob.setErrorMessage("Task Does Not Exist");
            }
            catch (AlreadyDoneException e) {
                Bob.setErrorMessage("Already Marked As Done");
            }
            catch (AlreadyUndoneException e) {
                Bob.setErrorMessage("Already Marked As Not Done");
            }
            catch (StringIndexOutOfBoundsException e) {
                Bob.setErrorMessage("Missing Date Or Time");
            }
            Bob.printMessage();
            userInput = in.nextLine();
        }
        Bob.printMessage();
    }
}