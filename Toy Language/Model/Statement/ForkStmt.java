package Model.Statement;

import Exceptions.MyException;
import Model.Adt.MyDict;
import Model.Adt.MyIDict;
import Model.Adt.MyStack;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

import java.io.IOException;

public class ForkStmt implements Stmt{

    Stmt statement;
    public ForkStmt(Stmt statement)
    {
        this.statement = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, IOException{

        return new PrgState(new MyStack<>(),
                            new MyDict<>((MyDict<String, Value>) state.getSymTable()),
                            state.getOut(),
                            state.getFileTable(),
                            state.getHeapTable(),
                            statement);
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        return statement.typecheck(typeEnv);

    }

    @Override
    public String toString()
    {
        return "Fork(" + statement + ")";
    }
}
