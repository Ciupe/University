import controller.Controller;
import exception.MyException;
import model.adt.MyDictionary;
import model.expression.*;
import model.type.BooleanType;
import model.type.IntegerType;
import model.type.ReferenceType;
import model.type.StringType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;
import programstate.ProgramState;
import repository.Repository;
import repository.IRepository;
import statement.*;
import view.*;

import javax.management.ValueExp;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, MyException{

        Statement statement1 = new CompoundStatement(
                new VariableDeclarationStatement(
                        "v",
                        new IntegerType()
                ),
                new CompoundStatement(
                        new AssignmentStatement(
                                "v",
                                new ValueExpression(new BooleanValue(true))
                        ),
                        new PrintStatement(
                                new VariableExpression("v")
                        )
                )
        );

        //statement1.typeCheck(new MyDictionary<>());

        ProgramState programState1 = new ProgramState();
        programState1.getExecutionStack().push(statement1);
        IRepository repo1 = new Repository(programState1, "log1");
        Controller controller1 = new Controller(repo1);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Statement statement2 = new CompoundStatement(
                new VariableDeclarationStatement("a",new IntegerType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b",new IntegerType()),
                        new CompoundStatement(
                                new AssignmentStatement(
                                        "a",
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntegerValue(2)),
                                                new ArithmeticExpression(
                                                        new ValueExpression(new IntegerValue(3)),
                                                        new ValueExpression(new IntegerValue(5)),
                                                        "*"
                                                ),
                                                "+"
                                        )
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement(
                                                "b",
                                                new ArithmeticExpression(
                                                        new VariableExpression("a"),
                                                        new ValueExpression(new IntegerValue(1)),
                                                        "+"
                                                )
                                        ),
                                        new PrintStatement(
                                                new VariableExpression("b")
                                        )
                                )
                        )
                )
        );

        statement2.typeCheck(new MyDictionary<>());

        ProgramState programState2 = new ProgramState();
        programState2.getExecutionStack().push(statement2);
        IRepository repo2 = new Repository(programState2, "log2");
        Controller controller2 = new Controller(repo2);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Statement statement3 = new CompoundStatement(
                new VariableDeclarationStatement("a",new BooleanType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntegerType()),
                        new CompoundStatement(
                                new AssignmentStatement(
                                        "a",
                                        new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement(
                                                        "v",
                                                        new ValueExpression(new IntegerValue(2))
                                                ),
                                                new AssignmentStatement(
                                                        "v",
                                                        new ValueExpression(new IntegerValue(3))
                                                )
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );

        statement3.typeCheck(new MyDictionary<>());

        ProgramState programState3 = new ProgramState();
        programState3.getExecutionStack().push(statement3);
        IRepository repo3 = new Repository(programState3, "log3");
        Controller controller3 = new Controller(repo3);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Statement statement4 = new CompoundStatement(
                new VariableDeclarationStatement("fileName", new StringType()),
                new CompoundStatement(new AssignmentStatement("fileName", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenRFStatement(new VariableExpression("fileName")),
                                new CompoundStatement(new VariableDeclarationStatement("x", new IntegerType()),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("fileName"), "x"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("x")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("fileName"), "x"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("x")),
                                                                        new CloseRFStatement(new VariableExpression("fileName"))))))))));

        statement4.typeCheck(new MyDictionary<>());

        ProgramState programState4 = new ProgramState();
        programState4.getExecutionStack().push(statement4);
        IRepository repo4 = new Repository(programState4, "log4");
        Controller controller4 = new Controller(repo4);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Statement statement5 = new CompoundStatement(
            new VariableDeclarationStatement("a", new IntegerType()),
            new AssignmentStatement("a", new ValueExpression(new IntegerValue(25))),
            new VariableDeclarationStatement("b", new IntegerType()),
            new AssignmentStatement("b", new ValueExpression(new IntegerValue(14))),
            new IfStatement(new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"),">="),
                    new PrintStatement(new ValueExpression(new BooleanValue(true))),
                    new PrintStatement(new ValueExpression(new BooleanValue(false))))
        );

        statement5.typeCheck(new MyDictionary<>());

        ProgramState programState5 = new ProgramState();
        programState5.getExecutionStack().push(statement5);
        IRepository repo5 = new Repository(programState5, "log5");
        Controller controller5 = new Controller(repo5);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Statement statement6 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(20))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))),
                new AllocateHeapStatement("a", new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(
                        new ArithmeticExpression(
                            new ReadHeapExpression(
                                    new ReadHeapExpression(
                                            new VariableExpression("a")
                                    )
                            ),
                            new ValueExpression(new IntegerValue(5)),
                            "+"
                        )
                )
        );

        statement6.typeCheck(new MyDictionary<>());

        ProgramState programState6 = new ProgramState();
        programState6.getExecutionStack().push(statement6);
        IRepository repo6 = new Repository(programState6, "log6");
        Controller controller6 = new Controller(repo6);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Statement statement7 = new CompoundStatement( new VariableDeclarationStatement("x", new IntegerType()),
                new AssignmentStatement("x", new ValueExpression(new IntegerValue(4))),
                new WhileStatement(new RelationalExpression(new VariableExpression("x"), new ValueExpression(new IntegerValue(0)), ">="),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("x")),
                                new AssignmentStatement("x", new ArithmeticExpression(new VariableExpression("x"),
                                new ValueExpression(new IntegerValue(1)), "-"))
                        )
                ),
                new PrintStatement(new VariableExpression("x")));

        statement7.typeCheck(new MyDictionary<>());

        ProgramState programState7 = new ProgramState();
        programState7.getExecutionStack().push(statement7);
        IRepository repo7 = new Repository(programState7, "log7");
        Controller controller7 = new Controller(repo7);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))

        Statement statement8 = new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(20))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))),
                new AllocateHeapStatement("a", new VariableExpression("v")),
                new AllocateHeapStatement("v", new ValueExpression(new IntegerValue(30))),
                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
        );

        statement8.typeCheck(new MyDictionary<>());

        ProgramState programState8 = new ProgramState();
        programState8.getExecutionStack().push(statement8);
        IRepository repo8 = new Repository(programState8, "log8");
        Controller controller8 = new Controller(repo8);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  int v; Ref int a; v=10; new(a,22); fork(wH(a,30); v=32; print(v); print(rH(a))); print(v); print(rH(a))

        Statement statement9 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())),
                new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                new AllocateHeapStatement("a", new ValueExpression(new IntegerValue(22))),
                new ForkStatement(
                        new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntegerValue(30))),
                new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))
                                ),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );

        statement9.typeCheck(new MyDictionary<>());

        ProgramState programState9 = new ProgramState();
        programState9.getExecutionStack().push(statement9);
        IRepository repo9 = new Repository(programState9, "log9");
        Controller controller9 = new Controller(repo9);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Statement aux = new AssignmentStatement(
                "v", new ArithmeticExpression(
                            new VariableExpression("v"),
                            new ValueExpression(new IntegerValue(1)), "-"
                        )
        );

        Statement statement10 = new CompoundStatement(
                new VariableDeclarationStatement(
                        "v", new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement(
                                "v", new ValueExpression(
                                        new IntegerValue(10)
                                        )
                        ),
                        new CompoundStatement(
                                new ForkStatement(
                                        new CompoundStatement(
                                                aux, new CompoundStatement(
                                                        aux, new PrintStatement(
                                                                new VariableExpression("v")
                                                            )
                                                    )
                                        )
                                ),
                                new CompoundStatement(
                                        new SleepStatement(10),new PrintStatement(
                                                new ArithmeticExpression(
                                                        new VariableExpression("v"),
                                                        new ValueExpression(
                                                                new IntegerValue(10)
                                                        ), "*"
                                                )
                                        )
                                )
                        )
                )
        );

        statement10.typeCheck(new MyDictionary<>());

        ProgramState programState10 = new ProgramState();
        programState10.getExecutionStack().push(statement10);
        IRepository repo10 = new Repository(programState10, "log10");
        Controller controller10 = new Controller(repo10);


        /*Statement aux2 =new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())),
new VariableDeclarationStatement("b", new ReferenceType(new IntegerType())),
        new VariableDeclarationStatement("v", new IntegerType()), new AllocateHeapStatement("a", new ValueExpression(new IntegerValue(0))),
new AllocateHeapStatement("b", new ValueExpression(new IntegerValue(0))), new WriteHeapStatement("a", new ValueExpression(new IntegerValue(1))),
                new WriteHeapStatement("b", new ValueExpression(new IntegerValue(2))));
*/
        Statement statement11= new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new VariableDeclarationStatement("x", new IntegerType()),
                new AssignmentStatement("x", new ValueExpression(new IntegerValue(15))),
                new ConditionalAssignmentStatement(
                        "v",
                        new RelationalExpression(new VariableExpression("x"), new ValueExpression(new IntegerValue(10)), "<"),
                        new ValueExpression(new IntegerValue(100)),
                        new ValueExpression(new IntegerValue(200))
                ),
                new PrintStatement(new VariableExpression("v"))
        );

        statement11.typeCheck(new MyDictionary<>());

        ProgramState programState11 = new ProgramState();
        programState11.getExecutionStack().push(statement11);
        IRepository repo11 = new Repository(programState11, "log11");
        Controller controller11 = new Controller(repo11);

        TextMenu menu = new TextMenu();

        repo1.addProgramState(programState1);
        repo2.addProgramState(programState2);
        repo3.addProgramState(programState3);
        repo4.addProgramState(programState4);
        repo5.addProgramState(programState5);
        repo6.addProgramState(programState6);
        repo7.addProgramState(programState7);
        repo8.addProgramState(programState8);
        repo9.addProgramState(programState9);
        repo10.addProgramState(programState10);
        repo11.addProgramState(programState11);


        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",statement1.toString(),controller1)); // (Assignment statement)
        menu.addCommand(new RunExample("2",statement2.toString(),controller2)); // (Arithmetic expression)
        menu.addCommand(new RunExample("3",statement3.toString(),controller3)); // (If statement)
        menu.addCommand(new RunExample("4",statement4.toString(),controller4)); // (IO Files)
        menu.addCommand(new RunExample("5",statement5.toString(),controller5)); // (Relational expression)
        menu.addCommand(new RunExample("6",statement6.toString(),controller6)); // (Heap/Reference)
        menu.addCommand(new RunExample("7",statement7.toString(),controller7)); // (While statement)
        menu.addCommand(new RunExample("8",statement8.toString(),controller8)); // (Garbage collector)
        menu.addCommand(new RunExample("9",statement9.toString(),controller9)); // (Fork)
        menu.addCommand(new RunExample("10",statement10.toString(),controller10)); // (Sleep)
        menu.addCommand(new RunExample("11",statement11.toString(),controller11)); // (Conditional assignment)
        menu.show();
    }
}
