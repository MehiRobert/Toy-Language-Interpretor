package Model;

import Exceptions.AdtException;
import Exceptions.MyException;
import Model.Adt.*;
import Model.Statement.Stmt;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;


public class PrgState {
    private MyIStack<Stmt> exeStack;
    private MyIDict<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDict<StringValue, BufferedReader> fileTable;
    private IHeap<Value> HeapTable;
    private static int id = 1;
    private int programid = 1;

    public PrgState(MyIStack<Stmt> stack, MyIDict<String, Value> symt, MyIList<Value> ot,MyIDict<StringValue,BufferedReader> filetable,IHeap<Value> heapT, Stmt prg)
    {
        exeStack = stack;
        symTable = symt;
        out = ot;
        fileTable = filetable;
        HeapTable = heapT;
        exeStack.push(prg);
        this.programid = increaseId();

    }
    public PrgState(Stmt prg){
        exeStack = new MyStack<>();
        symTable = new MyDict<>();
        out = new MyList<>();
        fileTable = new MyDict<>();
        HeapTable = new Heap();
        exeStack.push(prg);
    }

    private static synchronized int increaseId()
    {
        return ++id;
    }
    public MyIStack<Stmt> getStack() {return this.exeStack;}

    public MyIDict<String,Value> getSymTable() {return this.symTable;}

    public MyIList<Value> getOut() {return this.out;}

    public MyIDict<StringValue, BufferedReader> getFileTable() {return this.fileTable;}

    public IHeap<Value> getHeapTable() {return this.HeapTable;}

    public Boolean isNotCompleted(){
        return (!this.exeStack.isEmpty());
    }

    public PrgState oneStep() throws MyException, IOException {
        if(exeStack.isEmpty()) throw new AdtException("stack is empty");
        Stmt statement = exeStack.pop();
        return statement.execute(this);
    }
    @Override
    public String toString() {
        return
                "Id of the program " + programid + "\n"
                + "Exe Stack:" + exeStack + "\n" +
                "SymTable: " + symTable + "\n" +
                "Out: " + out + "\n" +
                "FileTable: " + fileTable.toString1() + "\n" +
                "HeapTable: " + HeapTable + "\n";
    }
}
