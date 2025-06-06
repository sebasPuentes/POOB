package src.domain;


import java.io.Serializable;

public class StatusEffect extends Effect{
    protected double probability;

    public StatusEffect(String newName, String newDescription, int newTimes, double prob){
        super(newName,newDescription,newTimes);
        prob = probability;
    }

    public void affectPokemon(Pokemon affectPokemon) throws POOBkemonException{
        decrementDuration();
        if (Math.random() * 100 <= probability) {
            throw new POOBkemonException(POOBkemonException.DURATION_OVER);
        }
        //que se termine el estado
        affectPokemon.removeStatusEffect();
    }
}
