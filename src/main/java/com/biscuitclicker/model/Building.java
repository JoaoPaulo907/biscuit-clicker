package com.biscuitclicker.model;

import com.biscuitclicker.exception.*;

/**
 * Represents all the units of buildings of the same type.
 * @since 1.0.0
 */
public class Building implements Purchasable, Upgradable {

    /* --- Attributes --- */

    private static final int MAX_QUANTITY = 1000;
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

    /* --- Constructor --- */

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

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public int getUpgradePrice() {
        return this.upgradePrice;
    }

    /* --- Methods --- */

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
     * Tries to increment the quantity of units of this building by {@code quantity}.
     * @since 1.0.0
     */
    @Override
    public void increment(int quantity) {
        if(this.quantity < 0 || this.quantity > MAX_QUANTITY)
            throw new IllegalStateException("Invalid building count.");

        if(quantity < 1)
            throw new IllegalArgumentException("Must add at least one building.");

        if(this.quantity == MAX_QUANTITY)
            throw new MaxQuantityException("Max quantity of buildings reached.");

        this.quantity += quantity;
        this.calculateAttributes();
    }

    /**
     * Tries to upgrade the building by one level.
     * @since 1.0.0
     */
    @Override
    public void upgrade() {
        if(this.level < 1 || this.level > MAX_LEVEL)
            throw new IllegalStateException("Invalid building level.");

        if(this.quantity < 1)
            throw new InsufficientQuantityException("You must have at least 1 building to upgrade it.");

        if(this.level == MAX_LEVEL)
            throw new MaxLevelException("Max building level reached.");

        this.level++;
        this.calculateAttributes();
    }
}
