package Model.Adt;

import java.util.Map;

public interface MyIDict<T1,T2> {

    void add(T1 val1,T2 val2);
    void update(T1 val1,T2 val2);

    T2 lookup(T1 key);

    boolean isDefined(T1 id);

    String toString();

    void delete(T1 id);

    String toString1();

    Map<T1,T2> getContent();



}
