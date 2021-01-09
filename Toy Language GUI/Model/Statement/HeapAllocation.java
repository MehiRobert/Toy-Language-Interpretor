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

public class HeapAllocation implements Stmt{
    private final Expr expression;
    private final String var_name;

    public HeapAllocation(String v,Expr e){
        this.expression = e;
        this.var_name = v;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        var symT = state.getSymTable();
        var heapT = state.getHeapTable();

        if(symT.isDefined(var_name))
        {
            var value = symT.lookup(var_name);
            var v2 = expression.eval(symT,heapT);
            var refV = (RefType) value.getType();
            if(v2.getType().equals(refV.getInner()))
            {
                var addr = heapT.allocate(v2);
                symT.add(var_name,new RefValue(addr,refV.getInner()));
            }
            else throw new StmtException("The result of the expression doesn't equal the inner type");
        }
        else throw new StmtException("Value is not defined in the SymTable");
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfVariable = typeEnv.lookup(var_name);
        var typeOfExpression = expression.typecheck(typeEnv);

        if(typeOfVariable.equals(new RefType(typeOfExpression)))
        {
            return typeEnv;
        }
        else throw new StmtException("New exception: right hand side and left hand side have different types ");

    }

    @Override
    public String toString(){
        return "new(" + this.var_name +","+ expression.toString() + ")";
    }

}
