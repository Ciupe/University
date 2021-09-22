package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.expression.Expression;
import model.type.IntegerType;
import model.type.StringType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;
import programstate.ProgramState;

import java.io.BufferedReader;
import java.io.IOException;


public class ReadFileStatement implements Statement {

    private Expression fileExpression;
    //private String fileName;
    private String variableName;

    public ReadFileStatement(Expression fileExpression, String variableName){
        this.fileExpression = fileExpression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, MyException {
        IMyDictionary<String, Value> symTable = state.getSymbolTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (symTable.isKey(variableName)) {
            if (symTable.lookup(variableName).getType().equals(new IntegerType())) {
                IMyHeap<Value> heap = state.getHeap();
                Value val = fileExpression.evaluate(symTable, heap);
                if (val.getType().equals(new StringType())) {
                    StringValue stringVal = (StringValue) val;
                    if (fileTable.isKey(stringVal)) {
                        BufferedReader bufferedReader = fileTable.lookup(stringVal);
                        try {
                            String line = bufferedReader.readLine();
                            Value intVal;
                            IntegerType type = new IntegerType();
                            if (line == null) {
                                intVal = type.defaultValue();
                            } else {
                                intVal = new IntegerValue(Integer.parseInt(line));
                            }
                            symTable.setValue(variableName, intVal);
                        } catch (IOException e) {
                            throw new MyException(e.getMessage());
                        }
                    }
                    else {
                        throw new MyException("File " + stringVal.getValue() + " does not exist in the File Table!");
                    }
                }
                else {
                    throw new MyException("The value couldn't be evaluated as a string value!");
                }
            }
            else {
                throw new MyException(variableName + " is not of type int!");
            }
        }
        else {
            throw new MyException(variableName + " is not defined in the Symbol Table");
        }

        return null;
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.isKey(variableName)) {
            Type variableType = typeEnv.lookup(variableName);
            Type expType = fileExpression.typeCheck(typeEnv);
            if (!variableType.equals(new IntegerType())) {
                throw new MyException("The variable in " + this.toString() + " is not an integer");
            }
            if (!expType.equals(new StringType())) {
                throw new MyException("The file name in " + this.toString() + " is not a string");
            }
            return typeEnv;
        }
        else {
            throw new MyException("The variable " + variableName + " is not defined");
        }
    }

    @Override
    public Statement deepCopy() {
        return new ReadFileStatement(fileExpression, variableName);
    }

    @Override
    public String toString() {
        return "Read (" + fileExpression + ", " + variableName + ")";
    }
}
