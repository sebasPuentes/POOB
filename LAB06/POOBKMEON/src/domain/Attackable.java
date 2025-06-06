package domain;
public interface Attackable{
    /**
     * Type effectiveness chart that determines damage multipliers based on type matchups.
     * The rows represent attacking types and the columns represent defending types.
     * The values indicate how effective an attack is (damage multiplier):
     * - 0.0: No effect (immune)
     * - 0.5: Not very effective (half damage)
     * - 1.0: Normal effectiveness
     * - 2.0: Super effective (double damage)
     * 
     * The order of types is:
     * ACERO, AGUA, BICHO, DRAGÓN, ELÉCTRICO, FANTASMA, FUEGO, HADA, HIELO, LUCHA,
     * NORMAL, PLANTA, PSÍQUICO, ROCA, SINIESTRO, TIERRA, VENENO, VOLADOR
     */
    public final double[][] multiplicadores = {
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
     * Executes an attack from one Pokemon to another.
     * Implementations must calculate damage based on the attacker's stats,
     * the attack's power, and type effectiveness against the defender.
     * 
     * @param attacker The Pokemon performing the attack
     * @param target The Pokemon receiving the attack
     * @return The amount of damage dealt by the attack
     * @throws PoobkemonException If there is an issue executing the attack
     */
    public int doAttackTo(Pokemon attacker, Pokemon target) throws PoobkemonException;

    /**
     * Calculates the type effectiveness multiplier against a specific Pokemon type.
     * The multiplier is determined based on the type matchup chart and indicates
     * how effective the attack is against the defending type.
     * 
     * @param defender The type of the defending Pokemon
     * @return The damage multiplier (0.0 for no effect, 0.5 for not very effective,
     *         1.0 for normal effectiveness, 2.0 for super effective)
     */
    public double getMultiplicator(PokemonType defender);
}