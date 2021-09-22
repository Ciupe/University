package model.expression;

import model.adt.IMyDictionary;
import model.value.Value;

public class VariableExpression implements Expression {

    private final String key;

    public VariableExpression(String key){
        this.key = key;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable) {
        return symbolTable.getValue(key);
    }

    @Override
    public String toString() {
        return key;
    }
}
