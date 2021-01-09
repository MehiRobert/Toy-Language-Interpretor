package Model.Statement;

import Exceptions.MyException;
import Model.Adt.MyIDict;
import Model.Adt.MyIStack;
import Model.PrgState;
import Model.Type.Type;

public class CompStmt implements Stmt{
    Stmt first;
    Stmt second;

    public CompStmt(Stmt f, Stmt s) {this.first = f;this.second = s;}
    @Override
    public String toString() {return first.toString() +" | "+ second.toString() ;}
    @Override
    public PrgState execute(PrgState state) {
        MyIStack<Stmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }
}
