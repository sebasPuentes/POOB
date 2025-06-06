package domain;
import java.io.*;
/**
 * Clase que representa una hiperpoción en el juego
 * Restaura una gran cantidad de PS
 */
public class HyperPotion extends Potion implements Serializable {
    /**
     * Constructor por defecto para una hiperpoción
     * Restaura 200 PS
     */
    public HyperPotion() {
        super("Hiperpoción", "Restaura 200 PS de un Pokémon.", 200);
    }
    
    /**
     * Constructor alternativo que permite personalizar la cantidad de curación
     * @param healAmount Cantidad de PS que restaura
     */
    public HyperPotion(int healAmount) {
        super("Hiperpoción", "Restaura " + healAmount + " PS de un Pokémon.", healAmount);
    }
}
