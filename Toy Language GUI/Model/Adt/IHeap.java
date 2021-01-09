package Model.Adt;

import java.util.Map;

public interface IHeap<T> {

    int allocate(T Val);

    void add(int address,T val);

    T getValue(int address);

    boolean isDefined(int address);

    void update(int address,T value);

    void setContent(Map<Integer,T> content);

    Map<Integer,T> getContent();


}
