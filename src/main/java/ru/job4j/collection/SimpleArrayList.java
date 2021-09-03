package ru.job4j.collection;

import ru.job4j.list.List;

import java.util.*;

public class SimpleArrayList<T> implements List<T> {

    private T[] container;

    private int size;

    private int modCount;

    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    @Override
    public void add(T value) {
        if (container.length == size) {
            T[] newContainer = (T[]) new Object[size * 2];
            System.arraycopy(container,
                    0,
                    newContainer,
                    0,
                    container.length);
            container = newContainer;
        }

        container[size++] = value;
        modCount++;
    }

    @Override
    public T set(int index, T newValue) throws IndexOutOfBoundsException {
        Objects.checkIndex(index, container.length);

        T oldValue = container[index];
        container[index] = newValue;
        return oldValue;
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        Objects.checkIndex(index, container.length);

        T removedValue = container[index];

        System.arraycopy(
                container,
                index + 1,
                container,
                index,
                container.length - index - 1
        );

        container[container.length - 1] = null;
        size--;
        modCount++;
        return removedValue;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        Objects.checkIndex(index, container.length);

        return container[index];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final int expectedModCount = modCount;
            private int cursor;

            @Override
            public boolean hasNext() throws ConcurrentModificationException {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

                boolean result = false;

                for (int index = cursor; cursor < size; index++) {
                    T value = container[cursor];
                    if (value != null) {
                        result = true;
                        break;
                    }
                }

                return result;
            }

            @Override
            public T next() throws ConcurrentModificationException {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return container[cursor++];
            }

        };
    }
}
