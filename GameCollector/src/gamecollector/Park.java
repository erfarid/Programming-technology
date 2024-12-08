package gamecollector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Park extends JPanel {

    private JFrame frame;
    private UI gameUI;
    public static final int WIDTH = 800;  // Park width
    public static final int HEIGHT = 700; // Park height
    private static Image backgroundImage; // Background image for the park

    // Define game configuration variables
    public static final int NUM_TREES = 4;
    public static final int NUM_MOUNTAINS = 5;
    public static final int NUM_PICNIC_BASKETS = 10;
    public static final int OBSTACLE_SIZE = 50;

    public Park() {
        // Load the background image once using a static method
        backgroundImage = loadBackgroundImage("C:\\Users\\ASUS\\OneDrive\\Desktop\\GameCollector\\src\\Images\\Park.png");

        frame = new JFrame("Yellowstone Park - Yogi Bear Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Set up the main container with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
                }
            }
        };

        // Create an instance of the UI with the configuration
        gameUI = new UI();
        // Add the game UI to the center
        mainPanel.add(gameUI, BorderLayout.CENTER);

        // Add the game menu to the left
        GameMenu gameMenu = new GameMenu(frame, gameUI, this);
        mainPanel.add(gameMenu.getMenuPanel(), BorderLayout.WEST);

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

    public static Image loadBackgroundImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.out.println("Error loading background image: " + e.getMessage());
            return null;
        }
    }

    public static int getParkWidth() {
        return WIDTH;
    }

    public static int getParkHeight() {
        return HEIGHT;
    }

    public static Image getBackgroundImage() {
        return backgroundImage;
    }
}
