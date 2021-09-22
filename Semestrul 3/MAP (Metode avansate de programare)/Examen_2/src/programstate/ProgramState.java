package programstate;

import exception.MyException;
import model.adt.*;
import model.value.Value;
import statement.Statement;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ProgramState {

    IMyStack<Statement> executionStack;
    IMyDictionary<String, Value> symbolTable;
    IMyList<Value> output;
    IMyHeap<Value> heap;
    private final IMyDictionary<StringValue, BufferedReader> fileTable;
    private final int stateID;
    private static int freeID = 0;
    private Statement originalProgram;

    public ProgramState(){
        this.executionStack = new MyStack<>();
        this.symbolTable = new MyDictionary<>();
        this.output = new MyList<>();
        this.fileTable = new MyDictionary<>();
        this.heap = new MyHeap<>();
        stateID = getNewProgramStateID();
    }

    public ProgramState(IMyStack<Statement> executionStack,
                        IMyDictionary<String, Value> symbolTable,
                        IMyList<Value> output,
                        IMyDictionary<StringValue, BufferedReader> fileTable,
                        IMyHeap<Value> heap,
                        Statement originalProgram){
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        stateID = getNewProgramStateID();
        this.originalProgram = originalProgram;
    }

    public ProgramState(IMyStack<Statement> executionStack,
                        IMyDictionary<String, Value> symbolTable,
                        IMyList<Value> output,
                        IMyDictionary<StringValue, BufferedReader> fileTable,
                        IMyHeap<Value> heap){
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        stateID = getNewProgramStateID();
    }

    public ProgramState(Statement programStatement) {
        this.executionStack = new MyStack<>(programStatement);
        this.symbolTable = new MyDictionary<>();
        this.output = new MyList<>();
        this.fileTable = new MyDictionary<>();
        this.heap = new MyHeap<>();
        stateID = getNewProgramStateID();
    }

    public IMyStack<Statement> getExecutionStack(){
        return this.executionStack;
    }

    public IMyDictionary<String, Value> getSymbolTable(){
        return this.symbolTable;
    }

    public IMyList<Value> getOutput(){
        return this.output;
    }

    public IMyHeap<Value> getHeap() {
        return this.heap;
    }

    /*public void setExecutionStack(IMyStack<Statement> executionStack){
        this.executionStack = executionStack;
    }
     */

    public Boolean isNotCompleted(){
        if (this.executionStack.isEmpty())
            return false;
        return true;
    }

    public ProgramState oneStep() throws IOException, MyException {
        if (executionStack.isEmpty())
            throw new MyException("Program state stack is empty!");
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public void setSymbolTable(IMyDictionary<String, Value> symbolTable){
        this.symbolTable = symbolTable;
    }

    public void setOutput(IMyList<Value> output){
        this.output = output;
    }

    public IMyDictionary<StringValue, BufferedReader> getFileTable(){
        return this.fileTable;
    }

    public static synchronized int getNewProgramStateID(){
        freeID++;
        return freeID;
    }

    public int getProgramId() {
        return this.stateID;
    }

    @Override
    public String toString() {
        //return "Execution Stack: " + this.executionStack + "\nSymbolTable:\n" + this.symbolTable + "\nOutput:\n" + this.output + "\n\n";
        StringBuilder output = new StringBuilder();
        output.append("Program state\n");
        output.append("ID: ").append(stateID).append(" \n");
        output.append("Execution Stack: ").append(executionStack).append(" \n");
        output.append("Symbol Table: ").append(symbolTable).append(" \n");
        output.append("Heap: ").append(heap).append(" \n");
        output.append("Output Console: ").append(this.output).append(" \n");
        output.append("File Table: ").append(fileTable).append(" \n");
        return output.toString();
    }
}
