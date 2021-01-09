package Model.Exp;

import Exceptions.MyException;
import Model.Adt.IHeap;
import Model.Adt.MyIDict;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class VarExpr implements Expr{

    private final String id;

    public VarExpr(String i) {this.id = i;}
    @Override
    public Value eval(MyIDict<String, Value> tab, IHeap<Value> heapT) {
        return tab.lookup(id);
    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString(){
        return id;
    }
}
