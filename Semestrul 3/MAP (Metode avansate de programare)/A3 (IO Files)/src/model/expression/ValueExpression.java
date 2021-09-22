package model.expression;

import model.adt.IMyDictionary;
import model.value.Value;

public class ValueExpression implements Expression {

    Value value;

    public ValueExpression(Value value){
        this.value = value;
    }

    @Override
    public Value evaluate (IMyDictionary<String, Value> symbolTable){
        return this.value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
