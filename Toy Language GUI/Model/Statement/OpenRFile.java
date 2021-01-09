package Model.Statement;

import Exceptions.MyException;
import Exceptions.StmtException;
import Model.Adt.MyIDict;
import Model.Exp.Expr;
import Model.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements Stmt{
    private final Expr expression;
    public OpenRFile(Expr e){
        this.expression = e;

    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        var mystack = state.getStack();
        var fileT = state.getFileTable();
        var heapT = state.getHeapTable();
        Value evaluation = expression.eval(state.getSymTable(),heapT);

        if(!evaluation.getType().equals(new StringType()))
            throw new StmtException("String Type expected!!!");
        else
        {
            StringValue filePath = (StringValue) evaluation;
            if(fileT.isDefined(filePath))
                throw new StmtException("Filename was already declared as a key");
            FileReader fr = new FileReader(filePath.toString());
            BufferedReader buf = new BufferedReader(fr);
            fileT.add(filePath,buf);
        }
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfExpression = expression.typecheck(typeEnv);
        if(typeOfExpression.equals(new StringType()))
            return typeEnv;
        else throw new StmtException("OpenRFile: Type of Expression is not a string type");
    }

    @Override
    public String toString(){
        return "OpenFile" + "(" + expression.toString() + ")";
    }
}
