package Model.Adt;

public interface MyIStack<T> {

    T pop();

    void push(T val);

    String toString();

    boolean isEmpty();

}
