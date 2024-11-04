package com.mycompany.main;

/**
 * The LuckyField class represents a special field on the game board
 * that provides a bonus to the player who lands on it.
 */
public class LuckyField extends Field {
    private final int bonus;

    /**
     * Constructs a LuckyField with the given name and bonus amount.
     *
     * @param name  the name of the lucky field
     * @param bonus the bonus amount awarded to the player
     */
    public LuckyField(String name, int bonus) {
        super(name);
        this.bonus = bonus;
    }

    /**
     * Handles the interaction when a player lands on this lucky field.
     * The player receives the specified bonus.
     *
     * @param player the player who landed on the lucky field
     */
    @Override
    public void Reached(Player player) {
        player.collect(bonus);
        System.out.println(player.getName() + " landed on a lucky field and received " + bonus);
    }
}
