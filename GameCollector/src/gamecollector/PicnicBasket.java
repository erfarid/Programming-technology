package gamecollector;

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

    // Method to check if the picnic basket collides with another object
    public boolean collidesWith(int otherX, int otherY, int otherSize) {
        return this.x < otherX + otherSize && this.x + this.size > otherX
                && this.y < otherY + otherSize && this.y + this.size > otherY;
    }

    // Static method to generate a picnic basket that does not overlap with other baskets or obstacles
    public static PicnicBasket generateNonOverlappingBasket(int parkWidth, int parkHeight, int basketSize, List<Obstacles> obstacles, List<PicnicBasket> baskets) {
        PicnicBasket basket = null;
        boolean validPosition = false;
        int minDistanceBetweenBaskets = basketSize * 3; // Minimum distance between baskets
        int minDistanceFromObstacles = basketSize * 5; // Minimum distance from obstacles

        while (!validPosition) {
            int x = (int) (Math.random() * (parkWidth - basketSize));
            int y = (int) (Math.random() * (parkHeight - basketSize));
            basket = new PicnicBasket(x, y, basketSize);

            validPosition = true;

            // Check for overlap or proximity with obstacles
            for (Obstacles obstacle : obstacles) {
                int dx = Math.abs(obstacle.x + obstacle.width / 2 - (basket.x + basketSize / 2));
                int dy = Math.abs(obstacle.y + obstacle.height / 2 - (basket.y + basketSize / 2));
                int distance = (int) Math.sqrt(dx * dx + dy * dy);

                if (distance < obstacle.width / 2 + minDistanceFromObstacles) {
                    validPosition = false;
                    break;
                }
            }

            // Check for overlap or proximity with other baskets
            for (PicnicBasket existingBasket : baskets) {
                int dx = Math.abs(existingBasket.x + basketSize / 2 - (basket.x + basketSize / 2));
                int dy = Math.abs(existingBasket.y + basketSize / 2 - (basket.y + basketSize / 2));
                int distance = (int) Math.sqrt(dx * dx + dy * dy);

                if (distance < minDistanceBetweenBaskets) {
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
