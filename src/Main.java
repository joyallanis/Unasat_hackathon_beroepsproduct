import java.util.Scanner;  // Import the Scanner class from the java.util package.
import java.util.Objects;  // Import the Objects class from the java.util package.

public class Main {
    static ViewStudents view = new ViewStudents();  // Create a static ViewStudents object named view.
    static DatabaseConnection connection = new DatabaseConnection();  // Create a static DatabaseConnection object named connection.

    public static void main(String[] args) {
        System.out.println("\u001B[38;5;208m" + "Welcome to UNASAT HACKATHON");  // Print a welcome message to the console in yellow text.
        System.out.print("\033[0m");  // Reset the console text color.
        Scanner scanner = new Scanner(System.in);  // Create a new Scanner object named scanner to read user input from the console.
        String choice;  // Declare a String variable named choice.
        do {
            System.out.println("Choose an option below then enter: ");  // Print a message to the console.
            System.out.println("view");  // Print a message to the console.
            System.out.println("exit");  // Print a message to the console.
            choice = scanner.next();  // Read the user's input from the console and store it in the choice variable.
            switch (choice) {  // Use a switch statement to determine which code to execute based on the user's choice.
                case "view" -> view.viewStudents();  // If the user enters "view", call the viewStudents() method of the view object.
                case "exit" -> System.out.println("Exiting program...");  // If the user enters "exit", print a message to the console.
                default -> System.out.println("Invalid choice.");  // If the user enters anything else, print an error message to the console.
            }
        } while (!Objects.equals(choice, "exit"));  // Repeat the loop until the user enters "exit".
    }
}
