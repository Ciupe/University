package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.type.Type;
import programstate.ProgramState;

public class SleepStatement implements Statement {

    private int number;

    public SleepStatement (int n) {number=n;}

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if(number!=0)
            state.getExecutionStack().push(new SleepStatement(number-1));

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new SleepStatement(number);
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws RuntimeException {
        return typeEnv;
    }

    @Override
    public String toString() {return "sleep("+number+")";}
}
