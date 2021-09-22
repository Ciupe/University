package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.adt.IMyHeap;
import model.adt.IMyStack;
import model.type.Type;
import model.expression.Expression;
import programstate.ProgramState;
import model.value.Value;

public class AssignmentStatement implements Statement {
    String id;
    Expression expression;

    public AssignmentStatement(String id, Expression expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyStack<Statement> executionStack = state.getExecutionStack();
        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();
        IMyHeap<Value> heap = state.getHeap();
        if (symbolTable.isKey(this.id)){

            Value value = this.expression.evaluate(symbolTable, heap);
            Type valueType = value.getType();

            Type typeId = symbolTable.lookup(this.id).getType();

            if (valueType.equals(typeId))
                symbolTable.setValue(this.id, value);
            else
                throw new MyException("Variable types do not match! " + symbolTable.lookup(this.id) + " (" + typeId + ")" + " != " + valueType);
        }
        else
            throw new MyException("Variable with id " + this.id + " does not exist!");

        return null;
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side operand have different types!");
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(id, expression);
    }

    @Override
    public String toString() {
        return this.id + " = " + this.expression.toString();
    }
}
