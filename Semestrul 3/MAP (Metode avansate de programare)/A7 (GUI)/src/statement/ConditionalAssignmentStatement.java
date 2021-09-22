package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.expression.Expression;
import model.type.BooleanType;
import model.type.Type;
import programstate.ProgramState;

import java.io.IOException;

public class ConditionalAssignmentStatement implements Statement{

    private final String v;
    private final Expression exp1;
    private final Expression exp2;
    private final Expression exp3;

    public ConditionalAssignmentStatement(String v, Expression e1, Expression e2, Expression e3) {
        this.v = v;
        this.exp1 = e1;
        this.exp2 = e2;
        this.exp3 = e3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, MyException {
        Statement ifStmt = new IfStatement(exp1, new AssignmentStatement(v, exp2), new AssignmentStatement(v, exp3));
        state.getExecutionStack().push(ifStmt);

        return null;
    }

    @Override
    public ConditionalAssignmentStatement deepCopy() {
        return new ConditionalAssignmentStatement(v, exp1, exp2, exp3);
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws MyException {

        if (!exp1.typeCheck(typeEnv).equals(new BooleanType()))
            throw new MyException("Condition isn't a boolean expression!\n");

        Type type_v = typeEnv.lookup(v);
        Type type_exp2 = exp2.typeCheck(typeEnv);
        Type type_exp3 = exp3.typeCheck(typeEnv);

        if(!type_v.equals(type_exp2) || !type_v.equals(type_exp3))
            throw new MyException("Variable and expression types do not match!\n");

        return typeEnv;
    }

    @Override
    public String toString() {
        return v + "=" + exp1 + "?" + exp2 + ":" + exp3;
    }
}
