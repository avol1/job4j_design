package ru.job4j.collection.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {

        if ((count * capacity / 100) >= LOAD_FACTOR) {
            expand();
        }

        int hash = hash(key.hashCode());
        int index = indexFor(hash);

        table[index] = new MapEntry<>(key, value);
        count++;
        modCount++;

        return true;
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash % capacity;
    }

    private void expand() {
        int oldCapacity = capacity;
        capacity *= 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];

        for (int i = 0; i < oldCapacity; i++) {
            MapEntry<K, V> entry = table[i];
            int index = indexFor(hash(entry.key.hashCode()));

            newTable[index] = entry;
        }

        table = newTable;
        modCount++;
    }

    @Override
    public V get(K key) {
        int hash = hash(key.hashCode());
        int index = indexFor(hash);

        MapEntry<K, V> entry = table[index];

        return (entry != null)
                && (hash == hash(entry.key.hashCode())
                && (key != null && key.equals(entry.key)))
                ? entry.value : null;
    }

    @Override
    public boolean remove(K key) {
        int hash = hash(key.hashCode());
        int index = indexFor(hash);

        MapEntry<K, V> entry = table[index];

        if ((entry != null)
                && (hash == hash(entry.key.hashCode())
                && (key != null && key.equals(entry.key)))) {
            table[index] = null;
            count--;
            modCount++;
            return true;
        }

        return false;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private final int expectedModCount = modCount;
            private int cursor;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

                boolean result = false;
                while (cursor < capacity) {
                    var entry = table[cursor];
                    if (entry == null) {
                        cursor++;
                        continue;
                    }
                    result = true;
                    break;
                }

                return result;
            }

            @Override
            public K next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return table[cursor++].key;
            }
        };
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
