package gamecollector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameLogic {

    private Yogi yogi;
    private List<Ranger> rangers;
    private int life = 3;
    private List<Obstacles> obstacles;
    private List<PicnicBasket> baskets;
    private int points = 0;
    private int basketSize = 30;
    private long startTime;
    private int level = 1; // Ensure the game starts at level 1
    private int rangerCount = 1;

    public GameLogic() {
        // Use configuration values from the Park class
        int numTrees = Park.NUM_TREES;
        int numMountains = Park.NUM_MOUNTAINS;
        int numBaskets = Park.NUM_PICNIC_BASKETS;
        int obstacleSize = Park.OBSTACLE_SIZE;

        // Initialize variables
        yogi = new Yogi(0, 0, this);
        rangers = new ArrayList<>();
        obstacles = new ArrayList<>();
        baskets = new ArrayList<>();

        startTime = System.currentTimeMillis();
    }

    // Explicit method to initialize the game elements
    public void initializeGame() {
        level = 1; // Reset level to 1 during initialization
        rangerCount = 1; // Ensure only one ranger starts
        obstacles.clear();
        baskets.clear();
        rangers.clear();
        generateObstacles();
        generatePicnicBaskets();
        generateRangers();
    }

    public Yogi getYogi() {
        return yogi;
    }

    public List<Ranger> getRangers() {
        return rangers;
    }

    public List<Obstacles> getObstacles() {
        return obstacles;
    }

    public List<PicnicBasket> getBaskets() {
        return baskets;
    }

    public int getLife() {
        return life;
    }

    public int getPoints() {
        return points;
    }

    public int getLevel() {
        return level;
    }

    public long getElapsedTime() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void generateRangers() {
        rangers.clear();
        for (int i = 0; i < rangerCount; i++) {
            Ranger ranger = new Ranger(100 + (i * 50), 100 + (i * 50));
            rangers.add(ranger);
        }
    }

    public void generateObstacles() {
        obstacles.clear();
        for (int i = 0; i < Park.NUM_TREES; i++) {
            placeObstacle(new Tree(randomObstaclePosition(Park.getParkWidth()), randomObstaclePosition(Park.getParkHeight()), Park.OBSTACLE_SIZE));
        }
        for (int i = 0; i < Park.NUM_MOUNTAINS; i++) {
            placeObstacle(new Mountain(randomObstaclePosition(Park.getParkWidth()), randomObstaclePosition(Park.getParkHeight()), Park.OBSTACLE_SIZE));
        }
    }

    private int randomObstaclePosition(int limit) {
        return (int) (Math.random() * (limit - Park.OBSTACLE_SIZE));
    }
    private void placeObstacle(Obstacles obstacle) {
        boolean validPosition = false;
        int minGap = 60;
        int borderGap = 50;
        int maxRetries = 100;
        int retries = 0;

        while (!validPosition && retries < maxRetries) {
            retries++;
            obstacle.x = randomObstaclePosition(Park.getParkWidth());
            obstacle.y = randomObstaclePosition(Park.getParkHeight());

            Rectangle obstacleBounds = new Rectangle(
                obstacle.x - minGap,
                obstacle.y - minGap,
                Park.OBSTACLE_SIZE + minGap * 2,
                Park.OBSTACLE_SIZE + minGap * 2
            );

            validPosition = true;

            if (obstacle.x < borderGap || obstacle.y < borderGap ||
                obstacle.x + Park.OBSTACLE_SIZE > Park.getParkWidth() - borderGap || obstacle.y + Park.OBSTACLE_SIZE > Park.getParkHeight() - borderGap) {
                validPosition = false;
                continue;
            }

            for (Obstacles existingObstacle : obstacles) {
                if (obstacleBounds.intersects(existingObstacle.getBounds())) {
                    validPosition = false;
                    break;
                }
            }
        }

        if (validPosition) {
            obstacles.add(obstacle);
        } else {
           // System.out.println("Failed to place obstacle after " + retries + " retries.");
        }
    }

    public void generatePicnicBaskets() {
        baskets.clear();
        for (int i = 0; i < Park.NUM_PICNIC_BASKETS; i++) {
            PicnicBasket basket = PicnicBasket.generateNonOverlappingBasket(Park.getParkWidth(), Park.getParkHeight(), basketSize, obstacles, baskets);
            baskets.add(basket);
        }
    }

    public boolean collidesWithObstacles(int x, int y, int width, int height) {
        for (Obstacles obstacle : obstacles) {
            if (obstacle.collisionDetect(x, y, width, height)) {
                return true;
            }
        }
        return false;
    }

    private String promptForPlayerName(String message) {
        String playerName = JOptionPane.showInputDialog(null,
                message + "\nEnter your name:",
                "Player Name",
                JOptionPane.PLAIN_MESSAGE);

        if (playerName == null || playerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return playerName.trim();
    }

    public void decreaseLife() {
        life--;
        if (life <= 0) {
            String playerName = promptForPlayerName("Game Over! You lost all your lives.");
            if (playerName != null) {
                //System.out.println("Player Name: " + playerName + ", Points: " + points + ", Status: Lost");
            }
            System.exit(0);
        } else {
            yogi.setPosition(0, 0);
            //System.out.println("Yogi lost a life! Lives remaining: " + life);
        }
    }

    public void checkBasketCollection() {
        for (PicnicBasket basket : baskets) {
            if (collidesWithBasket(basket)) {
                baskets.remove(basket);
                points++;
//                System.out.println("Basket collected! Points: " + points);
                break;
            }
        }
    }

    private boolean collidesWithBasket(PicnicBasket basket) {
        return basket.x < yogi.getX() + 50 && basket.x + basket.size > yogi.getX()
                && basket.y < yogi.getY() + 50 && basket.y + basket.size > yogi.getY();
    }

    public void checkWinCondition() {
        if (baskets.isEmpty()) {
            levelUp();
        }
    }

    private void levelUp() {
        if (level < 10) {
            level++;
            rangerCount++;
            obstacles.clear();
            baskets.clear();
            generateObstacles();
            generatePicnicBaskets();
            generateRangers();
            yogi.setPosition(0, 0);
//            System.out.println("Level " + level + " started! Rangers: " + rangerCount);
        } else {
            String playerName = promptForPlayerName("Congratulations! You've completed all 10 levels!");
            if (playerName != null) {
//                System.out.println("Player Name: " + playerName + ", Points: " + points + ", Status: Won");
            }
            System.exit(0);
        }
    }

    // Remaining methods remain the same...
}
