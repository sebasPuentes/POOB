package domain;
public class DMaxwellExceptions extends Exception{
    public static final String INVALID_MOVEMENT = "";
    public static final String ONLY_POSITIVE_DIMENTIONS = "";
    public static final String INVALID_TREATMENT = "";
    public static final String UNABLE_ADD_TREATMENT = "";
    public DMaxwellExceptions(String message){
        super(message);
    }
}

