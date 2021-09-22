package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.expression.Expression;
import model.value.StringValue;
import model.value.Value;
import programstate.ProgramState;
import model.type.StringType;

import java.io.*;

public final class OpenRFStatement implements Statement
{
    private final Expression fileExpression;

    public OpenRFStatement(Expression fileExpression){
        this.fileExpression = fileExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, MyException {

        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();
        IMyHeap<Value> heap = state.getHeap();
        Value value = this.fileExpression.evaluate(symbolTable, heap);

        if (value.getType().equals(new StringType())) {
            IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue stringValue = (StringValue) value;

            if (!fileTable.isKey(stringValue)) {
                try {
                    Reader reader = new FileReader(stringValue.getValue());
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    fileTable.setValue(stringValue, bufferedReader);
                } catch (IOException e) {
                    throw new MyException(e.getMessage());
                }
            }
            else {
                throw new MyException("File is already opened!");
            }
        }
        else {
            throw new MyException("Expression isn't a string! (File open)");
        }

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new OpenRFStatement(fileExpression);
    }

    @Override
    public String toString() {
        return "Open(" + fileExpression + ")";
    }
}






