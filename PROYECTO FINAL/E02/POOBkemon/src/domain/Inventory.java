package src.domain;

import java.util.*;

public class Inventory {

    private final int capacity = 10;
    private List<Pokemon> pokemons = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public Inventory() {
    }

    public void addPokemon(Pokemon pokemon){
        if(pokemons.size() + items.size() < capacity){
            pokemons.add(pokemon);
        }
    }

    public void removePokemon(Pokemon pokemon){
        if(pokemons.contains(pokemon)){
            pokemons.remove(pokemon);
        }
    }

    public void addItem(Item item){
        if(pokemons.size() + items.size() < capacity){
            items.add(item);
        }
    }

    public void removeItem(Item item){
        if(items.contains(item)){
            items.remove(item);
        }
    }
    
    /**
     * @param item El ítem a usar
     * @param target El objetivo sobre el que usar el ítem
     * @return true si el ítem se usó correctamente
     */
    public boolean useItem(Item item, Pokemon target) {
        if (!items.contains(item)) {
            return false;
        }
        boolean itemUsed = item.use(target);
        if (itemUsed) {
            items.remove(item);
        }
        return itemUsed;
    }

    /**
     * Obtiene todos los ítems del inventario
     * @return Lista con todos los ítems
     */
    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    /**
     * Obtiene todos los Pokémon del inventario
     * @return Lista con todos los Pokémon
     */
    public List<Pokemon> getAllPokemons() {
        return new ArrayList<>(pokemons);
    }
    /**
     * Verifica si el inventario está lleno
     * @return true si el inventario está lleno, false en caso contrario
     */
    public boolean isFull() {
        return pokemons.size() + items.size() >= capacity;
    }

    /**
     * Obtiene la capacidad total del inventario
     * @return Capacidad total
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Obtiene el espacio disponible en el inventario
     * @return Espacio disponible
     */
    public int getAvailableSpace() {
        return capacity - (pokemons.size() + items.size());
    }

    public boolean canChange(Pokemon pokemon){
        return pokemon.isAlive() && pokemons.contains(pokemon);
    }

    public ArrayList<Pokemon> getAlivePokemons(){
        ArrayList<Pokemon> alivePokemons= new ArrayList<Pokemon> ();
        for(Pokemon p: pokemons){
            if(p.isAlive()) alivePokemons.add(p);
        }
        return alivePokemons;
    }

    public ArrayList<Pokemon> getAlivePokemons(Pokemon pokemon){
        ArrayList<Pokemon> alivePokes= new ArrayList<Pokemon> ();
        for(Pokemon p: pokemons){
            if(p.isAlive() && !p.equals(pokemon)) alivePokes.add(p);
        }
        return alivePokes;
    }

    public boolean contains(Pokemon pokemon){
        return pokemons.contains(pokemon);
    }
}
