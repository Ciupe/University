package model.adt;

import java.util.Map;

public interface IMyDictionary<keyDataType, valueDataType> {

    void setValue (keyDataType key, valueDataType value);
    valueDataType getValue(keyDataType key);
    boolean isKey(keyDataType key);
    boolean isEmpty();
    void removeKey(keyDataType key);
    Map<keyDataType, valueDataType> getContent();
}
