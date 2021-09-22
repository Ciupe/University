package model.expression;

import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.type.IntegerType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;

public class ArithmeticExpression implements Expression {

    private final Expression leftExpression, rightExpression;
    private final String operation; // 1 = +, 2 = -, 3 = *, 4 = /

    public ArithmeticExpression(Expression leftExpression, Expression rightExpression, String operation){
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operation = operation;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> symbolTable, IMyHeap<Value> heap) throws MyException {
        Value value1, value2;
        value1 = leftExpression.evaluate(symbolTable, heap);

        if (value1.getType().equals(new IntegerType())){
            value2 = rightExpression.evaluate(symbolTable, heap);

            if (value2.getType().equals(new IntegerType())){
                IntegerValue int1 = (IntegerValue) value1;
                IntegerValue int2 = (IntegerValue) value2;

                int number1 = int1.getValue();
                int number2 = int2.getValue();

                /*
                if (operation == 1)
                    return new IntegerValue(number1 + number2);
                else if (operation == 2)
                    return new IntegerValue(number1 - number2);
                else if (operation == 3)
                    return new IntegerValue(number1 * number2);
                else if (operation == 4){
                    if (number2 == 0)
                        throw new MyException("Error! Division by 0!");
                    return new IntegerValue(number1 / number2);*/

                return switch (operation){
                    case "+" -> new IntegerValue(number1+number2);
                    case "-" -> new IntegerValue(number1-number2);
                    case "*" -> new IntegerValue(number1*number2);
                    case "/" -> new IntegerValue(number1/number2);
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
        return new ArithmeticExpression(leftExpression, rightExpression, operation);
    }

    @Override
    public String toString() {
        return switch (operation) {
            case "+" -> leftExpression + " + " + rightExpression;
            case "-" -> leftExpression + " - " + rightExpression;
            case "*" -> leftExpression + " * " + rightExpression;
            case "/" -> leftExpression + " / " + rightExpression;
            default -> "";
        };
    }
}
