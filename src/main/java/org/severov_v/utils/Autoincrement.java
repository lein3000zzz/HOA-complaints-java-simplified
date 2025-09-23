package org.severov_v.utils;

public class Autoincrement {
    private long current = 0;

    public Autoincrement() {
    }

    public Autoincrement(long start) {
        this.current = start;
    }

    public long increment() {
        return current++;
    }
}
