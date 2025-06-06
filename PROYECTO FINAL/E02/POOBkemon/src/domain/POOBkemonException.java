package src.domain;
public class POOBkemonException extends RuntimeException {
   public static final String INVALID_MOVEMENT = "Invalid Movement" ;
   public static final String INVALID_VALUES = "Invalid Values" ;
   public static final String DURATION_OVER = "Duration Over" ;
    public POOBkemonException(String message) {
        super(message);
    }
}