package src.domain;

import java.util.*;
import java.awt.*;
import java.util.List;

public class ChangingTrainer extends MachineTrainer {

    public ChangingTrainer(String name,Color color) {
        super(name,color);
    }


    @Override
    public Movement decide(Pokemon target){
        List<Pokemon> p = inventory.getAllPokemons();
        Pokemon pokemonActual = activePokemon;
        double possible = 0;
        for (Pokemon pok : p){
            double possibleMultiplicator = pok.getMovements().get(0).getMultiplicator(target.getPrincipalType());
            if (possible < possibleMultiplicator){
                possible = possibleMultiplicator;
                activePokemon = pok;
            }
        }
        if (pokemonActual.equals(activePokemon)){
            doOtherThen(target);
        }
        return null;
    }
}
