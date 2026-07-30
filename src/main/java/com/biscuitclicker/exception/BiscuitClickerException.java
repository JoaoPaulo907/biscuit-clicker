package com.biscuitclicker.exception;

/**
 * Represents a generic exception in Biscuit Clicker's business rules.
 * 
 * @since 1.0.0
 * @version 1.0.1
 */
public class BiscuitClickerException extends RuntimeException {
    public BiscuitClickerException() {
        super();
    }

    public BiscuitClickerException(String s) {
        super(s);
    }
}
