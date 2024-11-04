package com.mycompany.main;

/**
 * The PropertyField class represents a property field on the game board.
 * Players can buy this property, pay rent to the owner, and build houses on it.
 */
public class PropertyField extends Field {
    private Player owner = null;
    private boolean hasHouse = false;
    private final int price = 1000; // Property can be bought for 1000
    private final int housePrice = 4000; // Build a house on it 
    private final int rent = 500; // The player should pay to the owner 
    private final int houseRent = 2000; // Pay 2000 to player if house on the property 

    /**
     * Constructs a PropertyField with the given name.
     *
     * @param name the name of the property field
     */
    public PropertyField(String name) {
        super(name);
    }

    /**
     * Returns the purchase price of the property.
     *
     * @return the price of the property
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the price to build a house on the property.
     *
     * @return the house price
     */
    public int getHousePrice() {
        return housePrice;
    }

    /**
     * Returns the owner of the property.
     *
     * @return the owner of the property
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Checks if the property is owned.
     *
     * @return true if the property is owned, false otherwise
     */
    public boolean isOwned() {
        return owner != null;
    }

    /**
     * Checks if there is a house built on the property.
     *
     * @return true if there is a house, false otherwise
     */
    public boolean hasHouse() {
        return hasHouse;
    }

    /**
     * Returns the rent amount for the property without a house.
     *
     * @return the rent amount
     */
    public int getRent() {
        return rent;
    }

    /**
     * Returns the rent amount for the property with a house.
     *
     * @return the house rent amount
     */
    public int getHouseRent() {
        return houseRent;
    }

    /**
     * Sets the owner of the property.
     *
     * @param owner the player who becomes the owner of the property
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Builds a house on the property if there isn't one already.
     */
    public void buildHouse() {
        if (!hasHouse) {
            hasHouse = true; // Set to true when a house is built
            System.out.println("A house has been built on " + getName());
        } else {
            System.out.println("A house is already built on " + getName());
        }
    }

    /**
     * Handles the interaction when a player lands on this property field.
     *
     * @param player the player who landed on the property
     */
    @Override
    public void Reached(Player player) { // When player lands on it 
        // If he can afford it the player buys it 
        // If owned by another player, the landing player pays rent 
        if (owner == null) { // If property does not have an owner 
            if (player.canAfford(price)) {
                setOwner(player); // Player is set to the owner of that property 
                player.buyProperty(this);
            }
        } else if (owner != player) { // If owned by someone else
            if (hasHouse) {
                player.pay(owner, houseRent);
            } else {
                player.pay(owner, rent);
            }
            // player.pay(owner, hasHouse ? houseRent : rent);
        } else if (!hasHouse && player.canAfford(housePrice)) {
            player.buyHouse(this);
            buildHouse(); // Call buildHouse when a house is bought
        }
    }
}
