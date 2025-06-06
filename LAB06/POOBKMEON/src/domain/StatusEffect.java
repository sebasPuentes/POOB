package domain;
public class StatusEffect extends Effect{
    protected double probabilidad;

    /**
     * Constructor for creating a new StatusEffect
     * 
     * @param newName The name of the effect
     * @param newDescription A description of what the effect does
     * @param newTimes The duration of the effect in turns
     * @param prob The probability (percentage) that the effect will restrict actions each turn
     * 
     * Note: There appears to be an issue in the assignment. It should be 'probabilidad = prob' instead of 'prob = probabilidad'
     */
    public StatusEffect(String newName, String newDescription, int newTimes, double prob){
        super(newName,newDescription,newTimes);
        prob = probabilidad; // Note: This appears to be backwards, should be 'probabilidad = prob'
    }

    /**
     * Applies the status effect to a Pokemon.
     * Decrements the effect's duration and performs a random check based on the probability.
     * If the check succeeds, prevents the Pokemon from taking actions by throwing an exception.
     * If the check fails, removes the status effect from the Pokemon.
     * 
     * @param affectPokemon The Pokemon to apply the status effect to
     * @throws PoobkemonException POKEMON_CANT_INTERACT if the effect restricts movement
     */
    public void affectPokemon(Pokemon affectPokemon) throws PoobkemonException{
        decrementDuration();
        if (Math.random() * 100 <= probabilidad) {
            throw new PoobkemonException(PoobkemonException.POKEMON_CANT_INTERACT);
        }
        //que se termine el estado
        affectPokemon.removeStatusEffect();
    }
}