package gamecollector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/highscores"; // MySQL Database URL
    private static final String USERNAME = "root"; // Replace with your MySQL username
    private static final String PASSWORD = ""; // Replace with your MySQL password

    public Database() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            System.out.println("cheaking");
            System.out.println("Connection to the database established successfully.");
            
            // Create the leaderboard table
            createLeaderboardTable(connection);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    private void createLeaderboardTable(Connection connection) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS leaderboard (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "score INT NOT NULL, " +
                "level INT NOT NULL, " +
                "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
            System.out.println("Table 'leaderboard' created successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to create the table 'leaderboard'.");
            e.printStackTrace();
        }
    }

   
}
