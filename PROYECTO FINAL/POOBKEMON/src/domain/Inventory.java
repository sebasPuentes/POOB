package domain;

import java.io.Serializable;
import java.util.*;

public class Inventory implements Serializable {

    private final int capacity = 10;
    private TreeMap<String,Pokemon> pokemons = new TreeMap<>();
    private HashMap<String,Item> items = new HashMap<>();

    public Inventory() {
    }

    //-------------------ADDERS-------------------
    public void addPokemon(Pokemon pokemon){
        if(pokemons.size() + items.size() < capacity){
            pokemons.put(pokemon.getName(), pokemon);
        }
    }

    public void addItem(Item item){
        if(pokemons.size() + items.size() < capacity){
            items.put(item.getName(), item);
        }
    }

    //-------------------DELETERS-------------------
    public void removePokemon(Pokemon pokemon){
        if(pokemons.containsKey(pokemon.getName())){
            pokemons.remove(pokemon.getName());
        }
    }


    public void removeItem(Item item) throws POOBkemonException {
        String itemName = item.getName();
        if (!items.containsKey(itemName)) throw new POOBkemonException(POOBkemonException.INVALID_ITEM);
        items.remove(itemName);
    }

    //--------------------USERS-------------------
    /**
     * @param item El ítem a usar
     * @param pokemon El objetivo sobre el que usar el ítem
     * @return true si el ítem se usó correctamente
     */
    public void useItem(Pokemon pokemon, Item item) throws POOBkemonException {
        item.use(pokemon);
        items.remove(item.getName());
    }

    public void useItem(Pokemon pokemon,String item) throws POOBkemonException{
        System.out.println("LLEGO LA POCION ? :/");
        System.out.println(pokemon.getName() + " " + item);
        useItem(pokemon, getItemByName(item));
    }
    public Item getItemByName(String name) throws POOBkemonException {
        for (Item i : items.values()){
            System.out.println("Hola ?" + "" + i.getName());
        }
        for (Item i : items.values()){
            if (name.equals(i.getName())) return i;
        }
        throw new POOBkemonException(POOBkemonException.INVALID_ITEM);
    }

    //-------------------GETTERS-------------------
    public Item getRandomItem() throws POOBkemonException {
        if (items.isEmpty()) throw new POOBkemonException(POOBkemonException.EMPTY_INVENTORY);
        Random random = new Random();
        int randomIndex = random.nextInt(items.size());
        String itemName = (String) items.keySet().toArray()[randomIndex];
        Item item = items.get(itemName);
        items.remove(itemName);
        return item;
    }
    /**
     * Obtiene todos los ítems del inventario
     * @return Lista con todos los ítems
     */
    public HashMap<String,Item> getItems() {
        return items;
    }

    public ArrayList<String> getItemsArray(){
        ArrayList<String> itemsArray = new ArrayList<>();
        for (Item item : items.values()) {
            itemsArray.add(item.getName());
        }
        return itemsArray;
    }

    /**
     * Obtiene todos los Pokémon del inventario
     * @return Lista con todos los Pokémon
     */
    public TreeMap <String,Pokemon> getPokemons() {
        return pokemons;
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

    public ArrayList<Pokemon> getAlivePokemons(Pokemon pok){
        ArrayList<Pokemon> poke= new ArrayList<Pokemon>();
        for(Pokemon p: pokemons.values()){
            if(p.isAlive()) poke.add(p);
        }
        return poke;
    }

    public ArrayList<Pokemon> getAlivePokemons(){
        ArrayList<Pokemon> poke= new ArrayList<Pokemon>();
        for(Pokemon p: pokemons.values()){
            if(p.isAlive()) poke.add(p);
        }
        return poke;
    }

    public Pokemon getPokemonByName(String name) throws POOBkemonException{
        for (Map.Entry<String, Pokemon> entry :pokemons.entrySet()){
            if (name.equals(entry.getKey())) return entry.getValue();
        }
        throw new POOBkemonException(POOBkemonException.INVALID_POKEMON);
    }

    public boolean isDefeated(){
        return getAlivePokemons().size() < 0;
    }

    //-------------------BOOLEAN-------------------
    public boolean canChange(Pokemon pokemon){
        return pokemon.isAlive() && pokemons.containsValue(pokemon);
    }

    /**
     * Verifica si el inventario está lleno
     * @return true si el inventario está lleno, false en caso contrario
     */
    public boolean isFull() {
        return pokemons.size() + items.size() >= capacity;
    }

    public boolean contains(Pokemon pokemon){
        return pokemons.containsKey(pokemon.getName());
    }

    public boolean contains(Item item){
        return items.containsKey(item);
    }
}
