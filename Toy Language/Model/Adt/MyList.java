package Model.Adt;

import Model.Type.RefType;
import Model.Value.RefValue;

import javax.swing.*;
import java.util.*;

public class MyList<T> implements MyIList<T>{
    private final List<T> list;

    public MyList(){
        this.list = new Vector<>();
        };

    @Override
    public void add(T val) {
        list.add(val);
    }

    @Override
    public void remove(T Val) {
        list.remove(Val);
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public T getElement(int i) {
        return list.get(i);
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder("[");
        for (T item : list) {
            content.append(item);
            content.append(",");

        }
        if (content.length() > 2)
            content.deleteCharAt(content.length() - 1);
        return content + "]";
    }

}
