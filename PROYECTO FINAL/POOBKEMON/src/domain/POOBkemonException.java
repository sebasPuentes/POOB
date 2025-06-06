package domain;
public class POOBkemonException extends Exception {
    public static final String INVALID_MOVEMENT = "Invalid Movement" ;
    public static final String INVALID_POKEMON = "Invalid Pokemon" ;
    public static final String INVALID_VALUES = "Invalid Values" ;
    public static final String EXISTING_TRAINER= "Existing Trainer" ;
    public static final String DURATION_OVER = "Duration Over" ;
    public static final String INVALID_ITEM = "Invalid Item" ;
    public static final String INVALID_EFFECT = "Invalid Pokemon Type" ;
    public static final String DEAD_POKEMON = "Dead Pokemon" ;
    public static final String POKEMON_HAS_EFFECT = "Pokemon has effect" ;
    public static final String FROZEN = "Frozzed" ;
    public static final String PARALYZED = "Paralyzed" ;
    public static final String CONFUSED = "Confused" ;
    public static final String EMPTY_INVENTORY = "Empty Inventory" ;
    public static final String EFFECT_DURATION_OVER = "Effect Duration Over" ;
    public static final String INVALID_ITEM_TO_USE = "Invalid Item to use" ;
    public POOBkemonException(String message) {
        super(message);
    }
}