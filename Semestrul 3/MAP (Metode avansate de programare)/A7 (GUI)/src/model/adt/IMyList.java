package model.adt;

import exception.MyException;

import java.util.ArrayList;

public interface IMyList<TElem>{

    void add(TElem value);

    //boolean removeByValue (TElem value);
    //boolean removeByIndex (int index);

    //boolean setValue (int index, TElem value);
    TElem getValue (int index) throws MyException;

    TElem getFirst();
    //TElem getLast();

    ArrayList<TElem> getContent();

    int getSize();
    boolean isEmpty();

}
