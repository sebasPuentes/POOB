package src.domain;

import java.io.Serializable;

/**
 * Clase abstracta que representa un ítem en el juego Pokémon
 * El tipo de ítem se determina por la clase que lo implementa
 */
public abstract class Item implements Serializable {
    protected String name;
    protected String description;
    
    /**
     * Constructor para crear un ítem básico
     * @param name Nombre del ítem
     * @param description Descripción del ítem
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    /**
     * Método abstracto para usar el ítem (debe ser implementado por las subclases)
     * @param target Objetivo al que se aplica el ítem (Pokémon, Entrenador, etc.)
     * @return true si se usó correctamente, false en caso contrario
     */
    public abstract boolean use(Object target);
    
    /**
     * Obtiene el nombre del ítem
     * @return Nombre del ítem
     */
    public String getName() {
        return name;
    }
    
    /**
     * Obtiene la descripción del ítem
     * @return Descripción del ítem
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Obtiene el tipo de ítem
     * @return Nombre de la clase que define el tipo
     */
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public String toString() {
        return name + " - " + description;
    }
}
