package gamecollector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UI extends JPanel {

    private static UI instance; // Singleton instance of UI
    private Yogi yogi;
    private Ranger ranger;
    private int life = 3; // Yogi's initial lives
    private List<Obstacles> obstacles;
    private List<PicnicBasket> baskets; // List to hold the baskets
    private int numBaskets; // Number of picnic baskets
    private int points = 0; // Points for collecting baskets

    // Instance variables for the number of obstacles, baskets, and their size
    private int numTrees;
    private int numMountains;
    private int obstacleSize;
    private int basketSize = 30; // Size of each picnic basket

    public UI(int numTrees, int numMountains, int numBaskets, int obstacleSize) {
        instance = this; // Assign this instance to the singleton reference

        this.numTrees = numTrees;
        this.numMountains = numMountains;
        this.numBaskets = numBaskets;
        this.obstacleSize = obstacleSize;

        yogi = new Yogi(0, 0); // Yogi starts at the top-left corner
        ranger = new Ranger(100, 100);

        obstacles = new ArrayList<>();
        baskets = new ArrayList<>();
        generateObstacles(); // Generate obstacles at random positions
        generatePicnicBaskets(); // Generate picnic baskets

        setLayout(null);
        setPreferredSize(new Dimension(800, 700)); // Game area dimensions
        setFocusable(true);

        addKeyListener(yogi.getKeyListener());

        Timer timer = new Timer(100, e -> gameLoop());
        timer.start();
    }

    public static UI getInstance() {
        return instance; // Return the singleton instance
    }

    // Method to generate obstacles at random positions
    private void generateObstacles() {
        int parkWidth = 800;  // Park width
        int parkHeight = 700; // Park height

        // Place the trees
        for (int i = 0; i < numTrees; i++) {
            placeObstacle(new Tree(randomObstaclePosition(parkWidth), randomObstaclePosition(parkHeight), obstacleSize));
        }

        // Place the mountains
        for (int i = 0; i < numMountains; i++) {
            placeObstacle(new Mountain(randomObstaclePosition(parkWidth), randomObstaclePosition(parkHeight), obstacleSize));
        }
    }

    // Method to generate a random position for an obstacle or basket
    private int randomObstaclePosition(int limit) {
        return (int) (Math.random() * (limit - obstacleSize)); // Ensure obstacles are within the park boundaries
    }

    // Method to place an obstacle ensuring it doesn't overlap
    private void placeObstacle(Obstacles obstacle) {
        boolean validPosition = false;
        while (!validPosition) {
            validPosition = true;
            for (Obstacles existingObstacle : obstacles) {
                if (existingObstacle.collidesWith(obstacle.x, obstacle.y, obstacleSize, obstacleSize)) {
                    validPosition = false;
                    break;
                }
            }
            if (!validPosition) {
                // If overlap happens, generate a new random position
                obstacle.x = randomObstaclePosition(800);
                obstacle.y = randomObstaclePosition(700);
            }
        }
        obstacles.add(obstacle);
    }

    // Method to generate picnic baskets
    private void generatePicnicBaskets() {
        int parkWidth = 800;  // Park width
        int parkHeight = 700; // Park height

        // Place the baskets
        for (int i = 0; i < numBaskets; i++) {
            PicnicBasket basket = PicnicBasket.generateNonOverlappingBasket(parkWidth, parkHeight, basketSize, obstacles, baskets);
            baskets.add(basket);
        }
    }

    private void gameLoop() {
        yogi.move(); // Move Yogi based on user input

        if (collidesWithObstacles(yogi.getX(), yogi.getY(), obstacleSize, obstacleSize)) {
            yogi.undoLastMove(); // Prevent Yogi from moving into an obstacle
        }

        ranger.move(); // Let the Ranger move freely without checking obstacles

        // Check if Ranger crosses paths with Yogi (detect collision at all times, including during long press)
        if (ranger.crossesYogi(yogi.getX(), yogi.getY(), obstacleSize, obstacleSize)) {
            decreaseLife(); // Decrease life if Yogi and Ranger collide
        }

        // Check for picnic basket collection
        checkBasketCollection();

        repaint(); // Repaint the panel after updating positions
    }

    // Method to check if Yogi collides with any obstacles
    public boolean collidesWithObstacles(int x, int y, int width, int height) {
        for (Obstacles obstacle : obstacles) {
            if (obstacle.collidesWith(x, y, width, height)) {
                return true;
            }
        }
        return false;
    }

    // Method to check if Yogi collects a picnic basket
    private void checkBasketCollection() {
        for (PicnicBasket basket : baskets) {
            if (collidesWithBasket(basket)) {
                baskets.remove(basket); // Remove the collected basket
                points++; // Increment the points for collecting a basket
                System.out.println("Basket collected! Points: " + points);
                break; // Exit after collecting one basket
            }
        }
    }

    // Method to check if Yogi collides with a specific basket
    private boolean collidesWithBasket(PicnicBasket basket) {
        return basket.x < yogi.getX() + 50 && basket.x + basket.size > yogi.getX() &&
               basket.y < yogi.getY() + 50 && basket.y + basket.size > yogi.getY();
    }

    private void decreaseLife() {
        life--;
        if (life <= 0) {
            JOptionPane.showMessageDialog(this, "Game Over! You lost all your lives.");
            System.exit(0);
        } else {
            // Reset Yogi's position to the starting point (0, 0)
            yogi.setPosition(0, 0);
            System.out.println("Yogi lost a life! Lives remaining: " + life);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Lives: " + life, 400, 30);
        g.drawString("Points: " + points, 10, 30); // Display the points at the top-left corner

        // Paint obstacles
        for (Obstacles obstacle : obstacles) {
            obstacle.paintComponent(g);
        }

        // Paint baskets
        for (PicnicBasket basket : baskets) {
            basket.paintComponent(g);
        }

        yogi.paintComponent(g);
        ranger.paintComponent(g);
    }
}
