package com.mycompany.finalassign;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the user interface for the "Five in a Row" game.
 * This class handles the creation of the game window, game board setup,
 * player moves, and game state updates such as detecting wins or draws.
 * <p>
 * The game alternates turns between two players ('X' and 'O'). The game ends
 * when a player aligns five consecutive signs, or the board is filled without
 * any player winning, resulting in a draw. The interface is built using 
 * Swing components, with the game board displayed as a grid of buttons.
 * The board can be created with a customizable size (e.g., 6x6, 10x10).
 * </p>
 */
public class GameUI {
    private final JFrame frame;  
    private final GameBoard board;  
    private final GameLogic logic;  //this if for the Logic for checking game rules and conditions
    private final JLabel statusLabel;  // this Label to show the current player's turn
    private final JButton[][] buttons;  // Button grid representing the game board
    private char currentPlayer = 'X';  // Tracks the current player ('X' or 'O')

    /**
     * Initializes the game UI with a specified board size.
     * Creates the board, sets up game logic, and displays the main game frame.
     *
     * @param size the size of the game board (e.g., 6 for a 6x6 board).
     */
    public GameUI(int size) {
        board = new GameBoard(size);
        logic = new GameLogic(board);
        frame = new JFrame("Five in a Row Game");
        buttons = new JButton[size][size];
        statusLabel = new JLabel("Turn: Player " + currentPlayer);
        setupUI(size);
    }

    /**
     * Sets up the main components of the UI, including the frame, the status label,
     * and the grid of buttons that represents the game board.
     *
     * @param size the size of the game board (e.g., 6 for a 6x6 grid).
     */
    private void setupUI(int size) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(size, size));
        initializeButtons(size, boardPanel);
        frame.add(statusLabel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /**
     * Initializes each button in the game board grid, sets its appearance,
     * and attaches an ActionListener for handling player moves.
     *
     * @param size the size of the game board.
     * @param boardPanel the panel that holds the grid of buttons.
     */
    private void initializeButtons(int size, JPanel boardPanel) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton(" ");
                final int row = i, col = j;
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                buttons[i][j].addActionListener(e -> handleClick(row, col));
                boardPanel.add(buttons[i][j]);
            }
        }
    }

    /**
     * Handles a player's click on a specific cell. Updates the cell with the current
     * player's sign if the cell is empty, checks the game state (win or draw),
     * and toggles the player turn.
     *
     * @param row the row of the clicked cell.
     * @param col the column of the clicked cell.
     */
    private void handleClick(int row, int col) {
        if (board.getCell(row, col) == ' ') {
            board.setCell(row, col, currentPlayer);
            buttons[row][col].setText(String.valueOf(currentPlayer));
            checkGameEnd();
            togglePlayer();
            updateStatusLabel();
        }
    }

    /**
     * Switches the current player to the other player (between 'X' and 'O').
     */
    private void togglePlayer() {
     if (currentPlayer == 'X') {
        currentPlayer = 'O';
     } else {
        currentPlayer = 'X';
     }
 }

    /**
     * Updates the status label to display whose turn it is.
     */
    private void updateStatusLabel() {
        statusLabel.setText("Turn: Player " + currentPlayer);
    }

    /**
     * Checks if the game has ended due to a win or a draw. If there is a winner, displays a
     * message and starts a new game. If the board is full without a winner, it declares a draw.
     * If neither condition is met, it applies the game logic for removing consecutive signs
     * and refreshes the board display.
     */
    private void checkGameEnd() {
        if (logic.checkWin(currentPlayer)) {
            JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
            startNewGame(board.getSize());
        } else if (board.isFull()) {
            JOptionPane.showMessageDialog(frame, "It's a draw!");
            startNewGame(board.getSize());
        } else {
            logic.checkConsecutiveSigns(currentPlayer);
            refreshBoard();
        }
    }

    /**
     * Refreshes the game board display by updating each button's text to match
     * the current state of the board.
     */
    private void refreshBoard() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                buttons[i][j].setText(String.valueOf(board.getCell(i, j)));
            }
        }
    }

    /**
     * Starts a new game by clearing the current board and creating a new game UI.
     *
     * @param size the size of the game board.
     */
    private void startNewGame(int size) {
        board.clearBoard();
        frame.dispose();
        new GameUI(size);
    }
}
