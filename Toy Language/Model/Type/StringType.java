package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type{

    public boolean equals(Object obj){
        return obj instanceof StringType;
    }
    @Override
    public String toString(){
        return "String";
    }
    @Override
    public Value defaultValue() {
        return new StringValue(" ");
    }
}
