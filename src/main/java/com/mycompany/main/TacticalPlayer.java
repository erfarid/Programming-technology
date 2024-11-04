package com.mycompany.main;

import java.util.List;

/**
 * The TacticalPlayer class represents a player in the board game who adopts a tactical strategy.
 * This player has the option to skip their turn based on their previous actions.
 */
public class TacticalPlayer extends Player {
    private boolean leaveTurn = false; // Indicates whether the player should skip their turn

    /**
     * Constructs a TacticalPlayer instance with the specified name.
     *
     * @param name the name of the player
     */
    public TacticalPlayer(String name) {
        super(name);
    }

    /**
     * Executes the player's turn based on the result of a dice roll.
     * The player may skip their turn depending on the leaveTurn flag.
     *
     * @param diceRoll the result of the dice roll
     * @param board    the list of fields on the game board
     */
    @Override
    public void playTurn(int diceRoll, List<Field> board) {
        if (leaveTurn) { // if true player skip their turn for this round
            leaveTurn = false;  // Reset skip for the next turn
            return;
        }
        move(diceRoll, board);
        leaveTurn = true;  // Skip the next turn
    }

    /**
     * Handles the interaction of the player with the field they land on.
     * Tactical players do not perform any special actions and simply trigger the field's interaction method.
     *
     * @param field the field that the player lands on
     */
    @Override
    protected void handleFieldInteraction(Field field) {
        field.Reached(this); // Tactical players simply land on the field
    }
}
