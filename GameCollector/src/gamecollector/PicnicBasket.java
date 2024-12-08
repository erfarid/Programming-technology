package gamecollector;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class PicnicBasket {

    int x, y, size;

    public PicnicBasket(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size); // Define the bounding box of the basket
    }

    // Method to check if the picnic basket collides with another object
    public boolean collidesWith(int otherX, int otherY, int otherSize) {
        return this.x < otherX + otherSize && this.x + this.size > otherX
                && this.y < otherY + otherSize && this.y + this.size > otherY;
    }

    // Static method to generate a picnic basket that does not overlap with other baskets or obstacles
    public static PicnicBasket generateNonOverlappingBasket(
        int parkWidth,
        int parkHeight,
        int basketSize,
        List<Obstacles> obstacles,
        List<PicnicBasket> baskets
) {
    PicnicBasket basket = null;
    boolean validPosition = false;
    int maxRetries = 100; // Limit retries to avoid infinite loops
    int retries = 0;

    while (!validPosition && retries < maxRetries) {
        retries++;

        // Generate random position for the basket
        int x = (int) (Math.random() * (parkWidth - basketSize));
        int y = (int) (Math.random() * (parkHeight - basketSize));
        basket = new PicnicBasket(x, y, basketSize);

        // Get the bounds of the proposed basket
        Rectangle basketBounds = basket.getBounds();

        validPosition = true;

        // Check for overlap with obstacles
        for (Obstacles obstacle : obstacles) {
            if (basketBounds.intersects(obstacle.getBounds())) {
                validPosition = false;
                break;
            }
        }

        // Check for overlap with other baskets
        for (PicnicBasket existingBasket : baskets) {
            if (basketBounds.intersects(existingBasket.getBounds())) {
                validPosition = false;
                break;
            }
        }
    }

    if (retries >= maxRetries) {
        throw new RuntimeException("Unable to place picnic baskets without collision after " + maxRetries + " retries.");
    }

    return basket;
}

    public void paintComponent(Graphics g) {
        g.setColor(Color.RED); // Set color for the basket
        g.fillOval(x, y, size, size); // Draw the basket as a red circle
    }
}
