package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type {

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof StringType);
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
