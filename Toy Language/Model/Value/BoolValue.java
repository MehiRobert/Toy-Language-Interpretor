package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;
import com.sun.jdi.BooleanValue;

public class BoolValue implements Value {

    private final boolean val ;

    public BoolValue(boolean v) {
        val = v;
    }

    public boolean getBool() {
        return this.val;
    }

    public String toString() {
        return Boolean.toString(val);
    }
    @Override
    public boolean equals(Object v)
    {
        if(!(v instanceof BoolValue)) return false;
        BoolValue b = (BoolValue) v;
        return b.val == val;
    }
    @Override
    public Type getType() {
        return new BoolType();

    }
}
