package PrgState;

import Model.Heap;
import Model.IHeap;

public class ProgramState{

    private final Heap heapTable;

    public ProgramState(Heap heapTable) {
        this.heapTable = heapTable;
    }

    public Heap getHeap() {return heapTable;}

}
