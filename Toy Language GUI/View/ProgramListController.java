package View;

import Controller.Controller;
import Exceptions.MyException;
import Model.Adt.MyDict;
import Model.Exp.*;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repo.Repo;
import com.sun.glass.ui.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.junit.FixMethodOrder;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.event.ActionEvent;

public class ProgramListController implements Initializable {
    @FXML private ListView<String> listView;

    private  List<Controller> controllerList = new Vector<>();

    private int index;

    public void goToRun(ActionEvent event) {
        index = listView.getSelectionModel().getSelectedIndex();
        if(index == -1)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No program selected");
            alert.show();
        }
        else {
            var chosenController = controllerList.get(index);
            if (chosenController.getProgramList().get(0).getStack().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This program was executed already");
                alert.show();
            }
            else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("RunStmt.fxml"));

                    var RunController = new RunStmtController(chosenController);

                    loader.setController(RunController);

                    AnchorPane root = loader.load();

                    Scene runViewScene = new Scene(root, 800, 600);

                    Stage window = new Stage();

                    window.setScene(runViewScene);
                    window.show();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }



        
    }
    public int getIndexOfSelectedProgram(){
        return index;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Stmt> stmtList = new Vector<Stmt>();
        TextMenu menu = new TextMenu();
        String filename = "log";

        Stmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValExpr(new IntValue(2))), new PrintStmt(new VarExpr("v"))));
        stmtList.add(ex1);

        Stmt ex2 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValExpr(new BoolValue(false)), new ArithExp('*', new ValExpr(new IntValue(3)), new ValExpr(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExpr("a"), new ValExpr(new IntValue(1)))), new PrintStmt(new VarExpr("b"))))));
        stmtList.add(ex2);


        Stmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValExpr(new BoolValue(true))),
                                new CompStmt(new IFStmt(new VarExpr("a"), new AssignStmt("v", new ValExpr(new IntValue(2))), new AssignStmt("v", new ValExpr(new IntValue(3)))), new PrintStmt(new VarExpr("v"))))));

        stmtList.add(ex3);



        Stmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValExpr(new StringValue("test.txt"))),
                new CompStmt(new OpenRFile(new VarExpr("varf")),
                        new CompStmt(new VarDeclStmt("varc", new IntType()),
                                new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"),
                                        new CompStmt(new PrintStmt(new VarExpr("varc")),
                                                new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"),
                                                        new CompStmt(new PrintStmt(new VarExpr("varc")),
                                                                new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"),
                                                                        new CompStmt(new PrintStmt(new VarExpr("varc")),
                                                                                new CloseRFileStmt(new VarExpr("varf"))))))))))));

        stmtList.add(ex4);



        Stmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocation("v", new ValExpr(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocation("a", new VarExpr("v")),
                                        new CompStmt(new PrintStmt(new VarExpr("v")),
                                                new PrintStmt(new VarExpr("a")))))));

        stmtList.add(ex5);


        Stmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocation("v", new ValExpr(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocation("a", new VarExpr("v")),
                                        new CompStmt(new PrintStmt(new HeapReadingExpr(new VarExpr("v"))),
                                                new PrintStmt(new ArithExp('+',new HeapReadingExpr(new HeapReadingExpr(new VarExpr("a"))),
                                                        new ValExpr(new IntValue(5))
                                                )))))));
        stmtList.add(ex6);



        Stmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValExpr(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelExpression(new VarExpr("v"), new ValExpr(new IntValue(0)), ">"), new CompStmt(new PrintStmt(new VarExpr("v")), new AssignStmt("v", new ArithExp('-',new VarExpr("v"), new ValExpr(new IntValue(1)))))),
                                new PrintStmt(new VarExpr("v")))));
        stmtList.add(ex7);



        Stmt ex8 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new HeapAllocation("v",new ValExpr(new IntValue(20))),
                        new CompStmt(new PrintStmt(new HeapReadingExpr(new VarExpr("v"))),
                                new CompStmt(new HeapWriteStmt("v",new ValExpr(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+',new HeapReadingExpr(new VarExpr("v")),new ValExpr(new IntValue(5))))))));
        stmtList.add(ex8);

        Stmt ex9 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(new HeapAllocation("v",new ValExpr(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocation("a",new VarExpr("v")),
                                        new CompStmt(new HeapAllocation("v",new ValExpr(new IntValue(30))),new CompStmt(new HeapAllocation("v",new ValExpr(new IntValue(56))),new CompStmt( new PrintStmt(new HeapReadingExpr(new VarExpr("v"))),
                                                new PrintStmt(new HeapReadingExpr(new HeapReadingExpr(new VarExpr("a")))))))))));

        stmtList.add(ex9);


        Stmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValExpr(new IntValue(10))),
                                new CompStmt(new HeapAllocation("a", new ValExpr(new IntValue(20))),
                                        new CompStmt(new ForkStmt(new CompStmt(new HeapWriteStmt("a", new ValExpr(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValExpr(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExpr("v")),
                                                                new PrintStmt(new HeapReadingExpr(new VarExpr("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExpr("v")),
                                                        new PrintStmt(new HeapReadingExpr(new VarExpr("a")))))))));
        stmtList.add(ex10);


        for(int i = 0; i < stmtList.size() ; i++)
        {
            var stmt = stmtList.get(i);
            try {
                stmt.typecheck(new MyDict<>());
                PrgState state = new PrgState(stmt);
                Repo repo = new Repo(filename + (i + 1) + ".txt");
                repo.addPrg(state);
                Controller controller = new Controller(repo);
                menu.addCommand(new RunExample(Integer.toString(i + 1), stmt.toString(), controller));
                controllerList.add(controller);
                listView.getItems().addAll(stmt.toString());

            }
            catch(MyException e)
            {
                System.out.println("Statement " + (i+1) + " has thrown an exception " + e.getMessage());
            }
        }



        menu.addCommand(new ExitCommand("0","exit"));

    }



}

