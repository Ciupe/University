package gui;

import controller.Controller;
import exception.MyException;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
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
import repository.IRepository;
import repository.Repository;
import statement.*;

import javax.management.ValueExp;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindowController {

    public ListView<Statement> statementList;
    public Button runButton;

    public void initialize(){
        this.populateList();
        runButton.setDisable(true);
    }

    public void populateList() {
        try {
            var auxiliaryList = getStatementList();
            statementList.setItems(FXCollections.observableArrayList(auxiliaryList));
        }
        catch (MyException e) {
            var alert = new Alert(Alert.AlertType.ERROR, "Error encountered while populating list...");
            alert.show();
        }
    }

    public void statementSelection(){
        if (!statementList.getSelectionModel().getSelectedItems().isEmpty())
            runButton.setDisable(false);
    }

    public void runButtonPressed(){
        //var statement = statementList.getSelectionModel().getSelectedItem();

        var selectedIndex = statementList.getSelectionModel().getSelectedIndex();
        var selectedProgram = statementList.getItems().get(selectedIndex);
        var program = new ProgramState(selectedProgram);

        try {
            var programRepository = new Repository(program, "log" + selectedIndex + ".txt");
            var programController = new Controller(programRepository);
            programController.allProgramTypeCheck();

            this.createNewProgramWindow(programController);
        }
        catch (IOException | MyException e) {
            var error = new Alert(Alert.AlertType.ERROR, e.getMessage());
            error.show();
        }
    }

    public void createNewProgramWindow(Controller programController){
        FXMLLoader programWindowFXMLLoader = new FXMLLoader(getClass().getResource("ProgramWindow.fxml"));

        try {
            Parent programWindow = programWindowFXMLLoader.load();
            ProgramWindowController programWindowController = programWindowFXMLLoader.getController();
            programWindowController.setProgramController(programController);

            var programStage = new Stage();
            programStage.setTitle("Program Windowo");
            var programScene = new Scene(programWindow, 457, 424);
            programStage.setScene(programScene);
            programStage.show();
        } catch (IOException e) {
            var alert = new Alert(Alert.AlertType.ERROR, "Error while loading program windowo...");
            alert.show();
        }
    }

    public ArrayList<Statement> getStatementList() throws MyException {

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


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Ref int a; Ref int b; int v;
//new(a,0); new(b,0);
//wh(a,1); wh(b,2);
//v=(rh(a)<rh(b))?100:200;
//print(v);
//v= ((rh(b)-2)>rh(a))?100:200;
//print(v);
//The final Out should be {100,200}

        Statement statement10 = new CompoundStatement(
                new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())),
                new VariableDeclarationStatement("b", new ReferenceType(new IntegerType())),
                new VariableDeclarationStatement("v", new IntegerType()),
                new AllocateHeapStatement("a", new ValueExpression(new IntegerValue(0))),
                new AllocateHeapStatement("b", new ValueExpression(new IntegerValue(0))),
                new WriteHeapStatement("a", new ValueExpression(new IntegerValue(1))),
                new WriteHeapStatement("b", new ValueExpression(new IntegerValue(2))),
                new ConditionalAssignmentStatement(
                        "v",
                        new RelationalExpression(
                                new ReadHeapExpression(new VariableExpression("a")),
                                new ReadHeapExpression(new VariableExpression("b")),
                                "<"
                        ),
                        new ValueExpression(new IntegerValue(100)),
                        new ValueExpression(new IntegerValue(200))
                ),
                new PrintStatement(new VariableExpression("v")),
                new ConditionalAssignmentStatement(
                        "v",
                        new RelationalExpression(
                            new ArithmeticExpression(
                                new ReadHeapExpression(new VariableExpression("b")),
                                new ValueExpression(new IntegerValue(2)),
                                "-"
                            ),
                            new ReadHeapExpression(new VariableExpression("a")),
                                ">"
                        ),
                        new ValueExpression(new IntegerValue(100)),
                        new ValueExpression(new IntegerValue(200))
                ),
                new PrintStatement(new VariableExpression("v"))
        );



        ArrayList<Statement> statementList = new ArrayList<>();
        statementList.add(statement1);
        statementList.add(statement2);
        statementList.add(statement3);
        statementList.add(statement4);
        statementList.add(statement5);
        statementList.add(statement6);
        statementList.add(statement7);
        statementList.add(statement8);
        statementList.add(statement9);
        statementList.add(statement10);

        return statementList;
    }
}
