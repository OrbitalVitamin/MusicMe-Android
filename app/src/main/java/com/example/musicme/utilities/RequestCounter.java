package com.example.musicme.utilities;

public class RequestCounter {
    private int counter;
    public RequestCounter(int amount) { counter = amount; }
    public synchronized boolean decrementTillZero() {
        return 0 == --counter;
    }
}
