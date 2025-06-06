package domain;
public class PhysicalMovement extends Movement {
    
    /**
     * Constructor for creating a new PhysicalMovement
     * 
     * @param newName The name of the movement
     * @param newDescription A description of what the movement does
     * @param newPP The initial Power Points (PP) available for this movement
     * @param newPower The base power of the movement
     * @param newPrecision The accuracy percentage of the movement
     * @param newType The elemental type of the movement
     * @param newPriority The priority level of the movement in battle
     */
    public PhysicalMovement(String newName, String newDescription, int newPP, int newPower, int newPrecision, PokemonType newType, int newPriority){
        super(newName, newDescription, newPP, newPower, newPrecision, newType, newPriority);
    }

    /**
     * Executes the physical attack from one Pokemon to another.
     * The damage calculation uses the attacker's Attack and the target's Defense.
     * Applies type effectiveness multipliers and random variance to the damage.
     * Reduces PP by one after use.
     * 
     * @param attacker The Pokemon performing the attack
     * @param target The Pokemon receiving the attack
     * @return The amount of damage dealt
     * @throws PoobkemonException INVALID_MOVEMENT if PP is zero, MISSED_MOVEMENT if accuracy check fails
     */
    public int doAttackTo(Pokemon attacker, Pokemon target) throws PoobkemonException{
        if (!canMakeMove()) throw new PoobkemonException(PoobkemonException.INVALID_MOVEMENT);
        if (Math.random() * 100 > precision) {
            System.out.println("No se ha hecho el fisico por precision.");
            losePP();
            throw new PoobkemonException(PoobkemonException.MISSED_MOVEMENT);
        }
        
        double levelFactor = (2.0 * attacker.getLevel()) / 5.0 + 2.0;
        double attackDefenseRatio = (double) attacker.getAttack() / target.getDefense();
        double damage = ((levelFactor * power * attackDefenseRatio) / 50.0) + 2.0;
        damage *= getMultiplicator(target.getPrincipalType());
        damage *= 0.85 + (Math.random() * 0.15);
        target.losePS(damage);
        losePP();
        System.out.println("Movimiento fisico: " +name +" " + damage);
        return (int)damage;
    }
    
    /**
     * Creates a copy of this PhysicalMovement with the same properties
     * 
     * @return A new PhysicalMovement object with identical properties
     */
    @Override
    public Movement copy(){
        return new PhysicalMovement(name, description, PP, power, precision, type, priority);
    }
}