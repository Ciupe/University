package Model;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import Type.ReferenceValue;
import Type.Value;

public final class Heap<V> implements IHeap<V> {

    private final Map<Integer,V> heapTable;
    private final int nextAddress = 1;
    int freeLocation = 1;

    //nu folositi 0 pt ca e o adresa invalida, trebuie sa incepeti de la 1
    //Heap este un table, asadar treuie sa aveti o referinta spre un singur tabel, deoarece introduce variabile care contin noi adrese cu referinta catre Heap
    //utilizam noua adresa, introducem o noua valoare in dictionar, care sa aiba o noua locatie ca in Symtable

    public Heap() {
        heapTable = new HashMap<Integer,V>();
    }

    public Tuple<Heap, Integer> allocate(Value v) {
        Integer addr = nextAddress.getAndIncrement();
        return new Tuple<>(new Heap(nextAddress, heapTable.put(addr, v)), addr);
    }

    @Override
    public void add(V value) {
        heapTable.put(freeLocation++, value);
    }


    @Override
    public void update(int key, V value)
    {
        V val = heapTable.get(key);
        heapTable.put(key, value);
    }


    public Heap garbageCollect(Set<Integer> usedSymbolTableAddresses) {
        Set<Integer> usedHeapAddresses = HashSet.ofAll(heapTable
                .values()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> ((ReferenceValue) v).getAddress()));

        return new Heap(nextAddress,
                heapTable.removeAll(heapTable.keySet().diff(usedHeapAddresses.union(usedSymbolTableAddresses))));
    }

    public Heap put(int address, Value newV) {

        return new Heap(nextAddress, heapTable.put(address, newV));
    }

}



