package src.domain;

/**
 * Clase que maneja las relaciones de efectividad entre los diferentes tipos de Pokémon
 * Ahora utiliza la enumeración PokemonType para operaciones tipadas
 */
public class Type {
    
    /**
     * Obtiene el multiplicador de efectividad de un tipo atacante contra un tipo defensor
     * @param attackType Tipo del ataque (PokemonType)
     * @param defenseType Tipo del defensor (PokemonType)
     * @return Multiplicador de efectividad (0.0, 0.5, 1.0, 2.0)
     */
    public static double getEffectiveness(PokemonType attackType, PokemonType defenseType) {
        return TypeEffectiveness.getEffectiveness(attackType, defenseType);
    }
    
    /**
     * Obtiene el multiplicador de efectividad utilizando nombres de tipo como cadenas
     * @param attackType Nombre del tipo atacante
     * @param defenseType Nombre del tipo defensor
     * @return Multiplicador de efectividad (0.0, 0.5, 1.0, 2.0)
     */
    public static double getEffectiveness(String attackType, String defenseType) {
        return TypeEffectiveness.getEffectiveness(
            PokemonType.fromString(attackType), 
            PokemonType.fromString(defenseType)
        );
    }
    
    /**
     * Calcula la efectividad total considerando el tipo secundario
     * @param attackType Tipo del ataque
     * @param primaryDefenseType Tipo primario del defensor
     * @param secondaryDefenseType Tipo secundario del defensor (puede ser null)
     * @return Multiplicador de efectividad total
     */
    public static double getTotalEffectiveness(PokemonType attackType, 
                                              PokemonType primaryDefenseType, 
                                              PokemonType secondaryDefenseType) {
        return TypeEffectiveness.getTotalEffectiveness(attackType, primaryDefenseType, secondaryDefenseType);
    }
    
    /**
     * Calcula la efectividad total utilizando nombres de tipo como cadenas
     * @param attackType Nombre del tipo atacante
     * @param primaryDefenseType Nombre del tipo defensor primario
     * @param secondaryDefenseType Nombre del tipo defensor secundario (puede ser null)
     * @return Multiplicador de efectividad total
     */
    public static double getTotalEffectiveness(String attackType, 
                                              String primaryDefenseType, 
                                              String secondaryDefenseType) {
        return TypeEffectiveness.getTotalEffectiveness(
            PokemonType.fromString(attackType),
            PokemonType.fromString(primaryDefenseType),
            secondaryDefenseType != null ? PokemonType.fromString(secondaryDefenseType) : null
        );
    }
    
    /**
     * Describe la efectividad para mostrar en mensajes de batalla
     * @param effectiveness Valor de efectividad
     * @return Mensaje descriptivo
     */
    public static String getEffectivenessMessage(double effectiveness) {
        return TypeEffectiveness.getEffectivenessMessage(effectiveness);
    }
    
    /**
     * Determina si un movimiento es de categoría física o especial según su tipo
     * @param type Tipo del movimiento
     * @return "Fisico" o "Especial"
     */
    public static String getMoveCategory(PokemonType type) {
        return type != null ? type.getTypeMov() : "Físico";
    }
    
    /**
     * Determina si un movimiento es de categoría física o especial según su tipo (usando String)
     * @param typeName Nombre del tipo del movimiento
     * @return "Fisico" o "Especial"
     */
    public static String getMoveCategory(String typeName) {
        PokemonType type = PokemonType.fromString(typeName);
        return type != null ? type.getTypeMov() : "Físico";
    }
}
