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

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) throws MyException {
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



//        try {
//            ex1.typecheck(new MyDict<>() {
//            });
//            PrgState state1 = new PrgState(ex1);
//            Repo repo1 = new Repo("log.txt");
//            repo1.addPrg(state1);
//            Controller cr1 = new Controller(repo1);
//            menu.addCommand(new RunExample("1", ex1.toString(), cr1));
//        }
//         catch (MyException e)
//            {
//                System.out.println("Stmt1 has thrown an exception " + e.getMessage());
//            }
//        try {
//            ex2.typecheck(new MyDict<>());
//            PrgState state2 = new PrgState(ex2);
//            Repo repo2 = new Repo("log2.txt");
//            repo2.addPrg(state2);
//            Controller cr2 = new Controller(repo2);
//            menu.addCommand(new RunExample("2", ex2.toString(), cr2));
//        }
//        catch (MyException e)
//        {
//            System.out.println("Stmt1 has thrown an exception " +e.getMessage());
//        }
//
//        try {
//            ex3.typecheck(new MyDict<>());
//            PrgState state3 = new PrgState(ex3);
//            Repo repo3 = new Repo("log3.txt");
//            repo3.addPrg(state3);
//            Controller cr3 = new Controller(repo3);
//            menu.addCommand(new RunExample("3", ex3.toString(), cr3));
//        }
//        catch (MyException e)
//        {
//            System.out.println("Stmt1 has thrown an exception " +e.getMessage());
//        }
//
//        try {
//            ex4.typecheck(new MyDict<>());
//            PrgState state4 = new PrgState(ex4);
//            Repo repo4 = new Repo("log4.txt");
//            repo4.addPrg(state4);
//            Controller cr4 = new Controller(repo4);
//            menu.addCommand(new RunExample("4", ex4.toString(), cr4));
//        }
//        catch (MyException e)
//        {
//            System.out.println("Stmt1 has thrown an exception " +e.getMessage());
//        }
//
//        try {
//            ex5.typecheck(new MyDict<>());
//            PrgState state5 = new PrgState(ex5);
//            Repo repo5 = new Repo("log5.txt");
//            repo5.addPrg(state5);
//            Controller cr5 = new Controller(repo5);
//            menu.addCommand(new RunExample("5", ex5.toString(), cr5));
//        }
//        catch (MyException e)
//        {
//            System.out.println("Stmt1 has thrown an exception " +e.getMessage());
//        }
//
//        try {
//            ex6.typecheck(new MyDict<>());
//            PrgState state6 = new PrgState(ex6);
//            Repo repo6 = new Repo("log6.txt");
//            repo6.addPrg(state6);
//            Controller cr6 = new Controller(repo6);
//            menu.addCommand(new RunExample("6", ex6.toString(), cr6));
//        }
//        catch (MyException e)
//        {
//            System.out.println("Stmt1 has thrown an exception " +e.getMessage());
//        }
//
//        try {
//            ex7.typecheck(new MyDict<>());
//            PrgState state7 = new PrgState(ex7);
//            Repo repo7 = new Repo("log7.txt");
//            repo7.addPrg(state7);
//            Controller cr7 = new Controller(repo7);
//            menu.addCommand(new RunExample("7", ex7.toString(), cr7));
//        }
//        catch (MyException e)
//        {
//            System.out.println("Stmt1 has thrown an exception " +e.getMessage());
//        }
//
//        try {
//            ex8.typecheck(new MyDict<>());
//            PrgState state8 = new PrgState(ex8);
//            Repo repo8 = new Repo("log8.txt");
//            repo8.addPrg(state8);
//            Controller cr8 = new Controller(repo8);
//            menu.addCommand(new RunExample("8",ex8.toString(),cr8));
//        }
//        catch (MyException e)
//        {
//            System.out.println("Stmt1 has thrown an exception " +e.getMessage());
//        }
//
//        try {
//            ex9.typecheck(new MyDict<>());
//            PrgState state9 = new PrgState(ex9);
//            Repo repo9 = new Repo("log9.txt");
//            repo9.addPrg(state9);
//            Controller cr9 = new Controller(repo9);
//            menu.addCommand(new RunExample("8",ex8.toString(),cr9));
//        }
//        catch (MyException e)
//        {
//            System.out.println("Stmt1 has thrown an exception " +e.getMessage());
//        }
//
//        try {
//            ex10.typecheck(new MyDict<>());
//            PrgState state10 = new PrgState(ex10);
//            Repo repo10 = new Repo("log10.txt");
//            repo10.addPrg(state10);
//            Controller cr10 = new Controller(repo10);
//            menu.addCommand(new RunExample("10",ex10.toString(),cr10));
//        }
//        catch (MyException e)
//        {
//            System.out.println(e.getMessage());
//        }
//        for(int i = 0; i < stmtList.size(); i++)
//        {
//            prgStateList.add(new PrgState(stmtList.get(i)));
//            Repo repo = new Repo(filename  + i + ".txt");
//            repoList.add(repo);
//            //repo.addPrg(state);
//            Controller controller = new Controller(repo);
//            controllerList.add(controller);
//        }

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
