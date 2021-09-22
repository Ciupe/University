package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.expression.Expression;
import model.type.ReferenceType;
import model.value.ReferenceValue;
import model.value.Value;
import programstate.ProgramState;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;

public class AllocateHeapStatement implements Statement{

    private final String variableName;
    private final Expression expression;

    public AllocateHeapStatement(String variableName, Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, MyException {
        IMyDictionary<String, Value> symTable = state.getSymbolTable();
        IMyHeap<Value> heap = state.getHeap();
        if (symTable.isKey(variableName)) {
            if (symTable.getValue(variableName).getType() instanceof ReferenceType) {
                Value val = expression.evaluate(symTable, heap);
                if (symTable.getValue(variableName).getType().equals(new ReferenceType(val.getType()))) {
                    int address = heap.allocate(val);
                    symTable.setValue(variableName, new ReferenceValue(val.getType(), address));
                }
                else {
                    throw new MyException("Reference doesn't point to expected type");
                }
            }
            else {
                throw new MyException("Variable is not of type ReferenceType");
            }
        }
        else {
            throw new MyException(variableName + " is not defined");
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new AllocateHeapStatement(variableName, expression.deepCopy());
    }



    @Override
    public String toString(){
        return "new(" + variableName + ", " + expression + ")";
    }
}
