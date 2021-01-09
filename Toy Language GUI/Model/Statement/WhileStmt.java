package Model.Statement;

import Exceptions.MyException;
import Exceptions.StmtException;
import Model.Adt.MyIDict;
import Model.Exp.Expr;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;

import java.io.IOException;

public class WhileStmt implements Stmt{

    Expr expression;
    Stmt statement;
    public WhileStmt(Expr e,Stmt state){
        this.expression = e;
        this.statement = state;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        var stack1 = state.getStack();
        var symT = state.getSymTable();
        var heapT = state.getHeapTable();
        var value = expression.eval(symT,heapT);
        if(!value.getType().equals(new BoolType()))
            throw new StmtException("Conditional expression is not a boolean");
        else{
            if(value.equals(new BoolValue(true)))
            {
                stack1.push(this);
                stack1.push(statement);
            }
        }
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfExpression = expression.typecheck(typeEnv);
        if(typeOfExpression.equals(new BoolType()))
            return typeEnv;
        else throw new StmtException("WhileStmt: Conditional expression is not a boolean");
    }

    @Override public String toString(){
        var Str = statement.toString();
        var Str1 = Str.replace('|',',');
        return "While(" + expression.toString() + ")" + ',' + Str1;
    }
}
