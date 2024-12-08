package gamecollector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class Yogi {

    private int x, y;
    private final int speed = 15;
    private KeyAdapter keyAdapter; // Stores the KeyListener
    private int previousX, previousY; // Track the previous position for undo
    private GameLogic gameLogic; // Reference to GameLogic for collision checks
    private Image image; // Image to represent Yogi

    public Yogi(int startX, int startY, GameLogic gameLogic) {
        this.x = startX;
        this.y = startY;
        this.gameLogic = gameLogic; // Pass GameLogic instance

        // Load Yogi's image
        try {
            image = ImageIO.read(new File("C:\\Users\\ASUS\\OneDrive\\Desktop\\GameCollector\\src\\Images\\Yogi.jpg"))
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize the image to 50x50
            if (image == null) {
                System.out.println("Image not loaded: Yogi.jpg");
            } else {
                System.out.println("Image loaded successfully: Yogi.jpg");
            }
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

        // Initialize the KeyAdapter for movement
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Calculate intended new position
                int intendedX = x, intendedY = y;

                switch (keyCode) {
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A -> intendedX -= speed;
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> intendedX += speed;
                    case KeyEvent.VK_UP, KeyEvent.VK_W -> intendedY -= speed;
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> intendedY += speed;
                }

                // Check if the intended position collides with obstacles
                if (!gameLogic.collidesWithObstacles(intendedX, intendedY, 50, 50)) {
                    // If no collision, update position
                    previousX = x; // Save current position for undo
                    previousY = y;
                    x = intendedX;
                    y = intendedY;
                }

                // Ensure Yogi stays within the game bounds
                if (x < 0) x = 0;
                if (y < 0) y = 0;
                if (x > 750) x = 750; // Game area width minus Yogi's width
                if (y > 650) y = 650; // Game area height minus Yogi's height
            }
        };
    }

    public void move() {
        // Movement logic is handled by the KeyListener
    }

    public void undoLastMove() {
        x = previousX;
        y = previousY; // Restore the previous position if collision occurs
    }

    public void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, 50, 50, null); // Draw Yogi as an image
            System.out.println("Yogi image drawn at: (" + x + ", " + y + ")");
        } else {
            g.setColor(Color.YELLOW); // Fallback to yellow circle
            g.fillOval(x, y, 50, 50); // Draw Yogi as a yellow circle
            System.out.println("Fallback yellow circle drawn at: (" + x + ", " + y + ")");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public KeyAdapter getKeyListener() {
        return keyAdapter;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
