package domain;
public class PoobkemonException extends Exception {

    /** Error message when a Pokemon reference is invalid or null */
    public static final String INVALID_POKEMON = "THE POKEMON DOESN'T EXIST";

    /** Error message when an item does not exist in the game */
    public static final String ITEM_DONT_EXIST = "THE ITEM DOESN'T EXIST";

    /** Error message when an item cannot be used in the current context */
    public static final String ITEM_NOT_USABLE = "No se peude usar el item.";

    /** Error message when an item is not found in a trainer's inventory */
    public static final String ITEM_NOT_FOUND = "No se ha encontrado el ITEM.";

    /** Error message when a movement cannot be executed */
    public static final String INVALID_MOVEMENT = "no se puede hacer el movimiento.";

    /** Error message when a requested movement does not exist */
    public static final String MOVEMENT_NOT_FOUND = "no se ha encontrado el movimiento.";

    /** Error message when a Pokemon cannot be switched in battle */
    public static final String CANT_CHANGE_POKEMON = "no se puede cambiar el pokemon.";   

    /** Error message when parameter values are invalid */
    public static final String INVALID_VALUES = "valores invalidos";

    /** Error message when an effect is invalid or cannot be applied */
    public static final String INVALID_EFFECT = "effecto invalido";

    /** Error message when a movement misses its target */
    public static final String MISSED_MOVEMENT = "movimiento no realizado";

    /** Error message when a Pokemon cannot execute a movement */
    public static final String CANT_DO_MOVEMENT = "no puede hacer el movimiento";

    /** Error message when inventory capacity is exceeded */
    public static final String EXCESS_CAPACITY_OF_ITEMS = "no capacidad";

    /** Error message when a Pokemon is unable to take actions */
    public static final String POKEMON_CANT_INTERACT = "no puede actuar";

    /** Error message when a Pokemon is not found in the expected location */
    public static final String POKEMON_NOT_FOUND = "no se ha encontrado el pokemon";
    
    /** Error message when a Pokemon is not affected by a status effect */
    public static final String POKEMON_NOT_AFFECTED_BY_STATUS = "tiene un estado";

    /** Error message when a Pokemon does not have a status effect */
    public static final String NOT_STATUS_EFFECT = "no hay estado";

    /** Error message when an effect's duration has ended */
    public static final String EFFECT_DURATION_OVER = "Duración terminada del efecto";

    /** Error message when a movement cannot be added to a Pokemon */
    public static final String CANT_ADD_MOVEMENT = "no se puede añadir el movimiento";

    /** Error message when a trainer with the same name already exists */
    public static final String TRAINER_EXIST = "ya existe este trainer.";

    /** Error message when a trainer color is already in use */
    public static final String COLOR_EXIST = "ya existe este color";

    /** Error message when attempting to add a duplicate Pokemon to inventory */
    public static final String POKEMON_ALREADY_EXIST_IN_THE_INVENTORY = "el pokemon ya esta en el inventario y no se permite tener el mismo dos veces";

    /** Error message when a Pokemon is not found in the inventory */
    public static final String POKEMON_DOESNT_EXIST_IN_THE_INVENTORY_OR_NOT_EXIST = "EL POKEMON NO EXISTE O NO SE HA AGREGADO AL INVENTARIO";

    /** Error message when a revive item cannot be used on a Pokemon */
    public static final String THE_POKEMON_IS_ALIVE_OR_REVIVE_CANT_BE_USE = "El pokemon sigue vivo o el item de revivir ya fue usado";

    /** Error message when a tribute effect is invalid */
    public static final String INVALID_EFFECT_TRIBUTE_EFFECT = "effecto invalido Tribute EFFECT";

    /** Error message when a status effect is invalid */
    public static final String INVALID_EFFECT_STATUS_EFFECT = "effecto invalido status effect";
    
    /** Error message when a Pokemon has fainted */
    public static final String POKEMON_DIE = "El pokemon esta muerto";

    /** Error message when a Pokemon doesn't have the movement being used */
    public static final String POKEMON_DONT_HAVE_THESE_MOVEMENT = "El movimiento que se esta lanzando no lo tiene el pokemon asociado!";

    /** Error message when trying to add a movement that already exists */
    public static final String MOVEMENT_ALREADY_EXIST = "El movimiento ya existe en la lista de movimientos!";

    /** Error message when a revive item cannot be used */
    public static final String POKEMON_IS_ALIVE_OR_THE_REVIVED_ITEM_HAS_ALREADY_BEEN_USED = "El pokemon sigue vivo o el item de revivir ya ha sido usado";

    /** Error message when a move cannot be executed */
    public static final String CANT_DO_THE_MOVE = "El movimiento no puede hacerse";

    /**
     * Constructor for creating a new PoobkemonException with a specific error message.
     * 
     * @param message The error message describing what went wrong
     */
    public PoobkemonException(String message) {
        super(message);
    }
}