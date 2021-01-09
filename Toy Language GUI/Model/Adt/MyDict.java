package Model.Adt;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MyDict<T1,T2> implements MyIDict<T1,T2> {
    private final Map<T1,T2> dictionary;
    public MyDict(){
        this.dictionary = new HashMap<>();
    }

    public MyDict(MyDict<T1,T2> another)
    {
        this.dictionary = another.dictionary;
    }

    @Override
    public void add(T1 key, T2 value) {
        dictionary.put(key,value);
    }

    @Override
    public void update(T1 key, T2 newvalue) {
        dictionary.replace(key,newvalue);
    }

    @Override
    public T2 lookup(T1 key) {
        return dictionary.get(key);
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("[");
        for(Map.Entry<T1,T2> entry : dictionary.entrySet()){
            if(str.length() != 1)
            {
                str.append(",");
            }
            str.append(entry.getKey()).append("->").append(entry.getValue());
        }
        str.append("]");
        return str.toString();


    }

    @Override
    public void delete(T1 id) {
        dictionary.remove(id);
    }

    @Override
    public String toString1() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for(Map.Entry<T1,T2> entry : dictionary.entrySet()){
            str.append("(").append(entry.getKey()).append(")");
        }
        str.append("]");
        return str.toString();
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }

    @Override
    public Set<Map.Entry<T1, T2>> entrySet() {
        return dictionary.entrySet();

    }

    @Override
    public Set<T1> getKeys() {
        return dictionary.keySet();
    }
}
