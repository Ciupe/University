package model.type;

import model.value.Value;

public interface Type {

    boolean equals (Object object);
    Value defaultValue();
    Type deepCopy();
}
