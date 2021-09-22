package statement;

import exception.MyException;
import model.adt.*;
import model.value.StringValue;
import model.value.Value;
import programstate.ProgramState;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class ForkStatement implements Statement{

    Statement statement;

    public ForkStatement(Statement statement){
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, MyException {
        IMyDictionary<String, Value> symTable = state.getSymbolTable();
        MyDictionary<String, Value> newSymbolTable = new MyDictionary<String, Value>();
        for (Map.Entry<String, Value> entry: symTable.getContent().entrySet()) {
            newSymbolTable.setValue(new String(entry.getKey()), entry.getValue().deepCopy());
        }

        IMyStack newStack = new MyStack<Statement>();
        newStack.push(this.statement);
        return new ProgramState(newStack, newSymbolTable, state.getOutput(), state.getFileTable(), state.getHeap(), this.statement);
    }

    @Override
    public Statement deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}
