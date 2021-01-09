package Model.Adt;

import Model.Value.RefValue;
import Model.Value.Value;

import java.sql.Ref;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Heap implements IHeap<Value>{
    private Map<Integer, Value> heapTable;
    private final AtomicInteger address;

    public Heap(){
        this.heapTable = new HashMap<Integer, Value>();
        address = new AtomicInteger(1);

    }

    @Override
    public int allocate(Value Val) {
        var newaddress = address.getAndIncrement();
        this.heapTable.put(newaddress,Val);
        return newaddress;
    }

    @Override
    public void add(int address, Value val) {
        this.heapTable.put(address,val);
    }

    @Override
    public Value getValue(int address) {
        return heapTable.get(address);
    }

    @Override
    public boolean isDefined(int address) {
        return heapTable.containsKey(address);
    }

    @Override
    public void update(int address,Value value) {
            heapTable.replace(address,value);
    }

    @Override
    public void setContent(Map<Integer, Value> content) {
        heapTable = content;
    }

    @Override
    public Map<Integer, Value> getContent() {
        return heapTable;
    }

    @Override
    public String toString(){
        var str = new StringBuilder();
        str.append("[");
        for(Map.Entry<Integer,Value> entry : heapTable.entrySet())
        {
            if (str.length() != 1)
                str.append(",");
            str.append(entry.getKey()).append("->").append(entry.getValue());
        }
        str.append("]");
        return str.toString();
    }



}
