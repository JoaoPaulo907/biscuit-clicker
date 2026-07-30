package com.biscuitclicker.exception;

/**
 * An Exception that extends {@code BiscuitClickerException}, representing an error due to a lack of points.
 * 
 * @since 1.0.0
 * @version 1.0.1
 */
public class InsufficientPointsException extends BiscuitClickerException {
    public InsufficientPointsException() {
        super();
    }

    public InsufficientPointsException(String s) {
        super(s);
    }
}
