package gamecollector;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

public class PicnicBasket {
    int x, y, size;

    public PicnicBasket(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    // Method to check if the picnic basket collides with another object
    public boolean collidesWith(int otherX, int otherY, int otherSize) {
        return this.x < otherX + otherSize && this.x + this.size > otherX &&
               this.y < otherY + otherSize && this.y + this.size > otherY;
    }

    // Static method to generate a picnic basket that does not overlap with other baskets or obstacles
    public static PicnicBasket generateNonOverlappingBasket(int parkWidth, int parkHeight, int basketSize, List<Obstacles> obstacles, List<PicnicBasket> baskets) {
        PicnicBasket basket = null;
        boolean validPosition = false;

        while (!validPosition) {
            int x = (int) (Math.random() * (parkWidth - basketSize));
            int y = (int) (Math.random() * (parkHeight - basketSize));
            basket = new PicnicBasket(x, y, basketSize);

            validPosition = true;

            // Check for overlap with obstacles
            for (Obstacles obstacle : obstacles) {
                if (obstacle.collidesWith(basket.x, basket.y, basketSize, basketSize)) {
                    validPosition = false;
                    break;
                }
            }

            // Check for overlap with other baskets
            for (PicnicBasket existingBasket : baskets) {
                if (existingBasket.collidesWith(basket.x, basket.y, basketSize)) {
                    validPosition = false;
                    break;
                }
            }
        }

        return basket;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.RED); // Set color for the basket
        g.fillOval(x, y, size, size); // Draw the basket as a red circle
    }
}
