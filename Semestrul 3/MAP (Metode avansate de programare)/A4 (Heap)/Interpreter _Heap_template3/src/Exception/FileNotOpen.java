package Exception;

public class FileNotOpen extends RuntimeException {
    public FileNotOpen(String filename) {
        super("File " + filename + " is not open.");
    }
}
