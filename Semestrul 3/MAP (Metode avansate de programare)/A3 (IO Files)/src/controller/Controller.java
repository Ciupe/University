package controller;

import exception.MyException;
import model.adt.IMyStack;
import programstate.ProgramState;
import repository.IRepository;
import statement.Statement;

import java.io.IOException;


public class Controller {

    IRepository repository;

    public Controller(IRepository repository){
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState programState) throws IOException, MyException {
        IMyStack<Statement> executionStack = programState.getExecutionStack();
        if (executionStack.isEmpty())
            throw new MyException("Execution stack is empty!");
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(programState);
    }

    public void allSteps() throws IOException, MyException{
        ProgramState programState = repository.getCurrentProgram();
        while(!programState.getExecutionStack().isEmpty()){
            try{
                oneStep(programState);
            }
            catch (MyException | IOException e){
                throw new MyException(e.getMessage());
            }
            repository.logPrgStateExec(programState);
        }
    }
}
