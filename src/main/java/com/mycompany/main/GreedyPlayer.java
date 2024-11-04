package com.mycompany.main;

import java.util.List;

/**
 * The GreedyPlayer class represents a player in the board game who plays with a greedy strategy.
 * This player focuses on landing on fields without making transactions based on property ownership.
 */
public class GreedyPlayer extends Player {

    /**
     * Constructs a GreedyPlayer instance with the specified name.
     *
     * @param name the name of the player
     */
    public GreedyPlayer(String name) {
        super(name);
    }

    /**
     * Executes the player's turn based on the result of a dice roll.
     * This method moves the player to the new position based on the dice roll.
     *
     * @param diceRoll the result of the dice roll
     * @param board    the list of fields on the game board
     */
    @Override
    public void playTurn(int diceRoll, List<Field> board) {
        move(diceRoll, board); // moves the player according to the dice rolls
    }

    /**
     * Handles the interaction of the player with the field they land on.
     * Greedy players do not perform any transactions and simply trigger the field's interaction method.
     *
     * @param field the field that the player lands on
     */
    @Override
    protected void handleFieldInteraction(Field field) {
        field.Reached(this); // Greedy players simply land on the field
    }
}
