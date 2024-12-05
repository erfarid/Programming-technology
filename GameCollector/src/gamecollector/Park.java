package gamecollector;

import javax.swing.*;

public class Park {
    private JFrame frame;

    public Park() {
        // Initialize the JFrame
        frame = new JFrame("Yellowstone Park - Yogi Bear Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Create an instance of the UI (which contains the game logic)
        UI gameUI = new UI();

        // Add the UI to the frame
        frame.add(gameUI);

        // Pack the frame to automatically size it based on its components
        frame.pack();

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Make the window visible
        frame.setVisible(true);

        // Request focus for the UI to capture keyboard input
        gameUI.requestFocusInWindow();
    }
}
