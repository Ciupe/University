package statement;

import exception.MyException;
import model.adt.IMyDictionary;
import model.expression.Expression;
import model.type.BooleanType;
import model.type.Type;
import programstate.ProgramState;

public class ConditionalAssignmentStatement implements Statement {

    private final String v;
    private final Expression exp1;
    private final Expression exp2;
    private final Expression exp3;

    public ConditionalAssignmentStatement(String v, Expression e1, Expression e2, Expression e3) {this.v=v; exp1=e1; exp2=e2; exp3=e3;}

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        Statement ifStmt = new IfStatement(exp1, new AssignmentStatement(v, exp2), new AssignmentStatement(v, exp3));
        state.getExecutionStack().push(ifStmt);

        return null;
    }
    @Override
    public Statement deepCopy() {
        return new ConditionalAssignmentStatement(v, exp1, exp2, exp3);
    }
    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws RuntimeException, MyException {
        if (!exp1.typeCheck(typeEnv).equals(new BooleanType()))
            throw new MyException("Condition must be a boolean expression\n");
        Type tv = typeEnv.lookup(v);
        Type texp2 = exp2.typeCheck(typeEnv);
        Type texp3 = exp3.typeCheck(typeEnv);

        if(!tv.equals(texp2) || !tv.equals(texp3))
            throw new MyException("The variable and the 2 expressions must have the same type\n");

        return typeEnv;
    }

    @Override
    public String toString() {return v+"="+exp1+"?"+exp2+":"+exp3;}
}
