package domain;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * TributeEffect class represents effects that modify a Pokemon's stats.
 * These effects can change multiple stats simultaneously and have a limited duration.
 * Extends the base Effect class.
 */
public class TributeEffect extends Effect{
    protected HashMap<String,Integer> stateTo = new HashMap<>();

    /**
     * Constructor for creating a new TributeEffect without initial stat modifications
     * 
     * @param newName The name of the effect
     * @param newDescription A description of what the effect does
     * @param newTimes The duration of the effect in turns
     */
    public TributeEffect(String newName, String newDescription, int newTimes){
        super(newName,newDescription,newTimes);
    }

    /**
     * Constructor for creating a new TributeEffect with initial stat modifications
     * 
     * @param newName The name of the effect
     * @param newDescription A description of what the effect does
     * @param newTimes The duration of the effect in turns
     * @param tributes A HashMap mapping stat names to their modification values
     */
    public TributeEffect(String newName, String newDescription, int newTimes,HashMap<String,Integer> tributes){
        super(newName, newDescription, newTimes);
        stateTo = tributes;
    }

    /**
     * Gets the map of stats and their modification values
     * 
     * @return HashMap containing the stat names as keys and modification values as values
     */
    public HashMap<String,Integer> getStateTo(){
        return stateTo;
    }

    /**
     * Applies the stat modifications to a Pokemon
     * For each stat in the stateTo map, increases the corresponding stat by the specified amount.
     * Also decrements the duration of the effect.
     * 
     * @param affectPokemon The Pokemon to apply the stat modifications to
     * @throws PoobkemonException If a stat modification cannot be applied
     */
    public void affectPokemon(Pokemon affectPokemon) throws PoobkemonException{
        for (Map.Entry<String, Integer> entry : stateTo.entrySet()) {
            String stat = entry.getKey();
            int amount = entry.getValue();
            affectPokemon.increaseStat(stat, amount);
        }
        decrementDuration();
    }
}