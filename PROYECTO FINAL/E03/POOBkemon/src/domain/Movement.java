package src.domain;

import java.io.Serializable;


/**
 * Clase que representa un movimiento o ataque de Pokémon
 */
public abstract class Movement implements TypeEffectiveness, Serializable {
    protected String name;
    protected String description;
    protected int pp;
    protected int power;
    protected int precision = 100;

    protected PokemonType type;
    protected int priority;

    /**
     * Constructor para un movimiento sin efecto secundario
     * @param name Nombre del movimiento
     * @param type Tipo del movimiento
     * @param power Poder base
     * @param precision Precisión (de 0 a 100)
     * @param pp Puntos de poder (usos)
     */
    public Movement(String name, String description, int pp, int power, int precision, PokemonType type, int priority){
        this.name = name;
        this.description = description;
        this.pp = pp;
        this.power = power;
        this.precision = precision;
        this.type = type;
        this.priority = priority;
    }

    public void losePP()throws POOBkemonException{
        if (pp- 1 <= 0) throw new POOBkemonException(POOBkemonException.INVALID_VALUES);
        pp --;}

    public abstract Movement copy();

    //---------------getters y setters-------------------
    public void setPP(int pp){
        this.pp = pp;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getPrecision(){
        return precision;
    }

    public PokemonType getType(){
        return type;
    }

    public int getPriority(){
        return priority;
    }

    public double getMultiplicator(PokemonType defender){
        return multiplicadores[type.getIndex()][defender.getIndex()];
    }

    public boolean canUse(){
        return pp > 0;
    }

    public int getPP(){
        return pp;
    }

    public int getPower(){
        return power;
    }

}

