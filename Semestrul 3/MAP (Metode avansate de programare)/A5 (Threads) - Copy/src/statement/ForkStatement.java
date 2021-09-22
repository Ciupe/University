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

    ForkStatement(Statement statement){
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, MyException {
        IMyStack<Statement> stk = state.getExecutionStack();
        IMyDictionary<String, Value> symTable = state.getSymbolTable();
        IMyHeap<Value> heap = state.getHeap();
        IMyList<Value> outList = state.getOutput();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        MyStack<Statement> newStack = new MyStack<Statement>();
        MyDictionary<String, Value> newSymbolTable = new MyDictionary<String, Value>();
        for (Map.Entry<String, Value> entry: symTable.getContent().entrySet()) {
            newSymbolTable.setValue(new String(entry.getKey()), entry.getValue().deepCopy());
        }
        return new ProgramState(newStack, newSymbolTable, outList, fileTable, heap, this.statement);
    }

    @Override
    public Statement deepCopy() {
        return null;
    }
}
