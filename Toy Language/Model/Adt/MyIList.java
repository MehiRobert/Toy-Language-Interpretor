package Model.Adt;

public interface MyIList<T> {

    void add(T val);

    void remove(T Val);

    int getSize();

    T getElement(int i);

}
