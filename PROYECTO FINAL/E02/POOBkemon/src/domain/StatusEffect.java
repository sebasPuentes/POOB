package src.domain;
package src.domain;

import java.io.Serializable;

/**
 * Clase que representa un efecto de estado que puede sufrir un Pokémon
 * @author Julian López y Sebastian Puentes
 * @version 1.0
 */
public class StatusEffect implements Effect, Serializable {
    private String name;
    private String description;
    private int duration;
    private int damagePerTurn;
    
    /**
     * Constructor para un efecto de estado
     * @param name Nombre del efecto
     * @param description Descripción del efecto
     * @param duration Duración en turnos (0 para permanente hasta curación)
     * @param damagePerTurn Daño causado por turno (0 si no causa daño)
     */
    public StatusEffect(String name, String description, int duration, int damagePerTurn) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.damagePerTurn = damagePerTurn;
    }
    
    /**
     * Aplica el efecto al Pokémon objetivo
     * @param target Pokémon que recibe el efecto
     */
    @Override
    public void applyEffect(Pokemon target) {
        if (damagePerTurn > 0) {
            target.losePS(damagePerTurn);
        }
        
        if (duration > 0) {
            duration--;
        }
    }
    
    /**
     * Obtiene el nombre del efecto
     * @return Nombre del efecto
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Verifica si el efecto sigue activo
     * @return true si el efecto sigue activo, false si ha terminado
     */
    @Override
    public boolean isActive() {
        return duration != 0;
    }
    
    /**
     * Obtiene una copia del efecto
     * @return Copia del efecto
     */
    public StatusEffect copy() {
        return new StatusEffect(name, description, duration, damagePerTurn);
    }
}
public class StatusEffect extends Effect {
    protected double probability;

    public StatusEffect(String newName, String newDescription, int newTimes, double prob){
        super(newName,newDescription,newTimes);
        prob = probability;
    }

    public void affectPokemon(Pokemon affectPokemon) throws POOBkemonException {
        decrementDuration();
        if (Math.random() * 100 <= probability) {
            throw new POOBkemonException(POOBkemonException.DURATION_OVER);
        }
        //que se termine el estado
        affectPokemon.removeStatusEffect();
    }
}
