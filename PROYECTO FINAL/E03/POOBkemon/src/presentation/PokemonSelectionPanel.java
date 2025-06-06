package src.presentation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Panel para seleccionar los Pokémon y asignar movimientos por turnos.
 */
public class PokemonSelectionPanel extends JPanel {
    //==========================================================================
    // 1. DECLARACIÓN DE VARIABLES Y CONSTANTES
    //==========================================================================
    private final int MIN_POKEMON = 1;
    private final int MAX_POKEMON = 6;
    private POOBkemonGUI gui;
    private JPanel selectionGrid;
    private JScrollPane scrollPane;
    private JPanel controlPanel;
    private JButton confirmButton;
    private JButton cancelButton;
    private JButton goToMovements;
    private JLabel instructionLabel;
    private JLabel selectionCountLabel;

    private List<JPanel> pokemonCards;
    private Map<String, JPanel> pokemonNameToCardMap;
    private ArrayList<String> selectedPlayerPokemons;
    private ArrayList<String> selectedOpponentPokemons;
    private boolean selectingPlayerPokemons = true;
    private String player1Name = "Jugador 1";
    private String player2Name = "Jugador 2";

    private Image backgroundImage;
    private JPanel hoveredPanel = null;

    private final Color PLAYER_SELECTION_COLOR = new Color(30, 144, 255);
    private final Color OPPONENT_SELECTION_COLOR = new Color(220, 20, 60);
    private final Color UNSELECTED_COLOR = new Color(255, 255, 255, 180);
    private final Color HOVER_COLOR = new Color(255, 215, 0, 180);

    //==========================================================================
    // 2. CONSTRUCTOR E INICIALIZACIÓN
    //==========================================================================
    public PokemonSelectionPanel(POOBkemonGUI gui) {
        this.gui = gui;
        loadBackgroundImage();
        prepareElements();
        prepareActions();
    }
    //==========================================================================
    // 3. CARGA DE RECURSOS GRÁFICOS
    //==========================================================================

    private void loadBackgroundImage() {
        String imagePath = "resources/escenas/gimnasio.png";
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                backgroundImage = ImageIO.read(file);
            } else {
                createDefaultBackgroundImage();
            }
        } catch (IOException e) {
            createDefaultBackgroundImage();
        }
    }
    private void createDefaultBackgroundImage() {
        int width = 800;
        int height = 600;
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) backgroundImage.getGraphics();
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 128, 0), width, height, new Color(0, 64, 0));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
    }
    private void loadPokemonCards() {
        try {
            File resourcesDir = new File("resources/ataques");
            if (!resourcesDir.exists() || !resourcesDir.isDirectory()) {
                JLabel errorLabel = new JLabel("No se encontró el directorio de recursos", SwingConstants.CENTER);
                errorLabel.setForeground(Color.WHITE);
                selectionGrid.add(errorLabel);
                return;
            }
            File[] gifFiles = resourcesDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".gif"));
            if (gifFiles == null || gifFiles.length == 0) {
                JLabel errorLabel = new JLabel("No se encontraron GIFs de Pokémon", SwingConstants.CENTER);
                errorLabel.setForeground(Color.WHITE);
                selectionGrid.add(errorLabel);
                return;
            }

            ArrayList<String> pokedexNames = gui.getPokemonNames();
            for (File file : gifFiles) {
                String pokemonName = file.getName().split("\\.")[0];
                if (pokedexNames.contains(pokemonName)) {
                    JPanel card = createPokemonCard(file, pokemonName);
                    pokemonCards.add(card);
                    pokemonNameToCardMap.put(pokemonName, card);
                    selectionGrid.add(card);
                    }
                }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error al cargar imágenes: " + e.getMessage(), SwingConstants.CENTER);
            errorLabel.setForeground(Color.WHITE);
            selectionGrid.add(errorLabel);
        }
    }
    //==========================================================================
    // 4. PREPARACIÓN DE LA INTERFAZ DE USUARIO
    //==========================================================================
    private void prepareElements() {
        setLayout(new BorderLayout());
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        selectionGrid = new JPanel(new GridLayout(0, 3, 25, 25));
        selectionGrid.setOpaque(false);
        pokemonCards = new ArrayList<>();
        pokemonNameToCardMap = new HashMap<>();
        selectedPlayerPokemons = new ArrayList<>();
        selectedOpponentPokemons = new ArrayList<>();
        scrollPane = createScrollPane();
        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setOpaque(false);
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        paddingPanel.add(scrollPane, BorderLayout.CENTER);
        add(paddingPanel, BorderLayout.CENTER);
        controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
        loadPokemonCards();
    }
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        instructionLabel = new JLabel("Selecciona de 1 a 6 Pokémon para el Jugador 1");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        instructionLabel.setForeground(Color.BLACK.darker());
        selectionCountLabel = new JLabel("Pokémon seleccionados: 0/" + MAX_POKEMON);
        selectionCountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        selectionCountLabel.setForeground(Color.BLACK.darker());
        topPanel.add(instructionLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(selectionCountLabel);
        return topPanel;
    }
    private JScrollPane createScrollPane() {
        JScrollPane scrollPane = new JScrollPane(selectionGrid);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        confirmButton = createStyledButton("Confirmar selección", new Color(34, 139, 34));
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setEnabled(false);
        goToMovements = createStyledButton("Elegir Movimientos", new Color(34, 139, 34));
        goToMovements.setFont(new Font("Arial", Font.BOLD, 16));
        goToMovements.setEnabled(false);
        cancelButton = createStyledButton("Cancelar", new Color(178, 34, 34));
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        controlPanel.add(confirmButton);
        controlPanel.add(cancelButton);
        controlPanel.add(goToMovements);
        return controlPanel;
    }
    //==========================================================================
    // 5. COMPONENTES UI PERSONALIZADOS
    //==========================================================================
    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.brighter());
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
                button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
            }
        });
        return button;
    }

    private JPanel createPokemonCard(File imageFile, String pokemonName) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                boolean isSelectedByPlayer1 = selectedPlayerPokemons.contains(pokemonName);
                boolean isSelectedByPlayer2 = selectedOpponentPokemons.contains(pokemonName);
                if (isSelectedByPlayer1 && isSelectedByPlayer2) {
                    g.setColor(PLAYER_SELECTION_COLOR);
                    ((Graphics2D) g).setStroke(new BasicStroke(4));
                    g.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
                    g.setColor(OPPONENT_SELECTION_COLOR);
                    ((Graphics2D) g).setStroke(new BasicStroke(2));
                    g.drawRect(6, 6, getWidth() - 12, getHeight() - 12);
                } else if (isSelectedByPlayer1) {
                    g.setColor(PLAYER_SELECTION_COLOR);
                    ((Graphics2D) g).setStroke(new BasicStroke(4));
                    g.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
                } else if (isSelectedByPlayer2) {
                    g.setColor(OPPONENT_SELECTION_COLOR);
                    ((Graphics2D) g).setStroke(new BasicStroke(4));
                    g.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
                }
                if (hoveredPanel == this) {
                    g.setColor(HOVER_COLOR);
                    ((Graphics2D) g).setStroke(new BasicStroke(2));
                    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                }
            }
        };
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(160, 200));
        card.setOpaque(false);
        try {
            ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
            JLabel imageLabel = new JLabel(icon, SwingConstants.CENTER);
            imageLabel.setOpaque(false);
            card.add(imageLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
            errorLabel.setForeground(Color.RED);
            errorLabel.setOpaque(false);
            card.add(errorLabel, BorderLayout.CENTER);
        }
        JLabel nameLabel = new JLabel(pokemonName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setOpaque(false);
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                togglePokemonSelection(pokemonName);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                hoveredPanel = card;
                card.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (hoveredPanel == card) {
                    hoveredPanel = null;
                    card.repaint();
                }
            }
        });
        return card;
    }

    //==========================================================================
    // 6. LÓGICA DE SELECCIÓN DE POKÉMON
    //==========================================================================

    private void togglePokemonSelection(String pokemonName) {
        ArrayList<String> currentSelection = selectingPlayerPokemons ? selectedPlayerPokemons : selectedOpponentPokemons;
        // Alternar la selección del Pokémon para el jugador actual
        if (currentSelection.contains(pokemonName)) {
            currentSelection.remove(pokemonName);
        } else {
            if (currentSelection.size() < MAX_POKEMON) {
                currentSelection.add(pokemonName);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Ya has seleccionado el máximo de " + MAX_POKEMON + " Pokémon",
                        "Máximo alcanzado", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        // Actualizar la interfaz
        for (JPanel pokemonCard : pokemonCards) {
            pokemonCard.repaint(); // Repintar todas las tarjetas para reflejar la selección
        }

        updateSelectionCount();
        updateConfirmButtonState();
    }
    private void updateSelectionCount() {
        ArrayList<String> currentSelection = selectingPlayerPokemons ? selectedPlayerPokemons : selectedOpponentPokemons;
        selectionCountLabel.setText("Pokémon seleccionados: " + currentSelection.size() + "/" + MAX_POKEMON);
    }
    private void updateConfirmButtonState() {
        ArrayList<String> currentSelection = selectingPlayerPokemons ? selectedPlayerPokemons : selectedOpponentPokemons;
        confirmButton.setEnabled(currentSelection.size() >= MIN_POKEMON);
        goToMovements.setEnabled(!selectingPlayerPokemons &&
                !selectedPlayerPokemons.isEmpty() &&
                !selectedOpponentPokemons.isEmpty());
    }
    private void confirmSelection() {
        if (selectingPlayerPokemons) {
            selectingPlayerPokemons = false;
            instructionLabel.setText("Selecciona Pokémon para " + player2Name);
            selectionCountLabel.setText(player2Name + ": 0/" + MAX_POKEMON);
            confirmButton.setEnabled(false);
        } else {
            goToMovements.setEnabled(true);
            JOptionPane.showMessageDialog(this,
                    "¡Selección completada!\n\n" +
                            "Entrenador " + player1Name + ": " + selectedPlayerPokemons.size() + " Pokémon\n" +
                            "Entrenador " + player2Name + ": " + selectedOpponentPokemons.size() + " Pokémon\n\n" +
                            "Ahora puedes hacer clic en 'Elegir Movimientos' para asignar movimientos a tus Pokémon.",
                    "Selección completada", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    //==========================================================================
    // 7. GESTIÓN DE FLUJO DE INTERFAZ Y ESTADO
    //==========================================================================
    public void resetSelectionPanel() {
        selectingPlayerPokemons = true;
        selectedPlayerPokemons.clear();
        selectedOpponentPokemons.clear();
        for (JPanel card : pokemonCards) {
            card.repaint();
        }
        instructionLabel.setText("Selecciona de 1 a 6 Pokémon para el Jugador 1");
        selectionCountLabel.setText("Pokémon seleccionados: 0/" + MAX_POKEMON);
        confirmButton.setEnabled(false);
        goToMovements.setEnabled(false);
    }
    //==========================================================================
    // 8. RENDERIZADO Y MÉTODOS DE DIBUJO
    //==========================================================================

    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    //==========================================================================
    // 9. GETTERS Y SETTERS
    //==========================================================================
    public ArrayList<String> getPlayerPokemons() {
        return selectedPlayerPokemons;
    }
    public ArrayList<String> getOpponentPokemons() {
        return selectedOpponentPokemons;
    }
    public JButton getGoToMovementsButton() {
        return goToMovements;
    }
    public JButton getCancelButton() {
        return cancelButton;
    }
    /**
     * Prepara las acciones de los botones
     */
    private void prepareActions() {
        confirmButton.addActionListener(e -> confirmSelection());
        // Los otros botones son manejados por POOBkemonGUI
        // cancelButton y goToMovements no necesitan acción aquí
    }
    /**
     * Fuerza la repintura del panel
     */
    public void forceRepaint() {
        revalidate();
        repaint();
    }
    /**
     * Actualiza los nombres de los entrenadores en la interfaz
     *
     * @param player1Name Nombre del primer jugador
     * @param player2Name Nombre del segundo jugador
     */
    public void updateTrainerNames(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        instructionLabel.setText("Selecciona de 1 a 6 Pokémon para " + player1Name);
        updateSelectionCount();
    }
}
