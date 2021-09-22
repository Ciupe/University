package Model.Exp;

import Type.ReferenceValue;
import Type.Value;
import Model.Heap;

public class HeapReadExpression extends Expression{

    //ia var din Heap, dam exp ca parametru, apoi citim expresia

    private final Expression exp;

    public HeapReadExpression(Expression exp) {
        this.exp = exp;
    }


    public Value evaluate(ISymbolTable symbolTable, Heap heapTable) {
        return heapTable.lookup(new ReferenceValue(exp.evaluate(symbolTable, heapTable)).getAddress());
    }

}
