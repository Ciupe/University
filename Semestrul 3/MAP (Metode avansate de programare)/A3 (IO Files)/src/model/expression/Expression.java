package model.expression;

import model.adt.IMyDictionary;
import model.value.Value;
import exception.MyException;

public interface Expression {

    Value evaluate (IMyDictionary<String, Value> symbolTable) throws MyException;

}
