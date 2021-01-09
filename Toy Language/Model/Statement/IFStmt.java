package Model.Statement;

import Model.Adt.MyDict;
import Model.Adt.MyIDict;
import Model.Adt.MyIStack;
import Exceptions.MyException;
import Exceptions.StmtException;
import Model.Exp.Expr;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class IFStmt implements Stmt{
    private final Expr exp;
    private final Stmt thenS;
    private final Stmt elseS;

    public IFStmt(Expr e, Stmt t, Stmt el) {exp = e; thenS = t;elseS =el;}
    @Override
    public String toString() {return "(IF " + exp.toString() + " Then " + thenS.toString() +
                                " ELSE " + elseS.toString() + ")";}
    @Override
    public PrgState execute(PrgState state) throws MyException {

        var stack = state.getStack();
        var heapT = state.getHeapTable();
        Value condition = exp.eval(state.getSymTable(),heapT);
        if(!condition.getType().equals(new BoolType()))
            throw new StmtException(" Expected a boolean type!");
        if(condition.equals(new BoolValue(true)))
        {
            stack.push(thenS);
        }
        else {
            stack.push(elseS);
        }
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfExpression = exp.typecheck(typeEnv);
        if(typeOfExpression.equals(new BoolType()))
        {
            thenS.typecheck(new MyDict<>((MyDict<String,Type>) typeEnv));
            elseS.typecheck(new MyDict<>((MyDict<String,Type>) typeEnv));
            return typeEnv;
        }
        else throw new StmtException("Type of the expression is not a Bool Type");
    }
}
