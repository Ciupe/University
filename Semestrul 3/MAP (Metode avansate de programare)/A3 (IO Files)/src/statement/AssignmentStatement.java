package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyStack;
import model.type.Type;
import model.expression.Expression;
import programstate.ProgramState;
import model.value.Value;

public class AssignmentStatement implements Statement {
    String id;
    Expression expression;

    public AssignmentStatement(String id, Expression expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyStack<Statement> executionStack = state.getExecutionStack();
        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();

        if (symbolTable.isKey(this.id)){

            Value value = this.expression.evaluate(symbolTable);
            Type valueType = value.getType();

            Type typeId = symbolTable.getValue(this.id).getType();

            if (valueType.equals(typeId))
                symbolTable.setValue(this.id, value);
            else
                throw new MyException("Variable types do not match! " + symbolTable.getValue(this.id) + " (" + typeId + ")" + " != " + valueType);
        }
        else
            throw new MyException("Variable with id " + this.id + " does not exist!");

        return state;
    }

    @Override
    public String toString() {
        return this.id + " = " + this.expression.toString();
    }
}
