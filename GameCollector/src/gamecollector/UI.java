 package gamecollector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UI extends JPanel {

    private static UI instance; // Singleton instance of UI
    private Yogi yogi;
    private List<Ranger> rangers; // List of Rangers (more than 1 in higher levels)
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

    // Variable to track the start time for elapsed time
    private long startTime;

    private int level = 1; // Current level
    private int rangerCount = 1; // Initial number of Rangers (1 at the start)

    public UI(int numTrees, int numMountains, int numBaskets, int obstacleSize) {
        instance = this; // Assign this instance to the singleton reference

        this.numTrees = numTrees;
        this.numMountains = numMountains;
        this.numBaskets = numBaskets;
        this.obstacleSize = obstacleSize;

        yogi = new Yogi(0, 0); // Yogi starts at the top-left corner
        rangers = new ArrayList<>();

        obstacles = new ArrayList<>();
        baskets = new ArrayList<>();
        generateObstacles(); // Generate obstacles at random positions
        generatePicnicBaskets(); // Generate picnic baskets
        generateRangers(); // Generate the initial Rangers

        // Initialize the start time for elapsed time calculation
        startTime = System.currentTimeMillis();

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

    // Method to generate Rangers based on the current level
    private void generateRangers() {
        rangers.clear();
        for (int i = 0; i < rangerCount; i++) {
            Ranger ranger = new Ranger(100 + (i * 50), 100 + (i * 50)); // Position Rangers with offset
            rangers.add(ranger);
        }
    }

    // Method to generate obstacles at random positions
    private void generateObstacles() {
        int parkWidth = 800;  // Park width
        int parkHeight = 700; // Park height

        obstacles.clear(); // Clear previous obstacles to regenerate them with new random positions

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
    // Method to place an obstacle ensuring it doesn't overlap with other obstacles
    // Method to place an obstacle ensuring it doesn't overlap and leaves space for Yogi to pass// Method to place an obstacle ensuring it doesn't overlap, leaves space for Yogi to pass, and doesn't touch the border
    private void placeObstacle(Obstacles obstacle) {
        boolean validPosition = false;
        int minGap = 60; // Minimum gap between obstacles to allow Yogi to pass
        int borderGap = 50; // Minimum gap between the obstacle and the park border

        while (!validPosition) {
            validPosition = true;

            // Check if the obstacle is too close to the border
            if (obstacle.x < borderGap || obstacle.y < borderGap
                    || obstacle.x + obstacleSize > 800 - borderGap || obstacle.y + obstacleSize > 700 - borderGap) {
                validPosition = false; // The obstacle is too close to the border
            }

            // Check for collisions with existing obstacles
            for (Obstacles existingObstacle : obstacles) {
                int dx = Math.abs(existingObstacle.x - obstacle.x);
                int dy = Math.abs(existingObstacle.y - obstacle.y);
                int distance = (int) Math.sqrt(dx * dx + dy * dy);

                // Ensure obstacles don't overlap and maintain the minimum gap
                if (distance < obstacleSize + minGap) {
                    validPosition = false; // The obstacles are too close
                    break;
                }
            }

            // If not valid, generate a new random position for the obstacle
            if (!validPosition) {
                obstacle.x = randomObstaclePosition(800);
                obstacle.y = randomObstaclePosition(700);
            }
        }

        obstacles.add(obstacle); // Add the obstacle to the list if position is valid
    }

    // Method to generate picnic baskets
// Method to generate picnic baskets ensuring no overlap with obstacles
    // Method to generate picnic baskets ensuring no overlap with obstacles
    private void generatePicnicBaskets() {
        int parkWidth = 800;  // Park width
        int parkHeight = 700; // Park height

        baskets.clear(); // Clear any existing baskets

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

        for (Ranger ranger : rangers) {
            ranger.move(); // Let each Ranger move freely without checking obstacles

            // Check if Ranger crosses paths with Yogi (detect collision at all times)
            if (ranger.crossesYogi(yogi.getX(), yogi.getY(), obstacleSize, obstacleSize)) {
                decreaseLife(); // Decrease life if Yogi and Ranger collide
            }
        }

        // Check for picnic basket collection
        checkBasketCollection();

        // Check for win condition
        checkWinCondition();

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
        return basket.x < yogi.getX() + 50 && basket.x + basket.size > yogi.getX()
                && basket.y < yogi.getY() + 50 && basket.y + basket.size > yogi.getY();
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

    // Method to check if all baskets are collected (win condition)
    private void checkWinCondition() {
        if (baskets.isEmpty()) {
            levelUp(); // Move to next level if all baskets are collected
        }
    }

    // Method to advance to the next level
    // Method to advance to the next level
    private void levelUp() {
        if (level < 10) {
            level++; // Increase level
            rangerCount++; // Increase the number of Rangers
            generateRangers(); // Regenerate Rangers for the new level
            generatePicnicBaskets(); // Regenerate baskets for the new level
            generateObstacles(); // Regenerate obstacles for the new level

            // Reset Yogi to starting position (0, 0)
            yogi.setPosition(0, 0);

            System.out.println("Level " + level + " started! Rangers: " + rangerCount);
        } else {
            // If it's the last level, display a win message
            displayWinMessage();
        }
    }

    // Method to display win message and stop the game
    private void displayWinMessage() {
        JOptionPane.showMessageDialog(this, "Congratulations! You've completed all 10 levels!");
        System.exit(0); // End the game
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // Time in seconds

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        // Display Lives
        g.drawString("Lives: " + life, 500, 30);

        // Display Points and Level on the top-left corner
        g.drawString("Points: " + points, 10, 30);
        g.drawString("Level: " + level, 250, 30); // Moved Level further to the right of Points

        // Display Time at the top-right corner
        g.drawString("Time: " + elapsedTime + "s", 700, 30);

        // Paint obstacles
        for (Obstacles obstacle : obstacles) {
            obstacle.paintComponent(g);
        }

        // Paint baskets
        for (PicnicBasket basket : baskets) {
            basket.paintComponent(g);
        }

        yogi.paintComponent(g);
        for (Ranger ranger : rangers) {
            ranger.paintComponent(g);
        }
    }

}