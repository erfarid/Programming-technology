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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JMenuBar {
    private JMenu gameMenu;
    private JMenuItem restartItem, highscoreItem;

    public GameMenu(Game game) {
        // Create the menu bar
        gameMenu = new JMenu("Game");
        
        // Create Restart menu item
        restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.restartGame(); // Call the restart method in Game
            }
        });

        // Create Highscore menu item
        highscoreItem = new JMenuItem("High Scores");
        highscoreItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showHighScores(); // Call the show high scores method in Game
            }
        });

        // Add items to the Game menu
        gameMenu.add(restartItem);
        gameMenu.add(highscoreItem);

        // Add the Game menu to the menu bar
        add(gameMenu);
    }
}
