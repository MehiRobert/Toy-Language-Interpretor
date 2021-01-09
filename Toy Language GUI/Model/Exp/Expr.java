package Model.Exp;

import Exceptions.MyException;
import Model.Adt.IHeap;
import Model.Adt.MyIDict;
import Model.Type.Type;
import Model.Value.Value;

public interface Expr {
    Value eval(MyIDict<String,Value> tab, IHeap<Value> heapT) throws MyException;

    Type typecheck(MyIDict<String,Type> typeEnv) throws MyException;
}
