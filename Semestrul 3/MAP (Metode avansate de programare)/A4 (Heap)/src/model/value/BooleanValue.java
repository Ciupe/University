package model.value;

import model.type.Type;
import model.type.BooleanType;

public class BooleanValue implements Value {

    boolean value;
    BooleanType type = new BooleanType();

    public BooleanValue(boolean value){

        this.value = value;
    }

    public BooleanValue(){
        this.value = false;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public Value deepCopy() {
        return new BooleanValue(value);
    }
}
