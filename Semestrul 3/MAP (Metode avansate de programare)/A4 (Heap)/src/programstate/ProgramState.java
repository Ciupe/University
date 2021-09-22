package programstate;

import model.adt.*;
import model.value.Value;
import statement.Statement;
import model.value.StringValue;

import java.io.BufferedReader;

public class ProgramState {

    IMyStack<Statement> executionStack;
    IMyDictionary<String, Value> symbolTable;
    IMyList<Value> output;
    IMyHeap<Value> heap;
    private final IMyDictionary<StringValue, BufferedReader> fileTable;

    public ProgramState(){
        this.executionStack = new MyStack<>();
        this.symbolTable = new MyDictionary<>();
        this.output = new MyList<>();
        this.fileTable = new MyDictionary<>();
        this.heap = new MyHeap<>();
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

    public void setSymbolTable(IMyDictionary<String, Value> symbolTable){
        this.symbolTable = symbolTable;
    }

    public void setOutput(IMyList<Value> output){
        this.output = output;
    }

    public IMyDictionary<StringValue, BufferedReader> getFileTable(){
        return this.fileTable;
    }

    @Override
    public String toString() {
        return "Execution Stack: " + this.executionStack + "\nSymbolTable:\n" + this.symbolTable + "\nOutput:\n" + this.output + "\n\n";
    }
}
