package Model.Exp;

import Exceptions.ExpressionException;
import Exceptions.MyException;
import Model.Adt.IHeap;
import Model.Adt.MyIDict;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.RefValue;
import Model.Value.Value;

public class RelExpression implements Expr{
    private final Expr expression1;
    private final Expr expression2;
    private final String operation;

    public RelExpression(Expr e1,Expr e2,String operation){
        this.expression1 = e1;
        this.expression2 = e2;
        this.operation = operation;
    }
    public String toString(){
        return  "(" +  this.expression1.toString() + this.operation + this.expression2.toString() + ")";
    }
    @Override
    public Value eval(MyIDict<String, Value> tab, IHeap<Value> heapT) throws MyException {
       Value v1 = expression1.eval(tab, heapT);
       Value v2 = expression2.eval(tab, heapT);
       if(v1.getType().equals(new IntType()))
       {
            if(v2.getType().equals(new IntType()))
            {
                IntValue intValue = (IntValue) v1;
                IntValue intValue1 = (IntValue) v2;
                int a = intValue.getVal();
                int b = intValue1.getVal();

                switch(operation){
                    case "<":
                        return new BoolValue(a < b);
                    case "<=":
                        return new BoolValue(a <= b);
                    case "==":
                        return new BoolValue(a == b);
                    case "!=":
                        return new BoolValue(a != b);
                    case ">":
                        return new BoolValue(a > b);
                    case ">=":
                        return new BoolValue(a >= b );


                }
            }
            else throw new ExpressionException("Second expression is not an int");
       }
       else throw new ExpressionException("First expression is not an int");
       return new BoolValue(false);
    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfExpression1 = expression1.typecheck(typeEnv);
        var typeOfExpression2 = expression2.typecheck(typeEnv);

        if(!typeOfExpression1.equals(new IntType()))
            throw new ExpressionException("First Operand is not an integer type");
        if(!typeOfExpression2.equals(new IntType()))
            throw new ExpressionException("Second Operand is not an integer type");
        return new BoolType();
    }

}
