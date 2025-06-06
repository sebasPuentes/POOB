package domain;
import java.io.*;
public class PsPotion extends Potion implements Serializable{ 
    /**
     * Constructor for creating a new PsPotion
     * 
     * @param name The name of the potion
     * @param description A description of what the potion does
     * @param newPs The type of the potion, which determines its healing effectiveness
     */
    public PsPotion(String name, String description, PotionType newPs) {
        super(name, description, newPs);
    }

    /**
     * Uses the PsPotion on a Pokemon to restore its health points.
     * First calls the parent class's useItem method for validation,
     * then increases the Pokemon's PS (health points) by the amount specified
     * in the potion's statistics.
     * 
     * @param pokemon The Pokemon to use the potion on
     * @throws PoobkemonException If the potion cannot be used on the Pokemon
     *         (e.g., if the Pokemon is fainted or the potion is invalid)
     */
    public void useItem(Pokemon pokemon) throws PoobkemonException{
        super.useItem(pokemon);
        pokemon.gainPS(statics.getValue());
    }
}