package model.adt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<V> implements IMyHeap<V>{

    private Map<Integer, V> map;
    private AtomicInteger freeLocation;

    public MyHeap() {
        this.map = new ConcurrentHashMap<>();
        this.freeLocation = new AtomicInteger(0);
    }

    @Override
    public int allocate(V value) {
        int newLocation = freeLocation.incrementAndGet();
        map.put(newLocation, value);
        return newLocation;
    }

    @Override
    public void deallocate(int address) {
        map.remove(address);
    }

    @Override
    public void setValue(int address, V value) {
        map.put(address, value);
    }

    @Override
    public V getValue(int address) {
        return map.get(address);
    }

    @Override
    public boolean isKey(int address) {
        return map.containsKey(address);
    }

    @Override
    public Map<Integer, V> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<Integer, V> content) {
        this.map = content;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        map.forEach((key, value) -> output.append("(").append(key).append(" -> ").append(value).append(")").append(" "));

        return output.toString();
    }
}
