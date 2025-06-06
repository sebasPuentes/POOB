package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Panel para el modo Supervivencia que genera equipos Pokémon aleatorios para dos jugadores.
 */
public class SurvivalPanel extends JPanel {
    private POOBkemonGUI mainGUI;
    private JLabel titleLabel;
    private JTextField player1NameField;
    private JTextField player2NameField;
    private JButton startButton;
    private JButton backButton;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private static final int NUM_POKEMON = 6;
    private static final int NUM_MOVES = 4;
    /**
     * Constructor para el panel de supervivencia
     */
    public SurvivalPanel(POOBkemonGUI gui) {
        this.mainGUI = gui;
        setLayout(new BorderLayout());
        prepareElements();
        prepareActions();
    }

    /**
     * Crea un botón estilizado con colores personalizados
     * @param text Texto del botón
     * @param baseColor Color base del botón
     * @return Botón configurado
     */
    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(baseColor.brighter());
                    button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });
        return button;
    }

    /**
     * Inicializa los elementos de UI
     */
    private void prepareElements() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setOpaque(false);
        
        titleLabel = new JLabel("MODO SUPERVIVENCIA - JUGADOR VS JUGADOR");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(new Color(0, 0, 0, 180));
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JTextArea descriptionArea = new JTextArea(
                "En el modo supervivencia, se seleccionarán automáticamente 6 Pokémon " +
                        "aleatorios para cada jugador, cada uno con 4 movimientos aleatorios. " +
                        "\n\n¡Ingresa los nombres de los jugadores y prepárate para la batalla!"
        );
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(0, 0, 0, 180));
        descriptionArea.setOpaque(true);
        descriptionArea.setForeground(Color.WHITE);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel namesPanel = new JPanel();
        namesPanel.setLayout(new GridLayout(2, 2, 10, 10));
        namesPanel.setBackground(new Color(0, 0, 0, 180));
        namesPanel.setOpaque(true);
        namesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel player1Label = new JLabel("Jugador 1: ", JLabel.RIGHT);
        player1Label.setFont(new Font("Arial", Font.BOLD, 14));
        player1Label.setForeground(Color.WHITE);
        player1NameField = new JTextField(15);
        player1NameField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel player2Label = new JLabel("Jugador 2: ", JLabel.RIGHT);
        player2Label.setFont(new Font("Arial", Font.BOLD, 14));
        player2Label.setForeground(Color.WHITE);
        player2NameField = new JTextField(15);
        player2NameField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        namesPanel.add(player1Label);
        namesPanel.add(player1NameField);
        namesPanel.add(player2Label);
        namesPanel.add(player2NameField);
        namesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namesPanel.setMaximumSize(new Dimension(400, 60));
        
        statusLabel = new JLabel("Listo para comenzar");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBackground(new Color(0, 0, 0, 180));
        statusLabel.setOpaque(true);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setVisible(false);
        progressBar.setForeground(new Color(34, 139, 34));
        progressBar.setBackground(new Color(50, 50, 50));
        progressBar.setOpaque(true);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        
        startButton = createStyledButton("¡COMENZAR BATALLA!", new Color(34, 139, 34));
        startButton.setPreferredSize(new Dimension(250, 50));
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        backButton = createStyledButton("VOLVER", new Color(178, 34, 34));
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        buttonPanel.add(startButton);
        buttonPanel.add(backButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(descriptionArea);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(namesPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(progressBar);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Configura las acciones de los componentes
     */
    private void prepareActions() {
        startButton.addActionListener(e -> startSurvivalMode());
        backButton.addActionListener(e -> mainGUI.cardLayout.show(mainGUI.contentPanel, "JUGAR"));
    }

    /**
     * Inicia el modo supervivencia con la configuración aleatoria
     */
    private void startSurvivalMode() {
        String player1Name = player1NameField.getText().trim();
        String player2Name = player2NameField.getText().trim();
        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingresa los nombres de ambos jugadores para comenzar.",
                    "Nombres requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (player1Name.equals(player2Name)) {
            JOptionPane.showMessageDialog(this,
                    "Los nombres de los jugadores deben ser diferentes.",
                    "Nombres idénticos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        setControlsEnabled(false);
        progressBar.setVisible(true);
        progressBar.setValue(0);
        statusLabel.setText("Configurando entrenadores...");

        try {
            try {
                mainGUI.pooBkemon.leaveGame();
                mainGUI.pooBkemon.defaultGame();
            } catch (Exception ex) {
                System.out.println("Aviso al limpiar estado: " + ex.getMessage());
            }

            // 1. Obtener Pokémon y movimientos de la Pokédex existente
            statusLabel.setText("Obteniendo Pokémon disponibles...");
            progressBar.setValue(10);
            TreeMap<String, Pokemon> pokedex = mainGUI.pooBkemon.getPokedex();
            TreeMap<String, Movement> movements = mainGUI.pooBkemon.getDefaultMovementsMap();
            if (pokedex.isEmpty()) {
                throw new POOBkemonException("No hay Pokémon en la Pokédex");
            }
            if (movements.isEmpty()) {
                throw new POOBkemonException("No hay movimientos disponibles");
            }
            statusLabel.setText("Creando entrenadores...");
            progressBar.setValue(20);
            mainGUI.pooBkemon.addNewTrainer(player1Name, Color.RED);
            mainGUI.pooBkemon.addNewTrainer(player2Name, Color.BLUE);
            statusLabel.setText("Seleccionando equipos...");
            progressBar.setValue(30);
            ArrayList<Pokemon> pokemonList = new ArrayList<>(pokedex.values());
            ArrayList<Movement> movementList = new ArrayList<>(movements.values());
            Collections.shuffle(pokemonList);
            statusLabel.setText("Creando equipo para " + player1Name);
            progressBar.setValue(40);
            selectPokemonForTrainer(player1Name, pokemonList, movementList);
            statusLabel.setText("Creando equipo para " + player2Name);
            progressBar.setValue(70);
            selectPokemonForTrainer(player2Name, pokemonList, movementList);
            statusLabel.setText("Configurando Pokémon activos");
            progressBar.setValue(90);
            TreeMap<String, Pokemon> pokemons1 = mainGUI.pooBkemon.getTrainer(player1Name).getInventory().getPokemons();
            if (!pokemons1.isEmpty()) {
                String firstPokemon = pokemons1.firstKey();
                mainGUI.pooBkemon.getTrainer(player1Name).setPokemonInUse(firstPokemon);
            } else {
                throw new Exception("No se pudo añadir Pokémon al Jugador 1");
            }
            TreeMap<String, Pokemon> pokemons2 = mainGUI.pooBkemon.getTrainer(player2Name).getInventory().getPokemons();
            if (!pokemons2.isEmpty()) {
                String firstPokemon = pokemons2.firstKey();
                mainGUI.pooBkemon.getTrainer(player2Name).setPokemonInUse(firstPokemon);
            } else {
                throw new Exception("No se pudo añadir Pokémon al Jugador 2");
            }
            statusLabel.setText("¡Todo listo para la batalla!");
            progressBar.setValue(100);
            mainGUI.player1 = player1Name;
            mainGUI.player2 = player2Name;
            mainGUI.isPvsMMode = false;
            javax.swing.Timer timer = new javax.swing.Timer(1000, evt -> {
                try {
                    mainGUI.cardLayout.show(mainGUI.contentPanel, "BATALLA");
                    mainGUI.fightsPanel.startBattle(player1Name, player2Name);
                    setControlsEnabled(true);
                    progressBar.setVisible(false);
                    statusLabel.setText("Listo para comenzar");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SurvivalPanel.this,
                            "Error al iniciar la batalla: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Error: " + ex.getMessage());
                    ex.printStackTrace();
                    setControlsEnabled(true);
                    progressBar.setVisible(false);
                    statusLabel.setText("Listo para comenzar");
                }
            });
            timer.setRepeats(false);
            timer.start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al configurar la batalla: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            setControlsEnabled(true);
            progressBar.setVisible(false);
            statusLabel.setText("Listo para comenzar");
        }
    }

    /**
     * Selecciona Pokémon aleatorios para un entrenador usando solo métodos del dominio
     * @param trainerName Nombre del entrenador
     * @param pokemonList Lista de Pokémon disponibles
     * @param movementList Lista de movimientos disponibles
     */
    private void selectPokemonForTrainer(String trainerName, ArrayList<Pokemon> pokemonList, ArrayList<Movement> movementList) {
        Random random = new Random();
        int pokemonToSelect = Math.min(NUM_POKEMON, pokemonList.size());
        int startIndex = trainerName.equals(player2NameField.getText().trim()) ? pokemonList.size() / 2 : 0;
        int endIndex = trainerName.equals(player2NameField.getText().trim()) ? pokemonList.size() : pokemonList.size() / 2;
        if (endIndex - startIndex < pokemonToSelect) {
            startIndex = 0;
            endIndex = pokemonList.size();
        }
        int added = 0;
        for (int i = startIndex; i < endIndex && added < pokemonToSelect; i++) {
            // Crear copia del Pokémon para evitar referencias compartidas
            Pokemon pokemon = pokemonList.get(i).copy();
            Movement[] selectedMovements = new Movement[NUM_MOVES];
            Collections.shuffle(movementList);
            for (int j = 0; j < NUM_MOVES && j < movementList.size(); j++) {
                selectedMovements[j] = movementList.get(j);
            }
            pokemon.setMovements(selectedMovements);
            try {
                mainGUI.pooBkemon.addNewPokemon(trainerName, pokemon.getName(),
                        selectedMovements[0], selectedMovements[1], selectedMovements[2], selectedMovements[3]);
            } catch (Exception ex) {
                System.out.println("Error añadiendo Pokémon: " + ex.getMessage());
            }
            System.out.println("Añadiendo " + pokemon.getName() + " a " + trainerName);
            added++;
        }
    }

    /**
     * Habilita o deshabilita los controles de interfaz
     */
    private void setControlsEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
        backButton.setEnabled(enabled);
        player1NameField.setEnabled(enabled);
        player2NameField.setEnabled(enabled);
    }

    /**
     * Reinicia el panel para una nueva sesión
     */
    public void reset() {
        player1NameField.setText("");
        player2NameField.setText("");
        statusLabel.setText("Listo para comenzar");
        progressBar.setValue(0);
        progressBar.setVisible(false);
        setControlsEnabled(true);
    }

    /**
     * Método para pintar el componente.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Pintar fondo
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/escenas/" + "survival1" + ".GIF"));
        if (back != null && back.getIconWidth() > 0) {
            g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
            // Fondo de respaldo
            g.setColor(new Color(30, 30, 50));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
