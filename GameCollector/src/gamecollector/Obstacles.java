package gamecollector;

import java.awt.*;

public abstract class Obstacles {
    protected int x, y, width, height;
    protected Image image; // Add this field to store the image

    public Obstacles(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Define the bounding box of the obstacle
    }

    public abstract void paintComponent(Graphics g);

    // Check if the given coordinates collide with the obstacle
    public boolean collidesWith(int targetX, int targetY, int targetWidth, int targetHeight) {
        return targetX < x + width && targetX + targetWidth > x &&
               targetY < y + height && targetY + targetHeight > y;
    }
}
