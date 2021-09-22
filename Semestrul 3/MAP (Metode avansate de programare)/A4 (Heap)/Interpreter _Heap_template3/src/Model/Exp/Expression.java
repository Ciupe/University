package Model.Exp;

import Type.Value;
import Model.Heap;

public abstract class Expression {

    Value evaluate(ISymbolTable symbolTable, Heap heapTable);
}

