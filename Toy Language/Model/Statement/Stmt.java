package Model.Statement;

import Exceptions.MyException;
import Model.Adt.MyIDict;
import Model.PrgState;
import Model.Type.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Stmt {
    PrgState execute(PrgState state) throws MyException, IOException;

    MyIDict<String, Type> typecheck(MyIDict<String,Type> typeEnv) throws
            MyException;

}
