package model.adt;

import model.value.Value;

import java.util.Map;

public interface IMyHeap<V> {

    int allocate(V value);
    void deallocate(int address);

    void setValue(int address, V value);
    V getValue(int address);

    boolean isKey(int address);

    Map<Integer, V> getContent();
    void setContent(Map<Integer, V> content);
}
