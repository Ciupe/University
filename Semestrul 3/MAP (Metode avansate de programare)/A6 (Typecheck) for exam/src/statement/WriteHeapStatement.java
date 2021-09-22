package statement;


import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.expression.Expression;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;
import programstate.ProgramState;

import java.io.IOException;

public class WriteHeapStatement implements Statement {

    private String variableName;
    private Expression expression;

    public WriteHeapStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, MyException {
        IMyDictionary<String, Value> symTable = state.getSymbolTable();
        IMyHeap<Value> heap = state.getHeap();

        if (symTable.isKey(variableName)) {
            if (symTable.lookup(variableName).getType() instanceof ReferenceType) {
                ReferenceValue refVal = (ReferenceValue) symTable.lookup(variableName);
                if (heap.isKey(refVal.getAddress())) {
                    Value val = expression.evaluate(symTable, heap);
                    if (symTable.lookup(variableName).getType().equals(new ReferenceType(val.getType()))) {
                        int address = refVal.getAddress();
                        heap.setValue(address, val);
                    }
                    else {
                        throw new MyException("Pointing variable and evaluated expression have different types!");
                    }
                }
                else {
                    throw new MyException("The address to which " + variableName + " points is not in the heap");
                }
            }
            else {
                throw new MyException(variableName + " is not a reference variable!");
            }
        }
        else {
            throw new MyException(variableName + " is not defined in the symbol table!");
        }

        return null;
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.isKey(variableName)) {
            Type variableType = typeEnv.lookup(variableName);
            Type expType = expression.typeCheck(typeEnv);
            if (!(variableType instanceof ReferenceType)) {
                throw new MyException("The file name in " + this.toString() + " is not a string");
            }
            if (!variableType.equals(new ReferenceType(expType))) {
                throw new MyException("The right side of " + this.toString() + " has other type than the referenced type of the left side");
            }
            return typeEnv;
        }
        else {
            throw new MyException("The variable " + variableName + " is not defined");
        }
    }

    @Override
    public Statement deepCopy() {
        return new WriteHeapStatement(new String(variableName), expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + variableName + "," + expression + ")";
    }
}
