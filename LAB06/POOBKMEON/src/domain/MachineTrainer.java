package domain;
import java.awt.Color;
import java.util.*;
public abstract class MachineTrainer extends Trainer {
    
    /**
     * Constructor for creating a new MachineTrainer
     * 
     * @param name The name of the AI trainer
     * @param newColor The color associated with the trainer
     */
    public MachineTrainer(String name, Color newColor) {
        super(name, newColor);
    }

    /**
     * Abstract method that decides what action to take based on the opponent's Pokemon
     * To be implemented by subclasses with specific AI strategies
     * 
     * @param target The opponent's Pokemon
     * @return A string representing the decision made
     */
    public abstract String decide(Pokemon target);

    /**
     * Randomly chooses an alternative action when the primary strategy cannot be executed
     * May choose to use a move, an item, or change Pokemon
     * 
     * @param target The opponent's Pokemon
     */
    public void doOtherThen(Pokemon target) {
        Random random = new Random();
        int randomIndex = random.nextInt(4);
        if (randomIndex == 0) {
            Movement actual = null;
            try{
                actualPokemon.affectPokemonStatus();
                actual = pokemonMovementDecide(target);
                actual.doAttackTo(actualPokemon, target);
            }
            catch(PoobkemonException i){
                System.out.println("Fallo movimiento machine: "+ actual.getName()+" "+i.getMessage());            
            }
            
        }
        //if (randomIndex == 1) useItem();
        else {
            changePokemon();
        }
    }

    /**
     * Changes the current Pokemon (implementation ignores the parameter)
     * Delegates to the parameterless changePokemon method
     * 
     * @param pokemon The Pokemon to change to (ignored in this implementation)
     */
    public void changePokemon(Pokemon pokemon) { //mirar pues
        changePokemon();
    }
    
    /**
     * Changes the current Pokemon (implementation ignores the parameter)
     * Delegates to the parameterless changePokemon method
     * 
     * @param pokemon The name of the Pokemon to change to (ignored in this implementation)
     */
    public void changePokemon(String pokemon) {
        changePokemon();
    }
    
    /**
     * Sets the active Pokemon (overridden to use random selection instead)
     * Delegates to the parameterless changePokemon method
     * 
     * @param pokemonNew The name of the Pokemon to set as active (ignored in this implementation)
     * @throws PoobkemonException If there is an issue changing the Pokemon
     */
    @Override
    public void setPokemonInUse(String pokemonNew) throws PoobkemonException{
        changePokemon();
    }

    /**
     * Randomly selects an alive Pokemon from the trainer's inventory and sets it as the active Pokemon
     */
    public void changePokemon() {
        ArrayList<Pokemon> stillAlive = inventory.getAlivePokemons();
        Random random = new Random();
        int choicesToPick = random.nextInt(stillAlive.size());
        actualPokemon = stillAlive.get(choicesToPick);
    }

    /**
     * Uses an item by name (stub implementation)
     * Delegates to the parameterless useItem method
     * 
     * @param item The name of the item to use (ignored in this implementation)
     */
    public void useItem(String item) {
        useItem();
    }

    /**
     * Uses a specified item (stub implementation)
     * Delegates to the parameterless useItem method
     * 
     * @param item The item to use (ignored in this implementation)
     */
    public void useItem(Item item) {
        useItem();
    }

    /**
     * Placeholder method for using an item
     * Empty implementation to be overridden by subclasses if needed
     */
    public void useItem() {
    }

    /**
     * Executes a movement against a target Pokemon
     * Note: The implementation is commented out
     * 
     * @param mov The movement to execute
     * @param target The target Pokemon
     * @throws PoobkemonException If there is an issue executing the movement
     */
    @Override
    public void pokemonMovement(Movement mov, Pokemon target) throws PoobkemonException {
        //actualPokemon.useMovement(mov, target);
    }
    @Override
    public void pokemonMovement(String mov, Pokemon target) throws PoobkemonException{
        
    }

    /**
     * Selects a random movement for the active Pokemon to use against the target
     * 
     * @param target The target Pokemon
     * @return The randomly selected movement
     */
    public Movement pokemonMovementDecide(Pokemon target) {
        return actualPokemon.aleatoryMovement(target);
    }

    /**
     * Sets the initial Pokemon (implementation ignores the parameter)
     * Delegates to the parameterless inicialPokemon method
     * 
     * @param pokemon The name of the Pokemon to set as initial (ignored in this implementation)
     */
    @Override
    public void inicialPokemon(String pokemon) {
        inicialPokemon();
    }
    
    /**
     * Sets the first Pokemon in the inventory as the active Pokemon
     */
    public void inicialPokemon() {
        String temp = inventory.getPokemons().firstKey();
        actualPokemon = inventory.getPokemons().get(temp);
    }
}