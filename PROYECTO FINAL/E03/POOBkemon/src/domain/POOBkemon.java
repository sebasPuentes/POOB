package src.domain;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Clase principal que gestiona el juego POOBkemon
 * @author Julian López y Sebastian Puentes
 * @version 1.0
 * @since 2025-05-04
 */
public class POOBkemon implements Serializable{

    private HashMap<String, Trainer> trainers = new HashMap<>();
    private TreeMap<String, Pokemon> pokedex = new TreeMap<>();
    private TreeMap<String, Movement> defaultMovementsMap = new TreeMap<>();
    private HashMap<String, Item> defaultItemsMap = new HashMap<>();
    private ArrayList<Trainer> turnsTrainers = new ArrayList<>();
    private int currentTurn = 0;
    private boolean gameOver = false;
    private Trainer winner = null;
    private Movement lastAction = null;
    public POOBkemon() {
        defaultGame();
        serializateGame();
    }
    public void defaultGame() {
        pokedex.clear();
        Pokemon venusour = new Pokemon("Venusour", 100, 364, 289, 328, 291, 328, 284, PokemonType.PLANTA, PokemonType.VENENO, 3);
        Pokemon charizard = new Pokemon("Charizard", 90, 360, 293, 348, 280, 295, 328, PokemonType.FUEGO, PokemonType.VOLADOR, 6);
        Pokemon blastoise = new Pokemon("Blastoise", 100, 362, 291, 295, 328, 339, 280, PokemonType.AGUA, null, 9);
        Pokemon raichu = new Pokemon("Raichu", 100, 324, 306, 306, 229, 284, 350, PokemonType.ELECTRICO, null, 26);
        Pokemon machamp = new Pokemon("Machamp", 100, 384, 394, 251, 284, 295, 229, PokemonType.LUCHA, null, 68);
        Pokemon gengar = new Pokemon("Gengar", 100, 324, 251, 394, 240, 273, 350, PokemonType.FANTASMA, PokemonType.VENENO, 94);
        Pokemon dragonite = new Pokemon("Dragonite", 100, 386, 403, 328, 317, 328, 284, PokemonType.DRAGON, PokemonType.VOLADOR, 149);
        Pokemon togetic = new Pokemon("Togetic", 100, 314, 196, 284, 295, 339, 196, PokemonType.HADA, PokemonType.VOLADOR, 176);
        Pokemon donphan = new Pokemon("Donphan", 100, 384, 372, 240, 372, 240, 218, PokemonType.TIERRA, null, 232);
        Pokemon tyranitar = new Pokemon("Tyranitar", 100, 404, 403, 317, 350, 328, 243, PokemonType.ROCA, PokemonType.SINIESTRO, 248);
        Pokemon snorlax = new Pokemon("Snorlax", 100, 524, 350, 251, 251, 350, 174, PokemonType.NORMAL, null, 143);
        Pokemon delibird = new Pokemon("Delibird", 100, 294, 229, 251, 207, 207, 273, PokemonType.HIELO, PokemonType.VOLADOR, 225);
        Pokemon gardevoir = new Pokemon("Gardevoir", 100, 340, 251, 383, 251, 361, 284, PokemonType.PSIQUICO, PokemonType.HADA, 282);
        Pokemon metagross = new Pokemon("Metagross", 100, 364, 404, 317, 394, 306, 262, PokemonType.ACERO, PokemonType.PSIQUICO, 376);
        Pokemon blaziken = new Pokemon("Blaziken", 100, 364, 372, 328, 328, 328, 291, PokemonType.FUEGO, PokemonType.LUCHA, 257);
        Pokemon gyarados = new Pokemon("Gyarados", 100, 364, 372, 328, 328, 328, 291, PokemonType.AGUA, PokemonType.VOLADOR, 130);
        Pokemon hooh = new Pokemon("Ho-Oh", 100, 384, 403, 328, 328, 328, 291, PokemonType.FUEGO, PokemonType.VOLADOR, 250);
        Pokemon mrmime = new Pokemon("Mr Mime", 100, 324, 251, 394, 240, 273, 350, PokemonType.PSIQUICO, PokemonType.HADA, 122);
        Pokemon slaking = new Pokemon("Slaking", 100, 524, 350, 251, 251, 350, 174, PokemonType.NORMAL, null, 289);
        Pokemon golduck = new Pokemon("Golduck", 100, 364, 372, 328, 328, 328, 291, PokemonType.AGUA, null, 55);

        Pokemon venusour1 = new Pokemon("Venusur", 100, 364, 289, 328, 291, 328, 284, PokemonType.PLANTA, PokemonType.VENENO, 3);
        Pokemon charizard1 = new Pokemon("Charizard", 90, 360, 293, 348, 280, 295, 328, PokemonType.FUEGO, PokemonType.VOLADOR, 6);
        Pokemon blastoise1 = new Pokemon("Blastoise", 100, 362, 291, 295, 328, 339, 280, PokemonType.AGUA, null, 9);
        Pokemon raichu1 = new Pokemon("Raichu", 100, 324, 306, 306, 229, 284, 350, PokemonType.ELECTRICO, null, 26);
        Pokemon machamp1 = new Pokemon("Machamp", 100, 384, 394, 251, 284, 295, 229, PokemonType.LUCHA, null, 68);
        Pokemon gengar1 = new Pokemon("Gengar", 100, 324, 251, 394, 240, 273, 350, PokemonType.FANTASMA, PokemonType.VENENO, 94);
        Pokemon dragonite1 = new Pokemon("Dragonite", 100, 386, 403, 328, 317, 328, 284, PokemonType.DRAGON, PokemonType.VOLADOR, 149);
        Pokemon togetic1 = new Pokemon("Togetic", 100, 314, 196, 284, 295, 339, 196, PokemonType.HADA, PokemonType.VOLADOR, 176);
        Pokemon donphan1 = new Pokemon("Donphan", 100, 384, 372, 240, 372, 240, 218, PokemonType.TIERRA, null, 232);
        Pokemon tyranitar1 = new Pokemon("Tyranitar", 100, 404, 403, 317, 350, 328, 243, PokemonType.ROCA, PokemonType.SINIESTRO, 248);
        Pokemon snorlax1 = new Pokemon("Snorlax", 100, 524, 350, 251, 251, 350, 174, PokemonType.NORMAL, null, 143);
        Pokemon delibird1 = new Pokemon("Delibird", 100, 294, 229, 251, 207, 207, 273, PokemonType.HIELO, PokemonType.VOLADOR, 225);
        Pokemon gardevoir1 = new Pokemon("Gardevoir", 100, 340, 251, 383, 251, 361, 284, PokemonType.PSIQUICO, PokemonType.HADA, 282);
        Pokemon metagross1 = new Pokemon("Metagross", 100, 364, 404, 317, 394, 306, 262, PokemonType.ACERO, PokemonType.PSIQUICO, 376);
        Pokemon blaziken1 = new Pokemon("Blaziken", 100, 364, 372, 328, 328, 328, 291, PokemonType.FUEGO, PokemonType.LUCHA, 257);
        Pokemon gyarados1 = new Pokemon("Gyarados", 100, 364, 372, 328, 328, 328, 291, PokemonType.AGUA, PokemonType.VOLADOR, 130);
        Pokemon hooh1 = new Pokemon("Ho-Oh", 100, 384, 403, 328, 328, 328, 291, PokemonType.FUEGO, PokemonType.VOLADOR, 250);
        Pokemon mrmime1 = new Pokemon("Mr Mime", 100, 324, 251, 394, 240, 273, 350, PokemonType.PSIQUICO, PokemonType.HADA, 122);
        Pokemon slaking1 = new Pokemon("Slaking", 100, 524, 350, 251, 251, 350, 174, PokemonType.NORMAL, null, 289);
        Pokemon golduck1 = new Pokemon("Golduck", 100, 364, 372, 328, 328, 328, 291, PokemonType.AGUA, null, 55);

        addPokemon(venusour);
        addPokemon(charizard);
        addPokemon(blastoise);
        addPokemon(raichu);
        addPokemon(machamp);
        addPokemon(gengar);
        addPokemon(dragonite);
        addPokemon(togetic);
        addPokemon(donphan);
        addPokemon(tyranitar);
        addPokemon(snorlax);
        addPokemon(delibird);
        addPokemon(gardevoir);
        addPokemon(metagross);
        addPokemon(blaziken);
        addPokemon(gyarados);
        addPokemon(hooh);
        addPokemon(mrmime);
        addPokemon(slaking);
        addPokemon(golduck);


        AttributeEffect Burn = new AttributeEffect("Quemadura ", "Le inflinge daño al inicio de turno.", 2,
                new HashMap<String, Integer>() {{
                    put("Defense", -10);
                    put("PS", -30);
                }});
        AttributeEffect Poison = new AttributeEffect("veneno ", "Le inflinge daño al inicio de turno.", 3, new HashMap<String, Integer>() {{
            put("Defense", -30);
            put("PS", -90);
        }});
        AttributeEffect Defense = new AttributeEffect("Descansar", "Descansa ese turno y recupera sus estadisticas principales.", 1, new HashMap<String, Integer>() {{
            put("Defense", 50);
        }});
        AttributeEffect Regenerate = new AttributeEffect("Regenerar ", "", 1, new HashMap<String, Integer>() {{
            put("PS", 50);
        }});
        AttributeEffect Electrocuted = new AttributeEffect("Electrocutar ", "Le inflinge daño al inicio de turno.", 4, new HashMap<String, Integer>() {{
            put("Attack", -20);
            put("PS", -30);
        }});

        StatusEffect Freeze = new StatusEffect("congelado", "Un Pokémon congelado", 10, 0.90);
        StatusEffect Sleep = new StatusEffect("Dormido", "Un Pokémon dormido no puede realizar ningún movimiento durante su turno", 5, 1);

        // Movimientos de estado
        StateMovement freeze = new StateMovement("Congelar", "", 20, 20, 50, PokemonType.HIELO, Freeze, 60, 0);
        StateMovement sleep = new StateMovement("Dormir", "", 10, 15, 50, PokemonType.NORMAL, Sleep, 60, 0);
        AttributeMovement burn = new AttributeMovement("Burn", "", 30, 39, 80, PokemonType.FUEGO, Burn, 10);
        AttributeMovement poison = new AttributeMovement("Super Burn", "", 15, 60, 80, PokemonType.VENENO, Poison, 0);
        AttributeMovement defense = new AttributeMovement("Defense", "", 25, 0, 100, PokemonType.NORMAL, Defense, 0);
        AttributeMovement regenerate = new AttributeMovement("Regenerar", "", 25, 0, 100, PokemonType.NORMAL, Regenerate, 0);
        AttributeMovement electrocuted = new AttributeMovement("Electrocutar", "", 12, 30, 70, PokemonType.ELECTRICO, Electrocuted, 30);

        // Movimientos físicos
        Movement terremoto = new Physical("Terremoto", "Un potente ataque terrestre.", 10, 100, 100, PokemonType.TIERRA, 0);
        Movement avalancha = new Physical("Avalancha", "Un ataque de rocas que puede hacer retroceder.", 10, 75, 90, PokemonType.ROCA, 0);
        Movement garraDragon = new Physical("Garra Dragón", "Un ataque con garras afiladas de dragón.", 15, 80, 100, PokemonType.DRAGON, 0);
        Movement golpeCuerpo = new Physical("Golpe Cuerpo", "Un ataque que puede paralizar al objetivo.", 15, 85, 100, PokemonType.NORMAL, 0);
        Movement golpeAereo = new Physical("Golpe Aéreo", "Un ataque rápido desde el aire.", 15, 75, 95, PokemonType.VOLADOR, 0);
        Movement cuchillada = new Physical("Cuchillada", "Un ataque con garras afiladas.", 20, 70, 100, PokemonType.NORMAL, 0);
        Movement atizadorX = new Physical("Atizador-X", "Un ataque con descargas eléctricas.", 15, 80, 100, PokemonType.ELECTRICO, 0);
        Movement cola_hierro = new Physical("Cola Hierro", "Un golpe con cola metálica.", 15, 100, 75, PokemonType.ACERO, 0);

        // Movimientos especiales
        Movement lanzallamas = new Special("Lanzallamas", "Un ataque de fuego que puede quemar.", 15, 90, 100, PokemonType.FUEGO, 0);
        Movement giroFuego = new Special("Giro Fuego", "Un ataque de fuego que puede quemar.", 15, 90, 100, PokemonType.FUEGO, 0);
        Movement puñoFuego = new Special("Puño Fuego", "Un puñetazo ardiente que puede quemar.", 15, 75, 100, PokemonType.FUEGO, 0);
        Movement puñoAgua = new Special("Puño Agua", "Un puñetazo acuático que puede mojar.", 15, 75, 100, PokemonType.AGUA, 0);
        Movement puñoTierra = new Special("Puño Tierra", "Un puñetazo terrestre que puede causar daño.", 15, 75, 100, PokemonType.TIERRA, 0);
        Movement puñoElectrico = new Special("Puño Electrico", "Un puñetazo electrico que puede paralizar.", 15, 75, 100, PokemonType.ELECTRICO, 0);
        Movement puñoLucha = new Special("Puño Lucha", "Un puñetazo de lucha que puede causar daño.", 15, 75, 100, PokemonType.LUCHA, 0);
        Movement puñoFantasma = new Special("Puño Fantasma", "Un puñetazo fantasma que puede causar daño.", 15, 75, 100, PokemonType.FANTASMA, 0);
        Movement psicoRayo = new Special("Psico Rayo", "Un ataque psíquico que puede confundir.", 20, 65, 100, PokemonType.PSIQUICO, 0);
        Movement ondaIgnea = new Special("Onda Ígnea", "Un ataque de fuego que puede quemar al objetivo.", 15, 85, 90, PokemonType.FUEGO, 0);
        Movement pulsoDragon = new Special("Pulso Dragón", "Un ataque de energía dracónica.", 10, 85, 100, PokemonType.DRAGON, 0);
        Movement vientoPlata = new Special("Viento Plata", "Un ataque de insecto que puede aumentar las estadísticas.", 5, 60, 100, PokemonType.BICHO, 0);
        Movement hidrobomba = new Special("Hidrobomba", "Un potente chorro de agua.", 10, 110, 80, PokemonType.AGUA, 0);
        Movement rayoSolar = new Special("Rayo Solar", "Un ataque de energía solar.", 10, 120, 100, PokemonType.PLANTA, 0);

        try {
            addMovement(freeze);
            addMovement(sleep);
            addMovement(burn);
            addMovement(poison);
            addMovement(defense);
            addMovement(regenerate);
            addMovement(electrocuted);
            addMovement(terremoto);
            addMovement(avalancha);
            addMovement(garraDragon);
            addMovement(golpeCuerpo);
            addMovement(golpeAereo);
            addMovement(cuchillada);
            addMovement(atizadorX);
            addMovement(cola_hierro);
            addMovement(lanzallamas);
            addMovement(giroFuego);
            addMovement(puñoFuego);
            addMovement(puñoAgua);
            addMovement(puñoTierra);
            addMovement(puñoElectrico);
            addMovement(puñoLucha);
            addMovement(puñoFantasma);
            addMovement(psicoRayo);
            addMovement(ondaIgnea);
            addMovement(pulsoDragon);
            addMovement(vientoPlata);
            addMovement(hidrobomba);
            addMovement(rayoSolar);
        } catch (POOBkemonException e) {
            System.out.println(e.getMessage());
        }

        //Pokemon Con Movimientos Predefinidos para Máquinas
        venusour1.setMovements(new Movement[]{rayoSolar, sleep, terremoto, poison});
        charizard1.setMovements(new Movement[]{lanzallamas, psicoRayo, golpeAereo, burn});
        blastoise1.setMovements(new Movement[]{hidrobomba, psicoRayo, puñoAgua, regenerate});
        raichu1.setMovements(new Movement[]{atizadorX, psicoRayo, electrocuted, golpeCuerpo});
        machamp1.setMovements(new Movement[]{puñoLucha, puñoFantasma, golpeCuerpo, regenerate});
        gengar1.setMovements(new Movement[]{puñoFantasma, psicoRayo, golpeCuerpo, regenerate});
        dragonite1.setMovements(new Movement[]{pulsoDragon, psicoRayo, golpeCuerpo, regenerate});
        togetic1.setMovements(new Movement[]{vientoPlata, psicoRayo, golpeCuerpo, defense});
        donphan1.setMovements(new Movement[]{puñoTierra, psicoRayo, golpeCuerpo, defense});
        tyranitar1.setMovements(new Movement[]{puñoTierra, psicoRayo, vientoPlata, freeze});
        snorlax1.setMovements(new Movement[]{puñoTierra, psicoRayo, giroFuego, defense});
        delibird1.setMovements(new Movement[]{pulsoDragon, psicoRayo, terremoto, freeze});
        gardevoir1.setMovements(new Movement[]{puñoAgua, psicoRayo, golpeCuerpo, freeze});
        metagross1.setMovements(new Movement[]{puñoElectrico, psicoRayo, golpeCuerpo, freeze});
        blaziken1.setMovements(new Movement[]{puñoFuego, psicoRayo, golpeCuerpo, sleep});
        gyarados1.setMovements(new Movement[]{puñoElectrico, psicoRayo, golpeCuerpo, sleep});
        hooh1.setMovements(new Movement[]{puñoElectrico, psicoRayo, golpeCuerpo, poison});
        mrmime1.setMovements(new Movement[]{puñoElectrico, psicoRayo, golpeCuerpo, poison});
        slaking1.setMovements(new Movement[]{puñoElectrico, psicoRayo, golpeCuerpo, electrocuted});
        golduck1.setMovements(new Movement[]{puñoAgua, hidrobomba, golpeCuerpo, electrocuted});

        //Agregar Pokemones Con Movimientos Predefinidos
        addPokemon(venusour1);
        addPokemon(charizard1);
        addPokemon(blastoise1);
        addPokemon(raichu1);
        addPokemon(machamp1);
        addPokemon(gengar1);
        addPokemon(dragonite1);
        addPokemon(togetic1);
        addPokemon(donphan1);
        addPokemon(tyranitar1);
        addPokemon(snorlax1);
        addPokemon(delibird1);
        addPokemon(gardevoir1);
        addPokemon(metagross1);
        addPokemon(blaziken1);
        addPokemon(gyarados1);
        addPokemon(hooh1);
        addPokemon(mrmime1);
        addPokemon(slaking1);
        addPokemon(golduck1);

        Item psPotion = new NormalPotion();
        Item superPotion = new SuperPotion();
        Item hyperPotion = new HyperPotion();
        Item revive = new Revive();
        addItem(psPotion);
        addItem(superPotion);
        addItem(hyperPotion);
        addItem(revive);

        //Trainers
        Trainer defensive = new DefensiveTrainer("Defensivo", new Color(0, 0, 255));
        Trainer attacking = new AttackingTrainer("Ofensivo", new Color(255, 0, 0));
        Trainer changing = new ChangingTrainer("Changing", new Color(0, 255, 0));
        Trainer expert = new ExpertTrainer("Experto", new Color(255, 255, 0));

        Inventory defensiveInventory = new Inventory();
        defensive.setInventory(defensiveInventory);
        defensiveInventory.addPokemon(venusour1);
        defensiveInventory.addPokemon(charizard1);
        defensiveInventory.addPokemon(blastoise1);
        defensiveInventory.addPokemon(raichu1);
        defensiveInventory.addPokemon(machamp1);
        defensiveInventory.addPokemon(gengar1);
        defensiveInventory.addItem(revive);
        defensiveInventory.addItem(psPotion);
        defensiveInventory.addItem(superPotion);
        defensiveInventory.addItem(hyperPotion);

        Inventory attackingInventory = new Inventory();
        attacking.setInventory(attackingInventory);
        attackingInventory.addPokemon(dragonite1);
        attackingInventory.addPokemon(togetic1);
        attackingInventory.addPokemon(donphan1);
        attackingInventory.addPokemon(tyranitar1);
        attackingInventory.addPokemon(snorlax1);
        attackingInventory.addPokemon(delibird1);
        attackingInventory.addItem(revive);
        attackingInventory.addItem(psPotion);
        attackingInventory.addItem(superPotion);
        attackingInventory.addItem(hyperPotion);

        Inventory changingInventory = new Inventory();
        changing.setInventory(changingInventory);
        changingInventory.addPokemon(gardevoir1);
        changingInventory.addPokemon(metagross1);
        changingInventory.addPokemon(blaziken1);
        changingInventory.addPokemon(gyarados1);
        changingInventory.addPokemon(hooh1);
        changingInventory.addPokemon(mrmime1);
        changingInventory.addItem(revive);
        changingInventory.addItem(psPotion);
        changingInventory.addItem(superPotion);
        changingInventory.addItem(hyperPotion);

        Inventory expertInventory = new Inventory();
        expert.setInventory(expertInventory);
        expertInventory.addPokemon(slaking1);
        expertInventory.addPokemon(golduck1);
        expertInventory.addPokemon(venusour1);
        expertInventory.addPokemon(charizard1);
        expertInventory.addPokemon(blastoise1);
        expertInventory.addPokemon(raichu1);
        expertInventory.addItem(revive);
        expertInventory.addItem(psPotion);
        expertInventory.addItem(superPotion);
        expertInventory.addItem(hyperPotion);

        addTrainer(defensive);
        addTrainer(attacking);
        addTrainer(changing);
        addTrainer(expert);
    }

    public void initializateBattle(String player1, String player2){
        Trainer trainer1 = trainers.get(player1);
        Trainer trainer2 = trainers.get(player2);
        System.out.println("Entrenador 1: " + trainer1.getNombre() + "con" + " " + trainer1.getActivePokemon().getName());
        System.out.println("Entrenador 2: " + trainer2.getNombre() + "con" + " " + trainer2.getActivePokemon().getName());
        if (trainer1 == null || trainer2 == null) {
            JOptionPane.showMessageDialog(null, "Los trainers no existen");
            return;
        }
        turnsTrainers.add(trainer1);
        turnsTrainers.add(trainer2);
        currentTurn = 0;
        winner = null;
        gameOver = false;

    }

    public void useMovement(String movement) throws POOBkemonException{
        checkBattleState();
        Trainer currentTrainer = turnsTrainers.get(currentTurn);
        Trainer opponentTrainer = turnsTrainers.get((currentTurn + 1) % 2);
        System.out.println("Turno: " + currentTrainer.getNombre());
        currentTrainer.getActivePokemon().affectPokemonStatus();
        currentTrainer.getActivePokemon().applyStatusEffects();
        currentTrainer.useMovement(movement, opponentTrainer.getActivePokemon());
    }

    public void changePokemon(String pokemonName){
        Trainer currentTrainer = turnsTrainers.get(currentTurn);
        Trainer opponentTrainer = turnsTrainers.get((currentTurn + 1) % 2);
        currentTrainer.getActivePokemon().affectPokemonStatus();
        currentTrainer.getActivePokemon().applyStatusEffects();
        try {
            currentTrainer.changePokemon(pokemonName);
        }catch (POOBkemonException e){
            System.out.println(e.getMessage());
        }
    }

    public void afterAction() throws POOBkemonException{
        Trainer current = turnsTrainers.get(currentTurn);
        Trainer opponent = turnsTrainers.get((currentTurn + 1) % 2);
        verifyTurnMachine();
        checkBattleState();
    }

    private void checkBattleState(){
        Trainer trainer1 = turnsTrainers.get(0);
        Trainer trainer2 = turnsTrainers.get(1);
        if(trainer1.isDefeated()){
            gameOver = true;
            winner = trainer2;
        }
        else if(trainer2.isDefeated()){
            gameOver = true;
            winner = trainer1;
        }
        if(trainer1.getActivePokemon() == null){
            gameOver = true;
            winner = trainer2;
        }
        if( trainer2.getActivePokemon() == null){
            gameOver = true;
            winner = trainer1;

        }
    }

    public void verifyTurnMachine(){
        Trainer current = turnsTrainers.get(currentTurn);
        Trainer opponent = turnsTrainers.get((currentTurn + 1) % 2);
        if (lastAction!= null) {
            lastAction = opponent.decide(current.getActivePokemon());
            System.out.println("Ya jugue maquina?: " + lastAction);
            if (lastAction != null){
                advanceTurn();
            }
        }
        else{
            advanceTurn();
        }
    }

    private void advanceTurn(){
        currentTurn = (currentTurn + 1) % turnsTrainers.size();
    }

    public void resetTurns(){
        currentTurn = 0;
    }

    public void useItem(String item) throws POOBkemonException{
        System.out.println(getCurrentTrainer().getNombre());
        getCurrentTrainer().useItem(item);
        afterAction();
    }

    public void leaveGame(){
        gameOver = true;
        winner = null;
        trainers.clear();
        turnsTrainers.clear();
        currentTurn = 0;
        lastAction = null;
    }

    public void addNewPokemon(String entrenador, String pokemon,Movement m1,Movement m2, Movement m3, Movement m4){
        Pokemon pokemon1 = pokedex.get(pokemon).copy();
        pokemon1.setMovements(new Movement[]{m1,m2,m3,m4});
        trainers.get(entrenador).addPokemon(pokemon1);
    }

    public void addNewTrainer(String name, Color color) throws POOBkemonException{
        if (trainers.containsKey(name)) throw new POOBkemonException(POOBkemonException.INVALID_VALUES);
        trainers.put(name, new Trainer(name,color));
    }

    public void inicialTrainerPokemon(String trainer, String pokemon) throws POOBkemonException{
        Trainer trainer1 = trainers.get(trainer);
        trainer1.setPokemonInUse(pokemon);
    }

    public Trainer getCurrentTrainer(){
        return turnsTrainers.get(currentTurn);
    }

    public Trainer getOpponentTrainer(){
        return turnsTrainers.get((currentTurn + 1) % 2);
    }

    public Trainer getTrainer(String name) {
        return trainers.get(name);
    }

    public void addPokemon(Pokemon pokemon) {
        pokedex.put(pokemon.getName(), pokemon);
    }

    public void addTrainer(Trainer trainer) {
        trainers.put(trainer.getNombre(), trainer);
    }

    public void addItem(Item item) {
        defaultItemsMap.put(item.getName(), item);
    }

    public void addMovement(Movement movement) throws POOBkemonException {
        if (defaultMovementsMap.containsKey(movement.getName())) {
            throw new POOBkemonException(POOBkemonException.INVALID_MOVEMENT);
        }
        defaultMovementsMap.put(movement.getName(), movement);
    }


    public TreeMap<String, Pokemon> getPokedex() {
        return pokedex;
    }

    public TreeMap<String, Movement> getDefaultMovementsMap() {
        return defaultMovementsMap;
    }

    public HashMap<String, Item> getDefaultItemsMap() {
        return defaultItemsMap;
    }

    public HashMap<String, Trainer> getTrainers() {
        return trainers;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getWinner(){
        if (winner != null) {
            return winner.getNombre();
        } else {
            return null;
        }
    }

    //------------------Serializate Game-------------------
    private void serializateGame(String fileName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("gameData"));
            out.writeObject(this);
            out.close();
        } catch (NotSerializableException e) {
            System.out.println("Error al serializar: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error al guardar el juego: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void serializateGame(){
        String fileName = "gameData";
        serializateGame(fileName);
    }

    public void guardarJuego(File file) throws POOBkemonException{
        if (file == null) {
            throw new POOBkemonException("El archivo especificado es nulo");
        }
        try {
            ObjectOutputStream out = new ObjectOutputStream((new FileOutputStream(file)));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            throw new POOBkemonException("Error de entrada/salida al escribir el archivo: " + file.getName());
        }
    }

    public POOBkemon cargarJuego(File file) throws POOBkemonException {
        if (file == null) {
            throw new POOBkemonException("El archivo especificado es nulo");
        }
        if (!file.exists()) {
            throw new POOBkemonException("No se encontró el archivo: " + file.getAbsolutePath());
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object deserialize = in.readObject();
            if (deserialize instanceof POOBkemon) {
                return (POOBkemon) deserialize;
            } else {
                throw new POOBkemonException("El archivo no contiene un objeto de tipo City válido");
            }
        } catch (FileNotFoundException e) {
            throw new POOBkemonException("No se pudo encontrar el archivo: " + file.getName());
        } catch (ClassNotFoundException e) {
            throw new POOBkemonException("No se encontró la definición de la clase al deserializar");
        } catch (IOException e) {
            throw new POOBkemonException("Error de entrada/salida al leer el archivo: " + file.getName());
        }
    }



    //------------------Deserializate Game-------------------
    private POOBkemon deserializateGame(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("El archivo " + fileName + " no existe.");
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Object deserializedObject = in.readObject();
            if (deserializedObject instanceof POOBkemon) {
                POOBkemon poobkemon = (POOBkemon) deserializedObject;
                System.out.println("Juego cargado: " + fileName);
                //System.out.println("Pokédex: " + poobkemon.getPokedex());
                return poobkemon;
            } else {
                System.err.println("El archivo no contiene un objeto de tipo POOBkemon.");
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Clase no encontrada durante la deserialización: " + e.getMessage());
        }
        return null;
    }


    public POOBkemon deserializateGame(){
        String fileName = "gameData";
        return deserializateGame(fileName);
    }
}