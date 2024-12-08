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

    
    public boolean collisionDetect(int targetX, int targetY, int targetWidth, int targetHeight) {
        Rectangle targetBounds = new Rectangle(targetX, targetY, targetWidth, targetHeight);
        return this.getBounds().intersects(targetBounds);
    }

}
