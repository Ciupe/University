package gui;

import controller.Controller;
import exception.MyException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
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
import statement.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramWindowController {

    public TableView<java.util.Map.Entry<Integer, Value>> heapTableView = new TableView<>();
    public TableColumn<Map.Entry<Integer, Value>, String> heapTableAddresses;
    public TableColumn<Map.Entry<Integer, Value>, String> heapTableValues;

    public ListView<Value> outputListView = new ListView<>();
    public ListView<Value> fileTableListView = new ListView<>();
    public ListView<Integer> programIdsListView = new ListView<>();

    public TableView<java.util.Map.Entry<String, Value>> symbolTableView = new TableView<>();
    public TableColumn<Map.Entry<String, Value>, String> symbolTableVariableNames;
    public TableColumn<Map.Entry<String, Value>, String> symbolTableValues;

    public ListView<Statement> executionStackListView = new ListView<>();

    public Button runOneStepButton;
    public Text numberOfProgramsText;
    private Controller programController;

    public void init() {
        this.setTableColumnsForTables();
        this.populateProgramIdsList();
        this.setNumberOfProgramsText();
        this.programIdSelectionHandler();
        this.runOneStepButton.setDisable(false);
    }

    public void setProgramController(Controller programController) {
        this.programController = programController;
        // init here so we can have the programController ready
        this.init();
    }

    private void populateHeapTable(ProgramState programState) {
        var heapTable = programState.getHeap();
        heapTableView.getItems().clear();
        heapTableView.setItems(FXCollections.observableList(new ArrayList<>(heapTable.getContent().entrySet())));
    }

    private void populateOutputList(ProgramState programState) {
        var outputList = programState.getOutput();
        outputListView.setItems(FXCollections.observableArrayList(outputList.getContent()));
    }

    private void populateFileTable(ProgramState programState) {
        var fileTable = programState.getFileTable();
        fileTableListView.setItems(FXCollections.observableArrayList(fileTable.getContent().keySet()));
    }

    private void populateProgramIdsList() {
        var programIdsList = programController.getRepository().getProgramList().stream()
                .map(ProgramState::getProgramId)
                .collect(Collectors.toList());

        if (programIdsList.isEmpty()) {
            runOneStepButton.setDisable(true);
            executionStackListView.getItems().clear();
        }

        programIdsListView.setItems(FXCollections.observableList(programIdsList));
    }

    private void populateSymbolTable(ProgramState selectedProgram) {
        var correspondingSymbolTable = selectedProgram.getSymbolTable();
        symbolTableView.getItems().clear();
        symbolTableView.setItems(FXCollections.observableList(new ArrayList<>(correspondingSymbolTable.getContent().entrySet())));
    }

    private void populateExecutionStack(ProgramState selectedProgram) {
        var correspondingExecutionStack = selectedProgram.getExecutionStack();
        executionStackListView.getItems().clear();
        executionStackListView.setItems(FXCollections.observableList(correspondingExecutionStack.getContent()));
    }

    private void setTableColumnsForTables() {
        symbolTableVariableNames.setCellValueFactory(mapEntry -> new SimpleStringProperty(mapEntry.getValue().getKey()));
        symbolTableValues.setCellValueFactory(mapEntry -> new SimpleStringProperty(mapEntry.getValue().getValue().toString()));

        heapTableAddresses.setCellValueFactory(mapEntry -> new SimpleStringProperty(mapEntry.getValue().getKey().toString()));
        heapTableValues.setCellValueFactory(mapEntry -> new SimpleStringProperty(mapEntry.getValue().getValue().toString()));
    }

    private void setNumberOfProgramsText() {
        numberOfProgramsText.setText("Number of programs: " + programController.getRepository().getProgramList().size());
    }

    private void populateCommonComponents(ProgramState programState) {
        populateHeapTable(programState);
        populateOutputList(programState);
        populateFileTable(programState);
        populateProgramIdsList();
        setNumberOfProgramsText();

        populateSymbolTable(programState);
        populateExecutionStack(programState);
    }

    private void populateParticularComponents(ProgramState selectedProgram) {
        populateSymbolTable(selectedProgram);
        populateExecutionStack(selectedProgram);
    }

    public void programIdSelectionHandler() {
        var selectedId = programIdsListView.getSelectionModel().getSelectedItem();

        if (selectedId != null) {
            ProgramState selectedProgram = null;
            var programList = programController.getRepository().getProgramList();
            for (var program : programList) {
                if (program.getProgramId() == selectedId) {
                    selectedProgram = program;
                }
            }

            if (selectedProgram != null) {
                populateParticularComponents(selectedProgram);
            } else {
                executionStackListView.getItems().clear();
            }
        }
    }

    public void runOneStepButtonPressed() {
        var programState = programController.getRepository().getOriginalProgram();
        try {
            programController.executeOneStep();
            populateCommonComponents(programState);
            programIdSelectionHandler();

        } catch (MyException | InterruptedException | IOException e) {
            var alert = new Alert(Alert.AlertType.ERROR, "Error while execuwuting a statement...");
            alert.show();
        }
    }

}
