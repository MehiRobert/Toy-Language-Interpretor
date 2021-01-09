package Model.Statement;

import Exceptions.MyException;
import Exceptions.StmtException;
import Model.Adt.MyIDict;
import Model.Exp.Expr;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements Stmt {
    String id;
    Expr exp;

    public AssignStmt(String i,Expr e) {this.id = i; this.exp = e;}
    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var stack = state.getStack();
        var symTbl = state.getSymTable();
        var heapT = state.getHeapTable();
        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl,heapT);
            Type typId = (symTbl.lookup(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else throw new StmtException("declared type of variable"+id+" " +
                    "and type of the assigned expression do not match;");
        }
        else throw new StmtException("the used variable " +id + " was not declared before");
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfVariable = typeEnv.lookup(id);
        var typeOfExpression = exp.typecheck(typeEnv);

        if(typeOfVariable.equals(typeOfExpression))
            return typeEnv;
        else throw new StmtException("Assignment: Declared type of variable doesn't match the type of the expression");
    }
}
