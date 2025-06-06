package domain;
import java.awt.Color;
import java.io.Serializable;
import java.util.*;
public class ChangingTrainer extends MachineTrainer implements Serializable{
    
    /**
     * Constructor for creating a new ChangingTrainer
     * 
     * @param newName The name of the AI trainer
     * @param color The color associated with the trainer
     */
    public ChangingTrainer(String newName, Color color) {
        super(newName, color);
    }
    
    /**
     * Determines the best Pokemon to use against an opponent's Pokemon based on type effectiveness.
     * This implementation analyzes all Pokemon in the trainer's inventory and selects the one
     * whose first move has the highest type effectiveness multiplier against the opponent's type.
     * If no better Pokemon is found (current Pokemon is already optimal), it falls back to
     * other strategies like attacking, using items, or changing randomly.
     * 
     * @param target The opponent's Pokemon to analyze for type effectiveness
     * @return The name of a selected movement to use after switching Pokemon, or null if only switching
     */
    @Override
    public String decide(Pokemon target){
        TreeMap<String,Pokemon> p = inventory.getPokemons();
        Pokemon pokemonActual = actualPokemon;
        double possible = 0;
        for (Pokemon pok : p.values()){
            double possibleMultiplicator = pok.getMovements().get(0).getMultiplicator(target.getPrincipalType());
            if (possible < possibleMultiplicator){
                possible = possibleMultiplicator;
                actualPokemon = pok;
            }
        }
        if (pokemonActual.equals(actualPokemon)){
            doOtherThen(target);
        }
        //return null; doOtherThen si es una buena opcion?
        return actualPokemon.aleatoryMovement(target).getName();
    }
}