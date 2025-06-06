package domain;

import java.io.Serializable;
import java.util.*;
public class Inventory implements Serializable{
    /** Maximum number of items that can be stored in the inventory */
    private final int capacityOfItems = 20;
    
    /** Map of items and their quantities in the inventory */
    private HashMap<Item, Integer> items = new HashMap<>();
    
    /** Ordered map of Pokemon in the inventory, keyed by name */
    private TreeMap<String, Pokemon> pokemons = new TreeMap<>();

    /**
     * Constructor for creating a new empty Inventory
     */
    public Inventory() {
    }

    /**
     * Adds an item to the inventory
     * Each item can only be held up to 2 times
     * 
     * @param item The item to add to the inventory
     * @throws PoobkemonException EXCESS_CAPACITY_OF_ITEMS if the inventory is full or
     *                           if already have 2 of the same item
     */
    public void addItem(Item item) throws PoobkemonException{
        //if (items.containsKey(item)) throw new PoobkemonException(PoobkemonException.INVALID_ITEM);
        if (items.size() > capacityOfItems) throw new PoobkemonException(PoobkemonException.EXCESS_CAPACITY_OF_ITEMS);
        if (items.containsKey(item) && countItems(item) >= 2) throw new PoobkemonException(PoobkemonException.EXCESS_CAPACITY_OF_ITEMS);
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

    /**
     * Counts how many of a specific item are in the inventory
     * 
     * @param item The item to count
     * @return The quantity of the specified item
     * @throws PoobkemonException ITEM_DONT_EXIST if the item is not in the inventory
     */
    public int countItems(Item item) throws PoobkemonException{
        if (!items.containsKey(item)) throw new PoobkemonException(PoobkemonException.ITEM_DONT_EXIST);
        return items.get(item);
    }

    /**
     * Removes one of the specified item from the inventory
     * Private helper method used when items are consumed
     * 
     * @param item The item to remove
     * @throws PoobkemonException ITEM_DONT_EXIST if the item is not in the inventory or quantity is zero
     */
    private void delItem(Item item) throws PoobkemonException{
        if (!items.containsKey(item)) throw new PoobkemonException(PoobkemonException.ITEM_DONT_EXIST);
        Integer count = items.get(item);
        if (count <= 0) throw new PoobkemonException(PoobkemonException.ITEM_DONT_EXIST);
        if (count == 1) {
            items.remove(item); 
        } else {
            items.put(item, count - 1);
        }
    }

    /**
     * Adds a Pokemon to the inventory
     * A Pokemon can only be added once to the inventory
     * 
     * @param pokemon The Pokemon to add
     * @throws PoobkemonException POKEMON_ALREADY_EXIST_IN_THE_INVENTORY if the Pokemon is already in the inventory
     */
    public void addPokemon(Pokemon pokemon) throws PoobkemonException{
        if (pokemons.containsValue(pokemon)) throw new PoobkemonException(PoobkemonException.POKEMON_ALREADY_EXIST_IN_THE_INVENTORY);
        pokemons.put(pokemon.getName(), pokemon);
    }
    
    /**
     * Checks if a specific Pokemon is in the inventory
     * 
     * @param pokemon The Pokemon to check for
     * @return true if the Pokemon is in the inventory, false otherwise
     */
    public boolean contains(Pokemon pokemon){
        return pokemons.containsKey(pokemon.getName());
    }
    
    /**
     * Checks if a specific item is in the inventory
     * 
     * @param item The item to check for
     * @return true if the item is in the inventory, false otherwise
     */
    public boolean contains(Item item){
        return items.containsKey(item);
    }
    
    /**
     * Retrieves a Pokemon from the inventory by name
     * 
     * @param name The name of the Pokemon to retrieve
     * @return The Pokemon with the specified name
     * @throws PoobkemonException POKEMON_NOT_FOUND if no Pokemon with that name exists in the inventory
     */
    public Pokemon getPokemonByName(String name) throws PoobkemonException{
        for (Map.Entry<String, Pokemon> entry :pokemons.entrySet()){
            if (name.equals(entry.getKey())) return entry.getValue();
        }
        throw new PoobkemonException(PoobkemonException.POKEMON_NOT_FOUND);
    }
    
    /**
     * Retrieves an item from the inventory by name
     * 
     * @param name The name of the item to retrieve
     * @return The item with the specified name
     * @throws PoobkemonException ITEM_NOT_FOUND if no item with that name exists in the inventory
     */
    public Item getItemByName(String name) throws PoobkemonException{
        for (Item i : items.keySet()){
            System.out.println(i.getName() + "quien eres?");
        }
        for (Item i : items.keySet()){
            if (name.equals(i.getName())) return i;
        }
        throw new PoobkemonException(PoobkemonException.ITEM_NOT_FOUND);
    }

    /**
     * Gets all Pokemon in the inventory
     * 
     * @return A TreeMap of all Pokemon, keyed by their names
     */
    public TreeMap<String,Pokemon> getPokemons(){
        return pokemons;
    }
    
    /**
     * Checks if there are any Pokemon that can still battle
     * 
     * @return true if there is at least one alive Pokemon, false otherwise
     */
    public boolean canStillFighting(){ // prodria tambien verificar si no toiene revivir
        return getAlivePokemons().size() > 0;
    }
    
    /**
     * Gets a list of all alive Pokemon in the inventory
     * 
     * @return An ArrayList containing all alive Pokemon
     */
    public ArrayList<Pokemon> getAlivePokemons(){
        ArrayList<Pokemon> poke= new ArrayList<Pokemon>();
        for(Pokemon p: pokemons.values()){
            if(p.isAlive()) poke.add(p);
        }
        return poke;
    }

    /**
     * Gets a list of all alive Pokemon except the specified one
     * Useful for selecting a Pokemon to switch to
     * 
     * @param pok The Pokemon to exclude from the list
     * @return An ArrayList containing all alive Pokemon except the specified one
     */
    public ArrayList<Pokemon> getAlivePokemons(Pokemon pok){
        ArrayList<Pokemon> poke= new ArrayList<Pokemon>();
        for(Pokemon p: pokemons.values()){
            if(p.isAlive() && !p.equals(pok)) poke.add(p);
        }
        return poke;
    }

    /**
     * Checks if a Pokemon can be switched to during battle
     * A Pokemon can be changed to if it's alive and in the inventory
     * 
     * @param pokemon The Pokemon to check
     * @return true if the Pokemon is alive and in the inventory, false otherwise
     */
    public boolean canChange(Pokemon pokemon){
        return pokemon.isAlive() && pokemons.containsValue(pokemon);
    }

    /**
     * Uses an item on a Pokemon
     * Removes the item from inventory after use
     * 
     * @param pokemon The Pokemon to use the item on
     * @param item The item to use
     * @throws PoobkemonException ITEM_NOT_FOUND if the item is not in the inventory,
     *                            POKEMON_DOESNT_EXIST_IN_THE_INVENTORY_OR_NOT_EXIST if the Pokemon is not in the inventory
     */
    public void useItem(Pokemon pokemon,Item item) throws PoobkemonException{
        System.out.println("items: " + item.getName());
        for (Item i : items.keySet()){
            System.out.println(i.getName());
        }
        System.out.println("hola? Inventory");
        if (!items.containsKey(item.getName())) {
            System.out.println(item.getName());
            throw new PoobkemonException(PoobkemonException.ITEM_NOT_FOUND);
        }
        if (!pokemons.containsKey(pokemon.getName())) throw new PoobkemonException(PoobkemonException.POKEMON_DOESNT_EXIST_IN_THE_INVENTORY_OR_NOT_EXIST);
        System.out.println("Item: " + item);
        System.out.println("POKEMON: " + pokemon.getName());
        item.useItem(pokemon);
        items.remove(item.getName());
    }
    
    /**
     * Uses an item by name on a Pokemon
     * Convenience method that calls useItem(Pokemon, Item)
     * 
     * @param pokemon The Pokemon to use the item on
     * @param item The name of the item to use
     * @throws PoobkemonException Various exceptions depending on item and Pokemon status
     */
    public void useItem(Pokemon pokemon,String item) throws PoobkemonException{
        System.out.println("LLEGAS AL INVENTARIO DEL TRAINER?");
        System.out.println(pokemon.getName() + " " + item);
        useItem(pokemon, getItemByName(item));
    }

    /**
     * Gets a list of all item names in the inventory
     * 
     * @return An ArrayList containing the names of all items in the inventory
     */
    public ArrayList<String> getItemsArray(){
        ArrayList<String> temp = new ArrayList<>();
        for(Item i: items.keySet()){
            temp.add(i.getName());
        }
        return temp;
    }
}