package src.domain;

import java.util.Random;

public class StateMovement extends Special{
    private StatusEffect state;
    private int damageBase;

    public StateMovement(String newName, String newDescription, int newPP, int newPower, int newPrecision, PokemonType newPT, StatusEffect estado, int newpPriority, int newDamageBase){
        super(newName,newDescription,newPP,newPower,newPrecision,newPT, newpPriority);
        state = estado;
        damageBase = newDamageBase;
    }

    public Effect getStatus(){
        return state;
    }


    @Override
    public int doAttackTo(Pokemon attacker, Pokemon target) throws POOBkemonException{
        Random random = new Random();
        if (random.nextInt(100) > precision) {
            throw new POOBkemonException(POOBkemonException.INVALID_MOVEMENT);
        }
        target.addEffect(state);
        return damageBase;
    }

    @Override
    public Movement copy(){
        return new StateMovement(name, description, pp, power, precision, type, state, priority, damageBase);
    }
}
