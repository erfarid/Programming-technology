package gamecollector;

import javax.swing.*;

public class GameCollector {
    public static void main(String[] args) {
        // Define the number of trees, mountains, picnic baskets, and obstacle size
        int numTrees = 5;
        int numMountains = 3;
        int numPicnicBaskets = 10; // Set the number of picnic baskets
        int obstacleSize = 50;

        // Start the game by creating an instance of UI with the above values
        JFrame frame = new JFrame("Yogi's Adventure");
        UI gameUI = new UI(numTrees, numMountains, numPicnicBaskets, obstacleSize); // Pass parameters
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gameUI);
        frame.pack();
        
        // Center the window on the screen
        frame.setLocationRelativeTo(null); // This will center the window on the screen
        
        frame.setVisible(true);
    }
}
