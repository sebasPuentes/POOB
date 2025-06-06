package presentation;
import domain.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;
/**
 * Panel para gestionar y mostrar la batalla de Pokémon entre dos jugadores.
 */
public class FightsPanel extends JPanel {
    //==========================================================================
    // 1. DECLARACIÓN DE VARIABLES
    //==========================================================================
    protected POOBkemonGUI mainGUI;

    protected boolean inBattle = false;
    private boolean isPlayer1Turn = true;

    private ImageIcon background;
    private ImageIcon playerPokemon;
    private ImageIcon opponentPokemon;
    private int xFirst;
    private int yFirst;
    private int xSecond;
    private int ySecond;

    private JButton attackButton;
    private JButton bagButton;
    private JButton pokemonButton;
    private JButton runButton;
    private JButton pauseButton;
    private ArrayList<JButton> buttons = new ArrayList<>();

    protected JLabel turnLabel;
    protected JLabel messageLabel;
    // Estadísticas de Pokémon en batalla
    private int playerHealth;
    private int opponentHealth;
    private String playerName = "";
    private String opponentName = "";
    private int playerLevel = 100;
    private int opponentLevel = 100;
    // Nombres de jugadores
    private String player1Name ;
    private String player2Name ;

    private final double PLAYER_X_RATIO = 0.167;
    private final double PLAYER_Y_RATIO = 0.6;
    private final double PLAYER_SIZE_RATIO = 0.2;
    private final double OPPONENT_X_RATIO = 0.65;
    private final double OPPONENT_Y_RATIO = 0.15;
    private final double OPPONENT_SIZE_RATIO = 0.25;
    private final double TEXT_BOX_HEIGHT_RATIO = 0.083;
    private final double OPTIONS_BOX_WIDTH_RATIO = 0.5;

    protected JPanel actionPanel;
    protected CardLayout actionCardLayout;
    protected JPanel mainOptionsPanel;
    protected JPanel movementPanel;
    protected JPanel itemsPanel;
    protected JPanel pokemonSelectionPanel;
    // Temporizador para turnos
    protected Timer turnTimer;
    protected JLabel timerLabel;
    private int turnTimeRemaining = 20;
    private boolean timerRunning = true;
    //==========================================================================
    // 2. CONSTRUCTOR E INICIALIZACIÓN
    //==========================================================================
    /**
     * Constructor del panel de batalla.
     * Inicializa los componentes y configura el diseño.
     *
     * @param gui Referencia a la GUI principal
     */
    public FightsPanel(POOBkemonGUI gui) {
        this.mainGUI = gui;
        setLayout(null);
        player1Name = mainGUI.player1;
        player2Name = mainGUI.player2;
        loadResources();
        initializeComponents();
        initializeTimer();
        initializeActionPanels();
        addComponents();
        setButtons();
        prepareActions();
    }
    /**
     * Carga los recursos gráficos necesarios
     */
    private void loadResources() {
        try {
            background = new ImageIcon(getClass().getResource("/resources/escenas/batalla.png"));
        } catch (NullPointerException e) {
            System.err.println("Error: No se pudieron cargar las imágenes. Verifica las rutas.");
        }
    }
    /**
     * Inicializa los componentes del panel
     */
    private void initializeComponents() {
        // Inicialización de botones
        attackButton = createOptionButton("ATACAR");
        bagButton = createOptionButton("MOCHILA");
        pokemonButton = createOptionButton("POKÉMON");
        runButton = createOptionButton("HUIR");
        pauseButton = createOptionButton("PAUSA");
        // Crear etiqueta de turno con estilo destacado
        turnLabel = new JLabel("Turno: " + player1Name);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setBackground(new Color(0, 0, 0, 150));
        turnLabel.setOpaque(true);
        turnLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Etiqueta para mensajes de batalla
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBackground(new Color(0, 0, 0, 150));
        messageLabel.setOpaque(true);
        messageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        buttons.add(attackButton);
        buttons.add(bagButton);
        buttons.add(pokemonButton);
        buttons.add(runButton);
        buttons.add(pauseButton);
    }
    /**
     * Inicializa los paneles de acción con CardLayout
     */
    private void initializeActionPanels() {
        // Panel principal que contendrá todos los paneles de acciones
        actionPanel = new JPanel();
        actionCardLayout = new CardLayout();
        actionPanel.setLayout(actionCardLayout);
        actionPanel.setOpaque(false);
        // Panel principal de opciones (Atacar, Mochila, Pokémon, Huir)
        mainOptionsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        mainOptionsPanel.setOpaque(false);
        mainOptionsPanel.add(attackButton);
        mainOptionsPanel.add(bagButton);
        mainOptionsPanel.add(pokemonButton);
        mainOptionsPanel.add(runButton);
        movementPanel = createMovementPanel();
        itemsPanel = createItemsPanel();
        pokemonSelectionPanel = createPokemonSelectionPanel();
        actionPanel.add(mainOptionsPanel, "MAIN_OPTIONS");
        actionPanel.add(movementPanel, "MOVEMENTS");
        actionPanel.add(itemsPanel, "ITEMS");
        actionPanel.add(pokemonSelectionPanel, "POKEMON");
        actionCardLayout.show(actionPanel, "MAIN_OPTIONS");
    }
    /**
     * Crea el panel de movimientos
     */
    private JPanel createMovementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonsPanel.setOpaque(false);
        JButton backButton = createOptionButton("Volver");
        backButton.addActionListener(e -> actionCardLayout.show(actionPanel, "MAIN_OPTIONS"));
        JLabel titleLabel = new JLabel("Movimientos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(buttonsPanel, BorderLayout.CENTER);
        return panel;
    }
    /**
     * Crea el panel de items
     */
    private JPanel createItemsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JPanel buttonsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        buttonsPanel.setOpaque(false);
        JButton backButton = createOptionButton("Volver");
        backButton.addActionListener(e -> actionCardLayout.show(actionPanel, "MAIN_OPTIONS"));
        JLabel titleLabel = new JLabel("Mochila", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.EAST);
        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    /**
     * Crea el panel de selección de Pokémon
     */
    private JPanel createPokemonSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JPanel pokemonsPanel = new JPanel();
        pokemonsPanel.setLayout(new BoxLayout(pokemonsPanel, BoxLayout.Y_AXIS));
        pokemonsPanel.setOpaque(false);
        JButton backButton = createOptionButton("Volver");
        backButton.addActionListener(e -> actionCardLayout.show(actionPanel, "MAIN_OPTIONS"));
        JLabel titleLabel = new JLabel("Equipo Pokémon", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.EAST);
        JScrollPane scrollPane = new JScrollPane(pokemonsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    /**
     * Inicializa el temporizador de turno
     */
    private void initializeTimer() {
        timerLabel = new JLabel("20");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBackground(new Color(0, 0, 0, 150));
        timerLabel.setOpaque(true);
        timerLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnTimer = new Timer(1000, e -> {
            turnTimeRemaining--;
            timerLabel.setText(String.valueOf(turnTimeRemaining));
            if (turnTimeRemaining <= 5) {
                timerLabel.setForeground(Color.RED);
            }
            if (turnTimeRemaining <= 0) {
                stopTimer();
                try {
                    mainGUI.pooBkemon.afterAction();
                    updateTurnInfo();
                } catch (POOBkemonException ex) {
                    showMessage("Error: " + ex.getMessage());
                }
            }
        });
    }

    /**
     * Añade componentes al panel
     */
    private void addComponents() {
        add(turnLabel);
        add(messageLabel);
        add(timerLabel);
        add(pauseButton);
        add(actionPanel);
    }

    /**
     * Prepara las acciones de los botones
     */
    private void prepareActions() {
        attackButton.addActionListener(e -> {
            showMovesPanel();
            startTimer();
        });
        bagButton.addActionListener(e -> {
            showItemsPanel();
            startTimer();
        });
        pokemonButton.addActionListener(e -> {
            showPokemonPanel();
            startTimer();
        });
        runButton.addActionListener(e -> confirmExit());
        pauseButton.addActionListener(e -> pauseBattle());
    }
    //==========================================================================
    // 3. MÉTODOS DE INTERFAZ GRÁFICA
    //==========================================================================
    /**
     * Crea un botón de opción con un texto específico.
     *
     * @param text El texto que se mostrará en el botón.
     * @return El botón creado.
     */
    protected JButton createOptionButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setRolloverEnabled(true);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setBackground(new Color(70, 70, 70));
        return button;
    }
    /**
     * Configura el estilo de los botones.
     */
    private void setButtons() {
        for (JButton button : buttons) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
    /**
     * Dibuja una barra de salud con información adicional.
     *
     * @param g Objeto Graphics para dibujar
     * @param x Posición X
     * @param y Posición Y
     * @param width Ancho de la barra
     * @param health Porcentaje de salud
     * @param maxHealth Salud máxima
     * @param name Nombre a mostrar
     * @param level Nivel del Pokémon
     * @param color Color de la barra
     */
    private void drawHealthBarWithInfo(Graphics g, int x, int y, int width, int health, int maxHealth,
                                     String name, int level, Color color) {
        int barWidth = (int) (width * 0.8);
        int barHeight = 10;
        int filledWidth = (int) (barWidth * Math.max(0, Math.min(1, health / (double)maxHealth)));
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(name + " Lv" + level, x, y - 10);
        g.setColor(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);
        if (health <= maxHealth * 0.2) {
            color = Color.RED;
        } else if (health <= maxHealth * 0.5) {
            color = Color.YELLOW;
        }
        g.setColor(color);
        g.fillRect(x, y, filledWidth, barHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
        g.drawString(health + "/" + maxHealth, x + barWidth + 5, y + barHeight);
    }
    /**
     * Reposiciona los botones de opciones en la interfaz.
     */
    protected void repositionButtons() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int rawTextBoxHeight = (int) (panelHeight * TEXT_BOX_HEIGHT_RATIO);
        int textBoxHeight = Math.max(rawTextBoxHeight, 60); // altura mínima
        int optionsBoxWidth = (int) (panelWidth * OPTIONS_BOX_WIDTH_RATIO);
        int optionsBoxHeight = textBoxHeight;
        int optionsBoxX = panelWidth - optionsBoxWidth;
        int optionsBoxY = panelHeight - textBoxHeight;
        actionPanel.setBounds(optionsBoxX + 5, optionsBoxY + 5, optionsBoxWidth - 10, optionsBoxHeight - 10);
        turnLabel.setBounds(panelWidth/2 - 100, 10, 200, 40);
        messageLabel.setBounds(10, optionsBoxY + 15, optionsBoxX - 20, textBoxHeight - 30);
        timerLabel.setBounds(panelWidth/2 - 25, 60, 50, 40);
        int pauseButtonWidth = 100;
        int pauseButtonHeight = 40;
        int pauseButtonX = 10;
        int pauseButtonY = 10;
        pauseButton.setBounds(pauseButtonX, pauseButtonY, pauseButtonWidth, pauseButtonHeight);
    }
    /**
     * Dibuja todos los elementos del panel.
     *
     * @param g El objeto Graphics para dibujar
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        if (background != null) {
            g.drawImage(background.getImage(), 0, 0, panelWidth, panelHeight, this);
        }
        calculatePokemonPositions();
        int playerPokemonSize = (int) (panelWidth * PLAYER_SIZE_RATIO);
        int playerX = (int) (panelWidth * PLAYER_X_RATIO);
        int playerY = (int) (panelHeight * PLAYER_Y_RATIO);
        int opponentPokemonSize = (int) (panelWidth * OPPONENT_SIZE_RATIO);
        int opponentX = (int) (panelWidth * OPPONENT_X_RATIO);
        int opponentY = (int) (panelHeight * OPPONENT_Y_RATIO);
        if (playerPokemon != null) {
            g.drawImage(playerPokemon.getImage(), xFirst, yFirst,
                       playerPokemonSize, playerPokemonSize, this);
        }
        if (opponentPokemon != null) {
            g.drawImage(opponentPokemon.getImage(), xSecond, ySecond,
                       opponentPokemonSize, opponentPokemonSize, this);
        }
        if (inBattle && mainGUI != null && mainGUI.pooBkemon != null) {
            try {
                Trainer trainer1 = mainGUI.pooBkemon.getCurrentTrainer();
                Trainer trainer2 = mainGUI.pooBkemon.getOpponentTrainer();
                if (trainer1 != null && trainer2 != null) {
                    Pokemon pokemon1 = trainer1.getActivePokemon();
                    Pokemon pokemon2 = trainer2.getActivePokemon();
                    if (pokemon1 != null && pokemon2 != null) {
                        drawHealthBarWithInfo(g, playerX, playerY - 20, playerPokemonSize,
                                pokemon1.getCurrentPs(), pokemon1.getMaxPs(),
                                pokemon1.getName() + " (" + trainer1.getNombre() + ")",
                                pokemon1.getLevel(), Color.GREEN);
                        drawHealthBarWithInfo(g, opponentX, opponentY - 20, opponentPokemonSize,
                                pokemon2.getCurrentPs(), pokemon2.getMaxPs(),
                                pokemon2.getName() + " (" + trainer2.getNombre() + ")",
                                pokemon2.getLevel(), Color.green);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error obteniendo datos de Pokémon: " + e.getMessage());
            }
        } else {
            drawHealthBarWithInfo(g, playerX, playerY - 20, playerPokemonSize, mainGUI.pooBkemon.getCurrentTrainer().getActivePokemon().getCurrentPs(), mainGUI.pooBkemon.getCurrentTrainer().getActivePokemon().getMaxPs(),
                    playerName + " (" + player1Name + ")", playerLevel, Color.GREEN);
            drawHealthBarWithInfo(g, opponentX, opponentY - 20, opponentPokemonSize, mainGUI.pooBkemon.getOpponentTrainer().getActivePokemon().getCurrentPs(), mainGUI.pooBkemon.getOpponentTrainer().getActivePokemon().getMaxPs(),
                    opponentName + " (" + player2Name + ")", opponentLevel, Color.GREEN);
        }
        int rawTextBoxHeight = (int) (panelHeight * TEXT_BOX_HEIGHT_RATIO);
        int textBoxHeight = Math.max(rawTextBoxHeight, 60);
        int textBoxY = panelHeight - textBoxHeight;
        g.setColor(new Color(0, 128, 128));
        g.fillRoundRect(0, textBoxY, panelWidth, textBoxHeight, 15, 15);
        int optionsBoxWidth = (int) (panelWidth * OPTIONS_BOX_WIDTH_RATIO);
        int optionsBoxHeight = textBoxHeight;
        int optionsBoxX = panelWidth - optionsBoxWidth;
        int optionsBoxY = textBoxY;
        g.setColor(new Color(50, 50, 50));
        g.fillRoundRect(optionsBoxX, optionsBoxY, optionsBoxWidth, optionsBoxHeight, 15, 15);
        repositionButtons();
    }
    /**
     * Calcula las posiciones de los Pokémon en la pantalla
     */
    private void calculatePokemonPositions() {
        int scaledWidth = getWidth() / 3;
        int scaledHeight = getHeight() / 3;
        xFirst = (getWidth() - scaledWidth) / 2 - scaledWidth + scaledWidth / 4;
        yFirst = (getHeight() - scaledHeight) / 2 + scaledHeight / 2 + scaledHeight / 3;
        xSecond = (getWidth() - scaledWidth) / 2 + scaledWidth / 2 + scaledWidth / 3;
        ySecond = (getHeight() - scaledHeight) / 2 - scaledHeight / 3;
    }
    //==========================================================================
    // 4. MÉTODOS DE CARGA DE IMÁGENES
    //==========================================================================
    /**
     * Intenta cargar la imagen del Pokémon del jugador desde la ruta especificada
     *
     * @param pokemonName Nombre del Pokémon
     */
    private void loadPlayerPokemonImage(String pokemonName) {
        try {
            String[] possiblePaths = {
                "src/resources/pokedex/" + pokemonName + "Back.png",
                "src/resources/battle/back/" + pokemonName + ".PNG",
                "src/resources/pokemon/" + pokemonName + "Back.png"
            };
            boolean loaded = false;
            for (String path : possiblePaths) {
                File file = new File(path);
                if (file.exists()) {
                    playerPokemon = new ImageIcon(file.getAbsolutePath());
                    //System.out.println("Imagen del jugador cargada desde: " + file.getAbsolutePath());
                    loaded = true;
                    break;
                }
            }
            if (!loaded) {
                System.out.println("No se encontró la imagen del jugador para: " + pokemonName);
            }
        } catch (Exception e) {
            System.err.println("Error intentando cargar imagen del jugador: " + e.getMessage());
        }
        repaint();
    }
    /**
     * Intenta cargar la imagen del Pokémon del oponente desde la ruta especificada
     *
     * @param pokemonName Nombre del Pokémon
     */
    private void loadOpponentPokemonImage(String pokemonName) {
        try {
            String[] possiblePaths = {
                "src/resources/pokedex/" + pokemonName + "Front.png",
                "src/resources/battle/frente/" + pokemonName + ".PNG",
                "src/resources/pokemon/" + pokemonName + "Front.png"
            };
            boolean loaded = false;
            for (String path : possiblePaths) {
                File file = new File(path);
                if (file.exists()) {
                    opponentPokemon = new ImageIcon(file.getAbsolutePath());
                    //System.out.println("Imagen del oponente cargada desde: " + file.getAbsolutePath());
                    loaded = true;
                    break;
                }
            }
            if (!loaded) {
                System.out.println("No se encontró la imagen del oponente para: " + pokemonName);
            }
        } catch (Exception e) {
            System.err.println("Error intentando cargar imagen del oponente: " + e.getMessage());
        }
        repaint();
    }
    //==========================================================================
    // 5. GESTIÓN DE BATALLA Y TURNOS
    //==========================================================================
    /**
     * Inicia una batalla entre dos jugadores
     *
     * @param player1Name Nombre del jugador 1
     * @param player2Name Nombre del jugador 2
     */
    public void startBattle(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.isPlayer1Turn = true; // Comienza el jugador 1
        this.inBattle = true;
        mainGUI.pooBkemon.initializateBattle(player1Name, player2Name);
        updateBattlePokemonInfo();
        showMessage("¡Comienza la batalla Pokémon!");
        turnLabel.setText("Turno: " + player1Name);
        actionCardLayout.show(actionPanel, "MAIN_OPTIONS");
        repaint();
    }
    /**
     * Actualiza la información de los Pokémon en la batalla basándose en el estado del dominio
     */
    protected void updateBattlePokemonInfo() {
        if (mainGUI == null || mainGUI.pooBkemon == null || !inBattle) return;

        try {
            Trainer currentTrainer = mainGUI.pooBkemon.getCurrentTrainer();
            Trainer opponentTrainer = mainGUI.pooBkemon.getOpponentTrainer();
            if (currentTrainer == null || opponentTrainer == null) return;
            Pokemon currentPokemon = currentTrainer.getActivePokemon();
            Pokemon opponentPokemon = opponentTrainer.getActivePokemon();
            if (currentPokemon == null || opponentPokemon == null) return;
            this.playerName = currentPokemon.getName();
            this.playerLevel = currentPokemon.getLevel();
            this.playerHealth = currentPokemon.getCurrentPs();
            this.opponentName = opponentPokemon.getName();
            this.opponentLevel = opponentPokemon.getLevel();
            this.opponentHealth = opponentPokemon.getCurrentPs();
            loadPlayerPokemonImage(playerName);
            loadOpponentPokemonImage(opponentName);
            updateMovesPanel(currentPokemon);
            updateItemsPanel(currentTrainer);
            updatePokemonPanel(currentTrainer);
            repaint();
            checkGameOver();
        } catch (Exception e) {
            System.err.println("Error al actualizar información de batalla: " + e.getMessage());
        }
        repaint();
    }
    /**
     * Actualiza el panel de movimientos con los disponibles para el Pokémon actual
     */
    private void updateMovesPanel(Pokemon pokemon) {
        if (pokemon == null) return;
        JPanel movesButtonsPanel = null;
        for (Component comp : movementPanel.getComponents()) {
            if (comp instanceof JPanel && ((JPanel)comp).getLayout() instanceof GridLayout) {
                movesButtonsPanel = (JPanel)comp;
                break;
            }
        }
        if (movesButtonsPanel == null) return;
        movesButtonsPanel.removeAll();
        Movement[] movements = pokemon.getMovementsinArray();
        for (int i = 0; i < 4; i++) {
            if (i < movements.length && movements[i] != null) {
                Movement move = movements[i];
                JButton moveButton = createOptionButton(move.getName());
                moveButton.setToolTipText(move.getDescription() + " | Potencia: " + move.getPower() +
                                          " | PP: " + move.getPP() + "/" + move.getPP() +
                                          " | Tipo: " + move.getType());
                moveButton.addActionListener(e -> executeMovement(move.getName()));
                movesButtonsPanel.add(moveButton);
            } else {
                JButton emptyButton = createOptionButton("-");
                emptyButton.setEnabled(false);
                movesButtonsPanel.add(emptyButton);
            }
        }
        movesButtonsPanel.revalidate();
        movesButtonsPanel.repaint();
    }

    /**
     * Actualiza el panel de items con los disponibles para el entrenador actual
     */
    private void updateItemsPanel(Trainer trainer) {
        if (trainer == null) return;
        // Encontrar el panel de botones dentro del scrollPane del panel de items
        JPanel itemsButtonsPanel = null;
        for (Component comp : itemsPanel.getComponents()) {
            if (comp instanceof JScrollPane) {
                JScrollPane scroll = (JScrollPane)comp;
                if (scroll.getViewport().getView() instanceof JPanel) {
                    itemsButtonsPanel = (JPanel)scroll.getViewport().getView();
                    break;
                }
            }
        }
        if (itemsButtonsPanel == null) return;
        itemsButtonsPanel.removeAll();
        ArrayList<String> items = trainer.getInventory().getItemsArray();
        if (items.isEmpty()) {
            JLabel emptyLabel = new JLabel("No tienes ningún objeto en la mochila", SwingConstants.CENTER);
            emptyLabel.setForeground(Color.WHITE);
            itemsButtonsPanel.add(emptyLabel);
        } else {
            for (String itemName : items) {
                JButton itemButton = createOptionButton(itemName);
                itemButton.addActionListener(e -> useItem(itemName));
                itemsButtonsPanel.add(itemButton);
            }
        }
        itemsButtonsPanel.revalidate();
        itemsButtonsPanel.repaint();
    }

    /**
     * Actualiza el panel de selección de Pokémon
     */
    private void updatePokemonPanel(Trainer trainer) {
        if (trainer == null) return;
        JPanel pokemonListPanel = null;
        for (Component comp : pokemonSelectionPanel.getComponents()) {
            if (comp instanceof JScrollPane) {
                JScrollPane scroll = (JScrollPane)comp;
                if (scroll.getViewport().getView() instanceof JPanel) {
                    pokemonListPanel = (JPanel)scroll.getViewport().getView();
                    break;
                }
            }
        }
        if (pokemonListPanel == null) return;
        pokemonListPanel.removeAll();
        TreeMap<String,Pokemon> pokemons = trainer.getInventory().getPokemons();
        Pokemon activePokemon = trainer.getActivePokemon();
        if (pokemons.isEmpty()) {
            JLabel emptyLabel = new JLabel("No tienes ningún Pokémon en tu equipo", SwingConstants.CENTER);
            emptyLabel.setForeground(Color.WHITE);
            pokemonListPanel.add(emptyLabel);
        } else {
            for (Pokemon pokemon : pokemons.values()) {
                JPanel entryPanel = new JPanel(new BorderLayout());
                entryPanel.setOpaque(false);
                entryPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
                String statusText = pokemon.getCurrentPs() <= 0 ? " [DEBILITADO]" : "";
                JLabel pokemonLabel = new JLabel(pokemon.getName() + " - Nivel " + pokemon.getLevel() +
                                      " - PS: " + pokemon.getCurrentPs() + "/" + pokemon.getMaxPs() + statusText);
                pokemonLabel.setForeground(Color.WHITE);
                JButton selectButton = createOptionButton("Elegir");
                selectButton.setPreferredSize(new Dimension(100, 30));
                // Deshabilitar si es el Pokémon activo o está debilitado
                boolean isActive = activePokemon != null && activePokemon.getName().equals(pokemon.getName()) && pokemon.getCurrentPs() > 0;
                boolean isFainted = pokemon.getCurrentPs() <= 0;
                selectButton.setEnabled(!isActive && !isFainted);
                if (isFainted) {
                    pokemonLabel.setForeground(Color.RED);
                } else if (isActive) {
                    pokemonLabel.setForeground(Color.YELLOW);
                    pokemonLabel.setText(pokemonLabel.getText() + " [ACTIVO]");
                }
                selectButton.addActionListener(e -> changePokemon(pokemon.getName()));
                entryPanel.add(pokemonLabel, BorderLayout.CENTER);
                entryPanel.add(selectButton, BorderLayout.EAST);
                pokemonListPanel.add(entryPanel);
            }
        }
        pokemonListPanel.revalidate();
        pokemonListPanel.repaint();
    }

    /**
     * Ejecuta un movimiento seleccionado
     */
    private void executeMovement(String movementName) {
        stopTimer();
        showMessage(playerName + " usa " + movementName);

        // Debug: Registrar PS antes del ataque
        Pokemon currentPokemon = mainGUI.pooBkemon.getCurrentTrainer().getActivePokemon();
        Pokemon opponentPokemon = mainGUI.pooBkemon.getOpponentTrainer().getActivePokemon();
        System.out.println("ANTES DEL ATAQUE - Actual: " + currentPokemon.getName() +
                " PS: " + currentPokemon.getCurrentPs() +
                " | Oponente: " + opponentPokemon.getName() +
                " PS: " + opponentPokemon.getCurrentPs());
        try {
            // Ejecutar el movimiento en el dominio
            mainGUI.pooBkemon.useMovement(movementName);
            // Actualizar información de la batalla
            updateBattlePokemonInfo();
            // Cambiar turno
            try {
                mainGUI.pooBkemon.afterAction();
                updateTurnInfo();
                checkGameOver();
            } catch (POOBkemonException e) {
                showMessage("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            showMessage("Error al usar movimiento: " + e.getMessage());
        }
        actionCardLayout.show(actionPanel, "MAIN_OPTIONS");
    }
    /**
     * Cambia el Pokémon activo
     */
    private void changePokemon(String pokemonName) {
        stopTimer();
        showMessage("Cambias a " + pokemonName);
        try {
            // Cambiar Pokémon en el dominio
            mainGUI.pooBkemon.changePokemon(pokemonName);
            // Actualizar información de la batalla
            updateBattlePokemonInfo();
            // Cambiar turno
            try {
                mainGUI.pooBkemon.afterAction();
                updateTurnInfo();
                checkGameOver();
                startTimer();
            } catch (POOBkemonException e) {
                showMessage("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            showMessage("Error al cambiar Pokémon: " + e.getMessage());
        }
        actionCardLayout.show(actionPanel, "MAIN_OPTIONS");
    }
    /**
     * Usa un item seleccionado
     */
    private void useItem(String itemName) {
        stopTimer();
        showMessage("Usas " + itemName);
        try {
            mainGUI.pooBkemon.useItem(itemName);
            updateBattlePokemonInfo();
            try {
                mainGUI.pooBkemon.afterAction();
                updateTurnInfo();
                checkGameOver();
                startTimer();
            } catch (POOBkemonException e) {
                showMessage("Error: " + e.getMessage());
            }
        } catch (POOBkemonException e) {
            showMessage("Error al usar item: " + e.getMessage());
        }
        actionCardLayout.show(actionPanel, "MAIN_OPTIONS");
    }
    /**
     * Actualiza la información del turno actual
     */
    protected void updateTurnInfo() {
        if (mainGUI == null || mainGUI.pooBkemon == null || !inBattle) return;
        try {
            Trainer currentTrainer = mainGUI.pooBkemon.getCurrentTrainer();
            isPlayer1Turn = currentTrainer.getNombre().equals(player1Name);
            turnLabel.setText("Turno: " + currentTrainer.getNombre());
            updateBattlePokemonInfo();
            checkGameOver();
            startTimer();
        } catch (Exception e) {
            System.err.println("Error al actualizar información de turno: " + e.getMessage());
        }
    }
    /**
     * Verifica si la batalla ha terminado
     */
    protected void checkGameOver() {
        if (mainGUI == null || mainGUI.pooBkemon == null || !inBattle) return;
        if (mainGUI.pooBkemon.isGameOver()) {
            String winner = mainGUI.pooBkemon.getWinner();
            if (winner != null) {
                JOptionPane.showMessageDialog(this, "¡La batalla ha terminado!\nGanador: " + winner,
                                            "Fin de la batalla", JOptionPane.INFORMATION_MESSAGE);
                endBattle();
                mainGUI.cardLayout.show(mainGUI.contentPanel, "INICIO");
            }
        }
    }
    /**
     * Finaliza la batalla actual
     */
    protected void endBattle() {
        inBattle = false;
        stopTimer();
        if (mainGUI != null && mainGUI.pooBkemon != null) {
            mainGUI.pooBkemon.leaveGame();
        }
        try {
            mainGUI.pooBkemon = new POOBkemon();
            mainGUI.pooBkemon.deserializateGame();
            mainGUI.player1 = null;
            mainGUI.player2 = null;
        } catch (Exception e) {
            System.err.println("Error al resetear el juego: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Pausa la batalla
     */
    private void pauseBattle() {
        if (!inBattle) return;
        stopTimer();
        String[] options = {"Continuar"};
        int option = JOptionPane.showOptionDialog(this,
                "Batalla en pausa",
                "Pausa",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
    }

    /**
     * Confirma salir de la batalla
     */
    protected void confirmExit() {
        int option = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres abandonar la batalla?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            endBattle();
            mainGUI.cardLayout.show(mainGUI.contentPanel, "INICIO");
        }
    }
    /**
     * Muestra un panel de acciones específico
     */
    private void showMovesPanel() {
        actionCardLayout.show(actionPanel, "MOVEMENTS");
        startTimer();
    }
    private void showItemsPanel() {
        actionCardLayout.show(actionPanel, "ITEMS");
        startTimer();
    }
    private void showPokemonPanel() {
        actionCardLayout.show(actionPanel, "POKEMON");
        startTimer();
    }
    /**
     * Inicia el temporizador de turno
     */
    private void startTimer() {
        if (!timerRunning) {
            turnTimeRemaining = 20;
            timerLabel.setText(String.valueOf(turnTimeRemaining));
            timerLabel.setForeground(Color.WHITE);
            turnTimer.restart();
            timerRunning = true;
        }
    }
    /**
     * Detiene el temporizador de turno
     */
    private void stopTimer() {
        if (timerRunning) {
            turnTimer.stop();
            timerRunning = false;
        }
    }
    /**
     * Muestra un mensaje temporal en la etiqueta de mensajes
     * 
     * @param message El mensaje a mostrar
     */
    protected void showMessage(String message) {
        messageLabel.setText(message);
        Timer messageTimer = new Timer(3000, e -> {
            messageLabel.setText("");
        });
        messageTimer.setRepeats(false);
        messageTimer.start();
    }


    /**
     * Carga un juego guardado y muestra el panel de batalla con la información guardada
     */
    public void loadGame() {
        if (mainGUI != null && mainGUI.pooBkemon != null) {
            try {
                mainGUI.optionOpen();
                Trainer currentTrainer = mainGUI.pooBkemon.getCurrentTrainer();
                Trainer opponentTrainer = mainGUI.pooBkemon.getOpponentTrainer();
                if (currentTrainer == null || opponentTrainer == null) {
                    System.err.println("Error: No se pudieron recuperar los entrenadores");
                    return;
                }
                player1Name = currentTrainer.getNombre();
                player2Name = opponentTrainer.getNombre();
                inBattle = true;
                updateBattlePokemonInfo();
                turnLabel.setText("Turno: " + currentTrainer.getNombre());
                isPlayer1Turn = currentTrainer.getNombre().equals(player1Name);
                actionCardLayout.show(actionPanel, "MAIN_OPTIONS");
                showMessage("¡Batalla cargada correctamente!");
                repaint();
                checkGameOver();
            } catch (Exception e) {
                System.err.println("Error al cargar juego guardado: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Error: No hay un juego para cargar");
        }
    }
}
