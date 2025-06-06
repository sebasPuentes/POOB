package domain;
public class DMaxwellExceptions extends Exception{
    public static final String ONLY_POSITIVE_DIMENTIONS = "Dimensiones No Enteras";
    public DMaxwellExceptions(String message){
        super(message);
    }
}

