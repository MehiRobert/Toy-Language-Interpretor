package Model.Exp;

import Exceptions.MyException;
import Model.Adt.IHeap;
import Model.Adt.MyIDict;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.RefValue;
import Model.Value.Value;

public class ValExpr implements Expr{

    private final Value e;

    public ValExpr(Value Val) {
        this.e = Val;
    }

    @Override
    public Value eval(MyIDict<String, Value> tab, IHeap<Value> heapT) {
        return e;
    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString(){
        return e.toString();
    }
}
