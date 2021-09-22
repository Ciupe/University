package Type;

public abstract class Type {

    public static boolean equals(Value left, Value right) {
        return left.getType().equals(right.getType());
    }
}
