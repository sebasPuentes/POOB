package src.domain;

/**
 * Clase que gestiona la efectividad de los ataques entre diferentes tipos de Pokémon
 * utilizando una matriz de multiplicadores.
 */
public class TypeEffectiveness {
    
    // Matriz de multiplicadores de efectividad entre tipos
    private static final double[][] multiplicadores = {
        /*               ACERO AGUA BICHO DRAG ELECT FANTA FUEGO HADA HIELO LUCHA NORMA PLANTA PSIQUI ROCA SINIES TIERRA VENENO VOLADO */
        /* ACERO */     { 0.5, 0.5, 1.0, 1.0, 1.0,  1.0,  0.5, 2.0, 2.0,  1.0,  1.0,   1.0,  1.0,  2.0,  1.0,   1.0,   1.0,   0.5 },
        /* AGUA */      { 1.0, 0.5, 1.0, 1.0, 2.0,  1.0,  2.0, 1.0, 1.0,  1.0,  1.0,   2.0,  1.0,  1.0,  1.0,   2.0,   1.0,   1.0 },
        /* BICHO */     { 0.5, 1.0, 1.0, 1.0, 1.0,  0.5,  0.5, 0.5, 1.0,  0.5,  1.0,   2.0,  2.0,  1.0,  2.0,   0.5,   0.5,   2.0 },
        /* DRAGON */    { 0.5, 1.0, 1.0, 2.0, 1.0,  1.0,  0.5, 0.0, 1.0,  1.0,  1.0,   1.0,  1.0,  1.0,  1.0,   1.0,   1.0,   1.0 },
        /* ELECTRICO */ { 1.0, 1.0, 1.0, 1.0, 0.5,  1.0,  1.0, 1.0, 1.0,  1.0,  1.0,   0.5,  1.0,  1.0,  1.0,   0.0,   1.0,   2.0 },
        /* FANTASMA */  { 1.0, 1.0, 1.0, 1.0, 1.0,  2.0,  1.0, 1.0, 1.0,  0.5,  0.0,   1.0,  1.0,  1.0,  2.0,   1.0,   1.0,   1.0 },
        /* FUEGO */     { 2.0, 0.5, 2.0, 1.0, 1.0,  1.0,  0.5, 1.0, 2.0,  1.0,  1.0,   0.5,  1.0,  2.0,  1.0,   1.0,   1.0,   1.0 },
        /* HADA */      { 0.5, 1.0, 1.0, 2.0, 1.0,  1.0,  0.5, 1.0, 1.0,  2.0,  1.0,   1.0,  1.0,  0.5,  1.0,   1.0,   0.5,   1.0 },
        /* HIELO */     { 0.5, 0.5, 1.0, 2.0, 1.0,  1.0,  0.5, 1.0, 0.5,  1.0,  1.0,   2.0,  1.0,  1.0,  1.0,   2.0,   1.0,   2.0 },
        /* LUCHA */     { 2.0, 1.0, 0.5, 1.0, 1.0,  0.0,  1.0, 0.5, 2.0,  1.0,  2.0,   1.0,  0.5,  2.0,  0.5,   1.0,   0.5,   0.5 },
        /* NORMAL */    { 0.5, 1.0, 1.0, 1.0, 1.0,  0.0,  1.0, 1.0, 1.0,  1.0,  1.0,   1.0,  1.0,  0.5,  1.0,   1.0,   1.0,   1.0 },
        /* PLANTA */    { 0.5, 0.5, 0.5, 0.5, 1.0,  1.0,  2.0, 1.0, 1.0,  1.0,  1.0,   0.5,  1.0,  1.0,  1.0,   2.0,   0.5,   0.5 },
        /* PSIQUICO */  { 0.5, 1.0, 1.0, 1.0, 1.0,  2.0,  1.0, 1.0, 1.0,  2.0,  1.0,   1.0,  0.5,  1.0,  0.0,   1.0,   2.0,   1.0 },
        /* ROCA */      { 0.5, 2.0, 2.0, 1.0, 1.0,  1.0,  0.5, 1.0, 2.0,  0.5,  1.0,   1.0,  1.0,  1.0,  1.0,   0.5,   1.0,   2.0 },
        /* SINIESTRO */ { 0.5, 1.0, 1.0, 1.0, 1.0,  2.0,  1.0, 2.0, 1.0,  0.5,  1.0,   1.0,  2.0,  1.0,  0.5,   1.0,   1.0,   1.0 },
        /* TIERRA */    { 2.0, 1.0, 1.0, 1.0, 2.0,  1.0,  2.0, 1.0, 1.0,  1.0,  1.0,   0.5,  1.0,  2.0,  1.0,   1.0,   0.5,   0.0 },
        /* VENENO */    { 0.5, 1.0, 1.0, 1.0, 1.0,  0.5,  1.0, 2.0, 1.0,  1.0,  1.0,   2.0,  1.0,  0.5,  1.0,   0.5,   0.5,   1.0 },
        /* VOLADOR */   { 0.5, 1.0, 0.5, 1.0, 0.5,  1.0,  1.0, 1.0, 1.0,  2.0,  1.0,   2.0,  1.0,  0.5,  1.0,   1.0,   1.0,   1.0 },
    };

    /**
     * Obtiene el multiplicador de efectividad de un tipo atacante contra un tipo defensor
     * @param attackType Tipo del ataque
     * @param defenseType Tipo del defensor
     * @return Multiplicador de efectividad (0.0, 0.5, 1.0, 2.0)
     */
    public static double getEffectiveness(PokemonType attackType, PokemonType defenseType) {
        if (attackType == null || defenseType == null) {
            return 1.0; // Efectividad normal por defecto
        }
        
        return multiplicadores[attackType.getIndex()][defenseType.getIndex()];
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
        double effectiveness = getEffectiveness(attackType, primaryDefenseType);
        
        if (secondaryDefenseType != null) {
            effectiveness *= getEffectiveness(attackType, secondaryDefenseType);
        }
        
        return effectiveness;
    }
    
    /**
     * Describe la efectividad para mostrar en mensajes de batalla
     * @param effectiveness Valor de efectividad
     * @return Mensaje descriptivo
     */
    public static String getEffectivenessMessage(double effectiveness) {
        if (effectiveness == 0.0) {
            return "No afecta al Pokémon enemigo...";
        } else if (effectiveness < 1.0) {
            return "No es muy efectivo...";
        } else if (effectiveness > 1.0) {
            return "¡Es super efectivo!";
        } else {
            return "";
        }
    }
}
