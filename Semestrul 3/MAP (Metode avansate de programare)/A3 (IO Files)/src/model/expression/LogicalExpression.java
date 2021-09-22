package model.expression;

import model.adt.IMyDictionary;
import exception.MyException;
import model.value.BooleanValue;
import model.type.BooleanType;
import model.value.Value;

public class LogicalExpression implements Expression {

    private final Expression leftExpression, rightExpression;
    private final int operation;

    public LogicalExpression(Expression leftExpression, Expression rightExpression, int operation){
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operation = operation; // 1 = &&, 2 = ||
    }

    @Override
    public Value evaluate (IMyDictionary<String, Value> symbolTable) throws MyException{

        Value value1, value2;
        value1 = leftExpression.evaluate(symbolTable);
        if (value1.getType().equals(new BooleanType())){

            value2 = rightExpression.evaluate(symbolTable);
            if (value2.getType().equals(new BooleanType())){
                BooleanValue b1 = (BooleanValue) value1;
                BooleanValue b2 = (BooleanValue) value2;

                boolean bool1 = b1.getValue();
                boolean bool2 = b2.getValue();

                if (operation == 1)
                    return new BooleanValue (bool1 && bool2);
                else if (operation == 2)
                    return new BooleanValue (bool1 || bool2);
            }
            else
                throw new MyException("Right operand is not a bool!");
        }
        else
            throw new MyException("Left operand is not a bool!");

        return new BooleanValue();
    }

    @Override
    public String toString() {
        return switch (operation) {
            case 1 -> leftExpression + " && " + rightExpression;
            case 2 -> leftExpression + " || " + rightExpression;
            default -> "";
        };
    }
}
