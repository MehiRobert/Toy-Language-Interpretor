package Model.Statement;

import Model.Adt.MyIDict;
import Model.Adt.MyIList;
import Exceptions.MyException;
import Model.Exp.Expr;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStmt implements Stmt {
    private final Expr exp;

    public PrintStmt(Expr v) {
        this.exp = v;
    }
    @Override
    public String toString() {return "print(" +exp.toString() +")";}
    @Override
    public PrgState execute(PrgState state) throws MyException {

        var out = state.getOut();
        var heapT = state.getHeapTable();
        out.add(exp.eval(state.getSymTable(),heapT));

        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

}
