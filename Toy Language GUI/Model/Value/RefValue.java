package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value{
    int address;
    Type locationType;
    public RefValue(int addr, Type locType) {
        this.address = addr;
        this.locationType = locType;
    }
    @Override
    public boolean equals(Object v)
    {
        if(!(v instanceof RefValue)) return false;
        var b = (RefValue) v;
        return b.address == address;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public String toString(){
        return  "(" +  address + ", " + locationType.toString() + ")" ;
    }
    @Override
    public Type getType() {
        return new RefType(locationType);
    }
}
