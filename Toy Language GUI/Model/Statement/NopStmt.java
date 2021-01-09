package Model.Statement;

import Exceptions.MyException;
import Model.Adt.MyIDict;
import Model.PrgState;
import Model.Type.Type;

public class NopStmt implements Stmt{

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        return null;
    }
}
