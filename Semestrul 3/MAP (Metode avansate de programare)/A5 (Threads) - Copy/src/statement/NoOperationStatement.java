package statement;

import programstate.ProgramState;
import exception.MyException;

public class NoOperationStatement implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }

    @Override
    public Statement deepCopy() {
        return new NoOperationStatement();
    }
}
