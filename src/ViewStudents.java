import java.sql.*;   // Importing everything(*) is bad practice which causes bloatware but you should onlu import the necessary classes from the package. In this case it's java.sql
import java.util.Scanner;  // Import the Scanner class from the java.util package. This will be used for input

public class ViewStudents {
    public void viewStudents() {
        try (Connection conn = DatabaseConnection.getConnection()) {  // Try to open a new database connection using the getConnection() method from the DatabaseConnection class.
            String sql = "SELECT * FROM student";  // Create a String variable named sql that contains a SQL SELECT statement to retrieve all data from the student table.
            PreparedStatement stmt = conn.prepareStatement(sql);  // Prepare a PreparedStatement object with the SQL statement.
            ResultSet rs = stmt.executeQuery();  // Execute the SQL statement and store the results in a ResultSet object.
            while (rs.next()) {  // Loop through each row in the ResultSet.
                int id = rs.getInt("student_id");  // Get the value of the "student_id" column for the current row and store it in an int variable named id.
                String first = rs.getString("first_name");  // Get the value of the "first_name" column for the current row and store it in a String variable named first.
                String last = rs.getString("last_name");  // Get the value of the "last_name" column for the current row and store it in a String variable named last.
                System.out.println("ID: " + id + ", First Name: " + first + ", Last Name: " + last);  // Print out the values of the id, first, and last variables for the current row.
            }
            rs.close();  // Close the ResultSet object to free up resources.
            stmt.close();  // Close the PreparedStatement object to free up resources.
        } catch (SQLException ex) {  // Catch any SQLException that may occur.
            System.out.println("Error: " + ex.getMessage());  // Print out the error message associated with the SQLException.
        }
    }
    
        public void ViewTeams() {
        try (Connection conn = DatabaseConnection.getConnection()) {  
            String sql = "SELECT * FROM team and student_id, naam, achter_naam from student and unasat_emailadres from contact_gegevens";  
            PreparedStatement stmt = conn.prepareStatement(sql);  
            ResultSet rs = stmt.executeQuery();  
            while (rs.next()) {  
                int teamid = rs.getInt ("team_id");                                                              //get data from the team table
                String teamname = rs.getString( "team_naam");   
                int studentid = rs.getInt ("student_id");                                                       //get data from the student table
                String first = rs.getString ("naam");
                String last = rs.getString ("achter_naam");
                String email = rs.getString ("unasat_emailadres");                                             //get data from the contact_gegevens table
                System.out.println ("Team: /n ID: " + teamid + ", Name: " + teamname );                                                                                          
                System.out.println ("Members: /n ID: " + studentid + ", Name: " + first + last + ", E-mailadres: " + unasat_emailadres );  
            }
            rs.close();  
            stmt.close();  
        } 
        catch (SQLException ex) {  
            System.out.println("Error: " + ex.getMessage());  
        }
    }
}

