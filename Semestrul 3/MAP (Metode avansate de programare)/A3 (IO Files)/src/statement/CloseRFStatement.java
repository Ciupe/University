package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.expression.Expression;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;
import programstate.ProgramState;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFStatement implements Statement {

    private final Expression fileExpression;

    public CloseRFStatement(Expression fileExpression){
        this.fileExpression = fileExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, MyException {
        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();
        Value value = fileExpression.evaluate(symbolTable);
        if (value.getType().equals(new StringType())){
            IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue stringValue = (StringValue) value;
            if (fileTable.isKey(stringValue)){
                BufferedReader bufferedReader = fileTable.getValue(stringValue);
                try{
                    bufferedReader.close();
                    fileTable.removeKey(stringValue);
                }
                catch (IOException e){
                    throw new MyException(e.getMessage());
                }
                //fileTable.removeKey(stringValue);
            }
            else{
                throw new MyException("File doesn't exist in file table!");
            }
        }
        else{
            throw new MyException("Expression isn't a string! (File close)");
        }
        return null;
    }

    @Override
    public String toString() {
        return "Close(" + fileExpression + ")";
    }
}
