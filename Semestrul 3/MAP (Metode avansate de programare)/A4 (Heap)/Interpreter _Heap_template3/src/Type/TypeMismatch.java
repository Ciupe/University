package Type;

public class TypeMismatch extends RuntimeException {

    public TypeMismatch(Type found, Type expected) {
        super("Found type " + found + " while expecting " + expected);
    }
}
