package Model.Statement;

import Exceptions.MyException;
import Model.Adt.MyIDict;
import Exceptions.StmtException;
import Model.PrgState;
import Model.Type.*;
import Model.Value.Value;

public class VarDeclStmt implements Stmt{
    private final String name;
    private final Type typ;

    public VarDeclStmt(String n, Type t) {this.name = n; this.typ = t;}
    @Override
    public PrgState execute(PrgState state) throws StmtException {
        MyIDict<String, Value> symT = state.getSymTable();
        if(symT.isDefined(name))
            throw new StmtException("Variable is already declared!");
        else {
            if (typ.equals(new BoolType()))
                symT.add(name,typ.defaultValue());
            else if(typ.equals(new IntType()))
                symT.add(name,typ.defaultValue());
            else if(typ.equals(new StringType()))
                symT.add(name,typ.defaultValue());
            else if(typ instanceof RefType)
                symT.add(name,typ.defaultValue());
            else {
                throw new StmtException("Type doesn't exist");
            }
        }

        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        typeEnv.add(name,typ);
        return typeEnv;
    }

    @Override
    public String toString() {
        return typ.toString() + " " + name;
    }
}
