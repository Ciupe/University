package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.expression.Expression;
import model.value.StringValue;
import model.value.Value;
import programstate.ProgramState;
import model.type.StringType;

import java.io.*;

public final class OpenRFStatement implements Statement
{
    private final Expression fileExpression;

    //constructor
    public OpenRFStatement(Expression fileExpression){
        this.fileExpression = fileExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, MyException {

        //stack ..
        //symTable ..
        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();
        Value value = this.fileExpression.evaluate(symbolTable);

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
    public String toString() {
        return "Open(" + fileExpression + ")";
    }
}






