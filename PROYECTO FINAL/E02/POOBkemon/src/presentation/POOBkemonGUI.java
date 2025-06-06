package src.presentation;

import src.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.Timer;


public class POOBkemonGUI extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private HomeScreenPanel homePanel;
    private PlayScreenPanel playPanel;
    private PokedexPanel pokedexPanel;
    private OptionsPvsPPanel optionsPvsPPanel;
    private FightsPanel fightsPanel;
    private PokemonSelectionPanel pokemonSelectionPanel;
    private TrainersPanel trainersPanel;
    private MovementsSelectionPanel movementsPanel;
    private boolean isPaused = false;
    private POOBkemon pooBkemon;
    private List<String> availablePlayerPokemons;
    private List<String> availableOpponentPokemons;
    private Map<String, Pokemon> pokedex;


    private Timer turnTimer;
    private int currentTurn = 1;
    private boolean inBattle = false;
    private static final int TURN_DURATION = 20 * 1000; // 20 segundos en milisegundos
    private Pokemon playerActivePokemon;
    private Pokemon opponentActivePokemon;

    public POOBkemonGUI() {
        pooBkemon = new POOBkemon();
        prepareElements();
        prepareActions();
    }

    public void prepareElements() {
        setTitle("POOBkemon");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        add(contentPanel);

        // Inicializar paneles
        homePanel = new HomeScreenPanel();
        contentPanel.add(homePanel, "INICIO");

        playPanel = new PlayScreenPanel();
        contentPanel.add(playPanel, "JUGAR");

        optionsPvsPPanel = new OptionsPvsPPanel();
        contentPanel.add(optionsPvsPPanel, "OPCIONES");

        fightsPanel = new FightsPanel();
        fightsPanel.setLayout(null);
        contentPanel.add(fightsPanel, "BATALLA");

        pokedex = createPokedex();
        Map<String, Movement> defaultMovementsMap = createDefaultMovements();

        trainersPanel = new TrainersPanel();
        contentPanel.add(trainersPanel, "ENTRENADORES");

        pokemonSelectionPanel = new PokemonSelectionPanel();
        contentPanel.add(pokemonSelectionPanel, "POKEMON SELECTION");

        movementsPanel = new MovementsSelectionPanel();
        contentPanel.add(movementsPanel, "MOVIMIENTOS");

        List<String> pokedexImages = new ArrayList<>(pokedex.keySet());
        pokedexPanel = new PokedexPanel(pokedexImages);
        contentPanel.add(pokedexPanel, "POKEDEX");

        availablePlayerPokemons = new ArrayList<>();
        availableOpponentPokemons = new ArrayList<>();
    }

    public void prepareActions() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });

        homePanel.getExitButton().addActionListener(e -> exit());
        homePanel.getPlayButton().addActionListener(e -> cardLayout.show(contentPanel, "JUGAR"));
        playPanel.getBackButton().addActionListener(e -> cardLayout.show(contentPanel, "INICIO"));
        playPanel.getPokedexButton().addActionListener(e -> cardLayout.show(contentPanel, "POKEDEX"));
        playPanel.getPVsPButton().addActionListener(e -> cardLayout.show(contentPanel, "OPCIONES"));
        playPanel.getMVsMButton().addActionListener(e -> cardLayout.show(contentPanel, "BATALLA"));

        optionsPvsPPanel.getBackButton().addActionListener(e -> cardLayout.show(contentPanel, "JUGAR"));
        optionsPvsPPanel.getSurvivalMode().addActionListener(e -> cardLayout.show(contentPanel, "BATALLA"));
        optionsPvsPPanel.getNormalMode().addActionListener(e -> {cardLayout.show(contentPanel, "ENTRENADORES");});
        trainersPanel.getBackButton().addActionListener(e -> {
            cardLayout.show(contentPanel, "OPCIONES");
        });
        trainersPanel.getConfirmButton().addActionListener(e -> {
            // Get both names
            String player1Name = trainersPanel.getPlayer1Name();
            String player2Name = trainersPanel.getPlayer2Name();

            // Only navigate if player 2 name is set (which means we're on player 2 selection)
            if (!player1Name.isEmpty() && !player2Name.isEmpty()) {
                // Update the trainer names in your game model
                getTrainer1().setNombre(player1Name);
                getTrainer2().setNombre(player2Name);

                // Update the Pokémon selection panel with trainer names
                pokemonSelectionPanel.updateTrainerNames(player1Name, player2Name);

                // Navigate to Pokémon selection
                cardLayout.show(contentPanel, "POKEMON SELECTION");
                pokemonSelectionPanel.forceRepaint();
            }
            // If either name is empty, the TrainersPanel's own action handler will show an error
        });
        trainersPanel.getBackButton().addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas volver?",
                    "Confirmar Volver", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                trainersPanel.resetSelectionPanel();
                cardLayout.show(contentPanel, "OPCIONES");
            }
        });
        //Movements...
//        optionsPvsPPanel.getNormalMode().addActionListener(e -> {cardLayout.show(contentPanel, "POKEMON SELECTION");
//            pokemonSelectionPanel.forceRepaint();
//        });
        pokemonSelectionPanel.getCancelButton().addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas cancelar la selección?",
                    "Confirmar cancelación", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                pokemonSelectionPanel.resetSelectionPanel();
                trainersPanel.resetSelectionPanel();// Just reset the panel without confirmation
                cardLayout.show(contentPanel, "OPCIONES");
            }
        });
        pokemonSelectionPanel.getGoToMovementsButton().addActionListener(e -> cardLayout.show(contentPanel, "MOVIMIENTOS"));
        pokedexPanel.getBackButton().addActionListener(e -> cardLayout.show(contentPanel, "JUGAR"));
        fightsPanel.getRunButton().addActionListener(e -> run());
        fightsPanel.getPauseButton().addActionListener(e -> pause());
        fightsPanel.getPokemonButton().addActionListener(e -> handlePokemonSelection());
        fightsPanel.getBagButton().addActionListener(e -> JOptionPane.showMessageDialog(this, "Acción de la mochila no implementada.", "Mochila", JOptionPane.INFORMATION_MESSAGE));
        fightsPanel.getFightButton().addActionListener(e -> JOptionPane.showMessageDialog(this, "Acción de pelear no implementada.", "Pelear", JOptionPane.INFORMATION_MESSAGE));
    }

    /**
     * Muestra las opciones de movimientos disponibles según el turno actual
     */
    private void showMoveOptions() {
        Pokemon activePokemon = (currentTurn == 1) ? playerActivePokemon : opponentActivePokemon;

        if (activePokemon == null) {
            JOptionPane.showMessageDialog(this, "No hay Pokémon activo para realizar movimientos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Movement> movements = activePokemon.getMovements();
        if (movements == null || movements.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El Pokémon no tiene movimientos disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] moveOptions = new String[movements.size()];
        for (int i = 0; i < movements.size(); i++) {
            moveOptions[i] = movements.get(i).getName();
        }

        String selectedMove = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona un movimiento:",
                "Movimientos de " + activePokemon.getName(),
                JOptionPane.QUESTION_MESSAGE,
                null,
                moveOptions,
                moveOptions[0]
        );

        if (selectedMove != null) {
            executeMove(selectedMove);
        }
    }

//    private void startGame(){
//        pokemonSelectionPanel.startBattle();
//        setTrainer1Pokemons();
//        setTrainer2Pokemons();
//        startBattle();
//    }

    /**
     * Inicia la batalla entre los dos entrenadores
     */
    private void startBattle() {
        inBattle = true;
        currentTurn = 1;

        Trainer trainer1 = getTrainer1();
        Trainer trainer2 = getTrainer2();

        if (!trainer1.getTeam().isEmpty() && !trainer2.getTeam().isEmpty()) {
            playerActivePokemon = trainer1.getTeam().get(0);
            opponentActivePokemon = trainer2.getTeam().get(0);
            fightsPanel.getFightButton().removeActionListener(fightsPanel.getFightButton().getActionListeners()[0]);
            fightsPanel.getFightButton().addActionListener(e -> showMoveOptions());
            updateBattleUI();
            startTurnTimer();

            JOptionPane.showMessageDialog(this,
                    "¡La batalla ha comenzado! Tienes 20 segundos para realizar tu movimiento.",
                    "Inicio de Batalla",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "No hay Pokémon disponibles para la batalla.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            cardLayout.show(contentPanel, "INICIO");
        }
    }

    private TreeMap<String, Pokemon> createPokedex() {
        TreeMap<String, Pokemon> finalPokedex = new TreeMap<>();
        TreeMap<String, Pokemon> arrayPokedex = pooBkemon.getPokedex();
        for (Map.Entry<String, Pokemon> entry : arrayPokedex.entrySet()) {
            String key = entry.getKey();
            Pokemon value = entry.getValue();
            finalPokedex.put(key, value);
        }
        return finalPokedex;
    }

    private HashMap<String, Movement> createDefaultMovements() {
        HashMap<String, Movement> finalMovements = new HashMap<>();
        HashMap<String, Movement> arrayMovements = pooBkemon.getDefaultMovementsMap();
        for (Map.Entry<String, Movement> entry : arrayMovements.entrySet()) {
            String key = entry.getKey();
            Movement value = entry.getValue();
            finalMovements.put(key, value);
        }
        return finalMovements;
    }

    private HashMap<String, Item> createDefaultItems() {
        HashMap<String, Item> finalItems = new HashMap<>();
        HashMap<String, Item> arrayItems = pooBkemon.getDefaultItemsMap();
        for (Map.Entry<String, Item> entry : arrayItems.entrySet()) {
            String key = entry.getKey();
            Item value = entry.getValue();
            finalItems.put(key, value);
        }
        return finalItems;
    }
    public void handlePokemonSelection() {
        Object[] options = {"Jugador", "Oponente"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "¿Quién cambiará su Pokémon?",
                "Cambio de Pokémon",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

//       if (choice == JOptionPane.YES_OPTION) {
//           Pokemon newPokemon = fightsPanel.changePlayerPokemon();
//           if (newPokemon != null) {
//              playerActivePokemon = newPokemon;
//               updateBattleUI();
//           }
//        } else if (choice == JOptionPane.NO_OPTION) {
//          Pokemon newPokemon = fightsPanel.changeOpponentPokemon();
//           if (newPokemon != null) {
//               opponentActivePokemon = newPokemon;
//               updateBattleUI();
//            }
//        }
    }

    public void pause() {
        isPaused = !isPaused;

        fightsPanel.getFightButton().setEnabled(!isPaused);
        fightsPanel.getBagButton().setEnabled(!isPaused);
        fightsPanel.getPokemonButton().setEnabled(!isPaused);
        fightsPanel.getRunButton().setEnabled(!isPaused);

        if (isPaused) {
            fightsPanel.getPauseButton().setText("RESUME");
            JOptionPane.showMessageDialog(this, "La batalla está pausada.", "Pausa", JOptionPane.INFORMATION_MESSAGE);
            if (turnTimer != null) {
                turnTimer.stop();
            }
        } else {
            fightsPanel.getPauseButton().setText("PAUSE");
            JOptionPane.showMessageDialog(this, "La batalla se ha reanudado.", "Reanudar", JOptionPane.INFORMATION_MESSAGE);
            if (inBattle) {
                startTurnTimer();
            }
        }
    }

    /**
     * Ejecuta el movimiento seleccionado y aplica el daño al Pokémon rival
     * @param movementName Nombre del movimiento a ejecutar
     */
    private void executeMove(String movementName) {
        Pokemon attacker = (currentTurn == 1) ? playerActivePokemon : opponentActivePokemon;
        Pokemon target = (currentTurn == 1) ? opponentActivePokemon : playerActivePokemon;

        Movement selectedMovement = null;
        for (Movement m : attacker.getMovements()) {
            if (m.getName().equals(movementName)) {
                selectedMovement = m;
                break;
            }
        }
        if (selectedMovement != null) {
            if (turnTimer != null) {
                turnTimer.stop();
            }


            attacker.useMovement(selectedMovement, target);

            updateBattleUI();
            if (target.isFainted()) {
                JOptionPane.showMessageDialog(this,
                        target.getName() + " se ha debilitado!",
                        "Pokémon Debilitado",
                        JOptionPane.INFORMATION_MESSAGE);
                if (currentTurn == 2) {
                    handlePlayerDefeat();
                } else {
                    handleOpponentDefeat();
                }
            } else {
                switchTurn();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el movimiento seleccionado.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Maneja la derrota del Pokémon del jugador
     */
    private void handlePlayerDefeat() {
        Trainer trainer1 = getTrainer1();
        boolean hasMorePokemon = false;

        for (Pokemon pokemon : trainer1.getTeam()) {
            if (!pokemon.isFainted() && pokemon != playerActivePokemon) {
                hasMorePokemon = true;
                break;
            }
        }

        if (hasMorePokemon) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Tu Pokémon ha sido derrotado. ¿Deseas cambiar a otro Pokémon?",
                    "Cambio de Pokémon",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                fightsPanel.changePlayerPokemon();
                updateBattleUI();
                switchTurn();
            } else {
                endBattle("Jugador 2");
            }
        } else {
            endBattle("Jugador 2");
        }
    }

    /**
     * Maneja la derrota del Pokémon del oponente
     */
    private void handleOpponentDefeat() {
        Trainer trainer2 = getTrainer2();
        boolean hasMorePokemon = false;

        for (Pokemon pokemon : trainer2.getTeam()) {
            if (!pokemon.isFainted() && pokemon != opponentActivePokemon) {
                hasMorePokemon = true;
                break;
            }
        }

        if (hasMorePokemon) {
            fightsPanel.changeOpponentPokemon();
            updateBattleUI();
            switchTurn();
        } else {
            endBattle("Jugador 1");
        }
    }



    private void run() {
        JOptionPane.showMessageDialog(this, "El jugador ha decidido huir.", "Fin del Combate", JOptionPane.INFORMATION_MESSAGE);
        if (turnTimer != null) {
            turnTimer.stop();
        }
        inBattle = false;
        cardLayout.show(contentPanel, "INICIO");
    }

    /**
     * Inicia el temporizador para el turno actual
     */
    private void startTurnTimer() {
        if (turnTimer != null) {
            turnTimer.stop();
        }

        turnTimer = new Timer(TURN_DURATION, e -> {
            JOptionPane.showMessageDialog(this,
                    "¡Se acabó el tiempo! Turno perdido.",
                    "Tiempo Agotado",
                    JOptionPane.INFORMATION_MESSAGE);
            switchTurn();
        });

        turnTimer.setRepeats(false);
        turnTimer.start();
    }

    /**
     * Cambia al siguiente turno
     */
    private void switchTurn() {
        currentTurn = (currentTurn == 1) ? 2 : 1;
        startTurnTimer();
        if (currentTurn == 2) {
            simulateOpponentTurn();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Es tu turno. Tienes 20 segundos para realizar un movimiento.",
                    "Turno del Jugador",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Simula el turno del oponente controlado por la CPU
     */
    private void simulateOpponentTurn() {
        Timer delayTimer = new Timer(1000, e -> {
            if (opponentActivePokemon != null && !opponentActivePokemon.getMovements().isEmpty()) {
                int randomIndex = (int)(Math.random() * opponentActivePokemon.getMovements().size());
                Movement randomMove = opponentActivePokemon.getMovements().get(randomIndex);
                executeMove(randomMove.getName());
            } else {
                JOptionPane.showMessageDialog(this,
                        "El oponente no tiene movimientos disponibles.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                switchTurn();
            }
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    /**
     * Finaliza la batalla declarando un ganador
     * @param winner Nombre del ganador
     */
    private void endBattle(String winner) {
        inBattle = false;
        if (turnTimer != null) {
            turnTimer.stop();
        }

        JOptionPane.showMessageDialog(this,
                "¡La batalla ha terminado! El ganador es: " + winner,
                "Fin de la Batalla",
                JOptionPane.INFORMATION_MESSAGE);
        cardLayout.show(contentPanel, "INICIO");
    }

    public void closeWindow() {
        JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(this, "Confirmar Salida");
        dialog.setVisible(true);
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
            System.exit(0);
        }
    }

    public void exit() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(this, "Confirmar Salida");
        dialog.setVisible(true);
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
            System.exit(0);
        }
    }

    public Trainer getTrainer1(){
        Trainer trainer1 = pooBkemon.getTrainers().get(0);
        return trainer1;
    }

    public Trainer getTrainer2(){
        Trainer trainer2 = pooBkemon.getTrainers().get(1);
        return trainer2;
    }

//    //POKEMONES CON MOVIMIENTOS PRESELECCIONADOS
//    public void setTrainer2Pokemons(){
//        Trainer trainer2 = getTrainer2();
//        List<String> trainer2Pokemons = pokemonSelectionPanel.getOpponentPokemons();
//        for(String pokemonName : trainer2Pokemons){
//            Pokemon pokemon = pokedex.get(pokemonName);
//            trainer2.addPokemon(pokemon);
//        }
//    }
    //POKEMONES SIN MOVIMIENTOS PRESELECCIONADOS
//    public void setTrainer1Pokemons(){
//        Trainer trainer1 = getTrainer1();
//        List<String> trainer1Pokemons = pokemonSelectionPanel.getPlayerPokemons();
//        for(String pokemonName : trainer1Pokemons){
//            Pokemon pokemon = pokedex.get(pokemonName);
//            trainer1.addPokemon(pokemon);
//        }
//    }

    public List<Pokemon> getTrainerTeam(){
        Trainer trainer1 = getTrainer1();
        List<Pokemon> team1 = trainer1.getTeam();
        return team1;
    }

    public List<Pokemon> getOpponentTeam(){
        Trainer trainer2 = getTrainer2();
        List<Pokemon> team2 = trainer2.getTeam();
        return team2;
    }

    /**
     * Actualiza la información de los Pokémon en la interfaz de batalla
     */
    private void updateBattleUI() {
        if (playerActivePokemon == null || opponentActivePokemon == null) {
            Trainer trainer1 = getTrainer1();
            Trainer trainer2 = getTrainer2();

            if (!trainer1.getTeam().isEmpty() && !trainer2.getTeam().isEmpty()) {
                for (Pokemon p : trainer1.getTeam()) {
                    if (!p.isFainted()) {
                        playerActivePokemon = p;
                        break;
                    }
                }

                for (Pokemon p : trainer2.getTeam()) {
                    if (!p.isFainted()) {
                        opponentActivePokemon = p;
                        break;
                    }
                }
            }
        }

        if (playerActivePokemon != null && opponentActivePokemon != null) {
            fightsPanel.updatePokemonInfo(playerActivePokemon, opponentActivePokemon);
        }
    }

    public static void main(String[] args) {
        POOBkemonGUI pooBkemonGUI = new POOBkemonGUI();
        pooBkemonGUI.setLocationRelativeTo(null);
        pooBkemonGUI.setVisible(true);
    }
}