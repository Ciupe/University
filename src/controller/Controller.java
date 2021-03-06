package controller;

import exception.MyException;
import model.adt.*;
import model.type.ReferenceType;
import model.value.ReferenceValue;
import model.value.Value;
import programstate.ProgramState;
import repository.IRepository;
import statement.Statement;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller {

    IRepository repository;
    ExecutorService executorService;

    public Controller(IRepository repository){
        this.repository = repository;
    }

    void oneStepForAllPrograms(List<ProgramState> programStateList) throws IOException, MyException, InterruptedException{
        executorService = Executors.newFixedThreadPool(2);

        List<Callable<ProgramState>> callableList = programStateList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> { return p.oneStep();}))
                .collect(Collectors.toList());

        List<ProgramState> newProgramsList = executorService.invokeAll(callableList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (ExecutionException | InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        programStateList.addAll(newProgramsList);
        /*for (ProgramState prg : programStateList)
            System.out.println(prg);*/
        //System.out.println(programStateList.size());

        programStateList.forEach(programState -> {
            try{
                this.repository.logPrgStateExec(programState);
            }
            catch (IOException | MyException e) {
                //e.getStackTrace();
                System.out.println(e.getMessage());
            }
        });


        repository.setProgramList(programStateList); 
    }

    public void executeOneStep() throws MyException, InterruptedException, IOException {
        List<ProgramState> list = removeCompletedPrograms(repository.getProgramList());
        conservativeGarbageCollector(list);
        oneStepForAllPrograms(list);
        list = removeCompletedPrograms(repository.getProgramList());
        repository.setProgramList(list);
        if (list.isEmpty()) {
            executorService.shutdownNow();
        }
    }

    public void allSteps() throws IOException, MyException, InterruptedException{
        executorService = Executors.newFixedThreadPool(2);

        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramList());

        while (programStateList.size() > 0 ){
            conservativeGarbageCollector(programStateList);
            oneStepForAllPrograms(programStateList);
            programStateList = removeCompletedPrograms(repository.getProgramList());
        }

        executorService.shutdownNow();

        repository.setProgramList(programStateList);
    }

    private void conservativeGarbageCollector (List<ProgramState> programStateList){
        ProgramState currentProgramState = repository.getOriginalProgram();
        IMyHeap<Value> heap = currentProgramState.getHeap();
        IMyDictionary addresses = currentProgramState.getSymbolTable();

        garbageCollector(getAddrFromSymTable(addresses.getContent().values(), heap.getContent().values()), heap.getContent());
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

    List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList){
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public IRepository getRepository(){
        return this.repository;
    }

    public void allProgramTypeCheck() throws MyException{
        var allProgram = repository.getOriginalProgram();
        allProgram.getExecutionStack().firstElement().typeCheck(new MyDictionary<>());
    }
}
