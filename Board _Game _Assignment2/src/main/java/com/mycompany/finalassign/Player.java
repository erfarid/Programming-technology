package com.mycompany.finalassign;

/**
 * Represents a player in the "Five in a Row" game, with a name and a sign
 * (either 'X' or 'O') to mark their moves on the board.
 */
public class Player {
    private String name;  // The player's name
    private char sign;    // The player's sign, either 'X' or 'O'

    /**
     * Constructs a Player with a specified name and sign.
     *
     * @param name the name of the player.
     * @param sign the sign used by the player ('X' or 'O').
     */
    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
    }

    /**
     * Gets the player's name.
     *
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's sign.
     *
     * @return the sign of the player ('X' or 'O').
     */
    public char getSign() {
        return sign;
    }
}
