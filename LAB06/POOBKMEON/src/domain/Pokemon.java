package domain;
import java.io.Serializable;
import java.util.*;

public class Pokemon implements Serializable {
    private String name;
    private int level;
    private int pokedexIndex;
    private final int maxPs;
    private int ps;
    private int attack;
    private int specialAttack;
    private int specialDefense;
    private int defense;
    private int velocity;
    private PokemonType principalType;
    private PokemonType secondaryType;

    private boolean isAlive = true;
    private boolean usedReviveItem = false;

    private ArrayList<Movement> movements;
    private ArrayList<TributeEffect> tributeEffects;
    private StatusEffect statusEffect;
    //Por defecto todos los pokemones tienen este movimiento
    private Movement struggle = new PhysicalMovement("Struggle","A desperate attack that also hurts the user",0,50,100,getPrincipalType(),0);

    /**
     * Constructor for creating a new Pokemon
     * 
     * @param newName The name of the Pokemon
     * @param newLevel The level of the Pokemon
     * @param newPs The health points (PS) of the Pokemon
     * @param newAttack The attack stat of the Pokemon
     * @param newSpecialAttack The special attack stat of the Pokemon
     * @param newDefense The defense stat of the Pokemon
     * @param newSpecialDefense The special defense stat of the Pokemon
     * @param newVelocity The velocity stat of the Pokemon
     * @param newPrincipalType The primary type of the Pokemon
     * @param newSecondaryType The secondary type of the Pokemon (can be null)
     * @param index The Pokedex index number of the Pokemon
     */
    public Pokemon(String newName, int newLevel, int newPs, int newAttack, int newSpecialAttack, int newDefense,int newSpecialDefense, int newVelocity, PokemonType newPrincipalType, PokemonType newSecondaryType,int index) {
        name = newName;
        level = newLevel;
        pokedexIndex = index;
        ps = newPs;
        maxPs = newPs;
        attack = newAttack;
        specialAttack = newSpecialAttack;
        defense = newDefense;
        velocity = newVelocity;
        principalType = newPrincipalType;
        secondaryType = newSecondaryType;
        specialDefense = newSpecialDefense;
        movements = new ArrayList<>();
        tributeEffects = new ArrayList<>();
    }
    
    /**
     * Gets the Pokedex index of the Pokemon
     * 
     * @return The Pokedex index as an Integer
     */
    public Integer getPokedexIndex(){
        return pokedexIndex;
    }

    /**
     * Gets the current health points of the Pokemon
     * 
     * @return The current health points
     */
    public int getPs(){
        return ps;
    }
    
    /**
     * Gets the maximum health points of the Pokemon
     * 
     * @return The maximum health points
     */
    public int getMaxPs(){
        return maxPs;
    }

    /**
     * Gets the velocity stat of the Pokemon
     * 
     * @return The velocity stat
     */
    public int getVelocity(){
        return velocity;
    }

    /**
     * Gets the special attack stat of the Pokemon
     * 
     * @return The special attack stat
     */
    public int getSpecialAttack(){
        return specialAttack;
    }

    /**
     * Gets the name of the Pokemon
     * 
     * @return The name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the primary type of the Pokemon
     * 
     * @return The primary PokemonType
     */
    public PokemonType getPrincipalType(){
        return principalType;
    }
    
    /**
     * Gets the secondary type of the Pokemon
     * 
     * @return The secondary PokemonType, or null if not set
     */
    public PokemonType getSecondaryType(){
        return secondaryType;
    }    
    
    /**
     * Gets the level of the Pokemon
     * 
     * @return The level
     */
    public int getLevel(){
        return level;
    }
    
    /**
     * Gets the attack stat of the Pokemon
     * 
     * @return The attack stat
     */
    public int getAttack(){
        return attack;
    }
    
    /**
     * Gets the defense stat of the Pokemon
     * 
     * @return The defense stat
     */
    public int getDefense(){
        return defense;
    }

    /**
     * Checks if the Pokemon is alive
     * 
     * @return true if the Pokemon is alive, false otherwise
     */
    public boolean isAlive() {
        return isAlive;
    }
    
    /**
     * Gets the special defense stat of the Pokemon
     * 
     * @return The special defense stat
     */
    public int getSpecialDefense(){
        return specialDefense;
    }
    
    /**
     * Gets all movements available to the Pokemon
     * 
     * @return ArrayList of Movement objects
     */
    public ArrayList<Movement> getMovements(){
        return movements;
    }
    
    /**
     * Gets the names of all movements available to the Pokemon
     * 
     * @return ArrayList of movement names as strings
     */
    public ArrayList<String> getMovementsString(){
        ArrayList<String> movementNames = new ArrayList<>();
        for (Movement movement : movements) {
            movementNames.add(movement.getName());
        }
        return movementNames;
    }
    
    /**
     * Gets all tribute effects currently applied to the Pokemon
     * 
     * @return ArrayList of TributeEffect objects
     */
    public ArrayList<TributeEffect> getTributeEffects() {
        return tributeEffects;
    }
    
    /**
     * Gets the PP (Power Points) of a specific movement by name
     * 
     * @param name The name of the movement
     * @return The PP value of the movement
     * @throws PoobkemonException If the movement is not found
     */
    public int getPPByName(String name)throws PoobkemonException{
        for(Movement mov : movements){
            if (mov.getName().equals(name)) return mov.getPP();
        }
        throw new PoobkemonException(PoobkemonException.MOVEMENT_NOT_FOUND);
    }

    /**
     * Sets the movements for this Pokemon
     * 
     * @param newMovements Array of Movement objects to assign to the Pokemon
     */
    public void setMovements(Movement[] newMovements){
        ArrayList<Movement> list = new ArrayList<>();
        for (Movement m : newMovements){
            try {
                if (!movements.contains(m)) {
                    list.add(m);
                }
            } catch(Exception e){
                LogPOOBKEMON.record(e);
            }
        }
        movements = list;
    }

    /**
     * Adds a new movement to the Pokemon if valid
     * 
     * @param mov The Movement to add
     * @throws PoobkemonException If the movement already exists or is invalid for this Pokemon
     */
    public void addMovement(Movement mov) throws PoobkemonException{
        HashSet<Movement> movementsDontValid = new HashSet<>(movementsWeaksForMe());
        if (movements.contains(mov) || movementsDontValid.contains(mov)) {
            throw new PoobkemonException(PoobkemonException.CANT_ADD_MOVEMENT);
        }
        movements.add(mov);
    }

    /**
     * Gets all state-changing movements available to the Pokemon
     * 
     * @return ArrayList of MovementState objects
     */
    public ArrayList<MovementState> getStateMovements() {
        ArrayList<MovementState> stateMovements = new ArrayList<>();
        for (Movement movement : movements) {
            if (movement instanceof MovementState) {
                stateMovements.add((MovementState) movement);
            }
        }
        return stateMovements;
    }
    
    /**
     * Gets all tribute movements available to the Pokemon
     * 
     * @return ArrayList of MovementTribute objects
     */
    public ArrayList<MovementTribute> gettTributeMovements() {
        ArrayList<MovementTribute> tributeMovements = new ArrayList<>();
        for (Movement movement : movements) {
            if (movement instanceof MovementTribute) {
                tributeMovements.add((MovementTribute) movement);
            }
        }
        return tributeMovements;
    }
    
    /**
     * Gets all movements that can increase defense
     * 
     * @return ArrayList of MovementTribute objects that affect defense
     */
    public ArrayList<MovementTribute> getMovementsGiveDefense() {
        ArrayList<MovementTribute> stateMovements = gettTributeMovements();
        ArrayList<MovementTribute> deffenseGivers = new ArrayList<>();

        for (MovementTribute stateMov : stateMovements) {
            if (stateMov.getStateTo().containsKey("Defense")) {
                deffenseGivers.add(stateMov);
            }
        }
        return deffenseGivers;
    }

    /**
     * Gets all movements that can increase attack
     * 
     * @return ArrayList of MovementTribute objects that affect attack
     */
    public ArrayList<MovementTribute> getMovementsGiveAttack() {
        ArrayList<MovementTribute> stateMovements = gettTributeMovements();
        ArrayList<MovementTribute> attackGivers = new ArrayList<>();
        for (MovementTribute stateMov : stateMovements) {
            if (stateMov.getStateTo().containsKey("Attack")) {
                attackGivers.add((MovementTribute) stateMov);
            }
        }
        return attackGivers;
    }

    /**
     * Revives the Pokemon using an item with specified recovery percentage
     * 
     * @param recover The percentage of max HP to recover (0.0 to 1.0)
     * @throws PoobkemonException If the Pokemon is already alive or has already used a revive item
     */
    public void revivedByItem(double recover) throws PoobkemonException{
        if (isAlive || usedReviveItem) throw new PoobkemonException(PoobkemonException.THE_POKEMON_IS_ALIVE_OR_REVIVE_CANT_BE_USE);
        ps = (int) (maxPs*recover);
        isAlive = true;
        usedReviveItem = true;
    }
    
    /**
     * Checks if the Pokemon has already used a revive item
     * 
     * @return true if a revive item has been used, false otherwise
     */
    public boolean haveUsedReviveItem(){
        return usedReviveItem;
    }
    
    /**
     * Increases a specific stat by the specified amount
     * 
     * @param stat The name of the stat to increase
     * @param amount The amount to increase the stat by
     */
    public void increaseStat(String stat, int amount ){
        //mirar pues
    }
    
    /**
     * Reduces the Pokemon's health points by the specified amount
     * If health reaches 0 or below, the Pokemon is knocked out
     * 
     * @param losePs The amount of health points to lose
     */
    public void losePS(double losePs){
        ps -= (int)losePs;
        if (ps<=0) pokemonKO();
    }
    
    /**
     * Sets the Pokemon as knocked out (HP = 0, not alive)
     */
    private void pokemonKO(){
        ps = 0;
        isAlive = false;
    }
    
    /**
     * Increases the Pokemon's health points by the specified amount
     * 
     * @param gainPs The amount of health points to gain
     */
    public void gainPS(int gainPs){
        ps += gainPs; 
    }
    
    /**
     * Increases the Pokemon's attack stat by the specified amount
     * 
     * @param newAttack The amount to increase the attack by
     */
    public void gainAttack(int newAttack){  // pociones
        attack += newAttack;
    }
    
    /**
     * Decreases the Pokemon's attack stat by the specified amount
     * 
     * @param newAttack The amount to decrease the attack by
     * @throws PoobkemonException If the amount is negative or would reduce attack below 0
     */
    public void loseAttack(int newAttack) throws PoobkemonException{ //estados
        if (newAttack < 0) throw new PoobkemonException(PoobkemonException.INVALID_VALUES);
        if (attack - newAttack <= 0) throw new PoobkemonException(PoobkemonException.INVALID_VALUES);
        attack -= newAttack;
    }
    
    /**
     * Increases the Pokemon's special attack stat by the specified amount
     * 
     * @param plusSpecialAttack The amount to increase the special attack by
     */
    public void gainSpecialAttack(int plusSpecialAttack){
        specialAttack += plusSpecialAttack;
    }
    
    /**
     * Increases the Pokemon's special defense stat by the specified amount
     * 
     * @param plusSpecialDefense The amount to increase the special defense by
     */
    public void gainSpecialDefense(int plusSpecialDefense){
        specialDefense += plusSpecialDefense;
    }
    
    /**
     * Decreases the Pokemon's special attack stat by the specified amount
     * 
     * @param minusSpecialAttack The amount to decrease the special attack by
     * @throws PoobkemonException If the amount is negative or would reduce special attack below 0
     */
    public void loseSpecialAttack(int minusSpecialAttack) throws PoobkemonException{
        if (minusSpecialAttack < 0) throw new PoobkemonException(PoobkemonException.INVALID_VALUES);
        if (specialAttack - minusSpecialAttack <= 0) throw new PoobkemonException(PoobkemonException.INVALID_VALUES);
        specialAttack -= minusSpecialAttack;
    }
    
    /**
     * Increases the Pokemon's defense stat by the specified amount
     * 
     * @param plusDefense The amount to increase the defense by
     */
    public void gainDefense(int plusDefense){
        defense += plusDefense;
    }
    
    /**
     * Decreases the Pokemon's defense stat by the specified amount
     * 
     * @param minusDefense The amount to decrease the defense by
     * @throws PoobkemonException If the amount is negative or would reduce defense below 0
     */
    public void loseDefense(int minusDefense) throws PoobkemonException{
        if (minusDefense < 0) throw new PoobkemonException(PoobkemonException.INVALID_VALUES);
        if (defense - minusDefense <= 0) throw new PoobkemonException(PoobkemonException.INVALID_VALUES);
        defense -= minusDefense;
    }
    
    /**
     * Increases the Pokemon's velocity stat by the specified amount
     * 
     * @param plusVelocity The amount to increase the velocity by
     */
    public void gainVelocity(int plusVelocity){
        velocity += plusVelocity;
    }
    
    /**
     * Decreases the Pokemon's velocity stat by the specified amount
     * 
     * @param minusVelocity The amount to decrease the velocity by
     * @throws PoobkemonException If the amount is negative or would reduce velocity below 0
     */
    public void loseVelocity(int minusVelocity) throws PoobkemonException{
        if (minusVelocity < 0) throw new PoobkemonException(PoobkemonException.INVALID_VALUES);
        if (velocity - minusVelocity <= 0) throw new PoobkemonException(PoobkemonException.INVALID_VALUES);
        velocity -= minusVelocity;
    }

    /**
     * Adds a tribute effect to the Pokemon
     * 
     * @param effect The TributeEffect to add
     * @throws PoobkemonException If the effect already exists
     */
    public void addEffect(TributeEffect effect) throws PoobkemonException{
        if(tributeEffects.contains(effect)) throw new PoobkemonException(PoobkemonException.INVALID_EFFECT_TRIBUTE_EFFECT);
        tributeEffects.add(effect);
    }

    /**
     * Adds a status effect to the Pokemon
     * 
     * @param effect The StatusEffect to add
     * @throws PoobkemonException If another status effect is already active
     */
    public void addEffect(StatusEffect effect) throws PoobkemonException{
        if(statusEffect !=null && !statusEffect.isOver()) throw new PoobkemonException(PoobkemonException.INVALID_EFFECT_STATUS_EFFECT);
        statusEffect = effect;
    }

    /**
     * Applies all active tribute effects to the Pokemon
     * 
     * @throws PoobkemonException If an effect cannot be applied
     */
    public void affectPokemonStatus() throws PoobkemonException{
        for(TributeEffect st: tributeEffects){
            st.affectPokemon(this);
        }
    }
    
    /**
     * Uses a specific movement against a target Pokemon
     * 
     * @param movimiento The Movement to use
     * @param target The target Pokemon
     * @throws PoobkemonException If the movement cannot be performed
     */
    public void useMovement(Movement movimiento, Pokemon target) throws PoobkemonException{
        if (!isAlive) throw new PoobkemonException(PoobkemonException.POKEMON_DIE);
        if (!movements.contains(movimiento)) throw new PoobkemonException(PoobkemonException.POKEMON_DONT_HAVE_THESE_MOVEMENT);
        if (statusEffect != null) throw new PoobkemonException(PoobkemonException.CANT_DO_MOVEMENT);
        statusEffectVerify();

        if (dontHavePPForAllMovement()){actionF(target);} //seria mostrar ese movimiento, no que ejecute de una


        System.out.println(name +" ha usado: "+ movimiento.getName());
        movimiento.doAttackTo(this, target);
    }
    
    /**
     * Uses a movement by name against a target Pokemon
     * 
     * @param movimiento The name of the movement to use
     * @param target The target Pokemon
     * @throws PoobkemonException If the movement is not found or cannot be performed
     */
    public void useMovement(String movimiento, Pokemon target) throws PoobkemonException{
        for (Movement m : movements){
            if(m.getName().equals(movimiento)) {
                useMovement(m, target);
                return;
            }
        }
        throw new PoobkemonException(PoobkemonException.MOVEMENT_NOT_FOUND);
    }

    /**
     * Verifies if a status effect prevents movement
     * 
     * @throws PoobkemonException If a status effect prevents movement
     */
    public void statusEffectVerify() throws PoobkemonException{
        if (statusEffect != null) throw new PoobkemonException(PoobkemonException.CANT_DO_MOVEMENT);
        //statusEffect.affectPokemon(this);
    }

    /**
     * Removes the current status effect
     */
    public void removeStatusEffect(){
        statusEffect = null;
    }

    /**
     * Gets all movements that can currently be used (have PP)
     * 
     * @return ArrayList of usable Movement objects
     */
    public ArrayList<Movement> movementsUsables(){
        ArrayList<Movement> temp = new ArrayList<>();
        for(Movement m: movements){
            if (m.canMakeMove()){
                temp.add(m);
            }
        }
        return temp;
    }

    /**
     * Updates PP of movements based on time limits
     */
    public void limitOfTime(){
        for (Movement move: movements){
            move.limitOfTime();
        }
    }

    /**
     * Selects a random usable movement
     * 
     * @param target The target Pokemon (not used in selection)
     * @return A randomly selected Movement
     */
    public Movement aleatoryMovement(Pokemon target){
        ArrayList<Movement> temp = movementsUsables();
        Random random = new Random();
        int ramdomNum = random.nextInt(temp.size());
        Movement aleatoryMovement = temp.get(ramdomNum);
        return aleatoryMovement;
    }

    /**
     * Checks if all movements are out of PP
     * 
     * @return true if all movements have 0 PP, false otherwise
     */
    public boolean dontHavePPForAllMovement(){
        for (Movement m : movements){
            if (m.getPP() > 0){return false;}
        }
        return true;
    }

    /**
     * Uses the Struggle move when no other moves have PP
     * 
     * @param target The target Pokemon
     */
    public void actionF(Pokemon target){
        struggle.AttackToStruggle(this,target);
    }

    /**
     * Compares this Pokemon with another object for equality
     * 
     * @param ob The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object ob){
        return equals((Pokemon) ob);
    }

    /**
     * Compares this Pokemon with another Pokemon for equality
     * 
     * @param pokemon The Pokemon to compare with
     * @return true if the Pokemon are equal, false otherwise
     */
    public boolean equals(Pokemon pokemon){  //mirar caso null
        return name.equals(pokemon.getName()) &&
           level == pokemon.getLevel() &&
           ps == pokemon.getPs() &&
           attack == pokemon.getAttack() &&
           specialAttack == pokemon.getSpecialAttack() &&
           defense == pokemon.getDefense() &&
           velocity == pokemon.getVelocity() &&
           principalType.equals(pokemon.getPrincipalType()) &&
           (secondaryType == null ? pokemon.getSecondaryType() == null : secondaryType.equals(pokemon.getSecondaryType()));
    }

    /**
     * Gets movements that are weak against this Pokemon's type
     * 
     * @return ArrayList of Movement objects that are weak against this Pokemon
     */
    public ArrayList<Movement> movementsWeaksForMe(){
        ArrayList<Movement> movementsWeak = new ArrayList<>();
        for (int i = 0; i < movements.size(); i++){
            if (movements.get(i).getMultiplicator(principalType) > 1.0){
                movementsWeak.add(movements.get(i));
            }
        }
        return movementsWeak;
    }

    /**
     * Creates a copy of this Pokemon
     * 
     * @return A new Pokemon object with the same attributes
     */
    public Pokemon copy(){
        return new Pokemon(name,level,ps,attack,specialAttack,defense,specialDefense,velocity,principalType,secondaryType,pokedexIndex);
    }
    
    /**
     * Gets all types of this Pokemon (primary and secondary if present)
     * 
     * @return ArrayList of PokemonType objects
     */
    public ArrayList<PokemonType> getTypes(){
        ArrayList<PokemonType> temp = new ArrayList<>();
        temp.add(principalType);
        if (secondaryType != null) temp.add(secondaryType);
        return temp;
    }
    
    /**
     * Creates an HTML-formatted tooltip description of the Pokemon
     * 
     * @return HTML string with Pokemon details for tooltip display
     */
    public String createPokemonForToolTip() {
        return "<html>" +
                "<b style='font-size:12px; color:blue;'>" + name + "</b><br>" +
                "Type: " + getTypes() + "<br>" +
                "Level: " + level + "<br>" +
                "HP: " + ps + "<br>" +
                "Attack: " + attack + "<br>" +
                "Defense: " + defense + "<br>" +
                "Velocity: " +velocity+"<br>"+
                "Special Attack: " +specialAttack+"<br>"+
                "Special Defense: " +specialDefense+"<br>"+
                "</html>";
    }
}