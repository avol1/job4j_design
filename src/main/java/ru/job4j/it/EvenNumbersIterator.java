package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

public class EvenNumbersIterator implements Iterator<Integer> {

    private int[] data;
    private int index;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        if (index >= data.length) {
            return false;
        }

        return getNextEvenNumberOffset() >= 0;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        index = getNextEvenNumberOffset();

        return data[index++];
    }

    private int getNextEvenNumberOffset() {

        for (int i = index; i < data.length; i++) {
            if (data[i] % 2 == 0) {
                return i;
            }
        }

        return -1;
    }
}