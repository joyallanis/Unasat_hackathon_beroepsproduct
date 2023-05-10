import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Delete {
    public void DeleteInformation() {
        try (Connection conn = DatabaseConnection.getConnection()) { // Get a connection to the database
            Scanner scanner = new Scanner(System.in); //Create a scanner object to read user input
            System.out.println("What do you want to delete?"); // prints the message "What do you want to delete?" to the console.
            System.out.println("1. Student"); //represents a menu option for deleting a student.
            System.out.println("2. Team"); //represents a menu option for deleting a student.
            System.out.print("Enter your choice: "); // prints the message "Enter your choice: " prompting the user to enter their choice.
            int choice = scanner.nextInt(); //expect the user to enter an integer (a whole number) as input.
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    deleteStudent(conn, scanner); //This method handles the deletion of a student.
                    break; // the "break" statement is used within a switch statement to immediately exit the switch block and continue executing the code after the switch block.
                case 2:
                    deleteTeam(conn, scanner); //This method handles the deletion of a team.
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }

            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteStudent(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Student number: ");
        String student_number = scanner.nextLine();

        // Delete the student's information from the database
        String deleteSql = "DELETE FROM student WHERE studenten_nummer = ?";
        PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
        deleteStmt.setString(1, student_number);
        int rowsDeleted = deleteStmt.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("The information of student with student number " + student_number + " has been deleted.");
        } else {
            System.out.println("No student with student number " + student_number + " was found in the database.");
        }

        deleteStmt.close();
    }

    private void deleteTeam(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Team ID: ");
        int team_id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Delete the students associated with the team
        String deleteStudentsSql = "DELETE FROM student WHERE team_id = ?";
        PreparedStatement deleteStudentsStmt = conn.prepareStatement(deleteStudentsSql);
        deleteStudentsStmt.setInt(1, team_id);
        int studentsDeleted = deleteStudentsStmt.executeUpdate();

        // Delete the team
        String deleteTeamSql = "DELETE FROM team WHERE team_id = ?";
        PreparedStatement deleteTeamStmt = conn.prepareStatement(deleteTeamSql);
        deleteTeamStmt.setInt(1, team_id);
        int teamDeleted = deleteTeamStmt.executeUpdate();

        if (teamDeleted > 0) {
            System.out.println("The team with ID " + team_id + " has been deleted, including all associated students.");
        } else {
            System.out.println("No team with ID " + team_id + " was found in the database.");
        }

        deleteStudentsStmt.close();
        deleteTeamStmt.close();

    }
}
