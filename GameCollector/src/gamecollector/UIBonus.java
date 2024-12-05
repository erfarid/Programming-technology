/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author ASUS
 */
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
            placePicnicBasket(basket);
        }
    }

    // Place a picnic basket ensuring it doesn't overlap with obstacles or Yogi
    private void placePicnicBasket(PicnicBasket basket) {
        boolean validPosition = false;
        while (!validPosition) {
            validPosition = true;
            for (Obstacles obstacle : UI.getInstance().getObstacles()) {
                if (obstacle.collidesWith(basket.x, basket.y, basketSize, basketSize)) {
                    validPosition = false;
                    break;
                }
            }
            if (validPosition && UI.getInstance().collidesWithYogi(basket.x, basket.y)) {
                validPosition = false;
            }
            if (!validPosition) {
                basket.x = randomObstaclePosition(800);
                basket.y = randomObstaclePosition(700);
            }
        }
        picnicBaskets.add(basket);
    }

    // Check if Yogi collects a picnic basket
    public void checkBasketCollection(Yogi yogi, UI ui) {
        List<PicnicBasket> collectedBaskets = new ArrayList<>();
        for (PicnicBasket basket : picnicBaskets) {
            if (yogi.getX() < basket.x + basketSize && yogi.getX() + YOGI_SIZE > basket.x &&
                yogi.getY() < basket.y + basketSize && yogi.getY() + YOGI_SIZE > basket.y) {
                collectedBaskets.add(basket);
                points++; // Increase points for each collected basket
            }
        }

        // Remove collected baskets from the list
        picnicBaskets.removeAll(collectedBaskets);
    }

    // Get current points
    public int getPoints() {
        return points;
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
