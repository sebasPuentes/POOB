package src.domain;

import java.util.HashMap;

public class AttributeMovement extends Special{
    private HashMap<String,Integer> stateTo = new HashMap<>();
    private AttributeEffect state;
    private int damageBase = 0;

    public AttributeMovement(String newName, String newDescription, int newPP, int newPower, int newPrecision, PokemonType newPT, AttributeEffect  estado, int newpPriority){
        super(newName,newDescription,newPP,newPower,newPrecision,newPT, newpPriority);
        state = estado;
    }

    public Effect getStatus(){
        return state;
    }
    public HashMap<String,Integer> getStateTo(){
        return stateTo;
    }


    @Override
    public int doAttackTo(Pokemon attacker, Pokemon target) throws POOBkemonException{
        if (Math.random() * 100 > precision) {
            throw new POOBkemonException(POOBkemonException.INVALID_MOVEMENT);
        }
        target.addEffect(state);
        return damageBase;
    }
    @Override
    public Movement copy(){
        return new AttributeMovement(name, description, pp, power, precision, type, state, priority);
    }

}
