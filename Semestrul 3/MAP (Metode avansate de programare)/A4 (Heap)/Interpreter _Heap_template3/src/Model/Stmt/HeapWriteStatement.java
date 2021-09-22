package Model.Stmt;

import Model.Exp.Expression;
import Type.ReferenceValue;
import Type.Value;
import PrgState.ProgramState;

public class HeapWriteStatement implements IStatement{

    private final String varName;
    private final Expression value;

    public HeapWriteStatement(String varName, Expression value) {
        this.varName = varName;
        this.value = value;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value newV = (Value) value.equals(state.getSymbolTable(), state.getHeap());
        ReferenceValue oldV = ReferenceValue(
                state.getSymbolTable().lookup(varName), newV.getType());
        return state.getHeap().put(oldV.getAddress(), newV));

    }
}
