package statement;

import model.adt.IMyDictionary;
import model.type.*;
import model.value.*;
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
            /*if (type instanceof BooleanType)
                symbolTable.setValue(this.name, new BooleanValue());
            else if (type instanceof IntegerType)
                    symbolTable.setValue(this.name, new IntegerValue());
            else if (type instanceof StringType)
                symbolTable.setValue(this.name, new StringValue());
            else if (type instanceof ReferenceType)
                symbolTable.setValue(this.name, type.defaultValue());*/
            symbolTable.setValue(this.name, this.type.defaultValue());
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new VariableDeclarationStatement(name, type);
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}
