package model.adt;

import java.util.ArrayList;
//import java.util.Map;

import exception.MyException;

public class MyList<TElem> implements IMyList<TElem> {
    ArrayList<TElem> list;

    public MyList(){
        this.list = new ArrayList<>();
    }

    @Override
    public void add(TElem value) {
        list.add(value);
    }

    /*@Override
    public boolean removeByValue(TElem value) {
        return list.remove(value);
    }

    @Override
    public boolean removeByIndex(int index) {
        try {
            list.remove(index);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean setValue(int index, TElem value) {
        try {
            list.set(index, value);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    */
    @Override
    public TElem getValue(int index) throws MyException {
        if ( index > list.size() || index < 0 )
            throw new MyException("Index out of bounds!");
        return list.get(index);
    }

    @Override
    public TElem getFirst() {
        if (isEmpty())
            return null;
        return list.get(0);
    }

    @Override
    public ArrayList<TElem> getContent() {
        return this.list;
    }

    /*@Override
        public TElem getLast() {
            if (isEmpty())
                return null;
            return list.get(getSize()-1);
        }
        */
    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder output  = new StringBuilder();
        for (TElem element:this.list) {
            output.append(element.toString()).append("\n");
        }
        return output.toString();
    }

}
