package com.biscuitclicker.model;

import com.biscuitclicker.exception.*;

/**
 * Represents all the units of buildings of the same type.
 * @since 1.0.0
 */
public class Building {
    private static final int MAX_QUANTITY = 999;
    private static final int MAX_LEVEL = 10;

    private final String name;

    private int quantity;
    private int level;

    private double gain;
    private int price;
    private int upgradePrice;

    private final double initialGain;
    private final int initialPrice;
    private final int initialUpgradePrice;

    /* --- Constructors --- */

    /**
     * Creates a new Building initialized with {@code quantity = 0} and {@code level = 1}.
     * 
     * @param name The name of the building.
     * @param gain The amount of points gained per building.
     * @param price The price of each building.
     * @param upgradePrice The price of one upgrade.
     * 
     * @since 1.0.0
     */
    public Building(String name, double gain, int price, int upgradePrice) {
        if(name == null || name.isBlank() || gain <= 0 || price <= 0 || upgradePrice <= 0)
            throw new IllegalArgumentException("Invalid building values while initializing.");

        this.name = name;

        this.initialGain = this.gain = gain;
        this.initialPrice = this.price = price;
        this.initialUpgradePrice = this.upgradePrice = upgradePrice;

        this.quantity = 0;
        this.level = 1;
    }

    /**
     * Creates a new Building.
     * 
     * @param name The name of the building.
     * @param quantity The amount units of this building type.
     * @param level The level of this building.
     * @param gain The amount of points gained per building.
     * @param price The price of one building.
     * @param upgradePrice The price of one upgrade.
     * 
     * @since 1.0.0
     */
    public Building(String name, int quantity, int level, double gain, int price, int upgradePrice) {
        if(name == null || name.isBlank() || gain <= 0 || price <= 0 || upgradePrice <= 0)
            throw new IllegalArgumentException("Invalid building values while initializing.");

        if(quantity < 0 || quantity > MAX_QUANTITY)
            throw new IllegalArgumentException("Invalid building quantity while initializing.");

        if(level < 1 || level > MAX_LEVEL)
            throw new IllegalArgumentException("Invalid building level while initializing.");

        this.name = name;

        this.initialGain = this.gain = gain;
        this.initialPrice = this.price = price;
        this.initialUpgradePrice = this.upgradePrice = upgradePrice;

        this.quantity = quantity;
        this.level = level;
    }

    /* --- Out --- */

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getLevel() {
        return this.level;
    }

    public double getGain() {
        return this.gain;
    }

    public double getTotalGain() {
        return this.gain * this.quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public int getUpgradePrice() {
        return this.upgradePrice;
    }

    /* --- Main logic --- */

    /**
     * Sets {@code gain}, {@code price} and {@code upgradePrice} based on the level and count of the building.
     * @since 1.0.0
     */
    private void calculateAttributes() {
        if(this.level < 1 || this.level > MAX_LEVEL)
            throw new IllegalStateException("Invalid building level.");

        int quantityMult = this.quantity;
        int levelMult = this.level - 1;

        this.price        = (int) Math.round(this.initialPrice        * Math.pow(1.05, quantityMult));
        this.upgradePrice = (int) Math.round(this.initialUpgradePrice * Math.pow(2.00, levelMult   ));

        this.gain = this.initialGain * Math.pow(2.0, levelMult);
    }

    /**
     * Increments the quantity of units of this building by one.
     * @since 1.0.0
     */
    public void increment() {
        if(this.quantity < 0 || this.quantity > MAX_QUANTITY)
            throw new IllegalStateException("Invalid building count.");

        if(this.quantity == MAX_QUANTITY)
            throw new MaxQuantityException("Max quantity of buildings reached.");

        this.quantity++;
        this.calculateAttributes();
    }

    /**
     * Decrements the quantity of units of this building by one.
     * @since 1.0.0
     */
    public void decrement() {
        if(this.quantity < 0 || this.quantity > MAX_QUANTITY)
            throw new IllegalStateException("Invalid building count.");

        if(this.quantity == 0)
            throw new MaxQuantityException("Cannot decrement from 0 buildings.");

        this.quantity--;
        this.calculateAttributes();
    }

    /**
     * Upgrades the building by one level (if possible).
     * @since 1.0.0
     */
    public void upgrade() {
        if(this.level < 1 || this.level > MAX_LEVEL)
            throw new IllegalStateException("Invalid building level.");

        if(this.level == MAX_LEVEL)
            throw new MaxLevelException("Max building level reached.");

        this.level++;
        this.calculateAttributes();
    }
}
