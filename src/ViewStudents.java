import java.sql.*; // Import necessary libraries for database connectivity
import java.util.HashMap; // Import HashMap class for team tracking
import java.util.Map; // Import Map interface for team tracking
import java.util.Objects; // Import Objects class for null checking
import java.util.Scanner; // Import Scanner class for user input

public class ViewStudents {
    public void View() {
        Scanner scanner = new Scanner(System.in); // Create a scanner object to read user input
        String choice; // Declare a string variable to hold the user's choice
        do {
            System.out.println("Choose an option below then enter: "); // Print instructions for user
            System.out.println("View Teams"); // Print option to view teams
            System.out.println("Return"); // Print option to return to previous menu
            choice = scanner.nextLine().toLowerCase(); // Read user input and convert it to lowercase
            switch (choice) { // Check the value of the user's input
                case "view teams" -> ViewTeams(); // If the user wants to view teams, call the ViewTeams method
                case "return" -> System.out.println("Returning to menu"); // If the user wants to return, print a message
                default -> System.out.println("Invalid choice."); // If the user enters something else, print an error message
            }
        } while (!Objects.equals(choice, "return")); // Continue looping until the user chooses to return
    }

    public void ViewTeams() {
        try (Connection conn = DatabaseConnection.getConnection()) { // Get a connection to the database
            String sql = "SELECT team.team_id, team.team_naam, student.student_id, student.naam, student.achter_naam, contact_gegevens.unasat_emailadres\n" +
                    "                    FROM team\n" +
                    "                    LEFT JOIN student ON team.team_id = student.team_id\n" +
                    "                    LEFT JOIN contact_gegevens ON student.contact_id = contact_gegevens.contact_id;";
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepare a SQL statement
            ResultSet rs = stmt.executeQuery(); // Execute the statement and store the results
            Map<Integer, String> teamMap = new HashMap<>(); // create a HashMap to keep track of teams
            while (rs.next()) { // Iterate over the rows of the result set
                int teamid = rs.getInt("team_id"); // Get the team ID from the current row
                String teamname = rs.getString("team_naam"); // Get the team name from the current row
                int studentid = rs.getInt("student_id"); // Get the student ID from the current row
                String first = rs.getString("naam"); // Get the first name of the student from the current row
                String last = rs.getString("achter_naam"); // Get the last name of the student from the current row
                String email = rs.getString("unasat_emailadres"); // Get the email of the student from the current row
                if (!teamMap.containsKey(teamid)) { // Check if team has already been printed by checking if its ID is in the HashMap
                    System.out.println("\033[34mTeam:\033[0m"); // Print the word "Team:" in blue color using an escape code
                    System.out.println("\033[34mID:\033[0m " + teamid + ", \033[34mName:\033[0m " + teamname ); // Print the team ID and name in blue color using an escape code
                    teamMap.put(teamid, teamname); // Add the team to the HashMap
                }
                System.out.println("\033[34mMembers:\033[0m"); // Print the word "Members:" in blue color using an escape code
                System.out.println("\033[34mID:\033[0m " + studentid + ", \033[34mName:\033[0m " + first + " " + last + ", \033[34mE-mailadres:\033[0m " + email ); // Print the student ID, name, and email in blue color using an escape code
            }
            rs.close(); // Close the result set
            stmt.close(); // Close the statement
        } catch (SQLException ex) { // Catch any SQL exceptions that may occur
            System.out.println("Error: " + ex.getMessage()); // Print the error message
        }
    }
}
