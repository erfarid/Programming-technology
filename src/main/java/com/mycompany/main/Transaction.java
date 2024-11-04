package com.mycompany.main;

/**
 *
 * @author ASUS
 */
public class Transaction {

    // Method to handle buying a property

    /**
     *
     * @param player
     * @param property
     */
    public void handleBuyProperty(Player player, PropertyField property) {
        assert player != null : "Player cannot be null";
        assert property != null : "Property cannot be null";

        if (player.canAfford(property.getPrice())) {
            player.decreaseBalance(property.getPrice());
            player.addProperty(property);
            System.out.println(player.getName() + " bought " + property.getName());

            assert player.getBalance() >= 0 : "Player's balance should not be negative after purchase";
        } else {
            System.out.println(player.getName() + " can't afford to buy " + property.getName());
        }
    }

    // Method to handle building a house on a property

    /**
     *
     * @param player
     * @param property
     */
    public void handleBuyHouse(Player player, PropertyField property) {
        assert player != null : "Player cannot be null";
        assert property != null : "Property cannot be null";

        if (player.canAfford(property.getHousePrice())) {
            player.decreaseBalance(property.getHousePrice());
            property.buildHouse();
            System.out.println(player.getName() + " built a house on " + property.getName());

            assert player.getBalance() >= 0 : "Player's balance should not be negative after building a house";
            assert property.hasHouse() : "Property should have a house after the build";
        } else {
            System.out.println(player.getName() + " can't afford to build a house on " + property.getName());
        }
    }

    // Method to handle paying another player

    /**
     *
     * @param payer
     * @param owner
     * @param amount
     */
    public void handlePayment(Player payer, Player owner, int amount) {
        assert payer != null : "Payer cannot be null";
        assert owner != null : "Owner cannot be null";
        assert amount > 0 : "Amount must be positive";

        if (payer.getBalance() < amount) {
            System.out.println(payer.getName() + " is bankrupt!");
            payer.setBalance(0);
        } else {
            payer.decreaseBalance(amount);
            owner.collect(amount);
            System.out.println(payer.getName() + " paid " + amount + " to " + owner.getName());

            assert payer.getBalance() >= 0 : "Payer's balance should not be negative after payment";
            assert owner.getBalance() == (owner.getBalance() + amount) : "Owner's balance should increase after payment";
        }
    }
}
