package statement;

import exception.MyException;
import model.expression.Expression;
import programstate.ProgramState;
import model.type.BooleanType;
import model.value.BooleanValue;
import model.value.Value;

import java.io.IOException;

public class IfStatement implements Statement {

    Expression expression;
    Statement thenStatement, elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement){
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, MyException {
        Value value;
        value = expression.evaluate(state.getSymbolTable());
        if (value.getType().equals(new BooleanType())){
            BooleanValue bool = (BooleanValue) value;
            if (bool.getValue() == true)
                thenStatement.execute(state);
            else
                elseStatement.execute(state);
        }
        else
            throw new MyException("Condition is not of type boolean!");

        return state;
    }

    @Override
    public String toString() {
        return "If (" + expression.toString() + ") then {" + thenStatement.toString() + "} else {" + elseStatement.toString() + "}";
    }
}
