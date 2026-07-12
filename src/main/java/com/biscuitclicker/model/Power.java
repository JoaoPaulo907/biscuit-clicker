package com.biscuitclicker.model;

import com.biscuitclicker.exception.*;

/**
 * Represents the power of one click on the main button.
 * @since 1.0.0
 */
public class Power {
    private static final int MAX_LEVEL = 10;

    private int level;

    private int gain;
    private int upgradePrice;

    private final int initialGain = 1;
    private final int initialUpgradePrice = 100;

    /* --- Constructors --- */

    /**
     * Creates a new Power initialized in level 1.
     * @since 1.0.0
     */
    public Power() {
        this.level = 1;
        this.calculateAttributes();
    }

    /**
     * Creates a new Power.
     * @param level The starting level
     * 
     * @since 1.0.0
     */
    public Power(int level) {
        if(level < 1 || level > MAX_LEVEL)
            throw new IllegalArgumentException("Invalid power level while initializing.");

        this.level = level;
        this.calculateAttributes();
    }

    /* --- Out --- */

    public int getLevel() {
        return this.level;
    }

    public int getGain() {
        return this.gain;
    }

    public int getUpgradePrice() {
        return this.upgradePrice;
    }

    /* --- Main logic --- */

    /**
     * Sets {@code gain} and {@code upgradePrice} based on the level of the power.
     * @since 1.0.0
     */
    private void calculateAttributes() {
        if(this.level < 1 || this.level > MAX_LEVEL)
            throw new IllegalStateException("Invalid power level.");

        int levelMult = this.level - 1;

        this.gain         = (int) Math.round(this.initialGain         * Math.pow(2.0, levelMult));
        this.upgradePrice = (int) Math.round(this.initialUpgradePrice * Math.pow(2.0, levelMult));
    }

    /**
     * Upgrades the power by one level (if possible).
     * @since 1.0.0
     */
    public void upgrade() {
        if(this.level < 1 || this.level > MAX_LEVEL)
            throw new IllegalStateException("Invalid power level.");

        if(this.level == MAX_LEVEL)
            throw new MaxLevelException("Max power level reached.");

        this.level++;
        this.calculateAttributes();
    }
}
