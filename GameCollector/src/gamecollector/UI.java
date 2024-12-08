package gamecollector;

import javax.swing.*;
import java.awt.*;

public class UI extends JPanel {

    private static UI instance;
    private GameLogic gameLogic;
    private Image backgroundImage;

    public UI() { // Constructor no longer takes parameters
        instance = this;
        gameLogic = new GameLogic(); // Use the updated GameLogic constructor

        // Use the same method to load the background image
        backgroundImage = Park.getBackgroundImage();

        gameLogic.initializeGame();
        setLayout(null);
        setPreferredSize(new Dimension(Park.WIDTH, Park.HEIGHT)); // Use Park dimensions
        setFocusable(true);

        addKeyListener(gameLogic.getYogi().getKeyListener());

        Timer timer = new Timer(50, e -> gameLoop());
        timer.start();
    }

    public static UI getInstance() {
        return instance;
    }

    private void gameLoop() {
        gameLogic.getYogi().move();

        if (gameLogic.collidesWithObstacles(gameLogic.getYogi().getX(), gameLogic.getYogi().getY(), 50, 50)) {
            gameLogic.getYogi().undoLastMove();
        }

        for (Ranger ranger : gameLogic.getRangers()) {
            ranger.move();
            if (ranger.crossesYogi(gameLogic.getYogi().getX(), gameLogic.getYogi().getY(), 50, 50)) {
                gameLogic.decreaseLife();
            }
        }

        gameLogic.checkBasketCollection();
        gameLogic.checkWinCondition();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Park.WIDTH, Park.HEIGHT, null);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, Park.WIDTH, Park.HEIGHT);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        g.drawString("Lives: " + gameLogic.getLife(), 500, 30);
        g.drawString("Points: " + gameLogic.getPoints(), 10, 30);
        g.drawString("Level: " + gameLogic.getLevel(), 250, 30);
        g.drawString("Time: " + gameLogic.getElapsedTime() + "s", 700, 30);

        for (Obstacles obstacle : gameLogic.getObstacles()) {
            obstacle.paintComponent(g);
        }

        for (PicnicBasket basket : gameLogic.getBaskets()) {
            basket.paintComponent(g);
        }

        gameLogic.getYogi().paintComponent(g);
        for (Ranger ranger : gameLogic.getRangers()) {
            ranger.paintComponent(g);
        }
    }
}
