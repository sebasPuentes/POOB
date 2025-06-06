package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.awt.Color;

public class ExpertTrainer extends MachineTrainer implements Serializable{
    
    /**
     * Constructor for creating a new ExpertTrainer
     * 
     * @param newName The name of the AI trainer
     * @param newColor The color associated with the trainer
     */
    public ExpertTrainer(String newName, Color newColor) {
        super(newName, newColor);
    }

    /**
     * Determines the best attack strategy against an opponent's Pokemon.
     * This expert implementation analyzes type effectiveness and selects the move
     * that will be most effective against the target's type.
     * If no effective moves with PP remaining are found, it falls back to a random move.
     * 
     * @param target The opponent's Pokemon to analyze
     * @return The name of the selected movement to use
     */
    public String decide(Pokemon target){
        ArrayList<MovementTribute> movementsPokemon = inventory.getPokemons().get(actualPokemon.getName()).getMovementsGiveAttack();
        Movement bestAttackMovement = null;
        double possibleAttackMovement = 0;
        for (int i = 0; i < movementsPokemon.size(); i++){
            double attackMovement = movementsPokemon.get(i).getMultiplicator(target.getPrincipalType());
            if (possibleAttackMovement < attackMovement && movementsPokemon.get(i).getPP() > 0){
                possibleAttackMovement = attackMovement;
                bestAttackMovement = movementsPokemon.get(i);
            }
        }
        if (bestAttackMovement != null){
            try{
                actualPokemon.affectPokemonStatus();
                bestAttackMovement.doAttackTo(actualPokemon, target);}
            catch(PoobkemonException i){
                System.out.println("Fallo movimiento machine: "+ bestAttackMovement.getName()+" "+i.getMessage());
                
            }
            return bestAttackMovement.getName();
        }
        bestAttackMovement = actualPokemon.aleatoryMovement(target);
        try{
            actualPokemon.affectPokemonStatus();
            bestAttackMovement.doAttackTo(actualPokemon, target);}
        catch(PoobkemonException i){
            System.out.println("Fallo movimiento machine: "+ bestAttackMovement.getName()+" "+i.getMessage());            
        }
        return bestAttackMovement.getName();
    }
}