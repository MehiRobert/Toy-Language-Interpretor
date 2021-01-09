package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value{
    private final String str;
    public StringValue(String s) {this.str = s;}
    public String toString() {return this.str;}
    @Override
    public Type getType() {
        return new StringType();
    }
}
