package com.biscuitclicker.util;

/**
 * Centralizes the log system into one class.
 * 
 * @since 1.0.1
 * @version 1.0.1
 */
public abstract class Log {
    public static void log(Object msg) {
        System.out.println(msg);
    }

    public static void warn(Object msg) {
        System.out.println("[Warning] " + msg);
    }

    public static void error(Object msg) {
        System.err.println("[Error] " + msg);
    }
}
