package com.mycompany.finalassign;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

/**
 * The GameLogic class handles the core logic for the game, including checking for consecutive signs,
 * removing random signs when certain conditions are met, and checking for a win.
 */
public class GameLogic {

    private final GameBoard board;
    private final Random random;

    /**
     * Constructs a new GameLogic instance.
     *
     * @param board The game board associated with the game logic.
     */
    public GameLogic(GameBoard board) {
        this.board = board;
        this.random = new Random();
    }

    /**
     * Checks for any consecutive signs (3 or 4 in a row) in any direction on the board.
     * If 3 or 4 consecutive signs are found, random signs are removed from the board.
     *
     * @param sign The sign (X or O) to check for consecutive occurrences.
     */
    public void checkConsecutiveSigns(char sign) {
        int size = board.getSize(); // Get the board size
        // Here in the Directions, I am checking in the [horizontal, vertical, diagonal-right, diagonal-left]
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};

        // Here I will check all the directions, that is why I loop through the whole board
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < directions.length; j++) {
                int dRow = directions[j][0]; // Here I am doing direction change for rows
                int dCol = directions[j][1]; // Here I am doing direction change for columns

                // Check for consecutive signs starting from (i, 0) or (0, i)
                checkLineForRemoval(i, 0, dRow, dCol, sign);
                checkLineForRemoval(0, i, dRow, dCol, sign);
            }
        }
    }

    /**
     * Helper method to check a line in a specific direction for consecutive signs.
     * If 3 or 4 consecutive signs are found, removes random signs from the board.
     *
     * @param startRow The starting row position.
     * @param startCol The starting column position.
     * @param dRow     The row direction for movement.
     * @param dCol     The column direction for movement.
     * @param sign     The sign (X or O) to check for.
     */
    private void checkLineForRemoval(int startRow, int startCol, int dRow, int dCol, char sign) {
        List<int[]> positions = new ArrayList<>(); // In this List, I will store positions of consecutive signs
        int count = 0;

        // Loop through the line in the given direction
        for (int row = startRow, col = startCol;
             row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize();
             row += dRow, col += dCol) {

            // If the sign matches, then I will add position to the list and increment the count
            if (board.getCell(row, col) == sign) {
                positions.add(new int[]{row, col});
                count++;
            } else {
                // If there were 3 or 4 consecutive signs, remove random ones
                if (count == 3) {
                    removeRandomSigns(sign, 1);
                } else if (count == 4) {
                    removeRandomSigns(sign, 2); // Here I am removing 2 random signs from the board
                }
                positions.clear();
                count = 0;
            }
        }

        // When loop ends, then here I am checking if there were 3 or 4 consecutive signs at the end
        if (count == 3) {
            removeRandomSigns(sign, 1); // Remove 1 random sign if 3 consecutive found
        } else if (count == 4) {
            removeRandomSigns(sign, 2); // Remove 2 random signs if 4 consecutive found
        }
    }

    /**
     * This method is for randomly removing signs from the board.
     *
     * @param sign            The sign (X or O) to remove.
     * @param numSignsToRemove The number of signs to remove.
     */
    private void removeRandomSigns(char sign, int numSignsToRemove) {
        List<int[]> allSignPositions = getAllSignPositions(sign); // Get all positions of the sign
        randomlyClearCells(allSignPositions, numSignsToRemove); // Randomly clear the specified number of signs
    }

    /**
     * Collects all positions of a specific sign (X or O) on the board.
     *
     * @param sign The sign (X or O) to collect positions for.
     * @return A list of positions (row, column) of the specified sign.
     */
    private List<int[]> getAllSignPositions(char sign) {
        List<int[]> positions = new ArrayList<>();
        // Loop through the board to find all positions of the sign
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getCell(row, col) == sign) {
                    positions.add(new int[]{row, col}); // Here I am adding position to list
                }
            }
        }
        return positions;
    }

    /**
     * Randomly clears cells on the board.
     *
     * @param positions        A list of positions of the signs to be cleared.
     * @param numSignsToRemove The number of signs to remove randomly.
     */
    private void randomlyClearCells(List<int[]> positions, int numSignsToRemove) {
        // Shuffle the list of positions randomly
        Collections.shuffle(positions, random);

        // Clear cells for the specified number of signs or until the list is empty
        for (int i = 0; i < numSignsToRemove && i < positions.size(); i++) {
            int[] pos = positions.get(i); // Get the position of the sign to be removed
            board.clearCell(pos[0], pos[1]); // Clear the cell at that position
        }
    }

    /**
     * Method to check if the player has won by having five consecutive signs in a row.
     *
     * @param sign The sign (X or O) to check for a win.
     * @return True if the player has won, false otherwise.
     */
    public boolean checkWin(char sign) {
        // Loop through every cell in the board to check for five consecutive signs
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (hasFiveConsecutive(row, col, sign)) {
                    return true; // Return true if five consecutive signs are found
                }
            }
        }
        return false; // Return false if no win condition is met
    }

    /**
     * Helper method to check if there are five consecutive signs in any direction from a given starting point.
     *
     * @param row  The starting row.
     * @param col  The starting column.
     * @param sign The sign (X or O) to check for.
     * @return True if five consecutive signs are found, false otherwise.
     */
    private boolean hasFiveConsecutive(int row, int col, char sign) {
        // Check if five consecutive signs are found in any of the four directions
        boolean horizontal = checkConsecutive(row, col, 1, 0, sign);  // Check horizontally
        boolean vertical = checkConsecutive(row, col, 0, 1, sign);    // Check vertically
        boolean diagonalRight = checkConsecutive(row, col, 1, 1, sign); // Check diagonal (top-left to bottom-right)
        boolean diagonalLeft = checkConsecutive(row, col, 1, -1, sign); // Check diagonal (top-right to bottom-left)

        return horizontal || vertical || diagonalRight || diagonalLeft;
    }

    /**
     * Checks if there are five consecutive signs in a specific direction starting from a given point.
     *
     * @param row  The starting row.
     * @param col  The starting column.
     * @param dRow The row direction for movement.
     * @param dCol The column direction for movement.
     * @param sign The sign (X or O) to check for.
     * @return True if five consecutive signs are found, false otherwise.
     */
    private boolean checkConsecutive(int row, int col, int dRow, int dCol, char sign) {

        for (int i = 0; i < 5; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;

            if (r < 0 || r >= board.getSize() || c < 0 || c >= board.getSize() || board.getCell(r, c) != sign) {
                return false;
            }
        }
        return true; // Return true if 5 consecutive signs are found
    }
}
