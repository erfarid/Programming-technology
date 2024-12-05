package gamecollector;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:leaderboard.db";

    public Database() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            // Create the leaderboard table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Leaderboard (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "time INTEGER NOT NULL, " +
                    "level INTEGER NOT NULL, " +
                    "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            statement.execute(createTableQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert a new record into the leaderboard
    public void insertRecord(String name, int time, int level) {
        String insertQuery = "INSERT INTO Leaderboard (name, time, level) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, time);
            preparedStatement.setInt(3, level);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve the leaderboard records
    public String getLeaderboard() {
        StringBuilder leaderboard = new StringBuilder();
        String selectQuery = "SELECT name, time, level, date FROM Leaderboard ORDER BY time ASC LIMIT 10";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            leaderboard.append("Name\tTime\tLevel\tDate\n");
            while (resultSet.next()) {
                leaderboard.append(resultSet.getString("name")).append("\t")
                        .append(resultSet.getInt("time")).append("\t")
                        .append(resultSet.getInt("level")).append("\t")
                        .append(resultSet.getString("date")).append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboard.toString();
    }
}
