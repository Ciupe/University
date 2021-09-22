package model.adt;

import java.util.HashMap;
import java.util.Map;

public interface IMyDictionary<keyDataType, valueDataType> {

    void setValue (keyDataType key, valueDataType value);
    valueDataType lookup(keyDataType key);
    boolean isKey(keyDataType key);
    boolean isEmpty();
    void removeKey(keyDataType key);

    void setContent(HashMap<keyDataType, valueDataType> content);
    Map<keyDataType, valueDataType> getContent();

    IMyDictionary<keyDataType, valueDataType> deepCopy();
}
