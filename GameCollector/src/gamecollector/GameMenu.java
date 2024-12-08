package gamecollector;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class GameMenu {

    private JPanel menuPanel;
    private JFrame frame;
    private UI gameUI;
    private Park park;

    public GameMenu(JFrame frame, UI gameUI, Park park) {
        this.frame = frame;
        this.gameUI = gameUI;
        this.park = park;

        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setPreferredSize(new Dimension(200, Park.HEIGHT));
        menuPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("Game Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        menuPanel.add(titleLabel, gbc);

        gbc.gridy++;
        JButton restartButton = new JButton("Restart");
        restartButton.setPreferredSize(new Dimension(150, 40));
        restartButton.addActionListener(e -> restartGame());
        menuPanel.add(restartButton, gbc);

        gbc.gridy++;
        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setPreferredSize(new Dimension(150, 40));
        leaderboardButton.addActionListener(e -> showLeaderboard());
        menuPanel.add(leaderboardButton, gbc);

        gbc.gridy++;
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(150, 40));
        exitButton.addActionListener(e -> exitGame());
        menuPanel.add(exitButton, gbc);
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    private void restartGame() {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to restart the game?",
                "Restart Game", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            UI newGameUI = new UI();
            frame.getContentPane().removeAll();
            JPanel mainPanel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (Park.getBackgroundImage() != null) {
                        g.drawImage(Park.getBackgroundImage(), 0, 0, Park.WIDTH, Park.HEIGHT, null);
                    }
                }
            };
            mainPanel.add(newGameUI, BorderLayout.CENTER);
            mainPanel.add(getMenuPanel(), BorderLayout.WEST);
            frame.add(mainPanel);

            frame.revalidate();
            frame.repaint();

            gameUI = newGameUI;
            gameUI.requestFocusInWindow();
        }
    }

    private void exitGame() {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the game?",
                "Exit Game", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void showLeaderboard() {
    JFrame leaderboardFrame = new JFrame("Leaderboard");
    leaderboardFrame.setSize(400, 400);
    leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Add a window listener to request focus back to the game UI when the leaderboard is closed
    leaderboardFrame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            gameUI.requestFocusInWindow(); // Request focus back to the game UI
        }
    });

    // Panel to hold leaderboard content
    JPanel leaderboardPanel = new JPanel();
    leaderboardPanel.setBackground(new Color(50, 50, 50)); // Dark background
    leaderboardPanel.setLayout(new BorderLayout());

    // Title for the leaderboard
    JLabel titleLabel = new JLabel("Top 10 Players", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add some padding around the title
    leaderboardPanel.add(titleLabel, BorderLayout.NORTH);

    // Content panel to display leaderboard entries
    JPanel contentPanel = new JPanel();
    contentPanel.setBackground(new Color(70, 70, 70)); // Slightly lighter background
    contentPanel.setLayout(new GridLayout(0, 2, 10, 10)); // Two-column layout: Name and Points

    // Add headers
    JLabel nameHeader = new JLabel("Name", JLabel.CENTER);
    nameHeader.setFont(new Font("Arial", Font.BOLD, 16));
    nameHeader.setForeground(Color.WHITE);
    JLabel pointsHeader = new JLabel("Points", JLabel.CENTER);
    pointsHeader.setFont(new Font("Arial", Font.BOLD, 16));
    pointsHeader.setForeground(Color.WHITE);

    contentPanel.add(nameHeader);
    contentPanel.add(pointsHeader);

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/highscores", "root", "");
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT name, points FROM Scoreboard ORDER BY points DESC LIMIT 10")) {

        while (resultSet.next()) {
            JLabel nameLabel = new JLabel(resultSet.getString("name"), JLabel.CENTER);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            nameLabel.setForeground(Color.WHITE);

            JLabel pointsLabel = new JLabel(String.valueOf(resultSet.getInt("points")), JLabel.CENTER);
            pointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            pointsLabel.setForeground(Color.WHITE);

            contentPanel.add(nameLabel);
            contentPanel.add(pointsLabel);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(leaderboardFrame, "Failed to retrieve the leaderboard:\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Add content to leaderboard panel
    JScrollPane scrollPane = new JScrollPane(contentPanel);
    scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove scroll pane border
    leaderboardPanel.add(scrollPane, BorderLayout.CENTER);

    // Add the panel to the frame and display it
    leaderboardFrame.add(leaderboardPanel);
    leaderboardFrame.setVisible(true);
}

}
