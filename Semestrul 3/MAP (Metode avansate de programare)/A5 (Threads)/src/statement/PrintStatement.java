package statement;

import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.adt.IMyList;
import model.expression.Expression;
import model.value.Value;
import programstate.ProgramState;
import exception.MyException;

public class PrintStatement implements Statement {

    Expression expression;

    public PrintStatement(Expression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        //state.getOutput().add(expression.evaluate(state.getSymbolTable()));
        IMyList<Value> output = state.getOutput();
        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();
        IMyHeap<Value> heap = state.getHeap();
        output.add(expression.evaluate(symbolTable, heap));
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(expression);
    }

    @Override
    public String toString() {
        return "Print (" + this.expression.toString() + ")";
    }
}
