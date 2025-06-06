package domain;
import java.io.*;

public class DefensePotion extends Potion implements Serializable {
    
    /**
     * Constructor for creating a new DefensePotion
     * 
     * @param name The name of the potion
     * @param description A description of what the potion does
     * @param newDefense The type of potion defining its effect properties
     */
    public DefensePotion(String name, String description, PotionType newDefense) {
        super(name, description, newDefense);
    }
    
    /**
     * Uses this DefensePotion on the specified Pokemon.
     * First calls the parent useItem method to perform the basic effect,
     * then increases the Pokemon's Attack stat by the potion's value.
     * 
     * @param pokemon The Pokemon to use the potion on
     * @throws PoobkemonException INVALID_POKEMON if the Pokemon is null,
     *                            ITEM_NOT_USABLE if the Pokemon is not alive
     */
    public void useItem(Pokemon pokemon) throws PoobkemonException{
        super.useItem(pokemon);
        pokemon.gainAttack(statics.getValue());
    }
}