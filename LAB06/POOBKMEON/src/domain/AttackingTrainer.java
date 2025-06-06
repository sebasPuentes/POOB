package domain;
import java.io.Serializable;
import java.util.*;
import java.awt.Color;
public class AttackingTrainer extends MachineTrainer implements Serializable {

    /**
     * Constructor for creating a new AttackingTrainer
     * 
     * @param newName The name of the AI trainer
     * @param newColor The color associated with the trainer
     */
    public AttackingTrainer(String newName, Color newColor) {
        super(newName, newColor);
    }
    
    /**
     * Determines the best offensive strategy against an opponent's Pokemon.
     * This implementation prioritizes moves with high type effectiveness multipliers
     * against the opponent's Pokemon type. The strategy focuses on:
     * - Using moves that increase attack or special attack
     * - Using moves that lower the opponent's defense or special defense
     * - Selecting moves with type advantages against the opponent
     * 
     * If no suitable attack moves with PP remaining are found, it falls back to
     * a random move selection.
     * 
     * @param target The opponent's Pokemon to attack
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