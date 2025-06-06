package src.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa un Pokémon con sus características y comportamientos
 */
public class Pokemon implements Serializable {
    private String name;
    private int level = 100;
    private int maxPs;
    private int currentPs;
    private int attack;
    private int specialAttack;
    private int defense;
    private int specialDefense;
    private int velocity;
    private PokemonType principalType;
    private PokemonType secondaryType;
    private ArrayList<Movement> movements;
    private ArrayList<String> moves;
    private StatusEffect statusEffect;
    private ArrayList<AttributeEffect> attributeEffects;

    private boolean isAlive = true;
    private boolean usedRevive = false;

    public Pokemon(String name, PokemonType principalType) {
        this.name = name;
        this.principalType = principalType;
        secondaryType = null;
        movements = new ArrayList<>(4);

        Random random = new Random();
        maxPs = 200 + random.nextInt(100);
        currentPs = maxPs;
        attack = 100 + random.nextInt(50);
        defense = 100 + random.nextInt(50);
        velocity = 100 + random.nextInt(50);
    }

    /**
     * Constructor para crear un Pokémon con tipo secundario
     *
     * @param name          Nombre del Pokémon
     * @param principalType Tipo principal del Pokémon
     * @param secondaryType Tipo secundario del Pokémon
     */
    public Pokemon(String name, PokemonType principalType, PokemonType secondaryType) {
        this(name, principalType);
        this.secondaryType = secondaryType;
    }

    public Pokemon(String name, int level, int currentPs,int attack,int specialAttack,int defense,int specialDefense,int velocity,PokemonType principalType, PokemonType secondaryType, int index) {
        this.name = name;
        this.level = level;
        this.currentPs = currentPs;
        this.attack = attack;
        this.specialAttack = specialAttack;
        this.defense = defense;
        this.specialDefense = specialDefense;
        this.velocity = velocity;
        this.principalType = principalType;
        this.secondaryType = secondaryType;
        movements = new ArrayList<>(4);
    }

    /**
     * Cura al Pokémon restaurando todos sus PS
     */
    public void heal() {
        currentPs = maxPs;
    }

    /**
     * Cura parcialmente al Pokémon
     *
     * @param amount Cantidad de PS a restaurar
     */
    public void heal(int amount) {
        currentPs = Math.min(maxPs, currentPs + amount);
    }

    public boolean usedItem(){
        return usedRevive;
    }

    /**
     * Método para que el Pokémon pierda puntos de salud
     *
     * @param damage Cantidad de daño recibido
     * @return Puntos de salud restantes
     */
    public void losePS(int damage) {
        currentPs = Math.max(0, currentPs - damage);
        if(currentPs <= 0){
            isFainted();
        }
    }

    public boolean addMovement(Movement movement) {
        HashSet<Movement> movementsDontValid = new HashSet<>(movementsWeaks());
        if (movements.contains(movement)) {
            return false;
        }
        movements.add(movement);
        return true;
    }

    public void setMovements( Movement[] newMovements){
        ArrayList<Movement> list = new ArrayList<>();
        for (Movement m : newMovements){
            try {
                if (!movements.contains(m)) {
                    list.add(m);
                }
            } catch(Exception e){
                Log.record(e);
            }
        }
        movements = list;
    }

    public void addEffect(StatusEffect effect){
        statusEffect = effect;
    }

    public void addEffect(AttributeEffect effect) {
       attributeEffects.add(effect);
    }

    public void useMovement(Movement movimiento, Pokemon target) {
        movimiento.doAttackTo(this, target);
    }

    public void removeStatusEffect(){
        statusEffect = null;
    }

    public void increaseStat(String stat, int amount ){
    }
//--------------------Struggle-------------------
//    public boolean dontHavePPForAllMovement(){
//        for (Movement m : movements){
//            if (m.getPP() > 0){return false;}
//        }
//        return true;
//    }

//    public void actionF(Pokemon target){
//        struggle.AttackToStruggle(this,target);
//    }
    //-------------------Aleatory-------------------
    public ArrayList<Movement> movementsUsables(){
        ArrayList<Movement> temp = new ArrayList<>();
        for(Movement m: movements){
            if (m.canUse()){
                temp.add(m);
            }
        }
        return temp;
    }

    /**
     * Selecciona un movimiento aleatorio del Pokémon
     *
     * @param target Pokémon objetivo del ataque
     * @return Movimiento aleatorio
     */
    public Movement aleatoryMovement(Pokemon target){
        ArrayList<Movement> temp = movementsUsables();
        Random random = new Random();
        int ramdomNum = random.nextInt(temp.size());
        Movement aleatoryMovement = temp.get(ramdomNum);
        return aleatoryMovement;
    }

    //-------------------Weaks-------------------
    public ArrayList<Movement> movementsWeaks(){
        ArrayList<Movement> movementsWeak = new ArrayList<>();
        for (int i = 0; i < movements.size(); i++){
            if (movements.get(i).getMultiplicator(principalType) > 1.0){
                movementsWeak.add(movements.get(i));
            }
        }
        return movementsWeak;
    }

    //-------------------Getters y Setters-------------------
    public int getPs(){
        return currentPs;
    }

    public int getVelocity(){
        return velocity;
    }

    public int getSpecialAttack(){
        return specialAttack;
    }

    public String getName() {
        return name;
    }
    public PokemonType getPrincipalType(){
        return principalType;
    }
    public PokemonType getSecondaryType(){
        return secondaryType;
    }
    public int getLevel(){
        return level;
    }
    public int getAttack(){
        return attack;
    }
    public int getDefense(){
        return defense;
    }

    public boolean isAlive() {
        return currentPs > 0;
    }

    public int getCurrentPs() {
        return currentPs;
    }

    public int getMaxPs() {
        return maxPs;
    }

    public int getSpecialDefense(){
        return specialDefense;
    }

    public ArrayList<Movement> getMovements(){
        return movements;
    }

    public boolean isFainted() {
        isAlive = false;
        return currentPs <= 0;
    }

    public Movement getMovement(Movement movement){
        for (Movement m : movements) {
            if (m.getName().equals(movement.getName())) {
                return m;
            }
        }
        return null;

    }
    //-------------------ToString-------------------
    @Override
    public String toString() {
        return name + " (Nivel " + level + ") - PS: " + currentPs + "/" + maxPs +
                " - Tipo: " + principalType +
                (secondaryType != null ? "/" + secondaryType : "");
    }


}

