package model.expression;

import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.value.ReferenceValue;
import model.value.Value;

public class ReadHeapExpression implements Expression{

    private Expression expression;

    public ReadHeapExpression(Expression expression){
        this.expression = expression;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Value> heap) throws MyException {
        Value value = expression.evaluate(symbolTable, heap);
        if (value instanceof ReferenceValue) {
            ReferenceValue referenceValue = (ReferenceValue) value;
            if (heap.isKey(referenceValue.getAddress())) {
                return heap.getValue(referenceValue.getAddress());
            } else {
                throw new MyException("The address doesn't exist in the heap!");
            }

        } else {
            throw new MyException("The expression is not a ReferenceValue!");
        }
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExpression(expression);
    }

    @Override
    public String toString() {
        return "rH(" + expression + ")";
    }
}
