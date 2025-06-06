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
     * @return The movement to use against the target
     */
    @Override
    public Movement decide(Pokemon target) {
        // Obtener todos los movimientos del Pokémon activo
        ArrayList<Movement> movements = activePokemon.getMovements();
        Movement bestMovement = null;
        double bestEffectiveness = 0;
        for (Movement movement : movements) {
            if (movement.getPP() > 0) {
                double effectiveness = movement.getMultiplicator(target.getPrincipalType());
                if (effectiveness > bestEffectiveness) {
                    bestEffectiveness = effectiveness;
                    bestMovement = movement;
                }
            }
        }
        if (bestMovement != null) {
            return bestMovement;
        }
        else {
            return activePokemon.aleatoryMovement(target);
        }
    }
}
