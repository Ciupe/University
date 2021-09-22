package statement;


import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.expression.Expression;
import model.type.ReferenceType;
import model.type.StringType;
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
            if (symTable.getValue(variableName).getType() instanceof ReferenceType) {
                ReferenceValue refVal = (ReferenceValue) symTable.getValue(variableName);
                if (heap.isKey(refVal.getAddress())) {
                    Value val = expression.evaluate(symTable, heap);
                    if (symTable.getValue(variableName).getType().equals(new ReferenceType(val.getType()))) {
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
    public Statement deepCopy() {
        return new WriteHeapStatement(new String(variableName), expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + variableName + "," + expression + ")";
    }
}
