package src.domain;

/**
 * Clase que representa una superpoción en el juego
 * Restaura una cantidad media de PS
 */
public class SuperPotion extends Potion {
    /**
     * Constructor por defecto para una superpoción
     * Restaura 50 PS
     */
    public SuperPotion() {
        super("Superpoción", "Restaura 50 PS de un Pokémon.", 50);
    }
    
    /**
     * Constructor alternativo que permite personalizar la cantidad de curación
     * @param healAmount Cantidad de PS que restaura
     */
    public SuperPotion(int healAmount) {
        super("Superpoción", "Restaura " + healAmount + " PS de un Pokémon.", healAmount);
    }
}
