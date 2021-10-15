import java.util.ArrayList;

public class SymbolTable {

    private int capacity = 100;
    private int size;

    private ArrayList<String> tokenArray;

    public SymbolTable(){
        this.size = 0;
        tokenArray = new ArrayList<String>(capacity);
    }

    public void add (Token newToken){
        if (size >= capacity)
            rehashTable();

        int hashPosition = hash(newToken);

        if (tokenArray.get(hashPosition) != null){
            tokenArray.add(hashPosition, newToken.getValue());
        }
        else
        {
            while (tokenArray.get(hashPosition) != null){
                ++hashPosition;
                if (hashPosition >= capacity)
                    hashPosition = 0;
            }
        }

        size++;
    }

    public int findIndex (String value){
        int index = 0;
        while (index < capacity){
            if (tokenArray.get(index) == value)
                return index;
            ++index;
        }
        return -1;
    }

    public Boolean contains (String value){
        int index = 0;
        while (index < capacity){
            if (tokenArray.get(index) == value)
                return true;
            ++index;
        }
        return false;
    }

    public void rehashTable(){
        return;
    }

    public int hash (Token token){
        return token.getKey() % capacity;
    }
}