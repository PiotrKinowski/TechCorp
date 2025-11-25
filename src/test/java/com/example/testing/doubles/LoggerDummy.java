package com.example.testing.doubles;

public class LoggerDummy {
    public void log(String message) {
        throw new UnsupportedOperationException("Dummy object - method should not be called");
    }
}