package Model;

import Type.Value;

import java.util.Map;

public interface IHeap<V> {

    Value lookup(String symbolName);

    Tuple<Heap, Integer> allocate(Value v);

    Value lookup(Integer address);

    Map<Integer, V> getContent();

    V getValue(int key);

    int getFreeLocation();

    void add(V value);

    void update(int key, V value);

    void remove(int key);

   void setContent(Map<Integer, V> newContent);
}
