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
            System.out.println("\n\u001B[38;5;208m" + "Choose an option below then enter: ");
            System.out.print("\033[0m");
            System.out.print("\033[38;5;208m" + "| " + "\033[0m"); // Make the left | blue
            System.out.print("\033[38;5;33m" + "View Teams" + "\033[0m"); // Make the left orange
            System.out.print("\033[38;5;208m" + " | " + "\033[0m"); // Make the middle | orange
            System.out.print("\033[38;5;33m" + "Search Team" + "\033[0m"); // Make the option blue
            System.out.print("\033[38;5;208m" + " | " + "\033[0m"); // Make the middle | orange
            System.out.print("\033[38;5;33m" + "Return" + "\033[0m"); // Make the option blue
            System.out.print("\033[38;5;208m" + " | " + "\033[0m"); // Make the right | orange
            System.out.println();
            choice = scanner.nextLine().toLowerCase(); // Read user input and convert it to lowercase
            switch (choice) { // Check the value of the user's input
                case "view teams" -> ViewTeams(); // If the user wants to view teams, call the ViewTeams method
                case "search team" -> SearchTeamInfo(); // If the user wants to search spedific team information, cal the SearchTeamInfo method
                case "return" -> System.out.println("\nReturning to menu...\n"); // If the user wants to return, print a message
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
                    System.out.println("\n\033[34mTeam:\033[0m"); // Print the word "Team:" in blue color using an escape code
                    System.out.println("\033[34mID:\033[0m " + teamid + ", \033[34mName:\033[0m " + teamname ); // Print the team ID and name in blue color using an escape code
                    System.out.println("\033[34mMembers:\033[0m"); // Print the word "Members:" in blue color using an escape code
                    teamMap.put(teamid, teamname); // Add the team to the HashMap
                }
                System.out.println("\033[34mID:\033[0m " + studentid + ", \033[34mName:\033[0m " + first + " " + last + ", \033[34mE-mailadres:\033[0m " + email ); // Print the student ID, name, and email in blue color using an escape code
            }
            rs.close(); // Close the result set
            stmt.close(); // Close the statement
        } catch (SQLException ex) { // Catch any SQL exceptions that may occur
            System.out.println("Error: " + ex.getMessage()); // Print the error message
        }
    }

    public void SearchTeamInfo() {
        Scanner scanner = new Scanner(System.in); // Create a scanner object to read user input
        System.out.println("\nEnter team name: "); // Ask for user input
        String input = scanner.nextLine(); // Read user input

        try (Connection conn = DatabaseConnection.getConnection()) { // Get a connection to the database
            String sql = "SELECT team.team_id, team.team_naam, student.student_id, student.naam, student.achter_naam, student.leeftijd, student.geboorte_datum, student.vaardigheid, contact_gegevens.unasat_emailadres, contact_gegevens.contact_nummer, contact_gegevens.verblijfplaats\n" +
                    "FROM team\n" +
                    "LEFT JOIN student ON team.team_id = student.team_id\n" +
                    "LEFT JOIN contact_gegevens ON student.contact_id = contact_gegevens.contact_id\n" +
                    "WHERE team.team_naam = '" + input + "';";
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
                int age = rs.getInt("leeftijd"); // Get the age of the student from the current row
                String birth = rs.getString("geboorte_datum");
                String skill = rs.getString("vaardigheid");
                int contact = rs.getInt("contact_nummer");
                String residence = rs.getString("verblijfplaats");

                if (!teamMap.containsKey(teamid)) {
                    System.out.println("\n\033[34mTeam:\033[0m"); // Print the word "Team:" in blue color using an escape code
                    System.out.println("\033[34mID:\033[0m " + teamid + ", \033[34mName:\033[0m " + teamname ); // Print the team ID and name in blue color using an escape code
                    System.out.println("\033[34mMembers:\033[0m"); // Print the word "Members:" in blue color using an escape code
                    teamMap.put(teamid, teamname); // Add the team to the HashMap
                }
                System.out.println("\033[34mID:\033[0m " + studentid + ", \033[34mName:\033[0m " + first + " " + last + ", \033[34mAge:\033[0m " + age + ", \033[34mDate of birth:\033[0m " + birth + ", \033[34mSkill:\033[0m " + skill + ", \033[34mE-mailadres:\033[0m " + email + ", \033[34mContact number:\033[0m " + contact +  ", \033[34mResidence:\033[0m " + residence ); // Print the student ID, name, and email in blue color using an escape code

            }
            rs.close(); // Close the result set
            stmt.close(); // Close the statement
        }

        catch (SQLException ex) { // Catch any SQL exceptions that may occur
            System.out.println("Error: " + ex.getMessage()); // Print the error message
        }
    }
}
