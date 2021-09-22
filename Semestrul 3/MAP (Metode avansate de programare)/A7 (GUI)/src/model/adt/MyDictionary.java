package model.adt;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<keyDataType, valueDataType> implements IMyDictionary<keyDataType, valueDataType> {
    HashMap<keyDataType, valueDataType> dictionary;

    public MyDictionary(){
        //System.out.println("owo");
        this.dictionary = new HashMap<>();
    }

    @Override
    public void setValue(keyDataType key, valueDataType value) {
        dictionary.put(key, value);
    }

    @Override
    public valueDataType lookup(keyDataType key) {
        return dictionary.get(key);
    }

    @Override
    public boolean isKey(keyDataType key) {
        return dictionary.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public void removeKey(keyDataType key) {
        if (isKey(key))
            dictionary.remove(key);
    }

    @Override
    public void setContent(HashMap<keyDataType, valueDataType> content) {
        this.dictionary = content;
    }

    @Override
    public Map<keyDataType, valueDataType> getContent() {
        return this.dictionary;
    }

    @Override
    public String toString() {
        StringBuilder sb  = new StringBuilder();
        for (Map.Entry<keyDataType, valueDataType> e : dictionary.entrySet()) {
            sb.append(e.getKey()).append(" --> ").append(e.getValue()).append("\n");
        }
        if (sb.length() != 0){
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public IMyDictionary<keyDataType, valueDataType> deepCopy() {
        HashMap<keyDataType, valueDataType> newMap = new HashMap<keyDataType, valueDataType>(dictionary);
        MyDictionary<keyDataType, valueDataType> newDictionary = new MyDictionary<keyDataType, valueDataType>();
        newDictionary.setContent(newMap);
        return newDictionary;
    }
}
