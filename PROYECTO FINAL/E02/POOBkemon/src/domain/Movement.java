package src.domain;

/**
 * Clase que representa un movimiento o ataque de Pokémon
 */
public class Movement {
    private String name;
    private String type;
    private int power;
    private int accuracy;
    private int ppMax;
    private int ppCurrent;
    private String category; // Físico, Especial o Estado
    private String effect; // Efecto adicional como paralizar, quemar, etc.
    private int effectChance; // Probabilidad del efecto (0-100)
    
    /**
     * Constructor para crear un movimiento
     * @param name Nombre del movimiento
     * @param type Tipo del movimiento (Agua, Fuego, etc.)
     * @param power Poder base del ataque
     * @param accuracy Precisión del ataque (0-100)
     * @param pp Puntos de poder máximos
     * @param category Categoría del movimiento
     */
    public Movement(String name, String type, int power, int accuracy, int pp, String category) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.ppMax = pp;
        this.ppCurrent = pp;
        this.category = category;
        this.effect = null;
        this.effectChance = 0;
    }
    
    /**
     * Constructor para movimientos con efectos secundarios
     * @param name Nombre del movimiento
     * @param type Tipo del movimiento
     * @param power Poder base del ataque
     * @param accuracy Precisión del ataque
     * @param pp Puntos de poder máximos
     * @param category Categoría del movimiento
     * @param effect Efecto secundario
     * @param effectChance Probabilidad del efecto secundario
     */
    public Movement(String name, String type, int power, int accuracy, int pp, 
                    String category, String effect, int effectChance) {
        this(name, type, power, accuracy, pp, category);
        this.effect = effect;
        this.effectChance = effectChance;
    }
    
    /**
     * Utiliza el movimiento reduciendo sus PP
     * @return true si se pudo usar, false si no hay PP
     */
    public boolean use() {
        if (ppCurrent > 0) {
            ppCurrent--;
            return true;
        }
        return false;
    }
    
    /**
     * Verifica si el ataque acierta según su precisión
     * @return true si acierta, false si falla
     */
    public boolean hits() {
        return Math.random() * 100 < accuracy;
    }
    
    /**
     * Verifica si se activa el efecto secundario
     * @return true si se activa, false en caso contrario
     */
    public boolean activatesEffect() {
        return effect != null && Math.random() * 100 < effectChance;
    }
    
    /**
     * Restaura completamente los PP del movimiento
     */
    public void restorePP() {
        ppCurrent = ppMax;
    }
    
    /**
     * Restaura una cantidad específica de PP
     * @param amount Cantidad de PP a restaurar
     */
    public void restorePP(int amount) {
        ppCurrent = Math.min(ppMax, ppCurrent + amount);
    }
    
    // Getters
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public int getPower() {
        return power;
    }
    
    public int getAccuracy() {
        return accuracy;
    }
    
    public int getPpCurrent() {
        return ppCurrent;
    }
    
    public int getPpMax() {
        return ppMax;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getEffect() {
        return effect;
    }
    
    public int getEffectChance() {
        return effectChance;
    }
    
    @Override
    public String toString() {
        return name + " (" + type + ") - Poder: " + power + " - Precisión: " + accuracy + 
               " - PP: " + ppCurrent + "/" + ppMax;
    }
}
