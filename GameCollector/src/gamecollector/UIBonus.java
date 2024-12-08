package gamecollector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UIBonus {
    private List<PicnicBasket> picnicBaskets;
    private int points = 0;
    private int numBaskets;
    private int basketSize;

    public UIBonus(int numBaskets, int basketSize) {
        this.numBaskets = numBaskets;
        this.basketSize = basketSize;
        picnicBaskets = new ArrayList<>();
        generatePicnicBaskets(); // Initialize baskets when UIBonus is created
    }

    // Generate picnic baskets at random positions
   private void generatePicnicBaskets() {
    int parkWidth = 800;
    int parkHeight = 700;

    picnicBaskets.clear(); // Clear any existing baskets

    for (int i = 0; i < numBaskets; i++) {
        PicnicBasket basket = null;
        boolean validPosition = false;

        while (!validPosition) {
            int x = randomObstaclePosition(parkWidth);
            int y = randomObstaclePosition(parkHeight);
            basket = new PicnicBasket(x, y, basketSize);

            validPosition = true; // Assume the position is valid

            // Check if the new basket is too close to existing baskets
            for (PicnicBasket existingBasket : picnicBaskets) {
                int dx = Math.abs(existingBasket.x + basket.size / 2 - (basket.x + basket.size / 2));
                int dy = Math.abs(existingBasket.y + basket.size / 2 - (basket.y + basket.size / 2));
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < basketSize * 2) { // Ensure a minimum distance between baskets
                    validPosition = false;
                    break;
                }
            }
        }

        picnicBaskets.add(basket); // Add the valid basket to the list
    }
}


    // Method to generate a random position for a basket
    private int randomObstaclePosition(int limit) {
        Random random = new Random();
        return random.nextInt(limit - basketSize); // Ensure baskets are within the park boundaries
    }

    // Paint the picnic baskets on the screen
    public void paintComponent(Graphics g) {
        g.setColor(Color.RED);
        for (PicnicBasket basket : picnicBaskets) {
            basket.paintComponent(g);
        }
    }
}