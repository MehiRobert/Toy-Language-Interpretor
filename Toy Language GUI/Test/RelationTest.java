package Test;
import Controller.Controller;
import Exceptions.MyException;
import Model.Adt.*;
import Model.Exp.RelExpression;
import Model.Exp.ValExpr;
import Model.Exp.VarExpr;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repo.Repo;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

public class RelationTest {
    Repo MyRepo = new Repo("logtest.txt");
    Controller myCont = new Controller(MyRepo);
    MyStack<Stmt> stk = new MyStack<>();
    MyDict<String, Value> symT = new MyDict<>();
    MyList<Value> out = new MyList<>();
    MyDict<StringValue, BufferedReader> fileTable = new MyDict<>();
    IHeap<Value> heapT13 = new Heap();
    @Test
    public void testRelation() throws IOException, MyException, InterruptedException {
        Stmt test1 = new CompStmt(new VarDeclStmt("t2",new BoolType()),
                                new CompStmt(new AssignStmt("t2",new RelExpression(new ValExpr(new IntValue(2)),
                                        new ValExpr(new IntValue(4)),"<=")),new PrintStmt(new VarExpr("t2"))));
        PrgState newstate1 = new PrgState(stk,symT,out,fileTable,heapT13,test1);
        myCont.addProgram(newstate1);
        myCont.allStep();
        Assert.assertEquals(out.toString(),"[true]");


    }
}
