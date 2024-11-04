package com.mycompany.main;

import java.util.List;

/**
 * The CarefulPlayer class represents a player in the board game who plays with a cautious strategy.
 * This player will evaluate properties before making any transactions, ensuring they maintain a balance
 * above a certain threshold before making purchases.
 */
public class CarefulPlayer extends Player {
    
    /**
     * Constructs a CarefulPlayer instance with the specified name.
     *
     * @param name the name of the player
     */
    public CarefulPlayer(String name) {
        super(name);//call the superclass that is player
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
        move(diceRoll, board);
    }

    /**
     * Handles the interaction of the player with the field they land on.
     * This method contains logic for interacting with property fields and executing actions based on the state
     * of the field. 
     *
     * @param field the field that the player lands on
     */
    @Override
    protected void handleFieldInteraction(Field field) {
        if (field instanceof PropertyField propertyField) {
            if (!propertyField.isOwned() && canAfford(propertyField.getPrice()) && getBalance() >= 1000) {
                // then player can purchase it if the property is unowned then player can buy it 
                buyProperty(propertyField);
            } else if (propertyField.isOwned() && propertyField.getOwner() != this) {
                // if property is owned by another player the player will pay rent 
                if (propertyField.hasHouse()) {
                    pay(propertyField.getOwner(), propertyField.getHouseRent());
                } else {
                    pay(propertyField.getOwner(), propertyField.getRent());
                }

            } else if (propertyField.hasHouse() && propertyField.getOwner() == this && canAfford(propertyField.getHousePrice())) {
                buyHouse(propertyField);
            }
        } else {
            field.Reached(this); // if field is not property field the player will interact with it using this method 
        }
    }
}
