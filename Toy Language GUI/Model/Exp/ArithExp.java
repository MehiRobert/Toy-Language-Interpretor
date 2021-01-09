package Model.Exp;

import Exceptions.ExpressionException;
import Exceptions.MyException;
import Model.Adt.IHeap;
import Model.Adt.MyIDict;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithExp implements Expr{

    private final Expr ex1;
    private final Expr ex2;
    private char op;

    public ArithExp(char operation,Expr e1,Expr e2)
    {
        this.ex1 = e1;
        this.ex2 = e2;
        this.op = operation;


    }
    @Override
    public Value eval(MyIDict<String, Value> tab, IHeap<Value> heapT) throws MyException {
        Value v1,v2;
        v1 = ex1.eval(tab,heapT);
        if(v1.getType().equals(new IntType()))
        {
            v2 = ex2.eval(tab,heapT);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(op == '+') return new IntValue(n1+n2);
                if(op == '-') return new IntValue(n1-n2);
                if(op == '*') return new IntValue(n1*n2);
                if(op == '/') {
                    if(n2 == 0)
                        throw new ExpressionException("Division by zero");
                    return new IntValue(n1/n2);
                }

            }
            else throw new ExpressionException("Second operand is not an integer.");
        }
        else throw new ExpressionException("First operand is not an integer.");


        return new IntValue(0);
    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typ1 = ex1.typecheck(typeEnv);
        var typ2 =ex2.typecheck(typeEnv);

        if(!typ1.equals(new IntType()))
            throw new ExpressionException("First Operand is not an integer type");
        if(!typ2.equals(new IntType()))
            throw new ExpressionException("Second Operand is not an integer type");
        return new IntType();
    }

    @Override
    public String toString(){
        String sign = " ";
        if(op == '+')
            sign = "+";
        if(op == '-')
            sign = "-";
        if(op == '*')
            sign = "*";
        if(op == '/')
            sign = "/";
        return ex1 + sign + ex2;
    }
}
