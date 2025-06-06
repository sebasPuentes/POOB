package domain;
import java.io.Serializable;
public abstract class Movement implements Attackable, Serializable{
    protected String name;
    protected String description;
    protected int PP = 25;
    protected int power;
    protected int precision = 100;

    protected PokemonType type;
    protected int priority; 
    
    /**
     * Constructor for creating a new Movement
     * Initializes the movement with the specified parameters.
     * PP (Power Points) are automatically adjusted based on power - stronger moves have fewer uses.
     * 
     * @param newName The name of the movement
     * @param newDescription A description of what the movement does
     * @param newPP The initial Power Points (PP) available for this movement
     * @param newPower The base power of the movement
     * @param newPrecision The accuracy percentage of the movement
     * @param newType The elemental type of the movement
     * @param newpPriority The priority level of the movement in battle
     */
    public Movement(String newName, String newDescription, int newPP, int newPower, int newPrecision, PokemonType newType, int newpPriority){
        name = newName;
        description = newDescription;
        PP = newPP;
        power = newPower;
        precision = newPrecision;
        type = newType;
        priority = newpPriority;

        //los pp se ponen de acuerdo al power, si es debil se pueden usar mas segudo
        PP = Math.min(newPP, (newPower > 50 ? 20 : 40));
    }
    
    /**
     * Gets the name of this movement
     * 
     * @return The name of the movement
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gets the description of this movement
     * 
     * @return The description of the movement
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Gets the accuracy (precision) of this movement
     * 
     * @return The accuracy percentage value
     */
    public int getPrecision(){
        return precision;
    }

    /**
     * Gets the elemental type of this movement
     * 
     * @return The PokemonType of the movement
     */
    public PokemonType getType(){
        return type;
    }
    
    /**
     * Gets the priority level of this movement in battle
     * Higher priority moves are executed first
     * 
     * @return The priority value
     */
    public int getPriority(){
        return priority;
    }

    /**
     * Calculates the type effectiveness multiplier against a defending Pokemon type
     * Uses a pre-defined effectiveness table to determine damage multiplication
     * 
     * @param defender The PokemonType of the defending Pokemon
     * @return A multiplier value representing type effectiveness (e.g. 2.0 for super effective)
     */
    public double getMultiplicator(PokemonType defender){
            return multiplicadores[type.getIndex()][defender.getIndex()];
    }

    /**
     * Gets the remaining Power Points (PP) available for this movement
     * 
     * @return The current PP value
     */
    public int getPP(){
        return PP;
    }
    
    /**
     * Gets the base power of this movement
     * 
     * @return The power value
     */
    public int getPower(){
        return power;
    }

    /**
     * Method for handling time-based limitations
     * Empty implementation, to be overridden by subclasses if needed
     */
    public void limitOfTime(){}

    /**
     * Decreases the PP by one after using the movement
     * 
     * @throws PoobkemonException INVALID_VALUES if PP would become zero or negative
     */
    public void losePP()throws PoobkemonException{
        if (PP - 1 <= 0) throw new PoobkemonException(PoobkemonException.INVALID_VALUES);
        PP --;
    }

    /**
     * Checks if this movement can be used (has enough PP)
     * 
     * @return true if PP is greater than zero, false otherwise
     */
    public boolean canMakeMove(){
        return (PP>0);
    }
    
    /**
     * Executes the attack from one Pokemon to another
     * Abstract method to be implemented by subclasses
     * 
     * @param attacker The Pokemon performing the attack
     * @param target The Pokemon receiving the attack
     * @return The amount of damage dealt
     * @throws PoobkemonException Various exceptions depending on the implementation
     */
    public abstract int doAttackTo(Pokemon attacker, Pokemon target) throws PoobkemonException;

    /**
     * Performs a struggle attack that damages both the target and the attacker
     * Used when a Pokemon has no other moves available
     * 
     * @param attacker The Pokemon performing the attack
     * @param target The Pokemon receiving the attack
     */
    public void AttackToStruggle(Pokemon attacker, Pokemon target){
        try{
            int newValueAttacker = (doAttackTo(attacker, target)); //mirar desespspps
            attacker.losePS(newValueAttacker);
            losePP();
        }
        catch (PoobkemonException e){
            e.getMessage();
        }
    }
    
    /**
     * Creates a copy of this Movement with the same properties
     * Abstract method to be implemented by subclasses
     * 
     * @return A new Movement object with identical properties
     */
    public abstract Movement copy();
}