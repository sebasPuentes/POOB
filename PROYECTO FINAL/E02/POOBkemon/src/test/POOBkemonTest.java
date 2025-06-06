package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.domain.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class POOBkemonTest {
    private POOBkemon game;
    private Trainer trainer;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Item potion;
    
    @BeforeEach
    public void setUp() {
        game = new POOBkemon();
        trainer = new Trainer("Ash", Color.RED);
        pokemon1 = new Pokemon("Pikachu", PokemonType.ELECTRICO);
        pokemon2 = new Pokemon("Bulbasaur", PokemonType.PLANTA);


        potion = new NormalPotion();
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
        assertEquals("Ash", trainer.getNombre());
        assertTrue(trainer.getTeam().isEmpty());
    }
    
    @Test
    public void shouldAddPokemonToTeam() {
        assertTrue(trainer.addPokemon(pokemon1));
        assertEquals(1, trainer.getTeam().size());
        assertEquals(pokemon1, trainer.getActivePokemon());
        
        assertTrue(trainer.addPokemon(pokemon2));
        assertEquals(2, trainer.getTeam().size());
        assertEquals(pokemon1, trainer.getActivePokemon());
    }
    
    @Test
    public void shouldSwitchActivePokemon() {
        trainer.addPokemon(pokemon1);
        trainer.addPokemon(pokemon2);
        
        assertTrue(trainer.switchPokemon(pokemon2));
        assertEquals(pokemon2, trainer.getActivePokemon());
    }
    
    @Test
    public void shouldNotAllowSwitchingToFaintedPokemon() {
        trainer.addPokemon(pokemon1);
        trainer.addPokemon(pokemon2);

        pokemon2.losePS(400);
        
        assertFalse(trainer.switchPokemon(pokemon2));
        assertEquals(pokemon1, trainer.getActivePokemon());
    }
    
    @Test
    public void shouldDetectDefeatedTrainer() {
        trainer.addPokemon(pokemon1);
        
        assertFalse(trainer.isDefeated());

        pokemon1.losePS(400);
        
        assertTrue(trainer.isDefeated());
    }
    
    @Test
    public void shouldAddAndUseItems() {
        trainer.addPokemon(pokemon1);
        trainer.addItem(potion);

        int initialHealth = pokemon1.getMaxPs();
        pokemon1.losePS(20);
        
        assertTrue(trainer.useItem(potion, pokemon1));

        assertTrue(pokemon1.getCurrentPs() > initialHealth - 20);
    }
    
    @Test
    public void shouldCalculateDamageCorrectly() {
        Pokemon charizard = new Pokemon("Charizard",100,360,293,348,280,295,328,PokemonType.FUEGO,PokemonType.VOLADOR,6);
        Pokemon blastoise = new Pokemon("Blastoise",100,362,291,295,328,339,280,PokemonType.AGUA,null,9);
        
        // Crear movimientos usando el constructor actualizado
        Movement hidrobomba = new Special("HidroBomba", "Fuerte Ataque de agua", 5, 110, 80, PokemonType.AGUA, 0);
        StatusEffect burnEffect = new StatusEffect("Quemado", "El Pokémon sufre daño por quemaduras", 3, 10.0);
        Movement lanzallamas = new StateMovement("Lanzallamas", "Fuerte ataque de fuego", 15, 90, 100, PokemonType.FUEGO, burnEffect, 0, 90);
        
        blastoise.addMovement(hidrobomba);
        charizard.addMovement(lanzallamas);
    
        try {
            int hpBefore = charizard.getCurrentPs();
            blastoise.useMovement(hidrobomba, charizard);
            int hpAfter = charizard.getCurrentPs();
            System.out.println(hpAfter);
            
            assertTrue((hpBefore - hpAfter) > hidrobomba.getPower(), "El daño de agua contra fuego debería ser mayor que el poder base");

            hpBefore = blastoise.getCurrentPs();
            charizard.useMovement(lanzallamas, blastoise);
            hpAfter = blastoise.getCurrentPs();
            System.out.println(hpAfter);
    
            assertTrue((hpBefore - hpAfter) < lanzallamas.getPower(), "El daño de fuego contra agua debería ser menor que el poder base");
        } catch (POOBkemonException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    public void shouldUseRevive() {
        Revive revive = new Revive();
        trainer.addPokemon(pokemon1);
        trainer.addItem(revive);

        pokemon1.losePS(400);
        assertTrue(pokemon1.isFainted());
        
        assertTrue(trainer.useItem(revive, pokemon1));

        assertFalse(pokemon1.isFainted());
        assertTrue(pokemon1.getCurrentPs() > 0);
    }
    
    @Test
    public void shouldHaveLimitOnPokemonTeam() {
        for (int i = 0; i < 6; i++) {
            Pokemon p = new Pokemon("Pokemon" + i, PokemonType.NORMAL);
            assertTrue(trainer.addPokemon(p));
        }
        Pokemon extraPokemon = new Pokemon("Extra", PokemonType.NORMAL);
        assertFalse(trainer.addPokemon(extraPokemon));

        assertEquals(6, trainer.getTeam().size());
    }
}