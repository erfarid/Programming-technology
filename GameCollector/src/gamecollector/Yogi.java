package gamecollector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Yogi {

    private int x, y;
    private final int speed = 15;
    private KeyAdapter keyAdapter; // Stores the KeyListener
    private int previousX, previousY; // Track the previous position for undo

    public Yogi(int startX, int startY) {
        this.x = startX;
        this.y = startY;

        // Initialize the KeyAdapter for movement
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Calculate intended new position
                int intendedX = x, intendedY = y;

                switch (keyCode) {
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A ->
                        intendedX -= speed;
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                        intendedX += speed;
                    case KeyEvent.VK_UP, KeyEvent.VK_W ->
                        intendedY -= speed;
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S ->
                        intendedY += speed;
                }

                // Check if the intended position collides with obstacles
                if (!UI.getInstance().collidesWithObstacles(intendedX, intendedY, 50, 50)) {
                    // If no collision, update position
                    previousX = x; // Save current position for undo
                    previousY = y;
                    x = intendedX;
                    y = intendedY;
                }

                // Ensure Yogi stays within the game bounds
                if (x < 0) {
                    x = 0;
                }
                if (y < 0) {
                    y = 0;
                }
                if (x > 750) {
                    x = 750; // Game area width minus Yogi's width
                }
                if (y > 650) {
                    y = 650; // Game area height minus Yogi's height
                }
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
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, 50, 50); // Draw Yogi as a yellow circle
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
