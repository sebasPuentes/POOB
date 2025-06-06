package domain;

import java.util.HashMap;
public class MovementTribute extends SpecialMovement{
    private HashMap<String,Integer> stateTo = new HashMap<>();
    private TributeEffect state;
    private int damageBase = 0;

    /**
     * Constructor for creating a new MovementTribute
     * 
     * @param newName The name of the movement
     * @param newDescription A description of what the movement does
     * @param newPP The initial Power Points (PP) available for this movement
     * @param newPower The base power of the movement
     * @param newPrecision The accuracy percentage of the movement
     * @param newPT The elemental type of the movement
     * @param estado The tribute effect that will be applied to the target
     * @param newpPriority The priority level of the movement in battle
     */
    public MovementTribute(String newName, String newDescription, int newPP, int newPower, int newPrecision, PokemonType newPT, TributeEffect estado, int newpPriority){
        super(newName,newDescription,newPP,newPower,newPrecision,newPT, newpPriority);
        state = estado;
    }

    /**
     * Gets the tribute effect associated with this movement
     * 
     * @return The TributeEffect object that this movement applies
     */
    public Effect getStatus(){
        return state;
    }
    
    /**
     * Gets the map of stats that this movement affects
     * 
     * @return A HashMap containing the stat names and their modification values
     */
    public HashMap<String,Integer> getStateTo(){
        return stateTo;
    }

    /**
     * Executes the tribute attack from one Pokemon to another.
     * Unlike regular attacks, this primarily applies an effect to the target
     * rather than dealing significant damage.
     * The attack has a chance to miss based on precision.
     * 
     * @param attacker The Pokemon performing the attack
     * @param target The Pokemon receiving the attack
     * @return The base damage value (usually minimal)
     * @throws PoobkemonException MISSED_MOVEMENT if accuracy check fails
     */
    @Override
    public int doAttackTo(Pokemon attacker, Pokemon target) throws PoobkemonException{
        if (Math.random() * 100 > precision) {  //tiene prob de ocurri o no 
            System.out.println("No se ha aplicado el efecto por probabilidad.");
            losePP();
            throw new PoobkemonException(PoobkemonException.MISSED_MOVEMENT);
        }
        target.addEffect(state);
        System.out.println( "Aplicar movimiento atributo: " + damageBase);
        losePP();
        return damageBase;
    }
    
    /**
     * Creates a copy of this MovementTribute with the same properties
     * 
     * @return A new MovementTribute object with identical properties
     */
    @Override
    public Movement copy(){
        return new MovementTribute(name, description, PP, power, precision, type, state, priority);
    }
}