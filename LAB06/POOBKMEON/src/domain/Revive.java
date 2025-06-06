package domain;

import java.io.Serializable;
public class Revive extends Item{
    private final double recover;
    
    /**
     * Constructor for creating a new Revive item.
     * Initializes the item with the name "revive" and a description.
     * Sets the recovery percentage to 50% of the Pokemon's maximum health.
     */
    public Revive() {
        super("revive","Recupera 50% de la salud de un Pokémon caído en combate.");
        recover = 0.5;
    }

    /**
     * Uses the Revive item on a fainted Pokemon to restore it to battle.
     * The Pokemon will be revived with 50% of its maximum health points.
     * 
     * @param pokemon The Pokemon to use the Revive item on
     * @throws PoobkemonException INVALID_POKEMON if the pokemon is null,
     *                           POKEMON_IS_ALIVE_OR_THE_REVIVED_ITEM_HAS_ALREADY_BEEN_USED if the Pokemon
     *                           is already alive or has already been revived once in this battle
     */
    @Override
    public void useItem(Pokemon pokemon) throws PoobkemonException { 
        if(pokemon == null) throw new PoobkemonException(PoobkemonException.INVALID_POKEMON);
        if(pokemon.isAlive() || pokemon.haveUsedReviveItem()) throw new PoobkemonException(PoobkemonException.POKEMON_IS_ALIVE_OR_THE_REVIVED_ITEM_HAS_ALREADY_BEEN_USED);
        pokemon.revivedByItem(recover);
    }
}