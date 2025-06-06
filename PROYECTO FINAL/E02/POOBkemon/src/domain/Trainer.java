package src.domain;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Clase que representa a un entrenador Pokémon
 */
public class Trainer {
    protected String nombre;
    protected Color color;
    protected List<Pokemon> team;
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
        team = new ArrayList<>();
    }

    /**
     * Añade un Pokémon al equipo del entrenador
     *
     * @param pokemon Pokémon a añadir
     * @return true si se añadió correctamente, false si el equipo está lleno
     */
    public boolean addPokemon(Pokemon pokemon) {
        if (team.size() < 6) {
            team.add(pokemon);
            if (team.size() == 1) {
                activePokemon = pokemon;
            }
            return true;
        }
        return false;
    }

    /**
     * Cambia el Pokémon activo
     *
     * @param pokemon Pokémon a cambiar
     * @return true si se cambió correctamente, false en caso contrario
     */
    public boolean switchPokemon(Pokemon pokemon) {
        if (team.contains(pokemon)) {
            Pokemon newActive = pokemon;
            if (newActive.isFainted()) {
                return false;
            }
            activePokemon = newActive;
            return true;
        }
        return false;
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
     * @param target Objetivo del ítem (normalmente un Pokémon)
     * @return true si se usó correctamente, false en caso contrario
     */
    public boolean useItem(Item item, Pokemon target) {
        return inventory.useItem(item, target);
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

    /**
     * Obtiene el equipo de Pokémon
     *
     * @return Lista con los Pokémon del equipo
     */
    public List<Pokemon> getTeam() {
        return new ArrayList<>(team);
    }

    /**
     * Obtiene el Pokémon activo
     *
     * @return Pokémon activo actual
     */
    public Pokemon getActivePokemon() {
        return activePokemon;
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
        for (Pokemon pokemon : team) {
            if (!pokemon.isFainted()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Pokemon> getAlivePokemons() {
        ArrayList<Pokemon> alivePokemons = new ArrayList<>();
        for (Pokemon pokemon : team) {
            if (!pokemon.isFainted()) {
                alivePokemons.add(pokemon);
            }
        }
        return alivePokemons;
    }


    public Movement decide(Pokemon pokemon){
        return null;
    }

    @Override
    public String toString() {
        return "Pokémon: " + team.size();
    }
}