package model.expression;

import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.type.Type;
import model.value.Value;
import exception.MyException;

public interface Expression {

    Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Value> heap) throws MyException;
    Expression deepCopy();
    Type typeCheck(IMyDictionary<String, Type> typeEnv) throws MyException;
}
