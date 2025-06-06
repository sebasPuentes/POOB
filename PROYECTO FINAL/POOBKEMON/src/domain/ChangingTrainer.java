package domain;

import java.util.*;
import java.awt.*;
import java.util.List;

public class ChangingTrainer extends MachineTrainer {

    public ChangingTrainer(String name,Color color) {
        super(name,color);
    }


    @Override
    public Movement decide(Pokemon target){
        TreeMap<String,Pokemon> p = inventory.getPokemons();
        Pokemon pokemonActual = activePokemon;
        double possible = 0;
        for (Pokemon pok : p.values()){
            double possibleMultiplicator = pok.getMovements().get(0).getMultiplicator(target.getPrincipalType());
            if (possible < possibleMultiplicator){
                possible = possibleMultiplicator;
                activePokemon = pok;
            }
        }
        if (pokemonActual.equals(activePokemon)){
            try {
                doOtherThen(target);
            }catch (POOBkemonException e){
                System.out.println(e.getMessage());
            }
        }
        return pokemonMovementDecide(target);
    }
}
