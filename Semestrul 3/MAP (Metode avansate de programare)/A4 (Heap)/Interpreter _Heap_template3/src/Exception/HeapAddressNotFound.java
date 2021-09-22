package Exception;

public class HeapAddressNotFound extends RuntimeException {
    public HeapAddressNotFound(Integer address) {
        super("Heap address " + address + " not found");
    }
}