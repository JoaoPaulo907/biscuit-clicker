package com.joaopfsuarez.biscuitclicker.exception;

/**
 * An Exception that extends {@code BiscuitClickerException}, representing that the caller must have more units of something to execute the task.
 * @since 1.0.1
 */
public class InsufficientQuantityException extends BiscuitClickerException {
    public InsufficientQuantityException() {
        super();
    }

    public InsufficientQuantityException(String s) {
        super(s);
    }
}
