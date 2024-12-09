/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.finalassign;

import javax.swing.*;

/**
 * The final class is the entry point for the application. It handles the selection of the board size
 * through a dialog box and starts the game with the chosen board size. It uses the JOptionPane to display a prompt
 * for the user to select a board size from three options: 6x6, 10x10, and 14x14.
 * 
 * After the user selects the board size, it initializes the game interface by passing the chosen size to the
 * GameUI class.
 * 
 * @author ASUS
 */
public class Finalassign {

    /**
     * Default constructor for Finalassign.
     */
    public Finalassign() {
        // Default constructor with no specific initialization
    }

    /**
     * The main method is the entry point for the program. It prompts the user to choose a board size and
     * starts the game based on the selected size.
     * 
     * @param args The command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Prompt the user to select a board size
        String[] options = {"6x6", "10x10", "14x14"};
        int choice = JOptionPane.showOptionDialog(null, "Choose board size:", "Board Size Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        int boardSize;
        switch (choice) {
            case 1:
                boardSize = 10;
                break;
            case 2:
                boardSize = 14;
                break;
            default:
                boardSize = 6;
        }

        // Start the game with the selected board size
        new GameUI(boardSize);
    }
}
