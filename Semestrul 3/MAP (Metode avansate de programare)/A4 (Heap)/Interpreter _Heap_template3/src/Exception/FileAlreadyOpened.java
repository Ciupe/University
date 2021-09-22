package Exception;

public class FileAlreadyOpened extends RuntimeException {
    public FileAlreadyOpened(String filename) {
        super("File " + filename + " is already opened.");
    }
}
