package com.joaopfsuarez.biscuitclicker.model;

import java.util.List;
import java.util.Arrays;

import com.joaopfsuarez.biscuitclicker.exception.*;

/**
 * Controls the game status and main logic.
 * @since 1.0.0
 */
public class Game {

    /* --- Attributes --- */

    private static final Building[] STANDARD_BUILDINGS = {
        new Building("Cursor",  0.1,       15,      100     ),
        new Building("Grandma", 1.0,       100,     1000    ),
        new Building("Farm",    8.0,       1100,    11000   ),
        new Building("Mine",    47.0,      13000,   120000  ),
        new Building("Factory", 260.0,     130000,  1300000 ),
        new Building("Bank",    1400.0,    1400000, 14000000)
    };

    private double points;
    private double pointsPerSecond;

    private ClickPower clickPower;
    private List<Building> buildings;

    /* --- Constructor --- */

    public Game() {
        this.points = 0.0;
        this.pointsPerSecond = 0.0;

        this.clickPower = new ClickPower();
        this.buildings = Arrays.asList(STANDARD_BUILDINGS);
    }

    /* --- Out --- */

    public double getPoints() {
        return this.points;
    }

    public double getPointsPerSecond() {
        return this.pointsPerSecond;
    }

    public ClickPower getClickPower() {
        return this.clickPower;
    }

    public List<Building> getBuildings() {
        return this.buildings;
    }

    /* --- Methods --- */

    /**
     * Calculates current pps.
     * @since 1.0.0
     */
    private void calculatePointsPerSecond() {
        this.pointsPerSecond = 0.0;

        for(Building current : this.buildings)
            this.pointsPerSecond += current.getTotalGain();
    }

    /**
     * Verifies if the Game has points to buy something.
     * @param price The value to be spent.
     * 
     * @since 1.0.0
     */
    private boolean hasPointsToBuy(int price) {
        if(Math.abs(this.points - price) < 0.1)
            return true;
        return this.points >= price;
    }

    /**
     * Tries to buy something.
     * 
     * @param purchasable Something that can be purchased.
     * @param quantity The amount of units to be purchased.
     * 
     * @since 1.0.1
     */
    public void buy(Purchasable purchasable, int quantity) {
        if(purchasable == null)
            throw new NullPointerException();

        int totalPrice = 0;

        for(int i = 0; i < quantity; i++) {
            int curr = purchasable.getPrice();
            totalPrice += curr;
        }

        if(!this.hasPointsToBuy(totalPrice)) {
            throw new InsufficientPointsException(
                "You don't have enough biscuits (" + this.points + " / " + totalPrice + ")."
            );
        }

        purchasable.increment(quantity);
        this.points -= totalPrice;

        this.calculatePointsPerSecond();
    }

    /**
     * Tries to upgrade something.
     * 
     * @param upgradable Something that can be upgraded.
     * 
     * @since 1.0.1
     */
    public void upgrade(Upgradable upgradable) {
        if(upgradable == null)
            throw new NullPointerException();

        int totalPrice = upgradable.getUpgradePrice();

        if(!this.hasPointsToBuy(totalPrice)) {
            throw new InsufficientPointsException(
                "You don't have enough biscuits (" + this.points + " / " + totalPrice + ")."
            );
        }

        upgradable.upgrade();
        this.points -= totalPrice;

        this.calculatePointsPerSecond();
    }

    /**
     * Used when the main button is clicked to receive points.
     * @since 1.0.0
     */
    public void click() {
        this.points += this.clickPower.getGain();
    }

    /**
     * Increments the Game's total points by its pps.
     * @since 1.0.0
     */
    public void gainAutoPoints() {
        this.points += this.pointsPerSecond;
    }
}
