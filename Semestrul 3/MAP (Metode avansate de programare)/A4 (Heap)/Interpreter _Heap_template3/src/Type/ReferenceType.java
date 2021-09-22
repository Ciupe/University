package Type;

import java.util.Objects;

public final class ReferenceType extends Type {

    private final Type inner;

    //constructor
    public ReferenceType(Type inner) {
        this.inner = inner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReferenceType) {
            return ((ReferenceType) obj).getInner().equals(this.inner);
        }

        return false;
    }

    /*
    public static ReferenceType (Type t, Type inner) {
        if (!(t instanceof ReferenceType)) {
            throw new TypeMismatch(t, new ReferenceType(inner));
        }

        ReferenceType refT = (ReferenceType) t;
        if (!Objects.equals(refT.getInner(), inner)) {
            throw new TypeMismatch(refT.getInner(), inner);
        }

        return refT;
    }
    */

    public Type getInner() {
        return inner;
    }
}
