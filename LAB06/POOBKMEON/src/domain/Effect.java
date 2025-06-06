package domain;

import java.io.Serializable;
public abstract class Effect implements Serializable{

    /** The name of the effect */
    protected String name;
    
    /** Description of what the effect does */
    protected String description;
    
    /** Number of turns the effect lasts */
    protected int times;

    /**
     * Constructor for creating a new Effect
     * 
     * @param newName The name of the effect
     * @param newDescription A description of what the effect does
     * @param newTimes The duration of the effect in turns
     */
    public Effect(String newName, String newDescription, int newTimes){
        name = newName;
        description = newDescription;
        times = newTimes;
    }

    /**
     * Abstract method that applies the effect to a Pokemon
     * Each subclass must implement this to define how the effect influences the Pokemon
     * 
     * @param affectPokemon The Pokemon to apply the effect to
     * @throws PoobkemonException If there is an issue applying the effect
     */
    public abstract void affectPokemon(Pokemon affectPokemon) throws PoobkemonException;
    
    /**
     * Decreases the remaining duration of this effect by one turn
     * Called at the end of each turn to track effect duration
     * 
     * @throws PoobkemonException EFFECT_DURATION_OVER if the effect has already expired
     */
    public void decrementDuration() throws PoobkemonException{
        if (times== 0) throw new PoobkemonException(PoobkemonException.EFFECT_DURATION_OVER);
        times--;
    }

    /**
     * Checks if the effect has expired (no more turns remaining)
     * 
     * @return true if the effect has no more turns remaining, false otherwise
     */
    public boolean isOver() {
        return times <= 0;
    }

    /**
     * Gets the name of this effect
     * 
     * @return The name of the effect
     */
    public String getName(){
        return name;
    }
}