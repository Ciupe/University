package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.type.Type;
import programstate.ProgramState;
import model.adt.IMyStack;

import java.util.Arrays;

public class CompoundStatement implements Statement {

    Statement firstStatement, secondStatement;

    public CompoundStatement(Statement firstStatement, Statement secondStatement){
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    public CompoundStatement(Statement... statementInterfaces) throws MyException {
        if (statementInterfaces.length < 2) {
            throw new MyException("Compound statement needs at least 2 statements.");
        }

        firstStatement = statementInterfaces[0];
        if (statementInterfaces.length == 2) {
            secondStatement = statementInterfaces[1];
        }
        else {
            secondStatement = new CompoundStatement(Arrays.stream(statementInterfaces).skip(1).toArray(Statement[]::new));
        }
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyStack<Statement> executionStack = state.getExecutionStack();
        executionStack.push(secondStatement);
        executionStack.push(firstStatement);
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws MyException {
        /*
        IMyDictionary<String, Type> typeEnv1 = firstStatement.typeCheck(typeEnv);
        IMyDictionary<String, Type> typeEnv2 = secondStatement.typeCheck(typeEnv1);
        return typeEnv2;
         */
        return secondStatement.typeCheck(firstStatement.typeCheck(typeEnv));
    }

    @Override
    public Statement deepCopy() {
        return new CompoundStatement(firstStatement, secondStatement);
    }

    @Override
    public String toString() {
        return firstStatement.toString() + "; " + secondStatement.toString();
    }
}
