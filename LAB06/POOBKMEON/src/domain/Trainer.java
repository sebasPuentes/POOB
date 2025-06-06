package domain;

import java.awt.Color;
import java.io.Serializable;
public abstract class Trainer implements Serializable{
    protected String name;
    protected Inventory inventory;
    protected Color color; //mirar despues
    protected Pokemon actualPokemon;

    /**
     * Constructor for creating a new Trainer
     * 
     * @param newName The name of the trainer
     * @param newColor The color associated with the trainer
     */
    public Trainer(String newName, Color newColor) {
        name = newName;   
        color = newColor;
        inventory = new Inventory();
        
    }

    /**
     * Gets the inventory of the trainer
     * 
     * @return The trainer's inventory
     */
    public Inventory getInventory(){
        return inventory;
    }
    
    /**
     * Gets the name of the trainer
     * 
     * @return The trainer's name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gets the color associated with the trainer
     * 
     * @return The trainer's color
     */
    public Color getColor(){
        return color;
    }
    
    /**
     * Gets the current Pokemon in use by the trainer
     * If the current Pokemon is null or not alive, tries to get the first alive Pokemon
     * from the inventory
     * 
     * @return The Pokemon currently in use, or null if no alive Pokemon is available
     */
    public Pokemon getPokemonInUse(){
        if (actualPokemon != null && actualPokemon.isAlive()){
            return actualPokemon;
        }

        if (inventory != null && !inventory.getAlivePokemons().isEmpty()){
            actualPokemon = inventory.getAlivePokemons().get(0);
            return actualPokemon;
        }

        return null; //CAMBIADO
    }
    
    /**
     * Checks if the trainer can still participate in battle
     * (has at least one Pokemon that can fight)
     * 
     * @return true if the trainer can still fight, false otherwise
     */
    public boolean canStillFighting(){
        return inventory.canStillFighting();
    }

    /**
     * Commands the current Pokemon to use a specific movement on a target
     * 
     * @param mov The Movement object to use
     * @param target The target Pokemon
     * @throws PoobkemonException If the movement cannot be performed
     */
    public void pokemonMovement(Movement mov, Pokemon target) throws PoobkemonException{ //ya no se necesita?
        actualPokemon.useMovement(mov, target);
    }
    
    /**
     * Commands the current Pokemon to use a movement by name on a target
     * 
     * @param mov The name of the movement to use
     * @param target The target Pokemon
     * @throws PoobkemonException If the movement is not found or cannot be performed
     */
    public void pokemonMovement(String mov, Pokemon target) throws PoobkemonException{
        actualPokemon.useMovement(mov, target);
    }

    /**
     * Changes the current Pokemon to a new one by name
     * 
     * @param newPokemon The name of the Pokemon to switch to
     * @throws PoobkemonException If the Pokemon cannot be changed
     */
    public abstract void changePokemon(String newPokemon) throws PoobkemonException;

    /**
     * Changes the current Pokemon to a new one
     * 
     * @param newPokemon The Pokemon object to switch to
     * @throws PoobkemonException If the Pokemon cannot be changed
     */
    public abstract void changePokemon(Pokemon newPokemon) throws PoobkemonException;
    
    /**
     * Uses an item from the inventory
     * 
     * @param item The Item object to use
     * @throws PoobkemonException If the item cannot be used
     */
    public abstract void useItem(Item item) throws PoobkemonException;

    /**
     * Uses an item from the inventory by name
     * 
     * @param item The name of the item to use
     * @throws PoobkemonException If the item is not found or cannot be used
     */
    public abstract void useItem(String item) throws PoobkemonException;
    
    /**
     * Makes a decision for the trainer based on the opponent's Pokemon
     * 
     * @param pok The opponent's Pokemon
     * @return A string representing the decision made
     */
    public abstract String decide(Pokemon pok);

    /**
     * Compares this Trainer with another object for equality
     * 
     * @param ob The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object ob){
        return equals((Trainer) ob);
    }

    /**
     * Compares this Trainer with another Trainer for equality
     * 
     * @param trainer The Trainer to compare with
     * @return true if the trainers are equal, false otherwise
     */
    public boolean equals(Trainer trainer){
        return name.equals(trainer.getName()) && color.equals(trainer.getColor());
    }
    
    /**
     * Sets the initial Pokemon for the trainer
     * 
     * @param pokemon The name of the Pokemon to set as initial
     */
    public void inicialPokemon(String pokemon){
        actualPokemon = inventory.getPokemons().get(pokemon);
    }

    /**
     * Adds a new Pokemon to the trainer's inventory
     * 
     * @param pokemon The Pokemon to add
     * @throws PoobkemonException If the Pokemon cannot be added
     */
    public void addPokemon(Pokemon pokemon) throws PoobkemonException{
        inventory.addPokemon(pokemon);
    }
    
    /**
     * Sets a new inventory for the trainer
     * 
     * @param newInventory The new Inventory object
     */
    public void setInventory(Inventory newInventory){
        inventory = newInventory;
    }

    /**
     * Sets the Pokemon currently in use by the trainer
     * 
     * @param pokemonNew The Pokemon to set as currently in use
     * @throws PoobkemonException If the Pokemon is not alive
     */
    public void setPokemonInUse(Pokemon pokemonNew) throws PoobkemonException{
        if(! pokemonNew.isAlive())throw new PoobkemonException(PoobkemonException.CANT_CHANGE_POKEMON);
        actualPokemon = pokemonNew;//actualPokemon == null &&        
    }
    
    /**
     * Sets the Pokemon currently in use by the trainer by name
     * 
     * @param pokemonNew The name of the Pokemon to set as currently in use
     * @throws PoobkemonException If the Pokemon is not found or not alive
     */
    public void setPokemonInUse(String pokemonNew) throws PoobkemonException{
        setPokemonInUse(getPokemonByName(pokemonNew));
    }
    
    /**
     * Gets a Pokemon from the trainer's inventory by name
     * 
     * @param name The name of the Pokemon to retrieve
     * @return The Pokemon object with the specified name
     * @throws PoobkemonException If the Pokemon is not found
     */
    public Pokemon getPokemonByName(String name) throws PoobkemonException{
        return inventory.getPokemonByName(name);
    }
}