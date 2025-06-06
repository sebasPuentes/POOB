package domain;
import java.util.*;
import java.awt.Color;
import java.io.*;
public class DefensiveTrainer extends MachineTrainer implements Serializable {
    
    /**
     * Constructor for creating a new DefensiveTrainer
     * 
     * @param newName The name of the AI trainer
     * @param color The color associated with the trainer
     */
    public DefensiveTrainer(String newName, Color color) {
        super(newName,color);
    }
    
    /**
     * Determines the best defensive strategy against an opponent's Pokemon.
     * This implementation prioritizes moves that enhance defense stats.
     * If no suitable defensive moves with PP remaining are found, it falls back to
     * other strategies like attacking, switching Pokemon, or using items.
     * 
     * The strategy focuses on:
     * - Using moves that increase defense or special defense
     * - Using moves that provide protection against opponent attacks
     * - Using moves that lower the opponent's attack or special attack
     * 
     * @param target The opponent's Pokemon to defend against
     * @return The name of the selected movement to use, or null if falling back to an alternative strategy
     */
    @Override
    public String decide(Pokemon target){
        //Cambiar Movement -> MovementState
        ArrayList<MovementTribute> movementsPokemon = inventory.getPokemons().get(actualPokemon.getName()).getMovementsGiveDefense();
        Movement bestMovementDefensive = null;
        int status = 0;
        for (int i = 0; i < movementsPokemon.size(); i++){
            if (movementsPokemon.get(i).getStateTo().get("Defense") > status && movementsPokemon.get(i).getPP() > 0){
                status = movementsPokemon.get(i).getStateTo().get("Defense");
                bestMovementDefensive = movementsPokemon.get(i);
            }
        }

        if (bestMovementDefensive != null){
            try{bestMovementDefensive.doAttackTo(target, target);}
            catch(PoobkemonException i){
            System.out.println("Fallo movimiento machine: "+ bestMovementDefensive.getName()+" "+i.getMessage());            
        }
            return bestMovementDefensive.getName();
        }
        else{
            //hace otro movimiento que seria hacer un ataque o cambiar o gg 
            doOtherThen(target);
            return "Ha hecho otra accion.";
        }
    }
}