package Model.Adt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private final Stack<T> stack;

    public MyStack()
    {
        this.stack = new Stack<T>();
    }
    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T val) {
        stack.push(val);
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(T el : stack)
        {
            str.append(el).append(" ");
        }
        return str.toString();
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public Stack<T> getContent() {
        return stack;
    }


}
