package statement;

import exception.MyException;
import programstate.ProgramState;

import java.io.IOException;

public interface Statement {

    ProgramState execute(ProgramState state) throws IOException, MyException;
    Statement deepCopy();
}
