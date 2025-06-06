package domain;
public class StatusMovil extends StatusEffect{
    private TributeEffect tribute;

    /**
     * Constructor for creating a new StatusMovil effect
     * 
     * @param newName The name of the status effect
     * @param newDescription A description of what the status effect does
     * @param newTimes The duration of the effect in turns
     * @param newTribute The tribute effect to apply along with this status
     * @param prob The probability (percentage) that the effect will be applied each turn
     */
    public StatusMovil(String newName, String newDescription, int newTimes, TributeEffect newTribute, double prob){
        super(newName,newDescription,newTimes,prob);
        tribute = newTribute;
    }
    
    /**
     * Applies the status effect to a Pokemon with a chance-based result.
     * If the random check succeeds (based on the probability), applies the tribute effect
     * and prevents the Pokemon from taking actions.
     * In either case, decrements the duration of both the status effect and the tribute effect.
     * 
     * @param pokemon The Pokemon to apply the status effect to
     * @throws PoobkemonException POKEMON_CANT_INTERACT if the effect restricts movement,
     *                           POKEMON_NOT_AFFECTED_BY_STATUS if the effect doesn't restrict movement
     */
    @Override
    public void affectPokemon(Pokemon pokemon) throws PoobkemonException{
        if (Math.random() * 100 <= probabilidad) {
            decrementDuration();
            tribute.affectPokemon(pokemon);
            throw new PoobkemonException(PoobkemonException.POKEMON_CANT_INTERACT);
        }
        decrementDuration();
        tribute.decrementDuration();
        throw new PoobkemonException(PoobkemonException.POKEMON_NOT_AFFECTED_BY_STATUS);
    }
}