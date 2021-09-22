package model.type;

import model.value.ReferenceValue;
import model.value.Value;

import java.util.Objects;

public class ReferenceType implements Type{
    private Type innerType;

    public ReferenceType(Type innerType) {
        this.innerType = innerType;
    }

    public Type getInnerType() {
        return innerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferenceType refType = (ReferenceType) o;
        return Objects.equals(innerType, refType.innerType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(innerType);
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(innerType, 0);
    }

    @Override
    public Type deepCopy() {
        return new ReferenceType(innerType.deepCopy());
    }


    @Override
    public String toString() {
        return "Ref(" + innerType + ")";
    }
}
