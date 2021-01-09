package Model.Exp;

import Model.Adt.IHeap;
import Model.Adt.MyIDict;
import Exceptions.ExpressionException;
import Exceptions.MyException;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.RefValue;
import Model.Value.Value;

public class LogicExpr implements Expr {
    private final Expr e1;
    private final Expr e2;
    private final String op; // 1-or, 2-and

    public LogicExpr(String operator,Expr e1,Expr e2)
    {
        this.e1 = e1;
        this.e2 = e2;
        this.op = operator;


    }
    @Override
    public Value eval(MyIDict<String, Value> tab, IHeap<Value> heapT) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tab,heapT);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tab, heapT);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                if(op.equals("and")) return new BoolValue(b1.getBool() && b2.getBool());
                if(op.equals("or")) return new BoolValue(b1.getBool() || b2.getBool());
            }
            else throw new ExpressionException("Second operand is not a boolean");
        }
        else throw new ExpressionException("First operand is not a boolean");
        return new BoolValue(false);
    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfExpr1 = e1.typecheck(typeEnv);
        var typeOfExpr2 = e2.typecheck(typeEnv);

        if(!typeOfExpr1.equals(new BoolType()))
            throw new ExpressionException("First Operand is not a Boolean");
        if(!typeOfExpr2.equals(new BoolType()))
            throw new ExpressionException("Second Operand is not a boolean");
        return new BoolType();
    }

    @Override
    public String toString(){
        return e1+ " " + op + " " + e2;
    }
}