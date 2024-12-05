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
        Random random = new Random();
        int parkWidth = 800;
        int parkHeight = 700;

        for (int i = 0; i < numBaskets; i++) {
            PicnicBasket basket = new PicnicBasket(randomObstaclePosition(parkWidth), randomObstaclePosition(parkHeight), basketSize);
            picnicBaskets.add(basket);
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
