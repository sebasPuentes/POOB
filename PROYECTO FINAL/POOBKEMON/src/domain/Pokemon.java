package domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
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
    private ArrayList<AttributeEffect> attributeEffects = new ArrayList<>();

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
        maxPs = currentPs;
    }

    //-------------------HEAL-------------------
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

    //-------------------LOSE-------------------
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


    //-------------------ADDERS-------------------
    public void addEffect(StatusEffect effect) throws POOBkemonException {
        if (statusEffect != null) {
            throw new POOBkemonException(POOBkemonException.POKEMON_HAS_EFFECT);
        }
        statusEffect = effect;
    }

    public void addEffect(AttributeEffect effect) throws POOBkemonException {
        if(attributeEffects.contains(effect)) affectPokemonStatus();
        attributeEffects.add(effect);
    }


    public boolean addMovement(Movement movement) {
        HashSet<Movement> movementsDontValid = new HashSet<>(movementsWeaks());
        if (movements.contains(movement)) {
            return false;
        }
        movements.add(movement);
        return true;
    }
    //-------------------REMOVER-------------------
    public void removeStatusEffect(){
        statusEffect = null;
    }

    //-------------------USER-------------------
    public int useMovement(Movement movimiento, Pokemon target) {
        try {
            int damage = movimiento.doAttackTo(this, target);
            return damage;
        }catch (POOBkemonException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void applyStatusEffects() {
        if (statusEffect != null) {
            System.out.println("Aplicando efecto de estado");
            try {
                statusEffect.affectPokemon(this);
                System.out.println("Efecto de estado aplicado");
            }catch (POOBkemonException e){
                System.out.println("Error en el efecto de estado");
            }
        }
    }

    public void affectPokemonStatus()  {
        if (attributeEffects != null) {
            for (AttributeEffect effect : attributeEffects) {
                try {
                    effect.affectPokemon(this);
                } catch (POOBkemonException e) {
                    System.out.println(POOBkemonException.EFFECT_DURATION_OVER);
                }
            }
        }
    }

    /**
     * Incrementa una estadística específica del Pokémon
     * 
     * @param stat Nombre de la estadística a incrementar (attack, defense, specialAttack, specialDefense, velocity, maxPs)
     * @param amount Cantidad a incrementar
     */
    public void increaseStat(String stat, int amount) {
        String lowerStat = stat.toLowerCase();
        if ("attack".equals(lowerStat)) {
            this.attack += amount;
        } else if ("defense".equals(lowerStat)) {
            this.defense += amount;
        } else if ("specialattack".equals(lowerStat)) {
            this.specialAttack += amount;
        } else if ("specialdefense".equals(lowerStat)) {
            this.specialDefense += amount;
        } else if ("velocity".equals(lowerStat)) {
            this.velocity += amount;
        } else if ("ps".equals(lowerStat)) {
            this.maxPs += amount;
            this.currentPs += amount;
        } else {
            Log.record(new Exception("Estadística no válida: " + stat));
        }
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

    //-------------------Getters-------------------


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

    public Movement[] getMovementsinArray(){
        Movement[] movementsArray = new Movement[movements.size()];
        for (int i = 0; i < movements.size(); i++){
            movementsArray[i] = movements.get(i);
        }
        return movementsArray;
    }

    public ArrayList<AttributeMovement> getMovementsGiveAttack() {
        ArrayList<AttributeMovement> stateMovements = gettTributeMovements();
        ArrayList<AttributeMovement> attackGivers = new ArrayList<>();
        for (AttributeMovement stateMov : stateMovements) {
            if (stateMov.getStateTo().containsKey("Attack")) {
                attackGivers.add(stateMov);
            }
        }
        return attackGivers;
    }

    public ArrayList<AttributeMovement> getMovementsGiveDefense() {
        ArrayList<AttributeMovement> stateMovements = gettTributeMovements();
        System.out.println("TAMANO DE MOVIMIENTOS: " + stateMovements.size());
        ArrayList<AttributeMovement> deffenseGivers = new ArrayList<>();
        for (AttributeMovement stateMov : stateMovements) {
            if (stateMov.getStateTo().containsKey("Defense")) {
                deffenseGivers.add(stateMov);
            }
        }
        System.out.println("TAMANO DE MOVIMIENTOS DEFENSIVOS: " + deffenseGivers.size());
        return deffenseGivers;
    }

    public ArrayList<AttributeMovement> gettTributeMovements() {
        ArrayList<AttributeMovement> tributeMovements = new ArrayList<>();
        for (Movement movement : movements) {
            if (movement instanceof AttributeMovement) {
                tributeMovements.add((AttributeMovement) movement);
            }
        }
        return tributeMovements;
    }

    public Movement getMovement(Movement movement){
        for (Movement m : movements) {
            if (m.getName().equals(movement.getName())) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Movement> movementsWeaks(){
        ArrayList<Movement> movementsWeak = new ArrayList<>();
        for (int i = 0; i < movements.size(); i++){
            if (movements.get(i).getMultiplicator(principalType) > 1.0){
                movementsWeak.add(movements.get(i));
            }
        }
        return movementsWeak;
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

    public ArrayList<Movement> movementsUsables(){
        ArrayList<Movement> temp = new ArrayList<>();
        for(Movement m: movements){
            if (m.canUse()){
                temp.add(m);
            }
        }
        return temp;
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

    public Movement getMovement(String name) {
        for (Movement m : movements) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<AttributeEffect> getAtributeEffects(){
        return attributeEffects;
    }

    public StatusEffect getStatusEffect(){
        return statusEffect;
    }

    //-------------------BOOLEAN-------------------
    public boolean isFainted() {
        isAlive = false;
        return currentPs <= 0;
    }

    public boolean usedItem(){
        return usedRevive;
    }

    //-----------------------------------------------
    /**
     * Crea una copia del Pokémon actual
     * @return Una nueva instancia de Pokémon con las mismas características
     */
    public Pokemon copy() {
        Pokemon copy;
        if (secondaryType != null) {
            copy = new Pokemon(name, principalType, secondaryType);
        } else {
            copy = new Pokemon(name, principalType);
        }
        
        copy.level = this.level;
        copy.attack = this.attack;
        copy.defense = this.defense;
        copy.specialAttack = this.specialAttack;
        copy.specialDefense = this.specialDefense;
        copy.velocity = this.velocity;
        copy.maxPs = this.maxPs;
        copy.currentPs = this.currentPs;
        
        // Copiar los movimientos no es necesario ya que se asignarán nuevos
        return copy;
    }

    /**
     * Elimina todos los movimientos actuales del Pokémon
     */
    public void clearMovements() {
        if (movements != null) {
            movements.clear();
        } else {
            movements = new ArrayList<>(4);
        }
    }
    
    //-------------------ToString-------------------
    @Override
    public String toString() {
        return name + " (Nivel " + level + ") - PS: " + currentPs + "/" + maxPs +
                " - Tipo: " + principalType +
                (secondaryType != null ? "/" + secondaryType : "");
    }


}

