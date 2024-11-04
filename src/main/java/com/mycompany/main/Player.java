package com.mycompany.main;

import java.util.List;
import java.util.ArrayList;

/**
 * The Player class serves as an abstract representation of a player in the board game.
 * It manages the player's name, balance, properties, position on the board, and transaction handling.
 */
public abstract class Player {

    /**
     *
     */
    protected String name;                  // The name of the player

    /**
     *
     */
    protected int balance = 10000;         // The player's initial balance

    /**
     *
     */
    protected List<PropertyField> properties = new ArrayList<>(); // List of properties owned by the player

    /**
     *
     */
    protected int position = -1;            // The player's position on the board, initialized outside the board

    /**
     *
     */
    protected Transaction transaction = new Transaction(); // Handles transactions related to buying and payments

    /**
     * Constructs a Player instance with the specified name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Gets the current position of the player on the board.
     *
     * @return the current position of the player
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the player on the board.
     *
     * @param position the new position of the player
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Checks if the player can afford the specified amount.
     *
     * @param amount the amount to check
     * @return true if the player can afford the amount, false otherwise
     */
    public boolean canAfford(int amount) {
        return balance >= amount;
    }

    /**
     * Allows the player to buy a property.
     *
     * @param property the property to be purchased
     */
    public void buyProperty(PropertyField property) {
        transaction.handleBuyProperty(this, property);
    }

    /**
     * Allows the player to buy a house on a property.
     *
     * @param property the property to build a house on
     */
    public void buyHouse(PropertyField property) {
        transaction.handleBuyHouse(this, property);
    }

    /**
     * Handles payments from this player to another player.
     *
     * @param owner  the owner of the property to pay
     * @param amount the amount to be paid
     */
    public void pay(Player owner, int amount) {
        transaction.handlePayment(this, owner, amount);
    }

    /**
     * Collects a specified amount of money for the player.
     *
     * @param amount the amount to be collected
     */
    public void collect(int amount) {
        balance += amount;
    }

    /**
     * Decreases the player's balance by a specified amount.
     *
     * @param amount the amount to be deducted from the balance
     */
    public void decreaseBalance(int amount) {
        if (amount > 0) {
            balance -= amount;
        }
    }

    /**
     * Gets the current balance of the player.
     *
     * @return the player's current balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the player to a specified amount.
     *
     * @param balance the new balance of the player
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Adds a property to the player's collection.
     *
     * @param property the property to be added
     */
    public void addProperty(PropertyField property) {
        properties.add(property);
    }

    /**
     * Gets the name of the player.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Prints the current status of the player, including balance and the number of properties owned.
     */
    public void printStatus() {
        System.out.println(name + " has " + balance + " and owns " + properties.size() + " properties.");
    }

    /**
     * Moves the player based on the result of a dice roll.
     * The new position is calculated and the player interacts with the field they land on.
     *
     * @param diceRoll the result of the dice roll
     * @param board    the list of fields on the game board
     */
    protected void move(int diceRoll, List<Field> board) {
        int newPos = (getPosition() + diceRoll) % board.size(); // Calculate the new position on the board
        setPosition(newPos); // Update the player's current position
        Field field = board.get(newPos); // Retrieve the field where the player lands
        handleFieldInteraction(field); // Handle interaction based on the field
    }

    /**
     * Handles the interaction of the player with the field they land on.
     * This method is abstract and must be implemented by subclasses.
     *
     * @param field the field that the player lands on
     */
    protected abstract void handleFieldInteraction(Field field);

    /**
     * Executes the player's turn based on the result of a dice roll.
     * This method is abstract and must be implemented by subclasses.
     *
     * @param diceRoll the result of the dice roll
     * @param board    the list of fields on the game board
     */
    public abstract void playTurn(int diceRoll, List<Field> board);
}
