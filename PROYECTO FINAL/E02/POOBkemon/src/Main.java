package src;

import src.domain.*;
import java.util.List;

/**
 * Clase principal para probar el funcionamiento del sistema POOBkemon
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Bienvenido al Sistema POOBkemon ===");
        
        // Probar la enumeración PokemonType
        System.out.println("\n=== Tipos de Pokémon ===");
        for (PokemonType tipo : PokemonType.values()) {
            System.out.println(tipo.getName() + " - Categoría: " + tipo.getTypeMov());
        }
        
        // Crear algunos Pokémon
        System.out.println("\n=== Creación de Pokémon ===");
        Pokemon pikachu = new Pokemon("Pikachu", "Eléctrico");
        Pokemon charizard = new Pokemon("Charizard", "Fuego", "Volador");
        Pokemon blastoise = new Pokemon("Blastoise", "Agua");
        
        System.out.println(pikachu);
        System.out.println(charizard);
        System.out.println(blastoise);
        
        // Crear algunos movimientos
        System.out.println("\n=== Creación de Movimientos ===");
        Movement impactrueno = new Movement("Impactrueno", PokemonType.ELECTRICO, 40, 100, 30, "Especial", "paralizado", 10);
        Movement lanzallamas = new Movement("Lanzallamas", PokemonType.FUEGO, 90, 100, 15, "Especial", "quemado", 10);
        Movement hidrobomba = new Movement("Hidrobomba", PokemonType.AGUA, 110, 80, 5, "Especial");
        
        System.out.println(impactrueno);
        System.out.println(lanzallamas);
        System.out.println(hidrobomba);
        
        // Añadir movimientos a los Pokémon
        pikachu.addMovement("Impactrueno");
        charizard.addMovement("Lanzallamas");
        blastoise.addMovement("Hidrobomba");
        
        // Probar cálculos de efectividad
        System.out.println("\n=== Cálculos de Efectividad ===");
        probarEfectividad(PokemonType.ELECTRICO, PokemonType.AGUA);
        probarEfectividad(PokemonType.ELECTRICO, PokemonType.TIERRA);
        probarEfectividad(PokemonType.AGUA, PokemonType.FUEGO);
        probarEfectividad(PokemonType.AGUA, PokemonType.PLANTA);
        
        // Efectividad con tipos dobles
        System.out.println("\nEfectividad contra tipos dobles:");
        double efectividad = Type.getTotalEffectiveness(
                PokemonType.ELECTRICO, 
                PokemonType.FUEGO, 
                PokemonType.VOLADOR
        );
        System.out.println("Eléctrico vs Fuego/Volador: " + efectividad + " - " + 
                Type.getEffectivenessMessage(efectividad));
        
        // Simulación de batalla simple
        System.out.println("\n=== Simulación de Batalla ===");
        simularBatalla(pikachu, blastoise);
    }
    
    /**
     * Prueba la efectividad entre dos tipos y muestra el resultado
     */
    private static void probarEfectividad(PokemonType tipoAtaque, PokemonType tipoDefensa) {
        double efectividad = Type.getEffectiveness(tipoAtaque, tipoDefensa);
        System.out.println(tipoAtaque.getName() + " vs " + tipoDefensa.getName() + 
                ": " + efectividad + " - " + Type.getEffectivenessMessage(efectividad));
    }
    
    /**
     * Simula una batalla simple entre dos Pokémon
     */
    private static void simularBatalla(Pokemon pokemon1, Pokemon pokemon2) {
        System.out.println("¡Comienza la batalla entre " + pokemon1.getName() + " y " + pokemon2.getName() + "!");
        
        // Ataque del primer Pokémon
        int daño = pokemon1.attack(pokemon2, 0);
        String mensaje = pokemon1.getName() + " usa " + pokemon1.getMovements().get(0);
        
        // Calcular efectividad
        double efectividad = Type.getTotalEffectiveness(
                PokemonType.fromString(pokemon1.getPrincipalType()),
                PokemonType.fromString(pokemon2.getPrincipalType()),
                pokemon2.getSecondaryType() != null ? PokemonType.fromString(pokemon2.getSecondaryType()) : null
        );
        
        // Mostrar mensaje de efectividad si aplica
        String mensajeEfecto = Type.getEffectivenessMessage(efectividad);
        if (!mensajeEfecto.isEmpty()) {
            mensaje += "\n" + mensajeEfecto;
        }
        
        // Aplicar daño
        pokemon2.losePS(daño);
        mensaje += "\nInflige " + daño + " puntos de daño.";
        mensaje += "\n" + pokemon2.getName() + " tiene " + pokemon2.getCurrentPs() + 
                "/" + pokemon2.getMaxPs() + " PS restantes.";
        
        System.out.println(mensaje);
        
        // Verificar si el Pokémon está debilitado
        if (pokemon2.isFainted()) {
            System.out.println(pokemon2.getName() + " se ha debilitado.");
            System.out.println(pokemon1.getName() + " gana la batalla.");
        } else {
            System.out.println(pokemon2.getName() + " aún puede continuar luchando.");
        }
    }
}
