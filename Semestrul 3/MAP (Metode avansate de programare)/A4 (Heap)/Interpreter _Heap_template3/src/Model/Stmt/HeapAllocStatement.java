package Model.Stmt;

import Model.Heap;
import Model.Exp.Expression;
import Type.ReferenceType;
import Type.Value;
import PrgState.ProgramState;


public class HeapAllocStatement implements IStatement {

    private final String varName;
    private final Expression value;

    public HeapAllocStatement(String varName, Expression value) {
        this.varName = varName;
        this.value = value;
    }

    public ProgramState execute(ProgramState state) {
        ISymbolTable st = state.getSymbolTable();
        Heap ht = state.getHeap();
        Value newV = value.evaluate(st, ht);
        ReferenceType oldT = ReferenceType(st.lookup(varName).getType(), newV.getType());
        Tuple<Heap, Integer> nextAllocatedHeap = ht.allocate(newV);
        return state;
                //nextAllocatedHeap
               //st.update(varName, new ReferenceValue(nextAllocatedHeap, oldT.getInner());
    }
}

/*
    @Override
    public PrgState.ProgramState execute(PrgState.ProgramState state){
        ISymTable<String, Integer> symDict = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();

        int res = exp.Eval(symDict, heap);
        int freeLoc = heap.getFreeLocation();

        heap.add(res);
        symDict.add(varName, freeLoc);

        return state;
    }
*/