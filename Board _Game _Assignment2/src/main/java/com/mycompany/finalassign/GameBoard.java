/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalassign;

/**
 *
 * @author ASUS
 */


/**
 * Manages the game board for the "Five in a Row" game. Handles cell values, checks
 * if the board is full, and provides methods to set, get, and clear cell values.
 */
public class GameBoard {
    private final int size;       // Size of the game board (number of rows and columns)
    private final char[][] board; // 2D array representing the board cells

    /**
     * Constructs a GameBoard with the specified size and initializes it.
     *
     * @param size the dimension of the board (size x size).
     */
    public GameBoard(int size) {
        this.size = size;
        board = new char[size][size];
        clearBoard();
    }

    /**
     * Gets the size of the board.
     *
     * @return the size of the board.
     */
    public int getSize() {
        return size;
    }

    /**
     * Retrieves the value of a specific cell on the board.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return the character in the specified cell.
     */
    public char getCell(int row, int col) {
        return board[row][col];
    }

    /**
     * Sets a cell on the board to a specific player's sign.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @param sign the character to place in the specified cell ('X' or 'O').
     */
    public void setCell(int row, int col, char sign) {
        board[row][col] = sign;
    }

    /**
     * Clears the value of a specific cell by setting it to an empty space (' ').
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     */
    public void clearCell(int row, int col) {
        board[row][col] = ' ';
    }

    /**
     * Checks if the board is completely filled with no empty cells.
     *
     * @return true if all cells are occupied, false otherwise.
     */
    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Clears the entire board, setting all cells to an empty space (' ').
     */
    public void clearBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
    }
}

