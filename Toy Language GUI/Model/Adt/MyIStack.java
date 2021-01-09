package Model.Adt;

import java.util.List;
import java.util.Stack;

public interface MyIStack<T> {

    T pop();

    void push(T val);

    String toString();

    boolean isEmpty();

    Stack<T> getContent();

}
