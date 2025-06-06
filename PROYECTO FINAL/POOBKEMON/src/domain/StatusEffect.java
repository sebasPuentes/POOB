package domain;

import java.io.Serializable;

public class StatusEffect extends Effect {
    protected double probability;

    public StatusEffect(String newName, String newDescription, int newTimes, double prob) {
        super(newName, newDescription, newTimes);
        probability = prob;
    }

    public void affectPokemon(Pokemon affectPokemon) throws POOBkemonException {
        if (getDuration() > 0) {
            decrementDuration();
            if (getDuration() <= 0) {
                affectPokemon.removeStatusEffect();
                throw new POOBkemonException(POOBkemonException.DURATION_OVER);
            }
            if (Math.random() * 100 <= probability) {
                if (getName().equals("Quemado")) {
                    int damage = Math.max(1, affectPokemon.getCurrentPs() / 16);
                    affectPokemon.losePS(damage);
                    System.out.println("Quemadoooooo");
                } else if (getName().equals("Envenenado")) {
                    int damage = Math.max(1, affectPokemon.getCurrentPs() / 8);
                    affectPokemon.losePS(damage);
                } else if (getName().equals("Paralizado")) {
                    if (Math.random() < 0.25) {
                        throw new POOBkemonException(POOBkemonException.PARALYZED);
                    }
                } else if (getName().equals("Congelado")) {
                    throw new POOBkemonException(POOBkemonException.FROZEN);
                } else if (getName().equals("Confuso")) {
                    if (Math.random() < 0.33) {
                        int damage = Math.max(1, affectPokemon.getCurrentPs() / 8);
                        affectPokemon.losePS(damage);
                        throw new POOBkemonException(POOBkemonException.CONFUSED);
                    }
                } else if (getName().equals("Atrapado")) {
                    int damage = Math.max(1, affectPokemon.getCurrentPs() / 16);
                    affectPokemon.losePS(damage);
                }
            }
        } else {
            affectPokemon.removeStatusEffect();
            System.out.println("aSASDASDASDASDA");
        }
    }
}