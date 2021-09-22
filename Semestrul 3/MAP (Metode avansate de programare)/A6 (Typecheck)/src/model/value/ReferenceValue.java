package model.value;

import model.type.ReferenceType;
import model.type.Type;

public class ReferenceValue implements Value{
    private Type locationType;
    private int address;

    public ReferenceValue(Type locationType, int address) {
        this.locationType = locationType;
        this.address = address;
    }

    public Type getLocationType() {
        return locationType;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new ReferenceValue(locationType.deepCopy(), address);
    }


    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }
}
