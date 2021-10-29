package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import adt.Pair;

public class SymbolTable {

    private Map<Integer, List<String>> hashTable;
    private int size = 0;

    public SymbolTable(){
        hashTable = new HashMap<>();
    }

    public void add (String newToken){
        int hashIndex = hash(newToken);

        if (hashTable.get(hashIndex) == null)
            hashTable.put(hashIndex, new ArrayList<>());

        hashTable.get(hashIndex).add(newToken);
    }

    public Pair findIndexInArray (String value){
        int hashIndex = hash(value);
        var array = hashTable.get(hashIndex);

        int arrayIndex = 0;
        while (arrayIndex < array.size()){
            if (array.get(arrayIndex) == value)
                return new Pair(hashIndex, arrayIndex);
            ++arrayIndex;
        }

        return null;
    }

    public Boolean contains (String value){
        int hashIndex = hash(value);
        var array = hashTable.get(hashIndex);

        int arrayIndex = 0;
        while (arrayIndex < array.size()){
            if (array.get(arrayIndex) == value)
                return true;
            ++arrayIndex;
        }

        return false;
    }

    public int size(){
        return this.size;
    }

    public List<String> getValue(int index){
        return hashTable.get(index);
    }

    public int hash (String token){
        int sum = token.chars().sum();
        if (sum > size)
            size = sum;
        return sum;
    }
}

