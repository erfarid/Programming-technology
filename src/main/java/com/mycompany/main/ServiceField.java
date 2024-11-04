/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

/**
 * The ServiceField class represents a service field on the game board.
 * Players must pay a specified cost when they land on this field.
 */
public class ServiceField extends Field {
    private final int cost;

    /**
     * Constructs a ServiceField with a given name and cost.
     *
     * @param name the name of the service field
     * @param cost the cost that players must pay when landing on this field
     */
    public ServiceField(String name, int cost) {
        super(name);
        this.cost = cost;
    }

    /**
     * Handles the interaction when a player lands on this service field.
     *
     * @param player the player who landed on the field
     */
    @Override
    public void Reached(Player player) {
        if (player.canAfford(cost)) { // First here I am checking that the player has enough money to pay the service
            // player.balance -= cost;
            player.decreaseBalance(cost);
            System.out.println(player.getName() + " landed on a service field and paid " + cost + " to the bank.");
        } else {
            System.out.println(player.getName() + " landed on a service field but can't afford to pay " + cost + ". They are bankrupt!");
            // player.balance = 0; 
            player.setBalance(0); 
        }
    }
}
