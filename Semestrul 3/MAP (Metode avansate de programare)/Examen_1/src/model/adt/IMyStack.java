package model.adt;

import exception.MyException;

import java.util.ArrayList;

public interface IMyStack<TElem> {

    void push (TElem value);
    TElem pop() throws MyException;
    public TElem firstElement();

    ArrayList<TElem> getContent();

    boolean isEmpty();
    IMyStack deepCopy();
}
