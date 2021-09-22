package repository;

import exception.MyException;
import programstate.ProgramState;

import java.io.IOException;

public interface IRepository {

    ProgramState getCurrentProgram();
    void addProgramState(ProgramState programState);
    void logPrgStateExec(ProgramState programState) throws IOException, MyException;
    void emptyLogFile() throws IOException, MyException;
}
