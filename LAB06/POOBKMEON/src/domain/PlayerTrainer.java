package domain;

import java.awt.Color;
public class PlayerTrainer extends Trainer{


    /**
     * Constructor for creating a new PlayerTrainer
     * Initializes the trainer with the given name and color.
     * 
     * @param newName The name of the trainer
     * @param newColor The color associated with the trainer
     */
    public PlayerTrainer(String newName, Color newColor) {
        super(newName, newColor);
    }

    /**
     * Changes the currently active Pokemon to another Pokemon from the trainer's inventory.
     * 
     * @param newPokemon The Pokemon to switch to
     * @throws PoobkemonException POKEMON_DOESNT_EXIST_IN_THE_INVENTORY_OR_NOT_EXIST if the Pokemon
     *                           is not in the trainer's inventory
     */
    public void changePokemon(Pokemon newPokemon) throws PoobkemonException{
        if (!inventory.contains(newPokemon)) throw new PoobkemonException(PoobkemonException.POKEMON_DOESNT_EXIST_IN_THE_INVENTORY_OR_NOT_EXIST);
        if(!newPokemon.isAlive()) throw new PoobkemonException(PoobkemonException.POKEMON_DIE);
        setPokemonInUse(newPokemon);
    }
    
    /**
     * Changes the currently active Pokemon to another Pokemon from the trainer's inventory by name.
     * 
     * @param newPokemon The name of the Pokemon to switch to
     * @throws PoobkemonException POKEMON_DOESNT_EXIST_IN_THE_INVENTORY_OR_NOT_EXIST if no Pokemon
     *                           with the given name exists in the trainer's inventory
     */
    public void changePokemon(String newPokemon) throws PoobkemonException{
        changePokemon(getPokemonByName(newPokemon));
    }
    
    /**
     * Uses a specified item on the currently active Pokemon.
     * 
     * @param item The item to use
     * @throws PoobkemonException Various exceptions depending on the item and Pokemon state
     */
    public void useItem(Item item) throws PoobkemonException{
        System.out.println("LLEGA AL INVENTARIO: " + item.getName());
        inventory.useItem(actualPokemon, item);
    }
    
    /**
     * Uses an item by name on the currently active Pokemon.
     * 
     * @param item The name of the item to use
     * @throws PoobkemonException Various exceptions depending on the item and Pokemon state
     */
    public void useItem(String item) throws PoobkemonException{
        System.out.println("HOLA LLEGAS ACÁ ? STRING " + item);
        inventory.useItem(actualPokemon, item);
    }

    /**
     * Decision method for the player trainer.
     * This method is likely overridden from the parent class.
     * For PlayerTrainer, decisions are made by the human player, not by AI.
     * 
     * @param pokemon The Pokemon to make a decision for
     * @return A string indicating that decisions are made by the player
     */
    public String decide(Pokemon pokemon){
        return "Ya decidi";
    }
}