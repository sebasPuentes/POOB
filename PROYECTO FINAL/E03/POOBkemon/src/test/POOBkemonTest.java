package src.test;

import src.domain.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class POOBkemonTest {
    private POOBkemon game;
    private Trainer trainer;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Pokemon machamp;
    private Pokemon gardevoir;
    private Physical physicalMove;
    private Special specialMove;
    private AttributeEffect Burn;
    private StateMovement stateMovement;;
    private Item potion;
    
    @BeforeEach
    public void setUp() {
        game = new POOBkemon();
        trainer = new Trainer("Julian", Color.RED);
        pokemon1 = new Pokemon("Pikachu", PokemonType.ELECTRICO);
        pokemon2 = new Pokemon("Bulbasaur", PokemonType.PLANTA);
        potion = new NormalPotion();
        machamp = new Pokemon("Machamp",100,384,394,251,284,295,229,PokemonType.LUCHA,null,68);
        gardevoir = new Pokemon("Gardevoir",100,340,251,383,251,361,284,PokemonType.PSIQUICO,PokemonType.HADA,282);

        machamp.increaseStat("attack", 50);
        machamp.increaseStat("specialAttack", -50);

        gardevoir.increaseStat("defense", -87);
        gardevoir.increaseStat("specialDefense", 50);

        physicalMove = new Physical("Puño Dinámico", "Un potente puñetazo",
                20, 100, 100, PokemonType.LUCHA, 0);

        specialMove = new Special("Psíquico", "Un poderoso ataque mental",
                20, 100, 100, PokemonType.PSIQUICO, 0);

        StatusEffect quemadoEffect = new StatusEffect("Quemado", "El Pokémon está quemado y pierde PS cada turno.", 5, 10);
         Burn = new AttributeEffect("Quemadura ", "Le inflinge daño al inicio de turno.", 2,
                new HashMap<String, Integer>() {{
                    put("Defense", -10);
                    put("PS", -30);
                }});

        stateMovement = new StateMovement("Lanzallamas", "Un potente ataque con fuego que puede causar quemaduras.", 15, 90, 100, PokemonType.FUEGO, quemadoEffect, 0, 90);


        machamp.addMovement(physicalMove);
        machamp.addMovement(specialMove);
    }

    @AfterEach
    public void tearDown() {
        game = null;
        trainer = null;
        pokemon1 = null;
        pokemon2 = null;
        potion = null;
    }
    
    @Test
    public void shouldCreateTrainer() {
        assertNotNull(trainer);
        assertEquals("Julian", trainer.getNombre());
        assertTrue(trainer.getInventory().getPokemons().isEmpty());
    }
    
    @Test
    public void shouldAddPokemonToTeam() {
        trainer.addPokemon(pokemon1);
        assertEquals(1, trainer.getInventory().getPokemons().size());
        assertEquals(pokemon1, trainer.getActivePokemon());
        trainer.addPokemon(pokemon2);
        assertEquals(2, trainer.getInventory().getPokemons().size());
        assertEquals(pokemon1, trainer.getActivePokemon());
    }
    
    @Test
    public void shouldSwitchActivePokemon() {
        trainer.addPokemon(pokemon1);
        trainer.addPokemon(pokemon2);
        try{
            trainer.changePokemon(pokemon2);
        } catch (POOBkemonException e){
            fail();
        }
        assertEquals(pokemon2, trainer.getActivePokemon());
    }
    
    @Test
    public void shouldNotAllowSwitchingToFaintedPokemon() {
        trainer.addPokemon(pokemon1);
        trainer.addPokemon(pokemon2);
        pokemon2.losePS(400);
        try{
            trainer.changePokemon(pokemon2);
        } catch (POOBkemonException e){
            assertEquals(POOBkemonException.INVALID_POKEMON, e.getMessage());
        }
        assertEquals(pokemon1, trainer.getActivePokemon());
    }
    
    @Test
    public void shouldDetectDefeatedTrainer() {
        trainer.addPokemon(pokemon1);
        assertFalse(trainer.getActivePokemon().isFainted());
        pokemon1.losePS(400);
        System.out.println(pokemon1.getCurrentPs());
        assertFalse(trainer.isDefeated());
    }
    
    @Test
    public void shouldAddAndUseItems() {
        trainer.addPokemon(pokemon1);
        trainer.addItem(potion);

        int initialHealth = pokemon1.getMaxPs();
        pokemon1.losePS(20);

        try {
            trainer.useItem(pokemon1, potion);
        }catch (POOBkemonException e){
            System.out.print(e.getMessage());
        }

        assertTrue(pokemon1.getCurrentPs() > initialHealth - 20);
    }

    @Test
    public void shouldUseRevive() {
        Revive revive = new Revive();
        trainer.addPokemon(pokemon1);
        trainer.addItem(revive);
        int beforeHealth = pokemon1.getCurrentPs();
        System.out.println(beforeHealth);
        pokemon1.losePS(400);
        int psafter = pokemon1.getCurrentPs();
        System.out.println(psafter);
        assertTrue(pokemon1.isFainted());

        try {
            trainer.useItem(pokemon1, revive);
        }catch(POOBkemonException e){
            System.out.print(e.getMessage());
        }
        int currentHealth = pokemon1.getCurrentPs();
        System.out.println(currentHealth);
        assertFalse(pokemon1.isFainted());


        assertTrue(pokemon1.getCurrentPs() > 0);
    }
    
    @Test
    public void shouldHaveLimitOnPokemonTeam() {
        for (int i = 0; i < 6; i++) {
            Pokemon p = new Pokemon("Pokemon" + i, PokemonType.NORMAL);
            trainer.addPokemon(p);
        }
        Pokemon extraPokemon = new Pokemon("Extra", PokemonType.NORMAL);
        trainer.addPokemon(extraPokemon);

        assertEquals(6, trainer.getInventory().getPokemons().size());
    }

    //-------------------MOVIMIENTOS-------------------
    @Test
    public void shouldBeHighDamageVsLowDefenseForPhysicalMoves() {
        int initialHP = gardevoir.getCurrentPs();
        machamp.useMovement(physicalMove, gardevoir);
        int damageDealt = initialHP - gardevoir.getCurrentPs();
        // El daño debería ser alto ya que tenemos alto ataque físico vs baja defensa física
        assertTrue(damageDealt > 100,
                "El ataque físico debería causar daño significativo dado el alto ataque y baja defensa");
        System.out.println("Daño del ataque físico: " + damageDealt);
    }

    @Test
    public void shouldBeLowDamageVsHighSpecialDefense() {
        int initialHP = gardevoir.getCurrentPs();

        machamp.useMovement(specialMove, gardevoir);
        int damageDealt = initialHP - gardevoir.getCurrentPs();
        // El daño debería ser bajo ya que tenemos bajo ataque especial vs alta defensa especial
        assertTrue(damageDealt < 50,
                "El ataque especial debería causar poco daño dado el bajo ataque especial y alta defensa especial");

        System.out.println("Daño del ataque especial: " + damageDealt);
    }

    @Test
    public void shouldMakeDifferentDamageWithSameConditions() {

        Pokemon gengar = new Pokemon("Gengar",100,324,251,394,240,273,350,PokemonType.FANTASMA,PokemonType.VENENO,94);
        Pokemon snorlax = new Pokemon("Snorlax",100,524,350,251,251,350,174,PokemonType.NORMAL,null,143);

        int initialHPforPhysical = snorlax.getCurrentPs();
        int initialHPforSpecial = initialHPforPhysical;

        Physical physicalForTest = new Physical("Golpe", "Ataque físico básico",
                30, 70, 100, PokemonType.NORMAL, 0);

        Special specialForTest = new Special("Rayo", "Ataque especial básico",
                30, 70, 100, PokemonType.NORMAL, 0);

        gengar.addMovement(physicalForTest);
        gengar.addMovement(specialForTest);


        Pokemon defenderForPhysical = snorlax.copy();
        Pokemon defenderForSpecial = snorlax.copy();

        gengar.useMovement(physicalForTest, defenderForPhysical);
        int physicalDamage = initialHPforPhysical - defenderForPhysical.getCurrentPs();

        gengar.useMovement(specialForTest, defenderForSpecial);
        int specialDamage = initialHPforSpecial - defenderForSpecial.getCurrentPs();

        assertNotEquals(physicalDamage, specialDamage,
                "Los ataques físicos y especiales deberían causar daño diferente con las mismas condiciones");
        System.out.println("Daño físico: " + physicalDamage + ", Daño especial: " + specialDamage);

    }

    @Test
    public void shouldWaterAttackBeGreaterThanFireAttack() {
        Pokemon charizard = new Pokemon("Charizard",90,360,293,348,280,295,328,PokemonType.FUEGO,PokemonType.VOLADOR,6);
        Pokemon blastoise = new Pokemon("Blastoise",100,362,291,295,328,339,280,PokemonType.AGUA,null,9);

        blastoise.increaseStat("specialAttack", 50);
        charizard.increaseStat("specialAttack", 50);
        blastoise.increaseStat("specialDefense", 0);
        charizard.increaseStat("specialDefense", 0);

        Special waterMove = new Special("Hidrobomba", "Potente chorro de agua",
                5, 110, 100, PokemonType.AGUA, 0);

        Special fireMove = new Special("Lanzallamas", "Potente llama",
                15, 90, 100, PokemonType.FUEGO, 0);

        blastoise.addMovement(waterMove);
        charizard.addMovement(fireMove);


        // Agua vs fuego
        int fireInitialHP = charizard.getCurrentPs();
        blastoise.useMovement(waterMove, charizard);
        int waterDamage = fireInitialHP - charizard.getCurrentPs();
        // Fuego vs agua
        int waterInitialHP = blastoise.getCurrentPs();
        charizard.useMovement(fireMove, blastoise);
        int fireDamage = waterInitialHP - blastoise.getCurrentPs();

        assertTrue(waterDamage > fireDamage,
                "El ataque de agua debería causar significativamente más daño al Pokémon de fuego");

        System.out.println("Daño de agua a fuego: " + waterDamage);
        System.out.println("Daño de fuego a agua: " + fireDamage);

    }

    @Test
    public void shouldMakeMoreDamageThanAPokemonWithLowAttackStats() {

        Pokemon strongPhysical = machamp.copy();
        Pokemon weakPhysical = machamp.copy();

        int weak = weakPhysical.getAttack();
        System.out.println(weak);
        strongPhysical.increaseStat("attack", 100);
        weakPhysical.increaseStat("attack", -215);
        int weakAfter = weakPhysical.getAttack();
        System.out.println(weakAfter);


        Physical testMove = new Physical("Golpe", "Ataque básico",
                30, 80, 100, PokemonType.NORMAL, 0);
        strongPhysical.addMovement(testMove);
        weakPhysical.addMovement(testMove);
        Pokemon defenderCopy1 = gardevoir.copy();
        Pokemon defenderCopy2 = gardevoir.copy();
        int initialHP1 = defenderCopy1.getCurrentPs();
        strongPhysical.useMovement(testMove, defenderCopy1);
        int strongDamage = initialHP1 - defenderCopy1.getCurrentPs();

        int initialHP2 = defenderCopy2.getCurrentPs();
        weakPhysical.useMovement(testMove, defenderCopy2);
        int weakDamage = initialHP2 - defenderCopy2.getCurrentPs();

        assertTrue(strongDamage > weakDamage * 2,
                "El atacante con alto ataque debería causar más del doble de daño que el débil");

        System.out.println("Daño del Pokémon fuerte: " + strongDamage);
        System.out.println("Daño del Pokémon débil: " + weakDamage);

    }

    @Test
    public void shouldUseAttributeMovementAndIncreaseOrDecreaseStats(){
        Pokemon machampCopy = machamp.copy();
        Pokemon gardevoirCopy = gardevoir.copy();

        AttributeMovement burn = new AttributeMovement("Aumentar Ataque", "Aumenta el ataque del Pokémon",
                10, 100, 100, PokemonType.NORMAL, Burn, 1);
        machampCopy.addMovement(burn);

        int initialDefense = gardevoirCopy.getDefense();
        int initialPS = gardevoirCopy.getCurrentPs();

        machampCopy.useMovement(burn, gardevoirCopy);
        ArrayList<AttributeEffect> effects = gardevoirCopy.getAtributeEffects();
        for(AttributeEffect effect : effects)
            System.out.println(effect.getName());

        gardevoirCopy.affectPokemonStatus();

        int defenseAfter = gardevoirCopy.getDefense();
        int PSAfter = gardevoirCopy.getCurrentPs();

        assertTrue(initialDefense > defenseAfter, "La defensa debería disminuir después de usar el movimiento");
        assertEquals(initialDefense - 10, defenseAfter, "La defensa debería reducirse en 10 puntos");
        assertTrue(initialPS > PSAfter, "Los PS deberían disminuir después de usar el movimiento");
        assertEquals(initialPS - 30, PSAfter, "Los PS deberían reducirse en 30 puntos");
    }

    @Test
    public void shouldUseStateMovementAndApplyStatusEffect() {
        Pokemon machampCopy = machamp.copy();
        Pokemon gardevoirCopy = gardevoir.copy();

        // Modificar el porcentaje de daño para garantizar que haga efecto
        StatusEffect quemado = new StatusEffect("Quemado", "El Pokémon está quemado y pierde PS cada turno.", 2, 100);

        StateMovement stateMovement = new StateMovement("Lanzallamas", "Un potente ataque con fuego que puede causar quemaduras.",
                15, 90, 100, PokemonType.FUEGO, quemado, 0, 90);

        int initialPS = gardevoirCopy.getCurrentPs();
        System.out.println("PS iniciales: " + initialPS);

        machampCopy.useMovement(stateMovement, gardevoirCopy);
        StatusEffect effect = gardevoirCopy.getStatusEffect();
        if (effect != null) {
            System.out.println("Efecto aplicado: " + effect.getName());
        } else {
            System.out.println("No se aplicó ningún efecto");
            fail("El efecto de estado no se aplicó correctamente");
        }
        gardevoirCopy.applyStatusEffects();
        System.out.println(effect.getDuration());
        int PSAfter = gardevoirCopy.getCurrentPs();
        System.out.println("PS después del efecto: " + PSAfter);
        assertTrue(initialPS > PSAfter, "Los PS deberían disminuir después de usar el movimiento");
    }

    @Test
    public void shouldDefensiveTrainerUseDefensiveMovements() {
        // Configuración
        DefensiveTrainer defensiveTrainer = new DefensiveTrainer("Defensive Trainer", Color.BLUE);
        Trainer attackerTrainer = new Trainer("Attacker Trainer", Color.RED);
        Pokemon machampCopy1 = machamp.copy();
        Pokemon gardevoirCopy = gardevoir.copy();

        AttributeEffect defense = new AttributeEffect("Descansar", "Descansa ese turno y recupera sus estadisticas principales.", 2,
                new HashMap<String, Integer>() {{
                    put("Defense", 50);
                }}
        );

        AttributeMovement defenseMove = new AttributeMovement("Defense", "Aumenta defensa", 25, 0, 100, PokemonType.NORMAL, defense, 0);
        machampCopy1.addMovement(defenseMove);
        machampCopy1.addMovement(physicalMove);
        defensiveTrainer.addPokemon(machampCopy1);
        attackerTrainer.addPokemon(gardevoirCopy);
        int initialDefense = machampCopy1.getDefense();
        Movement selectedMovement = defensiveTrainer.decide(gardevoirCopy);
        assertTrue(selectedMovement instanceof AttributeMovement,
                "El entrenador defensivo debería seleccionar un movimiento de atributo");
        assertEquals("Defense", selectedMovement.getName(),
                "El entrenador defensivo debería seleccionar el movimiento de defensa");
        machampCopy1.useMovement(selectedMovement, machampCopy1);
        machampCopy1.affectPokemonStatus();
        machampCopy1.affectPokemonStatus();
        System.out.println(machampCopy1.getDefense());
        int finalDefense = machampCopy1.getDefense();
        assertTrue(finalDefense > initialDefense,
                "La defensa debería aumentar después de usar el movimiento defensivo");
    }

    @Test
    public void shouldAttackingTrainerUseOffensiveMovements(){
        AttackingTrainer offensiveTrainer = new AttackingTrainer("Offensive Trainer", Color.RED);
        Trainer defenderTrainer = new Trainer("Defender Trainer", Color.BLUE);
        Pokemon machampCopy1 = machamp.copy();
        Pokemon gardevoirCopy = gardevoir.copy();
        AttributeEffect Attack = new AttributeEffect("Potenciar Ataque", "Descansa ese turno y recupera sus estadisticas principales.", 1, new HashMap<String, Integer>() {{
            put("Attack", 50);
        }});
        AttributeMovement attack = new AttributeMovement("Attack", "", 25, 0, 100, PokemonType.NORMAL, Attack, 0);
        Item potion = new NormalPotion();
        machampCopy1.addMovement(attack);
        machampCopy1.addMovement(physicalMove);
        offensiveTrainer.addPokemon(machampCopy1);
        offensiveTrainer.addItem(potion);
        defenderTrainer.addPokemon(gardevoirCopy);
        int initialAttack = machampCopy1.getAttack();
        Pokemon activePokemon = offensiveTrainer.getActivePokemon();
        System.out.println(activePokemon.getName());
        Movement movement = offensiveTrainer.decide(gardevoirCopy);
        machampCopy1.useMovement(movement, machampCopy1);
        machampCopy1.affectPokemonStatus();
        int finalAttack = machampCopy1.getAttack();
        assertTrue(finalAttack> initialAttack, "El ataque debería aumentar después de usar el movimiento ofensivo");
    }

}