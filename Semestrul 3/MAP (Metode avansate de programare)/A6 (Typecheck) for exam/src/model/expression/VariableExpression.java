package model.expression;

import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.type.Type;
import model.value.Value;

public class VariableExpression implements Expression {

    private final String key;

    public VariableExpression(String key){
        this.key = key;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Value> heap) {
        return symbolTable.lookup(key);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(key);
    }

    @Override
    public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(key);
    }

    @Override
    public String toString() {
        return key;
    }
}
