package com.biscuitclicker.exception;

/**
 * An Exception that extends {@code BiscuitClickerException}, representing that the caller has already reached max level.
 * 
 * @since 1.0.0
 * @version 1.0.1
 */
public class MaxLevelException extends BiscuitClickerException {
    public MaxLevelException() {
        super();
    }

    public MaxLevelException(String s) {
        super(s);
    }
}
