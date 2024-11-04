package com.mycompany.main;

/**
 * The Bank class manages financial transactions between players and the game,
 * including handling payments from service fields and collecting rewards from lucky fields.
 */
public class Bank {

    /**
     * Handles collecting money from lucky fields.
     *
     * @param player the player who landed on the lucky field
     * @param amount the amount of money to collect
     */
    public void handleLuckyField(Player player, int amount) {
        // I am doing white box testing 
        assert player != null : "Player cannot be null";
        assert amount > 0 : "Amount must be positive";

        player.collect(amount);
        System.out.println(player.getName() + " received " + amount + " from a lucky field.");

        assert player.getBalance() >= amount : "Player's balance should be greater than or equal to the collected amount";
    }

    /**
     * Handles payment from a player when they land on service fields.
     *
     * @param player the player who landed on the service field
     * @param amount the amount to be paid to the bank
     */
    public void handleServiceField(Player player, int amount) {
        assert player != null : "Player cannot be null";
        assert amount > 0 : "Amount must be positive";

        if (player.getBalance() < amount) {
            System.out.println(player.getName() + " can't afford to pay the bank!");
            player.setBalance(0);
        } else {
            player.decreaseBalance(amount);
            System.out.println(player.getName() + " paid " + amount + " to the bank.");

            assert player.getBalance() >= 0 : "Player's balance should not be negative after bank payment";
        }
    }
}
