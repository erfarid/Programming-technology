package gamecollector;

import javax.swing.*;

public class GameMenu extends JMenuBar {
    private Game game;
    private Database db;

    public GameMenu(Game game, Database db) {
        this.game = game;
        this.db = db;

        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartItem = new JMenuItem("Restart");
        JMenuItem highScoresItem = new JMenuItem("High Scores");
        JMenuItem exitItem = new JMenuItem("Exit");

        restartItem.addActionListener(e -> game.restartGame());
        highScoresItem.addActionListener(e -> game.showHighScores());
        exitItem.addActionListener(e -> System.exit(0));

        gameMenu.add(restartItem);
        gameMenu.add(highScoresItem);
        gameMenu.add(exitItem);

        add(gameMenu);
    }
}
