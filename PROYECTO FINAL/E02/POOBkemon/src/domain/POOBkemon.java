package src.domain;
import src.presentation.POOBkemonGUI;

import javax.swing.Timer.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

/**
 * Clase principal que gestiona el juego POOBkemon
 * @author Julian López y Sebastian Puentes
 * @version 1.0
 * @since 2025-05-04
 */
public class POOBkemon {
    private ArrayList<Trainer> trainers = new ArrayList<>();
    Trainer trainer = new Trainer("Jugador 1", Color.RED);
    Trainer trainer2 = new Trainer("Jugador 2", Color.ORANGE);
    private TreeMap<String, Pokemon> pokedex = new TreeMap<>();
    private HashMap<String, Movement> defaultMovementsMap = new HashMap<>();
    private HashMap<String, Item> defaultItemsMap = new HashMap<>();
    private boolean gameInProgress;
    private boolean gamePaused;
    private boolean gameOver;
    private int currentTrainerIndex = 0;
    private static final int TURN_TIME_LIMIT = 20;

    public POOBkemon() {
        trainers.add(trainer);
        trainers.add(trainer2);
        defaultPokemons();
        defaultItems();
        defaultMovements();
    }

    public void defaultPokemons() {
        pokedex.clear();
        Pokemon charizard = new Pokemon("Charizard", PokemonType.FUEGO, PokemonType.VOLADOR);
        Pokemon blastoise = new Pokemon("Blastoise", PokemonType.AGUA);
        Pokemon venusaur = new Pokemon("Venusaur", PokemonType.PLANTA, PokemonType.VENENO);
        Pokemon raichu = new Pokemon("Raichu", PokemonType.ELECTRICO);
        Pokemon gengar = new Pokemon("Gengar", PokemonType.FANTASMA, PokemonType.VENENO);
        Pokemon snorlax = new Pokemon("Snorlax", PokemonType.NORMAL);
        Pokemon delibird = new Pokemon("Delibird", PokemonType.HIELO, PokemonType.NORMAL);
        Pokemon donphan = new Pokemon("Donphan", PokemonType.TIERRA);
        Pokemon dragonite = new Pokemon("Dragonite", PokemonType.DRAGON, PokemonType.VOLADOR);
        Pokemon gardevoir = new Pokemon("Gardevoir", PokemonType.PSIQUICO, PokemonType.HADA);
        Pokemon machamp = new Pokemon("Machamp", PokemonType.LUCHA);
        Pokemon metagross = new Pokemon("Metagross", PokemonType.PSIQUICO);
        Pokemon togetic = new Pokemon("Togetic", PokemonType.HADA, PokemonType.VOLADOR);
        Pokemon tyranitar = new Pokemon("Tyranitar", PokemonType.ROCA);
        Pokemon blaziken = new Pokemon("Blaziken", PokemonType.LUCHA);
        Pokemon golduck = new Pokemon("Golduck",PokemonType.AGUA);
        Pokemon gyarados = new Pokemon("Gyarados",PokemonType.AGUA);
        Pokemon hooh = new Pokemon("Ho-Oh",PokemonType.FUEGO,PokemonType.DRAGON);
        Pokemon mrmime = new Pokemon("Mr-Mime",PokemonType.HADA);
        Pokemon slaking = new Pokemon("Slaking",PokemonType.LUCHA);



        pokedex.put(charizard.getName(), charizard);
        pokedex.put(blastoise.getName(), blastoise);
        pokedex.put(venusaur.getName(), venusaur);
        pokedex.put(raichu.getName(), raichu);
        pokedex.put(gengar.getName(), gengar);
        pokedex.put(snorlax.getName(), snorlax);
        pokedex.put(delibird.getName(), delibird);
        pokedex.put(donphan.getName(), donphan);
        pokedex.put(dragonite.getName(), dragonite);
        pokedex.put(gardevoir.getName(), gardevoir);
        pokedex.put(machamp.getName(), machamp);
        pokedex.put(metagross.getName(), metagross);
        pokedex.put(togetic.getName(), togetic);
        pokedex.put(tyranitar.getName(), tyranitar);
        pokedex.put(blaziken.getName(), blaziken);
        pokedex.put(golduck.getName(), golduck);
        pokedex.put(gyarados.getName(),gyarados);
        pokedex.put(hooh.getName(), hooh);
        pokedex.put(mrmime.getName(),mrmime);
        pokedex.put(slaking.getName(),slaking);




    }

    public void defaultItems() {
        defaultItemsMap.clear();
        Item psPotion = new NormalPotion();
        Item superPotion = new SuperPotion();
        Item hyperPotion = new HyperPotion();
        Item revive = new Revive();
        defaultItemsMap.put(psPotion.getName(), psPotion);
        defaultItemsMap.put(superPotion.getName(), superPotion);
        defaultItemsMap.put(hyperPotion.getName(), hyperPotion);
        defaultItemsMap.put(revive.getName(), revive);
    }

    public void defaultMovements() {
        defaultMovementsMap.clear();
        
        // Crear efectos de estado
        StatusEffect quemadoEffect = new StatusEffect("Quemado", "El Pokémon sufre daño por quemaduras cada turno.", 3, 10.0);
        StatusEffect atrapadoEffect = new StatusEffect("Atrapado", "El Pokémon no puede escapar.", 3, 5.0);
        StatusEffect congeladoEffect = new StatusEffect("Congelado", "El Pokémon está congelado y no puede atacar.", 2, 0.0);
        StatusEffect confusoEffect = new StatusEffect("Confuso", "El Pokémon está confundido y puede dañarse a sí mismo.", 3, 0.0);
        StatusEffect envenenadoEffect = new StatusEffect("Envenenado", "El Pokémon sufre daño por veneno cada turno.", 4, 12.0);
        StatusEffect paralizadoEffect = new StatusEffect("Paralizado", "El Pokémon puede quedar inmóvil.", 3, 0.0);
        
        // Movimientos de tipo Especial sin efectos
        Movement lanzallamas = new StateMovement("Lanzallamas", "Un potente ataque con fuego que puede causar quemaduras.", 15, 90, 100, PokemonType.FUEGO, quemadoEffect, 0, 90);
        Movement hidrobomba = new Special("Hidrobomba", "Un poderoso chorro de agua a presión.", 5, 110, 80, PokemonType.AGUA, 0);
        Movement rayoSolar = new Special("Rayo Solar", "Un potente rayo que absorbe luz solar.", 10, 120, 100, PokemonType.PLANTA, 0);
        
        // Movimientos físicos sin efectos
        Movement golpeAereo = new Physical("Golpe Aéreo", "Un ataque rápido desde el aire.", 15, 75, 95, PokemonType.VOLADOR, 0);
        Movement cuchillada = new Physical("Cuchillada", "Un ataque con garras afiladas.", 20, 70, 100, PokemonType.NORMAL, 0);
        Movement atizadorX = new Physical("Atizador-X", "Un ataque con descargas eléctricas.", 15, 80, 100, PokemonType.ELECTRICO, 0);
        Movement cola_hierro = new Physical("Cola Hierro", "Un golpe con cola metálica.", 15, 100, 75, PokemonType.ACERO, 0);
        
        // Movimientos con efectos de estado
        Movement giroFuego = new StateMovement("Giro Fuego", "Rodea al enemigo con un remolino de fuego.", 15, 35, 85, PokemonType.FUEGO, atrapadoEffect, 0, 35);
        Movement rayo_hielo = new StateMovement("Rayo Hielo", "Un rayo gélido que puede congelar.", 10, 90, 100, PokemonType.HIELO, congeladoEffect, 0, 90);
        Movement cabezazo = new StateMovement("Cabezazo", "Un fuerte cabezazo que puede confundir.", 15, 70, 100, PokemonType.NORMAL, confusoEffect, 0, 70);
        Movement hidropulso = new StateMovement("Hidropulso", "Ondas ultrasónicas de agua que pueden confundir.", 20, 60, 100, PokemonType.AGUA, confusoEffect, 0, 60);
        Movement bombaLodo = new StateMovement("Bomba Lodo", "Lanza lodo tóxico que puede envenenar.", 10, 90, 100, PokemonType.VENENO, envenenadoEffect, 0, 90);
        Movement polvoVeneno = new StateMovement("Polvo Veneno", "Polvo venenoso que envenena al objetivo.", 35, 0, 75, PokemonType.VENENO, envenenadoEffect, 0, 0);
        Movement drenadoras = new StateMovement("Drenadoras", "Siembra semillas que drenan PS del objetivo.", 10, 0, 90, PokemonType.PLANTA, atrapadoEffect, 0, 0);
        Movement trueno = new StateMovement("Trueno", "Un potente rayo que puede paralizar.", 10, 110, 70, PokemonType.ELECTRICO, paralizadoEffect, 0, 110);
        Movement onda_trueno = new StateMovement("Onda Trueno", "Una onda eléctrica que paraliza al objetivo.", 20, 0, 90, PokemonType.ELECTRICO, paralizadoEffect, 0, 0);

        defaultMovementsMap.put(lanzallamas.getName(), lanzallamas);
        defaultMovementsMap.put(golpeAereo.getName(), golpeAereo);
        defaultMovementsMap.put(giroFuego.getName(), giroFuego);
        defaultMovementsMap.put(cuchillada.getName(), cuchillada);
        defaultMovementsMap.put(hidrobomba.getName(), hidrobomba);
        defaultMovementsMap.put(rayo_hielo.getName(), rayo_hielo);
        defaultMovementsMap.put(cabezazo.getName(), cabezazo);
        defaultMovementsMap.put(hidropulso.getName(), hidropulso);
        defaultMovementsMap.put(rayoSolar.getName(), rayoSolar);
        defaultMovementsMap.put(bombaLodo.getName(), bombaLodo);
        defaultMovementsMap.put(polvoVeneno.getName(), polvoVeneno);
        defaultMovementsMap.put(drenadoras.getName(), drenadoras);
        defaultMovementsMap.put(trueno.getName(), trueno);
        defaultMovementsMap.put(atizadorX.getName(), atizadorX);
        defaultMovementsMap.put(onda_trueno.getName(), onda_trueno);
        defaultMovementsMap.put(cola_hierro.getName(), cola_hierro);
    }

    /**
     * Termina el juego actual cuando un entrenador decide huir,
     * declarando como ganador al entrenador que no huyó.
     * @param fleeingTrainer El entrenador que decidió huir del combate
     * @return String con el mensaje indicando el ganador
     */
    public String leaveGame(Trainer fleeingTrainer) {
        String winnerMessage = "";
        if (trainers != null && trainers.size() == 2) {
            Trainer winner = null;
            for (Trainer trainer : trainers) {
                if (!trainer.equals(fleeingTrainer)) {
                    winner = trainer;
                    break;
                }
            }
            if (winner != null) {
                winnerMessage = "¡" + winner.getNombre() + " ha ganado el combate porque " +
                        fleeingTrainer.getNombre() + " ha huido!";
            }
        } else {
            winnerMessage = "El combate ha terminado.";
        }
        gameInProgress = false;
        gamePaused = false;
        gameOver = true;
        trainers.clear();
        pokedex.clear();
        defaultMovementsMap.clear();
        defaultItemsMap.clear();
        return winnerMessage;
    }

    /**
     * Obtiene el entrenador que está jugando actualmente su turno.
     * @return El entrenador actualmente activo en el juego
     */
    public Trainer getCurrentTrainer() {
        if (trainers != null && !trainers.isEmpty() && currentTrainerIndex >= 0 && currentTrainerIndex < trainers.size()) {
            return trainers.get(currentTrainerIndex);
        }
        return null;
    }

    public TreeMap<String, Pokemon> getPokedex() {
        return pokedex;
    }

    public HashMap<String, Movement> getDefaultMovementsMap() {
        return defaultMovementsMap;
    }

    public HashMap<String, Item> getDefaultItemsMap() {
        return defaultItemsMap;
    }

    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }
}