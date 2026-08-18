package com.joaopfsuarez.biscuitclicker.model;

/**
 * Represents something that can be upgraded.
 * @since 1.0.1
 */
public interface Upgradable {
    public abstract int getUpgradePrice();
    public abstract void upgrade();
}
