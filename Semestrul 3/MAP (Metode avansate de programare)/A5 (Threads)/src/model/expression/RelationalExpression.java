package model.expression;

import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.type.IntegerType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;

public class RelationalExpression implements Expression {

    Expression exp1, exp2;
    String operation;

    public RelationalExpression(Expression exp1, Expression exp2, String operation){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Value> heap) throws MyException {
        Value value1, value2;

        value1 = exp1.evaluate(symbolTable, heap);

        if (value1.getType().equals(new IntegerType())){
            value2 = exp2.evaluate(symbolTable, heap);

            if (value2.getType().equals(new IntegerType())){

                IntegerValue int1 = (IntegerValue) value1;
                IntegerValue int2 = (IntegerValue) value2;

                int number1 = int1.getValue();
                int number2 = int2.getValue();

                return switch (operation){
                    case "<" -> new BooleanValue(number1<number2);
                    case "<=" -> new BooleanValue(number1<=number2);
                    case "==" -> new BooleanValue(number1==number2);
                    case "!=" -> new BooleanValue(number1!=number2);
                    case ">" -> new BooleanValue(number1>number2);
                    case ">=" -> new BooleanValue(number1>=number2);
                    default -> throw new MyException("Invalid operation! ");
                };
            }
            else
                throw new MyException("Right operator is not an integer!");
        }
        else
            throw new MyException("Left operator is not an integer!");

    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(exp1, exp2, operation);
    }

    @Override
    public String toString() {
        return this.exp1 + " " + this.operation + " " + this.exp2;
    }
}
