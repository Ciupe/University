package statement;

import programstate.ProgramState;
import exception.MyException;

public class NoOperationStatement implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }
}
