package statement;

import model.adt.IMyDictionary;
import model.type.Type;
import programstate.ProgramState;
import exception.MyException;

public class NoOperationStatement implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new NoOperationStatement();
    }
}
