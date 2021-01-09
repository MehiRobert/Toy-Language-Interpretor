package Model.Exp;

import Exceptions.ExpressionException;
import Exceptions.MyException;
import Model.Adt.IHeap;
import Model.Adt.MyIDict;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class HeapReadingExpr implements Expr{

    private final Expr expression;

    public HeapReadingExpr(Expr expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyIDict<String, Value> tab, IHeap<Value> heapT) throws MyException {
        var valExpr = expression.eval(tab,heapT);
        if(valExpr instanceof RefValue)
        {
            if(heapT.isDefined(((RefValue) valExpr).getAddress()))
            {
                return heapT.getValue(((RefValue) valExpr).getAddress());
            }
            else throw new ExpressionException("Address is not defined");
        }
        else throw new ExpressionException("Value is not an instance of RefValue");

    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfExpression = expression.typecheck(typeEnv);
        if(typeOfExpression instanceof RefType)
        {
            RefType refType = (RefType) typeOfExpression;
            return refType.getInner();
        }
        else throw new ExpressionException("The Heap Reading argument is not a Reftype");
    }

    @Override
    public String toString(){
        return "rH(" + expression.toString() + ")";
    }
}
