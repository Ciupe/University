import controller.Controller;
import exception.MyException;
import model.expression.ArithmeticExpression;
import model.expression.RelationalExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.type.BooleanType;
import model.type.IntegerType;
import model.type.StringType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;
import programstate.ProgramState;
import repository.Repository;
import repository.IRepository;
import statement.*;
import view.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, MyException{
        //View view = new View();
        //view.startProgram();
        //runExamples();

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
        ProgramState programState1 = new ProgramState();
        programState1.getExecutionStack().push(statement1);
        IRepository repo1 = new Repository(programState1, "log1");
        Controller controller1 = new Controller(repo1);
        /*try {
            controller1.allSteps();
        }
        catch (MyException | IOException e){
            System.out.printf(e.getMessage());
        }*/

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
                                                        3
                                                ),
                                                1
                                        )
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement(
                                                "b",
                                                new ArithmeticExpression(
                                                        new VariableExpression("a"),
                                                        new ValueExpression(new IntegerValue(1)),
                                                        1
                                                )
                                        ),
                                        new PrintStatement(
                                                new VariableExpression("b")
                                        )
                                )
                        )
                )
        );
        ProgramState programState2 = new ProgramState();
        programState2.getExecutionStack().push(statement2);
        IRepository repo2 = new Repository(programState2, "log2");
        Controller controller2 = new Controller(repo2);
        /*try {
            controller2.allSteps();
        }
        catch (MyException | IOException e){
            System.out.printf(e.getMessage());
        }*/


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
        ProgramState programState3 = new ProgramState();
        programState3.getExecutionStack().push(statement3);
        IRepository repo3 = new Repository(programState3, "log3");
        Controller controller3 = new Controller(repo3);
        /*try {
            controller3.allSteps();
        }
        catch (MyException | IOException e){
            System.out.printf(e.getMessage());
        }*/

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

        ProgramState programState4 = new ProgramState();
        programState4.getExecutionStack().push(statement4);
        IRepository repo4 = new Repository(programState4, "log4");
        Controller controller4 = new Controller(repo4);

        Statement statement5 = new CompoundStatement(
            new VariableDeclarationStatement("a", new IntegerType()),
            new AssignmentStatement("a", new ValueExpression(new IntegerValue(25))),
            new VariableDeclarationStatement("b", new IntegerType()),
            new AssignmentStatement("b", new ValueExpression(new IntegerValue(14))),
            new IfStatement(new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"),">="),
                    new PrintStatement(new ValueExpression(new BooleanValue(true))),
                    new PrintStatement(new ValueExpression(new BooleanValue(false))))
        );

        ProgramState programState5 = new ProgramState();
        programState5.getExecutionStack().push(statement5);
        IRepository repo5 = new Repository(programState5, "log5");
        Controller controller5 = new Controller(repo5);


        TextMenu menu = new TextMenu();

        repo1.addProgramState(programState1);
        repo2.addProgramState(programState2);
        repo3.addProgramState(programState3);
        repo4.addProgramState(programState4);
        repo5.addProgramState(programState5);

        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",statement1.toString(),controller1));
        menu.addCommand(new RunExample("2",statement2.toString(),controller2));
        menu.addCommand(new RunExample("3",statement3.toString(),controller3));
        menu.addCommand(new RunExample("4",statement4.toString(),controller4));
        menu.addCommand(new RunExample("5",statement5.toString(),controller5));
        menu.show();
    }
}
