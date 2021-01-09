package Model.Statement;

import Exceptions.MyException;
import Exceptions.StmtException;
import Model.Adt.MyIDict;
import Model.Exp.Expr;
import Model.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;

import java.io.IOException;

public class HeapWriteStmt implements Stmt {

    private final String var_name;
    private final Expr expression;

    public HeapWriteStmt(String v,Expr expr){
        this.var_name = v;
        this.expression = expr;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        var symT = state.getSymTable();
        var heapT = state.getHeapTable();
        if(symT.isDefined(var_name))
        {
            var addr = symT.lookup(var_name);
            if(heapT.isDefined(((RefValue) addr).getAddress()))
            {
                var val = expression.eval(symT,heapT);
                if(val.getType().equals(((RefValue) addr).getLocationType()))
                {
                    heapT.update((((RefValue) addr).getAddress()),  val);
                }
            }
        }
        else throw new StmtException("Variable is not defined");
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfVariable = typeEnv.lookup(var_name);
        var typeOfExpression = expression.typecheck(typeEnv);
        if(typeOfVariable.equals(new RefType(typeOfExpression)))
            return typeEnv;
        else throw new StmtException("Write Stmt: The left hand side doesn't have the same type as the right hand side");
    }

    @Override
    public String toString(){
        return "wh(" + var_name + ","+ expression.toString() + ")";
    }
}
