public class Token {

    private int key;
    private String value;

    public Token(int key, String value){
        this.key = key;
        this.value = value;
    }

    public int getKey(){
        return this.key;
    }

    public String getValue(){
        return this.value;
    }
}
