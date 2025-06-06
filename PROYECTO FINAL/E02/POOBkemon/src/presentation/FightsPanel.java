package src.presentation;

import src.domain.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel para gestionar y mostrar la batalla de Pokémon.
 */
public class FightsPanel extends JPanel {
    private ImageIcon background;
    private ImageIcon playerPokemon;
    private ImageIcon opponentPokemon;
    private JButton fightButton;
    private JButton bagButton;
    private JButton pokemonButton;
    private JButton runButton;
    private JButton pauseButton;
    private ArrayList<JButton> buttons = new ArrayList<>();

    private List<String> playerTeam;
    private List<String> opponentTeam;

    private int currentPlayerPokemonIndex = 0;
    private int currentOpponentPokemonIndex = 0;

    private boolean canChangePlayerPokemon = true;
    private boolean canChangeOpponentPokemon = true;

    private int playerHealth = 100;
    private int opponentHealth = 100;
    private String playerName = "";
    private String opponentName = "";
    private int playerLevel = 100;
    private int opponentLevel = 100;

    private final double PLAYER_X_RATIO = 0.167;
    private final double PLAYER_Y_RATIO = 0.6;
    private final double PLAYER_SIZE_RATIO = 0.2;

    private final double OPPONENT_X_RATIO = 0.65;
    private final double OPPONENT_Y_RATIO = 0.15;
    private final double OPPONENT_SIZE_RATIO = 0.25;

    private final double TEXT_BOX_HEIGHT_RATIO = 0.083;
    private final double OPTIONS_BOX_WIDTH_RATIO = 0.5;

    /**
     * Constructor del panel de batalla.
     * Inicializa los componentes y configura el diseño.
     */
    public FightsPanel() {
        setLayout(null);

        try {
            background = new ImageIcon(getClass().getResource("/resources/escenas/batalla.png"));
        } catch (NullPointerException e) {
            System.err.println("Error: No se pudieron cargar las imágenes. Verifica las rutas.");
        }

        fightButton = createOptionButton("FIGHT");
        bagButton = createOptionButton("BAG");
        pokemonButton = createOptionButton("POKÉMON");
        runButton = createOptionButton("RUN");
        pauseButton = createOptionButton("PAUSE");

        add(fightButton);
        add(bagButton);
        add(pokemonButton);
        add(runButton);
        add(pauseButton);
        buttons.add(fightButton);
        buttons.add(bagButton);
        buttons.add(pokemonButton);
        buttons.add(runButton);
        buttons.add(pauseButton);

        // Configurar el botón de Pokémon para mostrar el menú de selección
        pokemonButton.addActionListener(e -> showPokemonSwitchMenu());

        setButtons();

        // Inicializar los equipos vacíos
        playerTeam = new ArrayList<>();
        opponentTeam = new ArrayList<>();
    }

    /**
     * Configura el color y el cursor de los botones.
     */
    private void setButtons() {
        for (JButton button : buttons) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    /**
     * Muestra un menú para elegir entre cambiar el Pokémon del jugador o del oponente
     */
    private void showPokemonSwitchMenu() {
        // Array con opciones
        String[] options = {"Cambiar Pokémon del Jugador", "Cambiar Pokémon del Oponente", "Cancelar"};

        // Mostrar diálogo
        int selection = JOptionPane.showOptionDialog(
                this,
                "¿Qué Pokémon deseas cambiar?",
                "Cambio de Pokémon",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Procesar selección
        switch (selection) {
            case 0: // Cambiar Pokémon del jugador
                changePlayerPokemon();
                break;
            case 1: // Cambiar Pokémon del oponente
                changeOpponentPokemon();
                break;
            default: // Cancelar o cerrar el diálogo
                break;
        }
    }

    /**
     * Crea un botón de opción con un texto específico.
     *
     * @param text El texto que se mostrará en el botón.
     * @return El botón creado.
     */
    private JButton createOptionButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));

        // Efecto al pasar el cursor
        button.setRolloverEnabled(true);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setBackground(new Color(70, 70, 70));

        return button;
    }

    /**
     * Configura el tamaño y la posición de los botones de opciones.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (background != null) {
            g.drawImage(background.getImage(), 0, 0, panelWidth, panelHeight, this);
        }

        int playerPokemonSize = (int) (panelWidth * PLAYER_SIZE_RATIO);
        int playerX = (int) (panelWidth * PLAYER_X_RATIO);
        int playerY = (int) (panelHeight * PLAYER_Y_RATIO);

        int opponentPokemonSize = (int) (panelWidth * OPPONENT_SIZE_RATIO);
        int opponentX = (int) (panelWidth * OPPONENT_X_RATIO);
        int opponentY = (int) (panelHeight * OPPONENT_Y_RATIO);

        if (playerPokemon != null) {
            g.drawImage(playerPokemon.getImage(), playerX, playerY, playerPokemonSize, playerPokemonSize, this);
        }

        if (opponentPokemon != null) {
            g.drawImage(opponentPokemon.getImage(), opponentX, opponentY, opponentPokemonSize, opponentPokemonSize, this);
        }
        // Dibujar barras de salud y nombres
        drawHealthBarWithInfo(g, playerX, playerY - 20, playerPokemonSize, playerHealth, playerName, playerLevel, Color.GREEN);
        drawHealthBarWithInfo(g, opponentX, opponentY - 20, opponentPokemonSize, opponentHealth, opponentName, opponentLevel, Color.RED);
        int rawTextBoxHeight = (int) (panelHeight * TEXT_BOX_HEIGHT_RATIO);
        int textBoxHeight = Math.max(rawTextBoxHeight, 60);
        int textBoxY = panelHeight - textBoxHeight;
        // Fondo de la barra de texto
        g.setColor(new Color(0, 128, 128));
        g.fillRoundRect(0, textBoxY, panelWidth, textBoxHeight, 15, 15); // Bordes redondeados

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, Math.max(12, panelWidth / 80)));
        g.drawString("What will " + playerName + " do?", 10, textBoxY + (textBoxHeight / 2) + 5);

        int optionsBoxWidth = (int) (panelWidth * OPTIONS_BOX_WIDTH_RATIO);
        int optionsBoxHeight = textBoxHeight;
        int optionsBoxX = panelWidth - optionsBoxWidth;
        int optionsBoxY = textBoxY;

        // Caja de opciones con borde redondeado
        g.setColor(new Color(50, 50, 50));
        g.fillRoundRect(optionsBoxX, optionsBoxY, optionsBoxWidth, optionsBoxHeight, 15, 15);
        repositionButtons();
    }

    /**
     * Dibuja una barra de salud con información adicional.
     *
     * @param g      El objeto Graphics para dibujar.
     * @param x      La posición x de la barra.
     * @param y      La posición y de la barra.
     * @param width  El ancho de la barra.
     * @param health La salud actual del Pokémon.
     * @param name   El nombre del Pokémon.
     * @param level  El nivel del Pokémon.
     * @param color  El color de la barra de salud.
     */
    private void drawHealthBarWithInfo(Graphics g, int x, int y, int width, int health, String name, int level, Color color) {
        int barWidth = (int) (width * 0.8);
        int barHeight = 10;
        int filledWidth = (int) (barWidth * (health / 100.0));

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(name + " Lv" + level, x, y - 10);

        g.setColor(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        g.setColor(color);
        g.fillRect(x, y, filledWidth, barHeight);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
    }

    /**
     * Reposiciona los botones de opciones en la interfaz.
     */
    private void repositionButtons() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int rawTextBoxHeight = (int) (panelHeight * TEXT_BOX_HEIGHT_RATIO);
        int textBoxHeight = Math.max(rawTextBoxHeight, 60); // altura mínima

        int optionsBoxWidth = (int) (panelWidth * OPTIONS_BOX_WIDTH_RATIO);
        int optionsBoxHeight = textBoxHeight;
        int optionsBoxX = panelWidth - optionsBoxWidth;
        int optionsBoxY = panelHeight - textBoxHeight;

        int buttonWidth = optionsBoxWidth / 2 - 10;
        int buttonHeight = optionsBoxHeight / 2 - 5;
        int buttonMarginX = 5;
        int buttonMarginY = 5;

        fightButton.setBounds(optionsBoxX + buttonMarginX, optionsBoxY + buttonMarginY, buttonWidth, buttonHeight);
        bagButton.setBounds(optionsBoxX + buttonWidth + 2 * buttonMarginX, optionsBoxY + buttonMarginY, buttonWidth, buttonHeight);
        pokemonButton.setBounds(optionsBoxX + buttonMarginX, optionsBoxY + buttonHeight + 2 * buttonMarginY, buttonWidth, buttonHeight);
        runButton.setBounds(optionsBoxX + buttonWidth + 2 * buttonMarginX, optionsBoxY + buttonHeight + 2 * buttonMarginY, buttonWidth, buttonHeight);
        int pauseButtonWidth = 100;
        int pauseButtonHeight = 40;
        int pauseButtonX = 10;
        int pauseButtonY = 10;
        pauseButton.setBounds(pauseButtonX, pauseButtonY, pauseButtonWidth, pauseButtonHeight);
    }

    /**
     * Obtiene el botón de "PELEA".
     */
    public JButton getFightButton() {
        return fightButton;
    }

    /**
     * Obtiene el botón de "BOLSA".
     */
    public JButton getBagButton() {
        return bagButton;
    }

    /**
     * Obtiene el botón de "POKÉMON".
     */
    public JButton getPokemonButton() {
        return pokemonButton;
    }

    /**
     * Obtiene el botón de "CORRER".
     */
    public JButton getRunButton() {
        return runButton;
    }

    /**
     * Configura los Pokémon seleccionados del jugador en la batalla.
     * Este método se conecta con el PokemonSelectionPanel.
     */
    public void setPlayerTeam(List<String> playerTeam) {
        this.playerTeam = playerTeam;
        if (playerTeam != null && !playerTeam.isEmpty()) {
            currentPlayerPokemonIndex = 0;
            String firstPokemon = playerTeam.get(0);

            // Intentar cargar imagen desde diferentes rutas
            loadPlayerPokemonImage(firstPokemon);

            // Establecer información
            setPlayerInfo(firstPokemon, 100);
        }
    }

    /**
     * Configura los Pokémon seleccionados del oponente en la batalla.
     * Este método se conecta con el PokemonSelectionPanel.
     */
    public void setOpponentTeam(List<String> opponentTeam) {
        this.opponentTeam = opponentTeam;
        if (opponentTeam != null && !opponentTeam.isEmpty()) {
            currentOpponentPokemonIndex = 0;
            String firstPokemon = opponentTeam.get(0);

            // Intentar cargar imagen desde diferentes rutas
            loadOpponentPokemonImage(firstPokemon);

            // Establecer información
            setOpponentInfo(firstPokemon, 100);
        }
    }

    /**
     * Intenta cargar la imagen del Pokémon del jugador de varias fuentes posibles
     */
    private void loadPlayerPokemonImage(String pokemonName) {
        boolean imageLoaded = false;

        // Lista de posibles rutas para intentar
        String[] resourcePaths = {
                "/resources/" + pokemonName.toLowerCase() + "Back.png",
                "/resources/pokemon/" + pokemonName + "_back.png"
        };

        // Intentar rutas de recursos
        for (String path : resourcePaths) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(path));
                if (icon.getIconWidth() > 0) {
                    playerPokemon = icon;
                    imageLoaded = true;
                    break;
                }
            } catch (Exception e) {
                // Continuar al siguiente intento
            }
        }

        // Si no se encontró en recursos, intentar cargar desde archivos
        if (!imageLoaded) {
            String[] filePaths = {
                    "resources/ataques/" + pokemonName + ".gif",
                    "POOBkemon/resources/ataques/" + pokemonName + ".gif",
                    "resources/pokemon/" + pokemonName + ".gif"
            };

            for (String path : filePaths) {
                try {
                    File file = new File(path);
                    if (file.exists()) {
                        playerPokemon = new ImageIcon(file.getAbsolutePath());
                        imageLoaded = true;
                        break;
                    }
                } catch (Exception e) {
                    // Continuar al siguiente intento
                }
            }
        }

        // Si aún no hay imagen, mostrar un marcador de posición
        if (!imageLoaded) {
            System.out.println("No se encontró imagen para " + pokemonName + ". Usando nombre como texto.");
            createTextBasedPokemonImage(pokemonName, true);
        }

        repaint();
    }

    /**
     * Intenta cargar la imagen del Pokémon del oponente de varias fuentes posibles
     */
    private void loadOpponentPokemonImage(String pokemonName) {
        boolean imageLoaded = false;

        // Lista de posibles rutas para intentar
        String[] resourcePaths = {
                "/resources/" + pokemonName.toLowerCase() + "Front.png",
                "/resources/pokemon/" + pokemonName + "_front.png"
        };

        // Intentar rutas de recursos
        for (String path : resourcePaths) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(path));
                if (icon.getIconWidth() > 0) {
                    opponentPokemon = icon;
                    imageLoaded = true;
                    break;
                }
            } catch (Exception e) {
                // Continuar al siguiente intento
            }
        }

        // Si no se encontró en recursos, intentar cargar desde archivos
        if (!imageLoaded) {
            String[] filePaths = {
                    "resources/ataques/" + pokemonName + ".gif",
                    "POOBkemon/resources/ataques/" + pokemonName + ".gif",
                    "resources/pokemon/" + pokemonName + ".gif"
            };

            for (String path : filePaths) {
                try {
                    File file = new File(path);
                    if (file.exists()) {
                        opponentPokemon = new ImageIcon(file.getAbsolutePath());
                        imageLoaded = true;
                        break;
                    }
                } catch (Exception e) {
                    // Continuar al siguiente intento
                }
            }
        }

        // Si aún no hay imagen, mostrar un marcador de posición
        if (!imageLoaded) {
            System.out.println("No se encontró imagen para " + pokemonName + ". Usando nombre como texto.");
            createTextBasedPokemonImage(pokemonName, false);
        }

        repaint();
    }

    /**
     * Crea una imagen con el nombre del Pokémon cuando no se encuentra un archivo de imagen
     */
    private void createTextBasedPokemonImage(String pokemonName, boolean isPlayer) {
        // Crear un panel con el nombre del Pokémon
        int size = 128;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        // Dibujar un fondo circular con el color adecuado
        Color color = isPlayer ? new Color(0, 100, 200, 180) : new Color(200, 0, 0, 180);
        g2d.setColor(color);
        g2d.fillOval(0, 0, size, size);

        // Borde
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(0, 0, size-1, size-1);

        // Texto con el nombre
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(Color.WHITE);

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(pokemonName);
        int textX = (size - textWidth) / 2;
        int textY = size / 2 + fm.getAscent() / 2;

        g2d.drawString(pokemonName, textX, textY);
        g2d.dispose();

        // Asignar la imagen al Pokémon correspondiente
        if (isPlayer) {
            playerPokemon = new ImageIcon(img);
        } else {
            opponentPokemon = new ImageIcon(img);
        }
    }

    /**
     * Cambia el Pokémon del jugador con un diálogo mejorado.
     */
    public void changePlayerPokemon() {
        if (playerTeam == null || playerTeam.isEmpty() || !canChangePlayerPokemon) {
            JOptionPane.showMessageDialog(this,
                    "No hay Pokémon disponibles para cambiar.",
                    "Equipo vacío",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Si solo hay un Pokémon, no se puede cambiar
        if (playerTeam.size() == 1) {
            JOptionPane.showMessageDialog(this,
                    "Solo tienes un Pokémon en tu equipo.",
                    "No se puede cambiar",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear un panel personalizado para mostrar los Pokémon
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Selecciona un Pokémon:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));

        // Grupo de botones para la selección
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] buttons = new JRadioButton[playerTeam.size()];

        for (int i = 0; i < playerTeam.size(); i++) {
            String pokemon = playerTeam.get(i);
            buttons[i] = new JRadioButton(pokemon);
            if (i == currentPlayerPokemonIndex) {
                buttons[i].setText(pokemon + " (Activo)");
                buttons[i].setEnabled(false);
            }
            group.add(buttons[i]);
            panel.add(buttons[i]);
            panel.add(Box.createVerticalStrut(5));
        }

        // Mostrar el diálogo
        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Cambiar Pokémon del Jugador",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Procesar la selección
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].isSelected()) {
                    String selectedPokemon = playerTeam.get(i);
                    currentPlayerPokemonIndex = i;
                    loadPlayerPokemonImage(selectedPokemon);
                    setPlayerInfo(selectedPokemon, 100);
                    JOptionPane.showMessageDialog(this,
                            "¡Adelante, " + selectedPokemon + "!",
                            "Cambio de Pokémon",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }

    /**
     * Cambia el Pokémon del oponente con un diálogo mejorado.
     */
    public void changeOpponentPokemon() {
        if (opponentTeam == null || opponentTeam.isEmpty() || !canChangeOpponentPokemon) {
            JOptionPane.showMessageDialog(this,
                    "No hay Pokémon disponibles para cambiar.",
                    "Equipo vacío",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Si solo hay un Pokémon, no se puede cambiar
        if (opponentTeam.size() == 1) {
            JOptionPane.showMessageDialog(this,
                    "El oponente solo tiene un Pokémon en su equipo.",
                    "No se puede cambiar",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear un panel personalizado para mostrar los Pokémon
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Selecciona un Pokémon para el oponente:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));

        // Grupo de botones para la selección
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] buttons = new JRadioButton[opponentTeam.size()];

        for (int i = 0; i < opponentTeam.size(); i++) {
            String pokemon = opponentTeam.get(i);
            buttons[i] = new JRadioButton(pokemon);
            if (i == currentOpponentPokemonIndex) {
                buttons[i].setText(pokemon + " (Activo)");
                buttons[i].setEnabled(false);
            }
            group.add(buttons[i]);
            panel.add(buttons[i]);
            panel.add(Box.createVerticalStrut(5));
        }

        // Mostrar el diálogo
        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Cambiar Pokémon del Oponente",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Procesar la selección
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].isSelected()) {
                    String selectedPokemon = opponentTeam.get(i);
                    currentOpponentPokemonIndex = i;
                    loadOpponentPokemonImage(selectedPokemon);
                    setOpponentInfo(selectedPokemon, 100);
                    JOptionPane.showMessageDialog(this,
                            "¡El oponente envía a " + selectedPokemon + "!",
                            "Cambio de Pokémon",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }

    /**
     * Establece la información del Pokémon del jugador.
     */
    public void setPlayerInfo(String name, int level) {
        this.playerName = name;
        this.playerLevel = level;
        repaint();
    }

    /**
     * Obtiene el botón de pausa.
     */
    public JButton getPauseButton(){
        return pauseButton;
    }

    /**
     * Establece la información del Pokémon del oponente.
     */
    public void setOpponentInfo(String name, int level) {
        this.opponentName = name;
        this.opponentLevel = level;
        repaint();
    }

    /**
     * Establece la imagen del Pokémon del jugador.
     */
    public void setPlayerPokemonImage(String imagePath) {
        try {
            playerPokemon = new ImageIcon(getClass().getResource(imagePath));
            if (playerPokemon.getIconWidth() <= 0) {
                // Si la imagen no se carga correctamente, intentar otras rutas
                loadPlayerPokemonImage(playerName);
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagen del jugador: " + e.getMessage());
            loadPlayerPokemonImage(playerName);
        }
        repaint();
    }

    /**
     * Establece la imagen del Pokémon del oponente.
     */
    public void setOpponentPokemonImage(String imagePath) {
        try {
            opponentPokemon = new ImageIcon(getClass().getResource(imagePath));
            if (opponentPokemon.getIconWidth() <= 0) {
                // Si la imagen no se carga correctamente, intentar otras rutas
                loadOpponentPokemonImage(opponentName);
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagen del oponente: " + e.getMessage());
            loadOpponentPokemonImage(opponentName);
        }
        repaint();
    }

    /**
     * Actualiza la información de los Pokémon en el panel de batalla.
     */
    public void updatePokemonInfo(Pokemon playerPokemon, Pokemon opponentPokemon) {
        if (playerPokemon != null) {
            this.playerName = playerPokemon.getName();
            this.playerLevel = playerPokemon.getLevel();
            this.playerHealth = (int)((playerPokemon.getCurrentPs() / (double)playerPokemon.getMaxPs()) * 100);
            loadPlayerPokemonImage(playerPokemon.getName());
        }

        if (opponentPokemon != null) {
            this.opponentName = opponentPokemon.getName();
            this.opponentLevel = opponentPokemon.getLevel();
            this.opponentHealth = (int)((opponentPokemon.getCurrentPs() / (double)opponentPokemon.getMaxPs()) * 100);
            loadOpponentPokemonImage(opponentPokemon.getName());
        }

        repaint();
    }
}