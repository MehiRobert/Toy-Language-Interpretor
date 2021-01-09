package Model.Statement;

import Exceptions.MyException;
import Exceptions.StmtException;
import Model.Adt.IHeap;
import Model.Adt.MyIDict;
import Model.Adt.MyIList;
import Model.Adt.MyIStack;
import Model.Exp.Expr;
import Model.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt  implements Stmt{
    Expr expression;
    public CloseRFileStmt(Expr e){
        this.expression = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        var fileT = state.getFileTable();
        var symTable = state.getSymTable();
        var heapT = state.getHeapTable();
        Value evaluation = expression.eval(symTable,heapT);
        if(evaluation.getType().equals(new StringType())){
            StringValue filepath = (StringValue) evaluation;
            if(fileT.isDefined(filepath)){
                BufferedReader bufferedReader = fileT.lookup(filepath);
                bufferedReader.close();
                fileT.delete(filepath);
            }
            else throw new StmtException("Filename is not defined");
        }
        else throw new StmtException("Expression is not a string type");
        return null;

     }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfExpression = expression.typecheck(typeEnv);
        if(typeOfExpression.equals(new StringType()))
           return typeEnv;
        else throw new StmtException("CloseRFileStmt: Type of the expression is not a string type");
    }

    @Override
    public String toString(){
        return "CloseFile(" + expression.toString() + ")";
     }
}
