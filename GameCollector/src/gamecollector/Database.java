package gamecollector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/highscores"; // MySQL Database URL
    private static final String USERNAME = "root"; // Replace with your MySQL username
    private static final String PASSWORD = ""; // Replace with your MySQL password

    public Database() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            System.out.println("Connection to the database established successfully.");

            // Create the Scoreboard table
            createLeaderboardTable(connection);

            // Display the Scoreboard table
            displayLeaderboard(connection);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    private void createLeaderboardTable(Connection connection) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Scoreboard (" +
                "name VARCHAR(255) NOT NULL, " +
                "points INT NOT NULL" +
                ")";
        
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
            System.out.println("Table 'Scoreboard' created successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to create the table 'Scoreboard'.");
            e.printStackTrace();
        }
    }

    private void displayLeaderboard(Connection connection) {
        String selectQuery = "SELECT name, points FROM Scoreboard ORDER BY points DESC";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            System.out.println("Leaderboard:");
            System.out.println("Name\tPoints");
            System.out.println("----------------");

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int points = resultSet.getInt("points");
                System.out.println(name + "\t" + points);
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve the leaderboard.");
            e.printStackTrace();
        }
    }
   public void insertScore(String name, int points) {
    String insertQuery = "INSERT INTO Scoreboard (name, points) VALUES (?, ?)";

    try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

        // Insert the new score
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, points);
        preparedStatement.executeUpdate();
        System.out.println("Score successfully saved for player: " + name);

        // Delete scores beyond the top 10
        String deleteQuery = "DELETE FROM Scoreboard WHERE name NOT IN (" +
                "SELECT name FROM (SELECT name FROM Scoreboard ORDER BY points DESC LIMIT 10) temp)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteQuery);
        }

    } catch (SQLException e) {
        System.err.println("Failed to insert score into the database.");
        e.printStackTrace();
    }
}




    public static void main(String[] args) {
        // Test the database connection and display the leaderboard
        new Database();
    }
}
