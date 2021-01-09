package View;

import Controller.Controller;
import Exceptions.MyException;
import Model.Adt.IHeap;
import Model.Adt.MyIStack;
import Model.PrgState;
import Model.Statement.Stmt;
import Model.Value.StringValue;
import Model.Value.Value;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RunStmtController implements Initializable {

    private Controller controller;

    @FXML
    private TextField nrPrograms;

    @FXML
    private ListView<Stmt> StackView;

    @FXML
    private TableView<Map.Entry<Integer,Value>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer,Value>,Integer> heapAddress;

    @FXML
    private TableColumn<Map.Entry<Integer,Value>,Value> heapValue;

    @FXML
    private ListView<Value> outView;

    @FXML
    private TableView<Map.Entry<String,Value>> SymTableView;

    @FXML
    private TableColumn<Map.Entry<String,Value>,String> symVar;

    @FXML
    private TableColumn<Map.Entry<String,Value>, Value> symVal;

    @FXML
    private ListView<StringValue> fileListView;

    @FXML
    private ListView<Integer> identifiersListView;


    public RunStmtController(Controller chosenController) {
        this.controller = chosenController;
    }


    @FXML
    public void goToProgramChooser(ActionEvent event) {
        try {
            Parent runViewParent = FXMLLoader.load(getClass().getResource("ProgramList.fxml"));
            Scene programChooserScene = new Scene(runViewParent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(programChooserScene);
            window.show();
        }catch (IOException e)
        {
            e.getStackTrace();
        }

    }

    public List<Integer> getPrgStateIds(List<PrgState> prgStates)
    {
        return prgStates.stream().map(PrgState::getId).collect(Collectors.toList());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nrPrograms.setText(String.valueOf(controller.getProgramList().size()));

        setIdentifiersListView();


        heapAddress.setCellValueFactory( p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValue.setCellValueFactory( p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        symVal.setCellValueFactory( p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        symVar.setCellValueFactory( p -> new SimpleStringProperty(p.getValue().getKey()));
    }

    @FXML
    public void OneStepExecution(ActionEvent event) throws InterruptedException {
        var programListSize = controller.getPrgStateList().size();
        if (programListSize == 0) {
            Alert alert=new Alert(Alert.AlertType.ERROR,"No programs left to execute");
            alert.show();
        }
        else {
            try {
                controller.OnlyOneStep();
                updateGui();
            } catch (InterruptedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        }

    }
    @FXML
    void switchProgramState(MouseEvent event) {
        int index = identifiersListView.getSelectionModel().getSelectedItem();
        var prgState = controller.getProgramList().stream().filter(
                s->s.getId()==index).collect(Collectors.toList()).get(0);
        try {
            List<Stmt> exeStackList = new ArrayList<>(prgState.getStack().getContent());
            Collections.reverse(exeStackList);
            StackView.setItems(FXCollections.observableArrayList(exeStackList));
            StackView.refresh();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Switch error");
            alert.setHeaderText("Switch error occured");
            alert.setContentText(e.toString());

            alert.showAndWait();
        }
    }
    public void updateGui()
    {
        setExeStack();
        setHeapTable();
        setOutView();
        setSymTableView();
        setFileListView();
        setIdentifiersListView();
        nrPrograms.setText(String.valueOf(controller.getPrgStateList().size()));


    }
    public void setExeStack()
    {
        var exeStack = controller.getProgramList().get(0).getStack();
        List<Stmt> exeStackList = new ArrayList<>(exeStack.getContent());
        Collections.reverse(exeStackList);
        StackView.setItems(FXCollections.observableArrayList(exeStackList));
        StackView.refresh();

    }
    public void setHeapTable()
    {

        var heapTable = controller.getProgramList().get(0).getHeapTable().getContent();
        if(heapTable.size() > 0) {
            ArrayList<Map.Entry<Integer, Value>> entries = new ArrayList<>();
            for (Map.Entry<Integer, Value> entry : heapTable.entrySet()) {
                entries.add(entry);
            }
            heapTableView.setItems(FXCollections.observableArrayList(entries));
        }
    }
    public void setOutView()
    {
//        var outList = controller.getProgramList().get(0).getOut();
//        if(outList.getSize() != 0)
//            outView.getItems().add(0,outList.toString());

            ObservableList<Value> observableList=FXCollections.observableList(controller.getProgramList().get(0).getOut().getElems());
            outView.setItems(observableList);
            outView.refresh();

    }
    public void setSymTableView()
    {
        var prgState = controller.getProgramList().get(0);
        var symTable = prgState.getSymTable();
        List<Map.Entry<String,Value>> symTableList=new ArrayList<>(symTable.entrySet());
        this.SymTableView.setItems(FXCollections.observableArrayList(symTableList));
        this.SymTableView.refresh();
    }
    public void setFileListView()
    {

        var filetable = controller.getProgramList().get(0).getFileTable();
        if(filetable.entrySet().size() != 0) {
            List<StringValue> filetableList = new ArrayList<>(filetable.getKeys());
            this.fileListView.setItems(FXCollections.observableArrayList(filetableList));
            fileListView.refresh();
        }
    }
    public void setIdentifiersListView()
    {
        List<PrgState> prgStates = controller.getPrgStateList();

        identifiersListView.setItems(FXCollections.observableList(getPrgStateIds(prgStates)));

    }

}
