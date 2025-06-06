package domain;


import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.Serializable;



/**
 * Clase que representa a un entrenador Pokémon
 */
public class Trainer implements Serializable {
    protected String nombre;
    protected Color color;
    protected Inventory inventory;
    protected Pokemon activePokemon;

    /**
     * Constructor para crear un entrenador
     *
     * @param name Nombre del entrenador
     */
    public Trainer(String name, Color color) {
        nombre = name;
        this.color = color;
        inventory = new Inventory();

    }


    /**
     * Añade un Pokémon al equipo del entrenador
     *
     * @param pokemon Pokémon a añadir
     * @return true si se añadió correctamente, false si el equipo está lleno
     */
    public boolean addPokemon(Pokemon pokemon) {
        if (inventory.getPokemons().size() < 6) {
            inventory.addPokemon(pokemon);
            if (inventory.getPokemons().size() == 1) {
                activePokemon = pokemon;
            }
            return true;
        }
        return false;
    }

    /**
     * Cambia el Pokémon activo
     *
     * @param newPokemon Pokémon a cambiar
     * @return true si se cambió correctamente, false en caso contrario
     */
    public void changePokemon(Pokemon newPokemon) throws POOBkemonException{
        if (!inventory.contains(newPokemon)) throw new POOBkemonException(POOBkemonException.INVALID_POKEMON);
        if(!newPokemon.isAlive()) throw new POOBkemonException(POOBkemonException.INVALID_POKEMON);
        setPokemonInUse(newPokemon);
    }

    public void setPokemonInUse(Pokemon pokemonNew){
        if (pokemonNew != null && pokemonNew.isAlive()){
            activePokemon = pokemonNew;
        }
    }

    public void setPokemonInUse(String pokemonNew) throws POOBkemonException{
        setPokemonInUse(getPokemonByName(pokemonNew));
    }

    public Pokemon getPokemonByName(String pokemonName) throws POOBkemonException{
        return inventory.getPokemonByName(pokemonName);
    }


    public void useMovement(String movement,Pokemon target){
        Movement move = activePokemon.getMovement(movement);
        activePokemon.useMovement(move, target);
    }

    public void changePokemon(String pokemon) throws POOBkemonException{
        changePokemon(inventory.getPokemonByName(pokemon));
    }

    /**
     * Añade un ítem al inventario
     *
     * @param item Ítem a añadir
     */
    public void addItem(Item item) {
        inventory.addItem(item);
    }

    /**
     * Usa un ítem del inventario
     *
     * @param item   Ítem a usar
     * @param pokemon   Objetivo del ítem (normalmente un Pokémon)
     * @return true si se usó correctamente, false en caso contrario
     */
    public boolean useItem(Pokemon pokemon , Item item) throws POOBkemonException{
        if(item == null || pokemon == null) {
            return false;
        }
        inventory.useItem(pokemon, item);
        return true;
    }

    public void useItem(String item) throws POOBkemonException{
        System.out.println("HOLA LLEGAS ACÁ ? STRING " + item);
        inventory.useItem(activePokemon, item);
    }

    /**
     * Obtiene el nombre del entrenador
     *
     * @return Nombre del entrenador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del entrenador
     *
     * @param nombre Nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public Pokemon getActivePokemon(){
        if (activePokemon != null && activePokemon.isAlive()){
            return activePokemon;
        }

        if (inventory != null && !inventory.getAlivePokemons().isEmpty()){
            activePokemon = inventory.getAlivePokemons().get(0);
            return activePokemon;
        }

        return null; //CAMBIADO
    }

    /**
     * Obtiene el inventario del entrenador
     *
     * @return Inventario del entrenador
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Verifica si todos los Pokémon del equipo están debilitados
     *
     * @return true si todos están debilitados, false si al menos uno sigue en pie
     */
    public boolean isDefeated() {
        return inventory.isDefeated();
    }

    /**
     * Establece el inventario del entrenador
     *
     * @param inventory Nuevo inventario
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Movement decide(Pokemon pokemon){
        return null;
    }

}