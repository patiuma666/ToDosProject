package com.example.iis5.todo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IIS 5 on 29-12-2017.
 */

public class PositiveAtomicInteger {

    private AtomicInteger value;

    //plz add additional checks if you always want to start from value>=0
    public PositiveAtomicInteger(int value) {
        this.value = new AtomicInteger(value);
    }

    public synchronized int incrementAndGet() {
        int result = value.incrementAndGet();
        //in case of integer overflow
        if (result < 0) {
            value.set(0);
            return 0;
        }
        return result;
    }
}


