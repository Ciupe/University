package repository;

import exception.MyException;
import model.adt.IMyList;
import model.adt.MyList;
import programstate.ProgramState;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    List<ProgramState> programStates = new ArrayList<>();
    ProgramState originalProgram;
    String fileName;

    public Repository(ProgramState programState, String fileName) throws IOException {
        this.originalProgram = programState;
        this.programStates.add(originalProgram);
        this.fileName = fileName;
        this.emptyLogFile();
    }
/*
    @Override
    public ProgramState getCurrentProgram(){
        ProgramState currentProgram = this.programStates.get(0);
        this.programStates.remove(0);
        return currentProgram;
    }

 */

    @Override
    public void setProgramList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.programStates;
    }

    @Override
    public void addProgramState(ProgramState programState) {
        this.programStates.add(programState);
    }

    @Override
    public void logPrgStateExec(ProgramState programState) throws MyException{
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(programState + "\n");
        }
        catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public ProgramState getOriginalProgram() {
        return this.originalProgram;
    }

    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.write("");
        pw.close();
    }
}
