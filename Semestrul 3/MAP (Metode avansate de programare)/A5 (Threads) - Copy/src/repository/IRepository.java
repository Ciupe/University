package repository;

import exception.MyException;
import programstate.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {

    //ProgramState getCurrentProgram();
    void addProgramState(ProgramState programState);
    void logPrgStateExec(ProgramState programState) throws IOException, MyException;
    void emptyLogFile() throws IOException, MyException;
    void setProgramList(List<ProgramState> programStates);
    List<ProgramState> getProgramList();
}
