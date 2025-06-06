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
    public static String PORTAGE_ERROR = "El porcentaje no esta entre 0 y 100";
    public static String IN_PERSON_UNKNOWN = "No hay persona disponible";
    public static String IN_PERSON_ERROR = "El numero De Personas Son Menores o Iguales a 0";
    public static String INVALID_NAME = "Nombre Invalido";
    public static String IMPOSSIBLE = "Imposible Calcular";
    public static String INVALID_CODE_LENGTH = "Longitud Del Codigo Invalido";
    public static String INVALID_CREDITS = "Creditos u Horas No Son Enteros";
    public static String INVALID_INPERSON = "Horas Inconsistentes";
    public static String INVALID_NAMES = "Nombres Iguales. Deben Ser Diferentes.";
    /**
     * Constructor for objects of class Plan15Exception
     */
    public Plan15Exception(String message)
    {
        super(message);
    }

}
