package gamecollector;

import javax.swing.*;
import java.awt.*;

public class Park {

    private JFrame frame;
    private UI gameUI;
    public static final int WIDTH = 800;  // Park width
    public static final int HEIGHT = 700; // Park height

    public Park() {
        frame = new JFrame("Yellowstone Park - Yogi Bear Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Set up the main container with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Game configuration: define the number of obstacles and baskets
        int numTrees = 4;
        int numMountains = 5;
        int numPicnicBaskets = 10;
        int obstacleSize = 50;

        // Create an instance of the UI with the configuration
        gameUI = new UI(numTrees, numMountains, numPicnicBaskets, obstacleSize);

        // Add the game UI to the center
        mainPanel.add(gameUI, BorderLayout.CENTER);

        // Add the game menu to the left
        mainPanel.add(createMenuPanel(), BorderLayout.WEST);

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.pack();

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Make the window visible
        frame.setVisible(true);

        // Request focus for the UI to capture keyboard input
        gameUI.requestFocusInWindow();
    }

    /**
     * Get the width of the park.
     */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * Get the height of the park.
     */
    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * Create a vertical game menu panel to be placed on the left side of the
     * frame.
     */
    /**
     * Create a vertical game menu panel to be placed on the left side of the
     * frame.
     */
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(200, HEIGHT)); // Set the width of the menu panel

        // Add a border to separate the menu panel from the park
        menuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK)); // Black line on the right

        JLabel titleLabel = new JLabel("Game Menu");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        menuPanel.add(titleLabel);

        menuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing

        JButton restartButton = new JButton("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> restartGame());
        menuPanel.add(restartButton);

        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(leaderboardButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> exitGame());
        menuPanel.add(exitButton);

        return menuPanel;
    }

    private void restartGame() {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to restart the game?",
                "Restart Game", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            // Create a new instance of the UI with the same configuration
            UI newGameUI = new UI(4, 5, 10, 50);

            // Replace the old gameUI with the new one
            frame.getContentPane().removeAll(); // Remove all components from the frame
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(newGameUI, BorderLayout.CENTER); // Add the new game UI to the center
            mainPanel.add(createMenuPanel(), BorderLayout.WEST); // Add the menu panel to the left
            frame.add(mainPanel);

            // Revalidate and repaint the frame to reflect the changes
            frame.revalidate();
            frame.repaint();

            // Update the reference to the new game UI
            gameUI = newGameUI;

            // Request focus for the new game UI
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
}
