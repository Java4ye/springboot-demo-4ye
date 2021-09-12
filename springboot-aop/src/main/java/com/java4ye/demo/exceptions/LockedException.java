package com.java4ye.demo.exceptions;

public class LockedException extends RuntimeException{
    public LockedException() {
        super();
    }

    public LockedException(String message) {
        super(message);
    }
}