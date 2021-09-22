package model.value;
import model.type.Type;
import model.type.StringType;

public class StringValue implements Value {

    String value;
    StringType type = new StringType();

    public StringValue(String value){
        this.value = value;
    }

    public StringValue(){
        this.value = "";
    }

    @Override
    public Type getType() {
        return this.type;
    }

    public void setValue(String newValue) {
        this.value = newValue;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
