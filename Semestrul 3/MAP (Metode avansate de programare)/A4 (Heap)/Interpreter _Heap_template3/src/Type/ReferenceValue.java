package Type;

public final class ReferenceValue extends Value {

    private final int address;
    private final Type locationType;

    public ReferenceValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {return address;}

    public final int getAndIncrement(){return address;} //returns the value before increment operation is performed to the previous value.

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }

}
