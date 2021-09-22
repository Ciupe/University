package model.adt;

import exception.MyException;

public interface IMyStack<TElem> {

    void push (TElem value);
    TElem pop() throws MyException;

    boolean isEmpty();
}
