package View;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Controller.Controller;
import Exceptions.MyException;
import Model.Adt.*;
import Model.Exp.*;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.*;
import Repo.Repo;
import View.ExitCommand;
import View.RunExample;
import View.TextMenu;

import java.awt.*;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        
        Parent root = FXMLLoader.load(getClass().getResource("ProgramList.fxml"));
        primaryStage.setTitle("My first project in JavaJx :)");
        primaryStage.setScene(new Scene(root, 600, 400, Color.AQUAMARINE));
        primaryStage.show();



    }


    public static void main(String[] args) throws MyException {
        launch(args);
        TextMenu menu = new TextMenu();
        List<Stmt> stmtList = new Vector<Stmt>();
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
            }
            catch(MyException e)
            {
                System.out.println("Statement " + (i+1) + " has thrown an exception " + e.getMessage());
            }
        }



        menu.addCommand(new ExitCommand("0","exit"));
        menu.show();
    }

}
