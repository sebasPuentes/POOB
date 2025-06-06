package src.presentation;

import src.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class POOBkemonGUI extends JFrame {

    //==========================================================================
    // 1. DECLARACIÓN DE VARIABLES Y CONSTANTES
    //==========================================================================
    // Interfaz principal
    public JPanel contentPanel;
    public CardLayout cardLayout;

    // Paneles de la aplicación
    protected HomeScreenPanel homePanel;
    protected PlayScreenPanel playPanel;
    protected PokedexPanel pokedexPanel;
    protected OptionsPvsPPanel optionsPvsPPanel;
    protected FightsPanel fightsPanel;
    protected FightPanelMvM fightPanelMvM; // Nuevo panel para batallas automáticas
    protected PokemonSelectionPanel pokemonSelectionPanel;
    protected TrainersPanel trainersPanel;
    protected MovementsSelectionPanel movementsPanel;
    protected ItemsPanel itemsPanel;
    protected MachineVsMachinePanel machineVsMachinePanel;
    private JMenuItem loadGameItem;
    private JMenuItem saveGameItem;
    private JMenuItem exitItem;

    // Paneles de acciones de batalla
    private JPanel movesPanel;
    private JPanel bagPanel;
    private JPanel pokemonTeamPanel;
    // Datos del juego
    protected POOBkemon pooBkemon;
    protected String player1;
    protected String player2;
    //==========================================================================
    // 2. CONSTRUCTOR E INICIALIZACIÓN
    //==========================================================================

    public POOBkemonGUI() {
        pooBkemon = new POOBkemon();
        pooBkemon.deserializateGame();
        prepareElements();
        prepareActions();
        prepareElementsMenu();
        prepareActionsMenu();
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

        fightsPanel = new FightsPanel(this);
        fightsPanel.setLayout(null);
        contentPanel.add(fightsPanel, "BATALLA");
        
        // Nuevo panel para batallas automáticas
        fightPanelMvM = new FightPanelMvM(this);
        fightPanelMvM.setLayout(null);
        contentPanel.add(fightPanelMvM, "BATALLA_AUTOMATICA");

        trainersPanel = new TrainersPanel();
        contentPanel.add(trainersPanel, "ENTRENADORES");

        pokemonSelectionPanel = new PokemonSelectionPanel(this);
        contentPanel.add(pokemonSelectionPanel, "POKEMON SELECTION");

        movementsPanel = new MovementsSelectionPanel(this);
        contentPanel.add(movementsPanel, "MOVIMIENTOS");

        movesPanel = new JPanel();
        movesPanel.setLayout(new GridLayout(0, 2, 10, 10));
        contentPanel.add(movesPanel, "MOVES");

        bagPanel = new JPanel();
        bagPanel.setLayout(new GridLayout(0, 2, 10, 10));
        contentPanel.add(bagPanel, "BAG");

        pokemonTeamPanel = new JPanel();
        pokemonTeamPanel.setLayout(new GridLayout(0, 2, 10, 10));
        contentPanel.add(pokemonTeamPanel, "POKEMON_TEAM");

        machineVsMachinePanel = new MachineVsMachinePanel(this);
        contentPanel.add(machineVsMachinePanel, "MACHINE_VS_MACHINE");


        ArrayList<String> pokedexImages = new ArrayList<>(pooBkemon.getPokedex().keySet());
        pokedexPanel = new PokedexPanel(pokedexImages);
        contentPanel.add(pokedexPanel, "POKEDEX");

        itemsPanel = new ItemsPanel(this);
        contentPanel.add(itemsPanel, "ITEMS");
    }

    private void prepareElementsMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        loadGameItem = new JMenuItem("Cargar Juego");
        saveGameItem = new JMenuItem("Guardar Juego");
        exitItem = new JMenuItem("Salir");
        fileMenu.add(loadGameItem);
        fileMenu.addSeparator();
        fileMenu.add(saveGameItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }



    //==========================================================================
    // 3. CONFIGURACIÓN DE ACCIONES Y EVENTOS
    //==========================================================================
    public void prepareActionsMenu(){
        loadGameItem.addActionListener(e -> {fightsPanel.loadGame();
        cardLayout.show(contentPanel, "BATALLA");});
        saveGameItem.addActionListener(e -> optionSave());
        exitItem.addActionListener(e -> exit());
    }

    public void prepareActions() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });

        // Acciones de pantalla inicial y navegación
        homePanel.getExitButton().addActionListener(e -> exit());
        homePanel.getPlayButton().addActionListener(e -> cardLayout.show(contentPanel, "JUGAR"));
        playPanel.getBackButton().addActionListener(e -> cardLayout.show(contentPanel, "INICIO"));
        playPanel.getPokedexButton().addActionListener(e -> cardLayout.show(contentPanel, "POKEDEX"));
        playPanel.getPVsPButton().addActionListener(e -> cardLayout.show(contentPanel, "OPCIONES"));
        playPanel.getMVsMButton().addActionListener(e -> cardLayout.show(contentPanel, "MACHINE_VS_MACHINE"));
        pokedexPanel.getBackButton().addActionListener(e -> cardLayout.show(contentPanel, "JUGAR"));
        // Acciones de opciones de juego
        optionsPvsPPanel.getBackButton().addActionListener(e -> cardLayout.show(contentPanel, "JUGAR"));
        optionsPvsPPanel.getSurvivalMode().addActionListener(e -> cardLayout.show(contentPanel, "BATALLA"));
        optionsPvsPPanel.getNormalMode().addActionListener(e -> cardLayout.show(contentPanel, "ENTRENADORES"));
        // Acciones de selección de entrenadores
        trainersPanel.getBackButton().addActionListener(e -> backButtonAction());
        trainersPanel.getConfirmButton().addActionListener(e -> confirmTrainers());
        // Acciones de selección de Pokémon
        pokemonSelectionPanel.getCancelButton().addActionListener(e -> backToOptions());
        pokemonSelectionPanel.getGoToMovementsButton().addActionListener(e -> gotToMovements());
        // Acciones de selección de movimientos
        movementsPanel.getSelectItemsButton().addActionListener(e -> {
            itemsPanel.initialize(this);
            cardLayout.show(contentPanel, "ITEMS");
        });
        // Acciones de selección de ítems
        itemsPanel.getCancelButton().addActionListener(e -> {
            itemsPanel.handleCancel();
        });
        itemsPanel.getSwitchTrainerButton().addActionListener(e -> {
            itemsPanel.switchPlayer();
        });
        itemsPanel.getConfirmButton().addActionListener(e -> {
            itemsPanel.confirmSelection();
            prepareForBattle();
        });
        machineVsMachinePanel.getBackButton().addActionListener(e -> cardLayout.show(contentPanel, "JUGAR"));
        machineVsMachinePanel.getStartBattleButton().addActionListener(e -> startMachineBattle());

    }

    public void createTrainer(String trainerEscogido ,Color color){
        try{
            if (trainerEscogido == null) {
                JOptionPane.showMessageDialog(null, "Error: Player trainer name not set");
                return;
            }
            pooBkemon.addNewTrainer(trainerEscogido,color);
        }catch(POOBkemonException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public ArrayList<String> addItemsToTrainer(String trainerEscogido,ArrayList<String> itemsEscogidos){
        ArrayList<String> items = itemsEscogidos;
        for (String i:items){
            pooBkemon.getTrainer(trainerEscogido).getInventory().addItem(pooBkemon.getDefaultItemsMap().get(i));
        }
        return pooBkemon.getTrainer(trainerEscogido).getInventory().getItemsArray();
    }

    public void addPokemonsToTrainer(String trainerEscogido, HashMap<String, ArrayList<String>> pokemonsWithAll) throws POOBkemonException {
        HashMap<String, Movement> movimientosMap = createDefaultMovements();
        for (Map.Entry<String, ArrayList<String>> entry : pokemonsWithAll.entrySet()) {
            String pokemonName = entry.getKey();
            ArrayList<String> movimientosSeleccionados = entry.getValue();
            if (movimientosSeleccionados.size() != 4) {
                throw new POOBkemonException("Cada Pokémon debe tener exactamente 4 movimientos seleccionados");
            }
            Movement mov1 = movimientosMap.get(movimientosSeleccionados.get(0));
            Movement mov2 = movimientosMap.get(movimientosSeleccionados.get(1));
            Movement mov3 = movimientosMap.get(movimientosSeleccionados.get(2));
            Movement mov4 = movimientosMap.get(movimientosSeleccionados.get(3));
            if (mov1 == null || mov2 == null || mov3 == null || mov4 == null) {
                throw new POOBkemonException("Uno o más movimientos seleccionados no existen");
            }
            pooBkemon.addNewPokemon(trainerEscogido, pokemonName, mov1, mov2, mov3, mov4);
        }
    }

    public void addNewPokemonToTrainer(String trainerEscogido, HashMap<String, ArrayList<String>> pokemonWithMovements) {
        try {
            addPokemonsToTrainer(trainerEscogido, pokemonWithMovements);
            System.out.println("Pokémon añadidos exitosamente al entrenador " + trainerEscogido);
        } catch (POOBkemonException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al añadir Pokémon: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void gotToMovements(){
        ArrayList<String> playerPokemonList = pokemonSelectionPanel.getPlayerPokemons();
        ArrayList<String> opponentPokemonList = pokemonSelectionPanel.getOpponentPokemons();
        System.out.println(playerPokemonList + "Entra En Movements Los Pokemons Aliados");
        System.out.println(opponentPokemonList + "Entra En Movements Los Pokemons Enemigos");
        if (playerPokemonList.isEmpty() || opponentPokemonList.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ambos jugadores deben seleccionar al menos un Pokémon.",
                    "Error de selección",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        movementsPanel.initialize(this, playerPokemonList, opponentPokemonList);
        cardLayout.show(contentPanel, "MOVIMIENTOS");
    }
    public void backToOptions(){
        int response = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas cancelar la selección?",
                "Confirmar cancelación", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            pokemonSelectionPanel.resetSelectionPanel();
            trainersPanel.resetSelectionPanel();
            cardLayout.show(contentPanel, "OPCIONES");
        }
    }
    public void confirmTrainers(){
        if (trainersPanel.isSelectionComplete()) {
            player1 = trainersPanel.getPlayer1Name();
            player2 = trainersPanel.getPlayer2Name();
            createTrainer(player1, Color.RED);
            createTrainer(player2, Color.BLUE);
            JOptionPane.showMessageDialog(this,
                    "¡Entrenadores registrados con éxito!\n\n" +
                            "Jugador 1: " + player1 + "\n" +
                            "Jugador 2: " + player2 + "\n\n" +
                            "¡Comienza tu aventura Pokémon! Elige tus Pokémon.",
                    "Entrenadores registrados", JOptionPane.INFORMATION_MESSAGE);

            pokemonSelectionPanel.updateTrainerNames(player1, player2);
            cardLayout.show(contentPanel, "POKEMON SELECTION");
            pokemonSelectionPanel.forceRepaint();
        }
    }
    public void backButtonAction(){
        int response = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas volver?",
                "Confirmar Volver", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            trainersPanel.resetSelectionPanel();
            cardLayout.show(contentPanel, "OPCIONES");
        }
    }
    public void closeWindow() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que quieres salir?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void exit() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que quieres salir?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Ejecuta un movimiento en el sistema de combate
     * @param movementName Nombre del movimiento a ejecutar
     * @param isPlayerAttacking True si ataca el jugador 1, false si ataca el jugador 2
     * @param playerPokemonIndex Índice del Pokémon del jugador 1
     * @param opponentPokemonIndex Índice del Pokémon del jugador 2
     * @return Daño causado por el ataque
     */
    /**
     * Ejecuta un movimiento en el sistema de combate
     *
     * @param movementName         Nombre del movimiento a ejecutar
     * @param isPlayerAttacking    True si ataca el jugador 1, false si ataca el jugador 2
     * @param playerPokemonIndex   Índice del Pokémon del jugador 1
     * @param opponentPokemonIndex Índice del Pokémon del jugador 2
     * @return Daño causado por el ataque
     */

    //==========================================================================
    // 6. GESTIÓN DE POKÉMON Y EQUIPOS
    //==========================================================================

    /**
     * Prepara la batalla con los entrenadores y Pokémon seleccionados
     */
    public void prepareForBattle() {
        HashMap<String,ArrayList<String>> playerPokemonsList = movementsPanel.getPlayerSelectedMovements();
        HashMap<String,ArrayList<String>> opponentPokemonsList = movementsPanel.getOpponentSelectedMovements();
        System.out.println(playerPokemonsList);
        System.out.println(opponentPokemonsList);
        if (playerPokemonsList.isEmpty() || opponentPokemonsList.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ambos jugadores deben seleccionar al menos un Pokémon.",
                    "Error de selección",
                    JOptionPane.ERROR_MESSAGE);
            cardLayout.show(contentPanel, "POKEMON SELECTION");
            return;
        }

        try {
            Trainer trainer1 = pooBkemon.getTrainer(player1);
            Trainer trainer2 = pooBkemon.getTrainer(player2);

            addNewPokemonToTrainer(trainer1.getNombre(), playerPokemonsList);
            addNewPokemonToTrainer(trainer2.getNombre(), opponentPokemonsList);
            System.out.println("Pokémon añadidos exitosamente al entrenador " + trainer1.getNombre());
            System.out.println("Pokémon añadidos exitosamente al entrenador " + trainer2.getNombre());
            addItemsToTrainer(trainer1.getNombre(), itemsPanel.getPlayerItems());
            addItemsToTrainer(trainer2.getNombre(), itemsPanel.getOpponentItems());
            System.out.println("Items añadidos exitosamente al entrenador " + trainer1.getNombre());
            System.out.println("Items añadidos exitosamente al entrenador " + trainer2.getNombre());
            cardLayout.show(contentPanel, "BATALLA");
            fightsPanel.startBattle(player1, player2);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al preparar la batalla: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void prepareForBattleMachines(){
        startMachineBattle();
    }

    //==========================================================================
    // 9. ACCESO A DATOS Y CREACIÓN DE RECURSOS
    //==========================================================================
    public HashMap<String, Movement> createDefaultMovements() {
        HashMap<String, Movement> finalMovements = new HashMap<>();
        TreeMap<String, Movement> arrayMovements = pooBkemon.getDefaultMovementsMap();
        for (Map.Entry<String, Movement> entry : arrayMovements.entrySet()) {
            String key = entry.getKey();
            Movement value = entry.getValue();
            finalMovements.put(key, value);
        }
        return finalMovements;
    }

    /**
     * Obtiene el entrenador del jugador 1
     */
    public Trainer getTrainer1() {
        Trainer trainer1 = pooBkemon.getTrainers().get(player1);
        return trainer1;
    }

    /**
     * Obtiene el entrenador del jugador 2
     */
    public Trainer getTrainer2() {
        Trainer trainer2 = pooBkemon.getTrainers().get(player2);
        return trainer2;
    }

    public ArrayList<String> getPokemonNames() {
        ArrayList<String> pokemonNames = new ArrayList<>();
        for (Pokemon pokemon : pooBkemon.getPokedex().values()) {
            pokemonNames.add(pokemon.getName());
        }
        return pokemonNames;
    }

    public ArrayList<String> getItemsNames() {
        ArrayList<String> items = new ArrayList<>();
        for (Item item : pooBkemon.getDefaultItemsMap().values()) {
            items.add(item.getName());
        }
        return items;
    }

    public ArrayList<String> getMovementsNames() {
        ArrayList<String> movements = new ArrayList<>();
        TreeMap<String, Movement> defaultMoves = pooBkemon.getDefaultMovementsMap();
        if (defaultMoves != null && !defaultMoves.isEmpty()) {
            for (Movement movement : defaultMoves.values()) {
                movements.add(movement.getName());
            }
        }
        if (defaultMoves == null || defaultMoves.isEmpty()) {
                System.out.println("No hay movimientos disponibles");
        }
        return movements;
    }

    protected void optionOpen(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                POOBkemon loadedPoobKemon = pooBkemon.cargarJuego(selectedFile);
                if (loadedPoobKemon != null) {
                    pooBkemon = loadedPoobKemon;
                    System.out.println(loadedPoobKemon.getTrainers().keySet());
                    JOptionPane.showMessageDialog(this, "File opened successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (POOBkemonException e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void optionSave(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                pooBkemon.guardarJuego(selectedFile);
                JOptionPane.showMessageDialog(this, "File saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch (POOBkemonException e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Inicia una batalla entre máquinas con los tipos seleccionados
     */
    private void startMachineBattle() {
        try {
            String trainer1Name = machineVsMachinePanel.getSelectedTrainer1Type();
            String trainer2Name = machineVsMachinePanel.getSelectedTrainer2Type();
            
            if (trainer1Name != null && trainer2Name != null) {
                player1 = trainer1Name;
                player2 = trainer2Name;
                
                // Cambiar a la pantalla de batalla automática
                cardLayout.show(contentPanel, "BATALLA_AUTOMATICA");
                
                // Iniciar la batalla en el panel de batalla automática
                fightPanelMvM.startBattle(trainer1Name, trainer2Name);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al iniciar la batalla: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        POOBkemonGUI pooBkemonGUI = new POOBkemonGUI();
        pooBkemonGUI.setLocationRelativeTo(null);
        pooBkemonGUI.setVisible(true);
    }

}
