package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.type.Type;
import programstate.ProgramState;

import java.io.IOException;

public interface Statement {

    ProgramState execute(ProgramState state) throws IOException, MyException;
    Statement deepCopy();
    IMyDictionary<String, Type> typeCheck (IMyDictionary<String, Type> typeEnv) throws MyException;
}
