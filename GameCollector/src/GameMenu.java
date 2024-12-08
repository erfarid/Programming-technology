package gamecollector;

import javax.swing.*;
import java.awt.*;

public class GameMenu {

    private JPanel menuPanel;
    private JFrame frame;
    private UI gameUI;
    private Park park;

    public GameMenu(JFrame frame, UI gameUI, Park park) {
        this.frame = frame;
        this.gameUI = gameUI;
        this.park = park;

        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setPreferredSize(new Dimension(200, Park.HEIGHT));
        menuPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("Game Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        menuPanel.add(titleLabel, gbc);

        gbc.gridy++;
        JButton restartButton = new JButton("Restart");
        restartButton.setPreferredSize(new Dimension(150, 40));
        restartButton.addActionListener(e -> restartGame());
        menuPanel.add(restartButton, gbc);

        gbc.gridy++;
        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setPreferredSize(new Dimension(150, 40));
        menuPanel.add(leaderboardButton, gbc);

        gbc.gridy++;
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(150, 40));
        exitButton.addActionListener(e -> exitGame());
        menuPanel.add(exitButton, gbc);
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    private void restartGame() {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to restart the game?",
                "Restart Game", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            UI newGameUI = new UI();
            frame.getContentPane().removeAll();
            JPanel mainPanel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (Park.getBackgroundImage() != null) {
                        g.drawImage(Park.getBackgroundImage(), 0, 0, Park.WIDTH, Park.HEIGHT, null);
                    }
                }
            };
            mainPanel.add(newGameUI, BorderLayout.CENTER);
            mainPanel.add(getMenuPanel(), BorderLayout.WEST);
            frame.add(mainPanel);

            frame.revalidate();
            frame.repaint();

            gameUI = newGameUI;
            gameUI.requestFocusInWindow();
        }
    }

    private void exitGame() {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the game?",
                "Exit Game", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
