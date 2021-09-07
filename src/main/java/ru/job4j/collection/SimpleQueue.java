package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();
    private int outCount;
    private int inCount;

    public T poll() {
        if (inCount == 0) {
            throw new NoSuchElementException();
        }
        if (outCount == 0) {
           for (int count = 0; count < inCount; count++) {
               out.push(in.pop());
               outCount++;
           }
        }
        outCount--;
        inCount--;
        return out.pop();
    }

    public void push(T value) {
        inCount++;
        in.push(value);
    }
}
