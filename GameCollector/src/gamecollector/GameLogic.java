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
    private int numBaskets;
    private int points = 0;
    private int numTrees;
    private int numMountains;
    private int obstacleSize;
    private int basketSize = 30;
    private long startTime;
    private int level = 1;
    private int rangerCount = 1;

    public GameLogic(int numTrees, int numMountains, int numBaskets, int obstacleSize) {
        this.numTrees = numTrees;
        this.numMountains = numMountains;
        this.numBaskets = numBaskets;
        this.obstacleSize = obstacleSize;

        // Initialize variables
        yogi = new Yogi(0, 0, this);
        rangers = new ArrayList<>();
        obstacles = new ArrayList<>();
        baskets = new ArrayList<>();

        startTime = System.currentTimeMillis();
    }

    // Explicit method to initialize the game elements
    public void initializeGame() {
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
    for (int i = 0; i < numTrees; i++) {
        placeObstacle(new Tree(randomObstaclePosition(Park.getWidth()), randomObstaclePosition(Park.getHeight()), obstacleSize));
    }
    for (int i = 0; i < numMountains; i++) {
        placeObstacle(new Mountain(randomObstaclePosition(Park.getWidth()), randomObstaclePosition(Park.getHeight()), obstacleSize));
    }
}


    private int randomObstaclePosition(int limit) {
        return (int) (Math.random() * (limit - obstacleSize));
    }

    private void placeObstacle(Obstacles obstacle) {
        boolean validPosition = false;
        int minGap = 60;
        int borderGap = 50;
        int maxRetries = 100;
        int retries = 0;

        while (!validPosition && retries < maxRetries) {
            retries++;
            obstacle.x = randomObstaclePosition(Park.getWidth());
            obstacle.y = randomObstaclePosition(Park.getHeight());

            Rectangle obstacleBounds = new Rectangle(
                obstacle.x - minGap,
                obstacle.y - minGap,
                obstacleSize + minGap * 2,
                obstacleSize + minGap * 2
            );

            validPosition = true;

            if (obstacle.x < borderGap || obstacle.y < borderGap ||
                obstacle.x + obstacleSize > Park.getWidth() - borderGap || obstacle.y + obstacleSize > Park.getHeight() - borderGap) {
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
            System.out.println("Failed to place obstacle after " + retries + " retries.");
        }
    }

    public void generatePicnicBaskets() {
        baskets.clear();
        for (int i = 0; i < numBaskets; i++) {
            PicnicBasket basket = PicnicBasket.generateNonOverlappingBasket(Park.getWidth(), Park.getHeight(), basketSize, obstacles, baskets);
            baskets.add(basket);
        }
    }

    public boolean collidesWithObstacles(int x, int y, int width, int height) {
        for (Obstacles obstacle : obstacles) {
            if (obstacle.collidesWith(x, y, width, height)) {
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
            // Prompt for player name on game over
            String playerName = promptForPlayerName("Game Over! You lost all your lives.");
            if (playerName != null) {
                System.out.println("Player Name: " + playerName + ", Points: " + points + ", Status: Lost");
            }
            System.exit(0);
        } else {
            yogi.setPosition(0, 0);
            System.out.println("Yogi lost a life! Lives remaining: " + life);
        }
    }

    public void checkBasketCollection() {
        for (PicnicBasket basket : baskets) {
            if (collidesWithBasket(basket)) {
                baskets.remove(basket);
                points++;
                System.out.println("Basket collected! Points: " + points);
                break;
            }
        }
    }

    private boolean collidesWithBasket(PicnicBasket basket) {
        return basket.x < yogi.getX() + 50 && basket.x + basket.size > yogi.getX()
                && basket.y < yogi.getY() + 50 && basket.y + basket.size > yogi.getY();
    }

public void checkWinCondition() {
    if (baskets.isEmpty() && level > 0) {
        System.out.println("No baskets left. Checking Level Up.");
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
            System.out.println("Level " + level + " started! Rangers: " + rangerCount);
        } else {
            // Prompt for player name on game win
            String playerName = promptForPlayerName("Congratulations! You've completed all 10 levels!");
            if (playerName != null) {
                System.out.println("Player Name: " + playerName + ", Points: " + points + ", Status: Won");
            }
            System.exit(0);
        }
    }
}
