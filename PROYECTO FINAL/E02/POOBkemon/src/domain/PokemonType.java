package src.domain;

/**
 * Enumeración de todos los tipos de Pokémon con su categoría de ataque (Físico/Especial)
 */
public enum PokemonType {
    ACERO("Fisico"),
    AGUA("Especial"),
    BICHO("Fisico"),
    DRAGON("Especial"),
    ELECTRICO("Especial"),
    FANTASMA("Fisico"),
    FUEGO("Especial"),
    HADA("Especial"),
    HIELO("Especial"),
    LUCHA("Fisico"),
    NORMAL("Fisico"),
    PLANTA("Especial"),
    PSIQUICO("Especial"),
    ROCA("Fisico"),
    SINIESTRO("Especial"),
    TIERRA("Fisico"),
    VENENO("Fisico"),
    VOLADOR("Fisico");

    private final String typeMov;

    PokemonType(String newTypeMov) {
        typeMov = newTypeMov;
    }
    
    public String getTypeMov() {
        return typeMov;
    }

    public int getIndex() {
        return this.ordinal();
    }
    
    /**
     * Obtiene el nombre del tipo en formato legible
     * @return Nombre del tipo con primera letra mayúscula
     */
    public String getName() {
        String name = this.name();
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
    
    /**
     * Convierte un String a PokemonType
     * @param typeName Nombre del tipo (puede estar en mayúsculas, minúsculas o mixto)
     * @return El tipo de Pokémon correspondiente o null si no existe
     */
    public static PokemonType fromString(String typeName) {
        if (typeName == null || typeName.isEmpty()) {
            return null;
        }
        
        try {
            return PokemonType.valueOf(typeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Intentar coincidencias parciales con capitalización normal
            for (PokemonType type : PokemonType.values()) {
                if (type.getName().equalsIgnoreCase(typeName)) {
                    return type;
                }
            }
            return null;
        }
    }
}
