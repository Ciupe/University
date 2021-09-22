package repository;

import exception.MyException;
import model.adt.IMyList;
import model.adt.MyList;
import programstate.ProgramState;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Repository implements IRepository {

    IMyList<ProgramState> programStates = new MyList<>();
    String fileName;

    public Repository(ProgramState programState, String fileName) throws IOException {
        this.programStates.add(programState);
        this.fileName = fileName;
        this.emptyLogFile();
    }

    @Override
    public ProgramState getCurrentProgram(){
        return this.programStates.getFirst();
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
    public void emptyLogFile() throws IOException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.write("");
        pw.close();
    }
}
