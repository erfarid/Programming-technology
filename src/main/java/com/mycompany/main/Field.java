package com.mycompany.main;

/**
 * The Field class serves as an abstract representation of a field on the game board.
 * Each field has a name and defines an action that occurs when a player lands on it.
 */
public abstract class Field {

    /**
     *
     */
    protected String name;

    /**
     * Constructs a Field with a specified name.
     *
     * @param name the name of the field
     */
    public Field(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the field.
     *
     * @return the name of the field
     */
    public String getName() {
        return name;
    }

    /**
     * Defines the action that occurs when a player lands on the field.
     *
     * @param player the player who landed on the field
     */
    public abstract void Reached(Player player);
}
