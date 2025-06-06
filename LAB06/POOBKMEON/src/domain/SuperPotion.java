package domain;
import java.io.*;
public class SuperPotion extends Potion implements Serializable{
    
    /**
     * Constructor for creating a new SuperPotion
     * 
     * @param name The name of the potion
     * @param description A description of what the potion does
     * @param newSuper The type of the potion, which determines its effectiveness
     */
    public SuperPotion(String name, String description, PotionType newSuper) {
        super(name, description, newSuper);
    }
    
    /**
     * Uses the SuperPotion on a Pokemon to enhance multiple stats.
     * Calls the parent class's useItem method and then increases attack, health points, and defense
     * by the amount specified in the potion's statistics.
     * 
     * @param pokemon The Pokemon to use the potion on
     * @throws PoobkemonException If the potion cannot be used on the Pokemon
     */
    public void useItem(Pokemon pokemon) throws PoobkemonException {
        super.useItem(pokemon);
        pokemon.gainAttack(statics.getValue());
        pokemon.gainPS(statics.getValue());
        pokemon.gainDefense(statics.getValue());
    }
}