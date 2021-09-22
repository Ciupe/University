package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyStack;
import model.expression.Expression;
import model.type.BooleanType;
import model.type.Type;
import model.value.BooleanValue;
import model.value.Value;
import programstate.ProgramState;

import java.io.IOException;

public class WhileStatement implements Statement{
    private Expression expression;
    private Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute (ProgramState state) throws IOException, MyException {
        IMyStack<Statement> stack = state.getExecutionStack();
        IMyDictionary<String, Value> symTable = state.getSymbolTable();
        Value value = expression.evaluate(symTable, state.getHeap());
        if (value.getType().equals(new BooleanType())) {
            BooleanValue boolValue = (BooleanValue) value;
            if (boolValue.getValue()) {
                stack.push(this.deepCopy());
                stack.push(statement);
            }
        }
        else {
            throw new MyException("While condition is not a boolean!");
        }
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new BooleanType())){
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("While condition is not a boolean!");
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "(while (" + expression + ") " + statement + ")";
    }
}

