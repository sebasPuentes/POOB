package domain;

public class CityException extends RuntimeException {
    public static String FILE_NOT_FOUND = "File was not found";
    public static String IMPORT_ERROR = "Error importing file";
    public CityException(String message) {
        super(message);
    }
}
