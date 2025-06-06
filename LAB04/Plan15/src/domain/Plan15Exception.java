package domain;


/**
 * Write a description of class Plan15Exception here.
 *
 * @author (Julian Lopez , Sebastian Puentes)
 * @version 1.0
 */
public class Plan15Exception extends Exception
{
    public static String CREDITS_UNKNOWN = "Se desconocen los creditos";
    public static String CREDITS_ERROR = "El numero de creditos son Menores o Iguales a 0";
    public static String IN_PERSON_UNKNOWN = "No hay persona disponible";
    public static String IN_PERSON_ERROR = "El numero De Personas Son Menores o Iguales a 0";
    public static String INVALID_NAME = "Nombre Invalido";
    public static String IMPOSSIBLE = "Imposible Calcular";
    public static String INVALID_CODE_LENGTH = "Longitud Del Codigo Invalido";
    /**
     * Constructor for objects of class Plan15Exception
     */
    public Plan15Exception(String message)
    {
        super(message);
    }

}
