package controller;

import exception.MyException;
import model.adt.IMyStack;
import model.type.ReferenceType;
import model.value.ReferenceValue;
import model.value.Value;
import programstate.ProgramState;
import repository.IRepository;
import statement.Statement;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        repository.logPrgStateExec(programState);
        while(!programState.getExecutionStack().isEmpty()){
            try{
                oneStep(programState);
                repository.logPrgStateExec(programState);
                programState.getHeap()
                        .setContent(
                                garbageCollector(
                                        getAddrFromSymTable(
                                                programState.getSymbolTable().getContent().values(),
                                                programState.getHeap().getContent().values()
                                        ),
                                        programState.getHeap().getContent()
                                )
                        );
            }
            catch (MyException | IOException e){
                throw new MyException(e.getMessage());
            }
            repository.logPrgStateExec(programState);
        }
    }

    Map<Integer, Value> garbageCollector(List<Integer> addresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(element -> addresses.contains(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }



    List<Integer> getAddrFromSymTable(Collection<Value> symbolTableValues, Collection<Value> heap) {
        return Stream.concat(
                symbolTableValues.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {
                            ReferenceValue v1 = (ReferenceValue) v;
                            return v1.getAddress();
                        }),
                heap.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {
                            ReferenceValue v1 = (ReferenceValue) v;
                            return v1.getAddress();
                        }))
                .collect(Collectors.toList());
    }
}
