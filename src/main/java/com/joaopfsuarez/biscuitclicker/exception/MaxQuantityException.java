package com.joaopfsuarez.biscuitclicker.exception;

/**
 * An Exception that extends {@code BiscuitClickerException}, representing that the caller has already reached the max quantity of units.
 * @since 1.0.0
 */
public class MaxQuantityException extends BiscuitClickerException {
    public MaxQuantityException() {
        super();
    }

    public MaxQuantityException(String s) {
        super(s);
    }
}
