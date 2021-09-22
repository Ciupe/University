package model.adt;

public interface IMyDictionary<keyDataType, valueDataType> {

    void setValue (keyDataType key, valueDataType value);
    valueDataType getValue(keyDataType key);
    boolean isKey(keyDataType key);
    boolean isEmpty();
    void removeKey(keyDataType key);
}
