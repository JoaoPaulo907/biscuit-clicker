package com.joaopfsuarez.biscuitclicker.model;

/**
 * Represents something that can be purchased.
 * @since 1.0.1
 */
public interface Purchasable {
    public abstract int getPrice();
    public abstract void increment(int quantity);
}
