package com.biscuitclicker.model;

import com.biscuitclicker.exception.*;

import java.util.List;
import java.util.Arrays;

/**
 * Controls the game status and main logic.
 * @since 1.0.0
 */
public class Game {
    private static final Building[] standardBuildings = {
        new Building("Cursor",  0.1,       15,      100     ),
        new Building("Grandma", 1.0,       100,     1000    ),
        new Building("Farm",    8.0,       1100,    11000   ),
        new Building("Mine",    47.0,      13000,   120000  ),
        new Building("Factory", 260.0,     130000,  1300000 ),
        new Building("Bank",    1400.0,    1400000, 14000000)
    };

    private double points;
    private double pointsPerSecond;

    private Power power;
    private List<Building> buildings;

    /* --- Constructors --- */

    /**
     * Creates a new Game.
     * @since 1.0.0
     */
    public Game() {
        this.points = 0.0;
        this.pointsPerSecond = 0.0;

        this.power = new Power();
        this.buildings = Arrays.asList(standardBuildings);
    }

    /**
     * Creates a new Game initialized with the given values.
     * 
     * @param points The initial value of points.
     * @param powerLevel The initial level of the power of the clicks.
     * @param buildings A List with the already owned buildings.
     * 
     * @since 1.0.0
     */
    public Game(double points, int powerLevel, List<Building> buildings) {
        this.points = points;
        this.power = new Power(powerLevel);
        this.buildings = buildings;
    }

    /* --- Out --- */

    public double getPoints() {
        return this.points;
    }

    public double getPointsPerSecond() {
        return this.pointsPerSecond;
    }

    public Power getPower() {
        return this.power;
    }

    public List<Building> getBuildings() {
        return this.buildings;
    }

    /* --- Main logic --- */

    /**
     * Calculates current pps.
     * @since 1.0.0
     */
    private void calcPointsPerSecond() {
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
     * Buys {@code quantity} units of one building type.
     * 
     * @param index The index of the building to be bougth.
     * @param quantity The quantity of instances to be bougth - this impacts on the price of the action.
     * 
     * @since 1.0.0
     */
    public void buy(int index, int quantity) {
        if(index < 0 || index >= this.buildings.size())
            throw new IndexOutOfBoundsException();

        Building building = this.buildings.get(index);

        for(int i = 0; i < quantity; i++) {
            int price = building.getPrice();

            if(this.hasPointsToBuy(price)) {
                building.increment();
                this.points -= price;
                this.calcPointsPerSecond();
            } else {
                for(int j = 0; j < i; j++) {
                    building.decrement();
                    this.points += building.getPrice();
                }

                this.calcPointsPerSecond();

                throw new InsufficientPointsException(
                    "You don't have enough biscuits (" + this.points + " / " + price + ")"
                );
            }
        }
    }

    /**
     * Upgrades one building by 1 level.
     * @param index The index of the building to be upgraded.
     * 
     * @since 1.0.0
     */
    public void upgradeBuilding(int index) {
        if(index < 0 || index >= this.buildings.size())
            throw new IndexOutOfBoundsException();

        Building building = this.buildings.get(index);
        int price = building.getUpgradePrice();

        if(this.hasPointsToBuy(price)) {
            building.upgrade();

            this.points -= price;
            this.calcPointsPerSecond();
        } else {
            throw new InsufficientPointsException(
                "You don't have enough biscuits (" + this.points + " / " + price + ")"
            );
        }
    }

    /**
     * Used when the main button is clicked to receive points.
     * @since 1.0.0
     */
    public void click() {
        this.points += this.power.getGain();
    }

    /**
     * Upgrades the power of the clicks by one level.
     * @since 1.0.0
     */
    public void upgradePower() {
        int price = this.power.getUpgradePrice();

        if(this.hasPointsToBuy(price)) {
            this.power.upgrade();
            this.points -= price;
        } else {
            throw new InsufficientPointsException(
                "You don't have enough biscuits (" + this.points + " / " + price + ")"
            );
        }
    }

    /**
     * Increments the Game's total points by it's pps.
     * @since 1.0.0
     */
    public void gainAutoPoints() {
        this.points += this.pointsPerSecond;
    }
}
