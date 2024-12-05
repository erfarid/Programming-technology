/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamecollector;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private int score = 0;
    private int lives = 3;

    public Game() {
        setTitle("Yogi's Adventure");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add the GameMenu to the frame
        GameMenu gameMenu = new GameMenu(this);
        setJMenuBar(gameMenu);

        // Set up other game components (e.g., the game panel, player, etc.)
        // Add a game panel for drawing the game screen, etc.

        setVisible(true);
    }

    // Restart the game
    public void restartGame() {
        score = 0;
        lives = 3;
        // Reset any game-related logic (e.g., positions, obstacles, etc.)
        System.out.println("Game Restarted!");
    }

    // Show the high scores
    public void showHighScores() {
        JOptionPane.showMessageDialog(this, "High Scores: \n1. John - 500\n2. Jane - 400\n...");
    }

    public static void main(String[] args) {
        new Game(); // Initialize the game
    }
}
