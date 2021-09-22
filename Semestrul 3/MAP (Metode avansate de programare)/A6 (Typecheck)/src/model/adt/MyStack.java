package model.adt;

import exception.MyException;
import java.util.Stack;

public class MyStack<TElem> implements IMyStack<TElem> {

    Stack<TElem> stack;

    public MyStack(){
        this.stack = new Stack<>();
    }

    public MyStack(Stack<TElem> givenStack){
        this.stack = givenStack;
    }

    @Override
    public void push(TElem value) {
        stack.push(value);
    }

    @Override
    public TElem pop() throws MyException{
        if (stack.isEmpty())
            throw new MyException("Stack is empty!");
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public MyStack deepCopy() {
        return new MyStack(this.stack);
    }

    @Override
    public String toString() {
        StringBuilder output  = new StringBuilder();
        for (TElem element:this.stack) {
            output.append(element.toString()).append(" ");
        }
        return output.toString();
    }
}
