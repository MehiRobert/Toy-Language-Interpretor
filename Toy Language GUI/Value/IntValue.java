package Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value{

    private final int val ;
    public IntValue(int v) {val = v;}

    public int getVal() {return this.val;}

    public String toString() {return Integer.toString(val);}
    @Override
    public Type getType() {
        return new IntType();
    }
}
