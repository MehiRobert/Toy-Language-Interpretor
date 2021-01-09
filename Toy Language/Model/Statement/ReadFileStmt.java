package Model.Statement;

import Exceptions.MyException;
import Exceptions.StmtException;
import Model.Adt.MyIDict;
import Model.Adt.MyIStack;
import Model.Exp.Expr;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileStmt implements Stmt{
    private final Expr expression;
    private final String varName;

    public ReadFileStmt(Expr e,String varName){
        this.expression = e;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        var mystack = state.getStack();
        var symTable = state.getSymTable();
        var fileT = state.getFileTable();
        var heapT = state.getHeapTable();
        Value evaluation = expression.eval(symTable,heapT);
        if(state.getSymTable().isDefined(varName))
        {
            if(symTable.lookup(varName).getType().equals(new IntType()))
            {
                if(evaluation.getType().equals(new StringType()))
                {
                    StringValue filepath = (StringValue) evaluation;
                    if(fileT.isDefined(filepath))
                    {
                        String line = " ";
                        Value number;
                        BufferedReader buf = fileT.lookup(filepath);
                        if(( line = buf.readLine()) != null)
                        {
                            number = new IntValue(Integer.parseInt(line));

                        }
                        else{ number = new IntValue(0);}
                        symTable.update(varName,number);

                    }
                    else throw new StmtException("Filepath is not defined");
                }
                else throw  new StmtException("Expression is not a string Type");
            }
            else throw new StmtException("Variable Name is not an int type");
        }
        else throw new StmtException("Variable name is not defined");
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        var typeOfExpression = expression.typecheck(typeEnv);
        var typeOfVariable = typeEnv.lookup(varName);
        if(typeOfVariable.equals(new IntType()))
        {
            if(typeOfExpression.equals(new StringType()))
                return typeEnv;
            else throw new StmtException("Expression is not a string Type");
        }
        else throw new StmtException("Variable name is not an int type");

    }

    @Override
    public String toString(){
        return "ReadFile(" + expression.toString() + ")";
    }
}
