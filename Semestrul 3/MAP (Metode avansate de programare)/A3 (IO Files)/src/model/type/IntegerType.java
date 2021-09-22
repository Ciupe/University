package model.type;

import model.value.IntegerValue;
import model.value.Value;

public class IntegerType implements Type {

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof IntegerType);
    }

    @Override
    public Value defaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public String toString() {
        return "int";
    }
}
