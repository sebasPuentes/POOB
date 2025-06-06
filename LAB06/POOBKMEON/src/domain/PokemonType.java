package domain;
public enum PokemonType {
    /** Steel type */
    ACERO(),
    /** Water type */
    AGUA(),
    /** Bug type */
    BICHO(),
    /** Dragon type */
    DRAGON(),
    /** Electric type */
    ELECTRICO(),
    /** Ghost type */
    FANTASMA(),
    /** Fire type */
    FUEGO(),
    /** Fairy type */
    HADA(),
    /** Ice type */
    HIELO(),
    /** Fighting type */
    LUCHA(),
    /** Normal type */
    NORMAL(),
    /** Grass type */
    PLANTA(),
    /** Psychic type */
    PSIQUICO(),
    /** Rock type */
    ROCA(),
    /** Dark type */
    SINIESTRO(),
    /** Ground type */
    TIERRA(),
    /** Flying type */
    VOLADOR(),
    /** Poison type */
    VENENO();

    /**
     * Gets the numerical index of this Pokemon type in the enum.
     * This index can be used for type effectiveness calculations or array lookups.
     * 
     * @return The zero-based ordinal position of this type in the enum declaration
     */
    public int getIndex() {
        return this.ordinal();
    }
}