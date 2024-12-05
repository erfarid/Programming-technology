package gamecollector;

import javax.swing.*;

public class Park {
    private JFrame frame;
    private UI gameUI;
    public static final int WIDTH = 800;  // Park width
    public static final int HEIGHT = 700; // Park height

    public Park() {
        frame = new JFrame("Yellowstone Park - Yogi Bear Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Game configuration: define the number of obstacles and baskets
        int numTrees = 4;
        int numMountains = 5;
        int numPicnicBaskets = 10;
        int obstacleSize = 50;

        // Create an instance of the UI with the configuration
        gameUI = new UI(numTrees, numMountains, numPicnicBaskets, obstacleSize);

        frame.add(gameUI);
        frame.pack();

        // Add the menu bar to the frame
        frame.setJMenuBar(createMenuBar());

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

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");

        JMenuItem restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(e -> restartGame());
        gameMenu.add(restartItem);

        JMenuItem leaderboardItem = new JMenuItem("Leaderboard");
        gameMenu.add(leaderboardItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> exitGame());
        gameMenu.add(exitItem);

        menuBar.add(gameMenu);

        return menuBar;
    }

    private void restartGame() {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to restart the game?", 
                "Restart Game", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            frame.remove(gameUI);
            gameUI = new UI(4, 5, 10, 50);
            frame.add(gameUI);
            frame.revalidate();
            frame.repaint();
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
