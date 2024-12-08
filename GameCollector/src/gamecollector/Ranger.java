package gamecollector;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ranger extends JPanel {
    private int x, y; // Ranger's position
    private final int speed = 15; // Speed of Ranger's movement
    private final int parkWidth = 800; // Width of the park (game window)
    private final int parkHeight = 700; // Height of the park (game window)

    // Movement direction: horizontal or vertical, positive or negative
    private final boolean movesHorizontally; // True for horizontal movement, false for vertical
    private boolean movesPositive; // True for positive direction, false for negative

    public Ranger(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        Random random = new Random();

        // Determine movement type and direction upon initialization
        this.movesHorizontally = random.nextBoolean(); // Randomly choose horizontal or vertical
        this.movesPositive = random.nextBoolean(); // Randomly choose positive or negative direction
    }

    // Method to move the Ranger
    public void move() {
        if (movesHorizontally) {
            // Move horizontally
            if (movesPositive) {
                x += speed; // Move right
            } else {
                x -= speed; // Move left
            }
        } else {
            // Move vertically
            if (movesPositive) {
                y += speed; // Move down
            } else {
                y -= speed; // Move up
            }
        }

        // Ensure the ranger stays within the bounds of the park
        if (x < 0) {
            x = 0;
            if (!movesPositive) reverseDirection();
        }
        if (y < 0) {
            y = 0;
            if (!movesPositive) reverseDirection();
        }
        if (x > parkWidth - 30) {
            x = parkWidth - 30;
            if (movesPositive) reverseDirection();
        }
        if (y > parkHeight - 30) {
            y = parkHeight - 30;
            if (movesPositive) reverseDirection();
        }

        repaint(); // Repaint the panel after moving
    }

    // Reverse the current direction of movement
    private void reverseDirection() {
        movesPositive = !movesPositive;
    }

    // Method to check if Yogi crosses the Ranger's bounds
    public boolean crossesYogi(int yogiX, int yogiY, int yogiWidth, int yogiHeight) {
        // Check if Yogi's X or Y boundaries cross the Ranger's boundaries
        return (x < yogiX + yogiWidth && x + 30 > yogiX) && (y < yogiY + yogiHeight && y + 30 > yogiY);
    }

    // Method to check if the ranger is too close to Yogi (within 15 units)
    public boolean isTooCloseToYogi(int yogiX, int yogiY) {
        // Calculate the distance between the ranger and Yogi
        int distance = (int) Math.sqrt(Math.pow(x - yogiX, 2) + Math.pow(y - yogiY, 2));

        return distance <= 15; // If within 15 units, return true
    }

    // Override the paintComponent method to draw the Ranger
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set the color to blue for the Ranger
        g.setColor(Color.BLUE);
        // Draw a square to represent the Ranger
        g.fillRect(x, y, 30, 30); // The size of the square is 30x30
    }
}