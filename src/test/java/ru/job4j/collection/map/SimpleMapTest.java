package ru.job4j.collection.map;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class SimpleMapTest {

    @Test
    public void whenPutTwoRecordsInMap() {
        SimpleMap<Integer, String> map = new SimpleMap<>();

        map.put(1, "Test1");
        map.put(2, "Test2");

        assertEquals("Test1", map.get(1));
        assertEquals("Test2", map.get(2));
    }

    @Test
    public void whenNullInMap() {
        SimpleMap<Integer, String> map = new SimpleMap<>();

        assertNull(map.get(1));
    }

    @Test
    public void whenAddTwoRecordAndRemoveOneInMap() {
        SimpleMap<Integer, String> map = new SimpleMap<>();

        map.put(1, "Test1");
        map.put(2, "Test2");


        assertTrue(map.remove(1));
        assertEquals("Test2", map.get(2));
    }

    @Test
    public void whenRemoveInEmptyMap() {
        SimpleMap<Integer, String> map = new SimpleMap<>();

        assertFalse(map.remove(1));
    }

    @Test
    public void whenIteratorReturnValuesInMap() {
        SimpleMap<Integer, String> map = new SimpleMap<>();

        map.put(1, "Test1");
        map.put(2, "Test2");

        Iterator<Integer> it = map.iterator();

        assertEquals(Integer.valueOf(1), it.next());
        assertEquals(Integer.valueOf(2), it.next());
        assertFalse(it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNextReturnErrorInEmptyMap() {
        SimpleMap<Integer, String> map = new SimpleMap<>();

        map.iterator().next();
    }

}