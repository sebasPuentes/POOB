package src.domain;

/**
 * Clase que representa una poción normal en el juego
 * Restaura una pequeña cantidad de PS
 */
public class NormalPotion extends Potion {
    /**
     * Constructor por defecto para una poción normal
     * Restaura 20 PS
     */
    public NormalPotion() {
        super("Poción", "Restaura 20 PS de un Pokémon.", 20);
    }
    
    /**
     * Constructor alternativo que permite personalizar la cantidad de curación
     * @param healAmount Cantidad de PS que restaura
     */
    public NormalPotion(int healAmount) {
        super("Poción", "Restaura " + healAmount + " PS de un Pokémon.", healAmount);
    }
}
