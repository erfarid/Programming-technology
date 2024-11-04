package com.mycompany.main;

import java.util.List;
import java.util.ArrayList;

/**
 * The Game class simulates a board game involving multiple players and various fields.
 * It manages player turns, handles dice rolls, and maintains the game state.
 */
public class Game {
    // Here I make a list that will store all participating players
    private final List<Player> players = new ArrayList<>();
    private final List<Field> board = new ArrayList<>();
    // Here the board is containing the different fields

    /**
     * Adds a player to the game.
     *
     * @param player the player to be added to the game
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Adds a field to the game board.
     *
     * @param field the field to be added to the board
     */
    public void addField(Field field) {
        board.add(field);
    }

    /**
     * Simulates the game using predefined dice rolls.
     *
     * @param diceRolls a list of integer arrays representing the dice rolls for each round
     */
    public void simulateGame(List<int[]> diceRolls) { // Here diceRolls contains the rolls for a particular 
        for (int round = 0; round < diceRolls.size(); round++) {
            System.out.println("Round " + (round + 1) + ":");
            
            playRound(diceRolls.get(round));
            
            System.out.println(); 
        }
    }

    /**
     * Handles each round of the game by processing player turns based on the current round's dice rolls.
     *
     * @param currentRoundRolls an array of dice rolls for the current round
     */
    private void playRound(int[] currentRoundRolls) {
        int index = 0;
        for (Player player : players) {
            int diceRoll = currentRoundRolls[index++]; // Here I am incrementing index after accessing the roll
            
            player.playTurn(diceRoll, board);
            
            player.printStatus();
        }
    }
    
    // Print each player's status at the end of the game
    // public void printFinalPlayerStatus() {
    //     System.out.println("Final Player Status:");
    //     for (Player player : players) {
    //         player.printStatus();
    //     }
    // }
}
