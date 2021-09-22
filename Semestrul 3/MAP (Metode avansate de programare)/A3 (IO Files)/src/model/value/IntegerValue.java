package model.value;

import model.type.Type;
import model.type.IntegerType;

public class IntegerValue implements Value {

    int value;
    IntegerType type = new IntegerType();

    public IntegerValue(int value){
        this.value = value;
    }

    public IntegerValue(){
        this.value = 0;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public void setValue (int newValue){
        this.value = newValue;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
