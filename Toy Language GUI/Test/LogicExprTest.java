package Test;

import Controller.Controller;
import Exceptions.MyException;
import Model.Adt.*;
import Model.Exp.LogicExpr;
import Model.Exp.ValExpr;
import Model.Exp.VarExpr;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repo.Repo;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

public class LogicExprTest {
    @Test
    public void TestLogic() throws MyException, IOException, InterruptedException {
        Repo MyRepo = new Repo("test.txt");
        Controller myCont = new Controller(MyRepo);
        MyStack<Stmt> stk = new MyStack<>();
        MyDict<String, Value> symT = new MyDict<>();
        MyList<Value> out = new MyList<>();
        MyDict<StringValue, BufferedReader> fileTable = new MyDict<>();
        IHeap<Value> heapT12 = new Heap();
        Stmt test1 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("b",new BoolType()),new CompStmt(
                        new AssignStmt("a",new LogicExpr("and",new ValExpr(new BoolValue(true)),new ValExpr(new BoolValue(false)))),
                        new CompStmt(new AssignStmt("b",new LogicExpr("or",new VarExpr("a"),new ValExpr(new BoolValue(true)))),new PrintStmt(new VarExpr("b"))))));
        PrgState newstate1 = new PrgState(stk,symT,out,fileTable,heapT12,test1);
        myCont.addProgram(newstate1);
        myCont.allStep();
        Assert.assertEquals(out.toString(),"[true]");
    }
}
