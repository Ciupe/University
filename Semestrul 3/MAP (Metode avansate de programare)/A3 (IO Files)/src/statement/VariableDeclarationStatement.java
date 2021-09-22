package statement;

import model.adt.IMyDictionary;
import model.type.BooleanType;
import model.type.IntegerType;
import model.type.StringType;
import model.type.Type;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;
import programstate.ProgramState;
import exception.MyException;

public class VariableDeclarationStatement implements Statement {

    String name;
    Type type;

    public VariableDeclarationStatement(String name, Type type){
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();
        if (symbolTable.isKey(name))
            throw new MyException("Variable already declared!");
        else
        {
            if (type instanceof BooleanType)
                symbolTable.setValue(this.name, new BooleanValue());
            else if (type instanceof IntegerType)
                    symbolTable.setValue(this.name, new IntegerValue());
            else if (type instanceof StringType)
                symbolTable.setValue(this.name, new StringValue());
        }
        return state;
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}
