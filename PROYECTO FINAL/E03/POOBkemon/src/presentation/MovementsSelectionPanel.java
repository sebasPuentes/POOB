package src.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class MovementsSelectionPanel extends JPanel {
    private POOBkemonGUI parentGUI;
    private ArrayList<String> playerPokemons = new ArrayList<>();
    private ArrayList<String> opponentPokemons = new ArrayList<>();
    
    // Separar los movimientos de cada entrenador en HashMaps independientes
    private HashMap<String, ArrayList<String>> playerSelectedMovements = new HashMap<>();
    private HashMap<String, ArrayList<String>> opponentSelectedMovements = new HashMap<>();
    
    private ArrayList<String> availableMovements = new ArrayList<>();

    private JPanel pokemonsPanel;
    private JButton confirmButton;
    private JButton backButton;
    private JButton switchPlayerButton;
    private JLabel playerLabel;
    private JLabel instructionLabel;
    private Image backgroundImage;

    // Control para alternar entre jugadores
    private boolean isSelectingPlayer1 = true;
    private String player1Name = "Jugador 1";
    private String player2Name = "Jugador 2";
    private Color player1Color = new Color(30, 144, 255); // Azul
    private Color player2Color = new Color(220, 20, 60);  // Rojo

    public MovementsSelectionPanel(POOBkemonGUI gui) {
        this.parentGUI = gui;
        setLayout(new BorderLayout());
        loadBackgroundImage();
        prepareElements();
    }

    private void loadBackgroundImage() {
        try {
            String[] possiblePaths = {
                "resources/escenas/fondo_movimientos.png",
                "resources/escenas/gimnasio.png",
                "resources/escenas/seleccionPersonajes.png"
            };
            
            for (String path : possiblePaths) {
                File file = new File(path);
                if (file.exists()) {
                    backgroundImage = javax.imageio.ImageIO.read(file);
                    return;
                }
            }
            
            // Si no encuentra imagen, crear un fondo por defecto
            createDefaultBackgroundImage();
        } catch (Exception e) {
            System.err.println("Error al cargar imagen de fondo: " + e.getMessage());
            createDefaultBackgroundImage();
        }
    }

    private void createDefaultBackgroundImage() {
        int width = 800;
        int height = 600;
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) backgroundImage.getGraphics();
        // Crear un degradado como fondo
        GradientPaint gradient = new GradientPaint(0, 0, new Color(40, 44, 52),
                width, height, new Color(60, 64, 72));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
        // Agregar un poco de textura
        g2d.setColor(new Color(255, 255, 255, 10));
        for (int i = 0; i < height; i += 4) {
            g2d.drawLine(0, i, width, i);
        }
        g2d.dispose();
    }

    private void prepareElements() {
        // Panel superior con título y jugador actual
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        
        JLabel title = new JLabel("Selección de Movimientos", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        playerLabel = new JLabel("JUGADOR 1", SwingConstants.CENTER);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerLabel.setForeground(player1Color);
        playerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        
        instructionLabel = new JLabel("Selecciona hasta 4 movimientos para cada Pokémon", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionLabel.setForeground(Color.WHITE);
        
        titlePanel.add(title);
        
        JPanel playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));
        playerInfoPanel.setOpaque(false);
        playerInfoPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        playerInfoPanel.add(playerLabel);
        playerInfoPanel.add(instructionLabel);
        
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(playerInfoPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        // Panel central con scroll para Pokémon
        // Cambiar a GridLayout para organizar mejor cuando hay muchos Pokémon
        pokemonsPanel = new JPanel();
        // Usar GridLayout flexible que se ajuste al número de Pokémon (2 columnas)
        pokemonsPanel.setLayout(new GridLayout(0, 3, 15, 15)); // 0 filas = tantas como sea necesario, 3 columnas
        pokemonsPanel.setOpaque(false);

        JScrollPane scrollPokemons = new JScrollPane(pokemonsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPokemons.setOpaque(false);
        scrollPokemons.getViewport().setOpaque(false);
        scrollPokemons.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        // Asegurarse que el scroll funcione correctamente
        scrollPokemons.getVerticalScrollBar().setUnitIncrement(16);
        scrollPokemons.setPreferredSize(new Dimension(700, 400)); // Dar un tamaño explícito al scroll
        
        add(scrollPokemons, BorderLayout.CENTER);

        // Panel inferior con botones de control
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        bottomPanel.setOpaque(false);
        
        backButton = createStyledButton("Volver", new Color(231, 76, 60));
        switchPlayerButton = createStyledButton("Cambiar a Jugador 2", new Color(155, 89, 182));
        confirmButton = createStyledButton("Confirmar", new Color(46, 204, 113));
        
        // Inicialmente deshabilitado hasta verificar selecciones
        confirmButton.setEnabled(false);
        
        bottomPanel.add(backButton);
        bottomPanel.add(switchPlayerButton);
        bottomPanel.add(confirmButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Configurar acciones de los botones
        backButton.addActionListener(e -> {
            if (parentGUI != null) parentGUI.cardLayout.show(parentGUI.contentPanel, "POKEMON SELECTION");
        });
        
        switchPlayerButton.addActionListener(e -> {
            switchPlayer();
        });
        
        confirmButton.addActionListener(e -> {
            if (validateSelections()) {
                if (parentGUI != null)
                    parentGUI.cardLayout.show(parentGUI.contentPanel, "ITEMS");
                System.out.println("Player movements: " + playerSelectedMovements);
                System.out.println("Opponent movements: " + opponentSelectedMovements);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Cada Pokémon debe tener al menos un movimiento seleccionado.",
                    "Selección incompleta", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public void initialize(POOBkemonGUI parent, ArrayList<String> playerPokemonList, ArrayList<String> opponentPokemonList) {
        this.parentGUI = parent;
        this.playerPokemons = new ArrayList<>(playerPokemonList);
        this.opponentPokemons = new ArrayList<>(opponentPokemonList);
        
        // Limpiar las selecciones previas
        this.playerSelectedMovements.clear();
        this.opponentSelectedMovements.clear();
        
        pokemonsPanel.removeAll();
        
        // Restablecer a jugador 1 y obtener nombres si están disponibles
        isSelectingPlayer1 = true;
        if (parent != null && parent.getTrainer1() != null && parent.getTrainer2() != null) {
            player1Name = parent.getTrainer1().getNombre();
            player2Name = parent.getTrainer2().getNombre();
        }
        
        updatePlayerInfo();

        // Obtener movimientos disponibles del sistema
        availableMovements.clear();
        if (parentGUI != null) {
            availableMovements = parentGUI.getMovementsNames();
        }

        // Cargar los Pokémon del jugador 1 primero
        loadPlayerPokemons();
        
        // Forzar actualización visual y re-renderizado
        revalidate();
        repaint();
    }

    private void loadPlayerPokemons() {
        pokemonsPanel.removeAll();
        
        ArrayList<String> currentPokemons = isSelectingPlayer1 ? playerPokemons : opponentPokemons;
        
        if (currentPokemons.isEmpty()) {
            JLabel noPokeLabel = new JLabel("No hay Pokémon seleccionados para este jugador", SwingConstants.CENTER);
            noPokeLabel.setForeground(Color.WHITE);
            noPokeLabel.setFont(new Font("Arial", Font.BOLD, 16));
            pokemonsPanel.add(noPokeLabel);
        } else {
            System.out.println("Cargando " + currentPokemons.size() + " Pokémon para " + 
                (isSelectingPlayer1 ? player1Name : player2Name));
            
            for (String pokemonName : currentPokemons) {
                JPanel pokePanel = createPokemonSelectionPanel(pokemonName);
                pokemonsPanel.add(pokePanel);
                System.out.println("Añadido panel para: " + pokemonName);
            }
        }
        
        // Forzar actualizacion inmediata
        pokemonsPanel.revalidate();
        pokemonsPanel.repaint();
        revalidate();
        repaint();
    }

    private JPanel createPokemonSelectionPanel(String pokemonName) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 8));
        panel.setPreferredSize(new Dimension(200, 340));
        panel.setBackground(new Color(44, 49, 58));
        
        // Borde con el color del jugador actual
        Color borderColor = isSelectingPlayer1 ? player1Color : player2Color;
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 3),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(140, 140));
        
        // Panel para el GIF con un fondo distintivo
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(50, 55, 65));
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(80, 85, 95), 2));
        imagePanel.add(imgLabel, BorderLayout.CENTER);
        
        // Cargar la imagen del Pokémon
        loadPokemonImage(pokemonName, imgLabel);

        panel.add(imagePanel, BorderLayout.NORTH);

        JLabel nameLabel = new JLabel(pokemonName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel, BorderLayout.CENTER);

        JPanel movesPanel = new JPanel();
        movesPanel.setLayout(new BoxLayout(movesPanel, BoxLayout.Y_AXIS));
        movesPanel.setBackground(new Color(30, 34, 43));
        movesPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane movesScroll = new JScrollPane(movesPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        movesScroll.setPreferredSize(new Dimension(170, 120));
        movesScroll.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 1));
        movesScroll.getViewport().setBackground(new Color(30, 34, 43));
        
        // Mejorar velocidad de desplazamiento
        movesScroll.getVerticalScrollBar().setUnitIncrement(16);

        // Obtener los movimientos seleccionados del HashMap correcto según el jugador actual
        HashMap<String, ArrayList<String>> currentSelectedMovements = isSelectingPlayer1 
                ? playerSelectedMovements 
                : opponentSelectedMovements;
                
        ArrayList<String> selected = currentSelectedMovements.computeIfAbsent(pokemonName, k -> new ArrayList<>());
        
        if (availableMovements.isEmpty()) {
            JLabel noMovesLabel = new JLabel("No hay movimientos disponibles", SwingConstants.CENTER);
            noMovesLabel.setForeground(Color.WHITE);
            movesPanel.add(noMovesLabel);
        } else {
            for (String moveName : availableMovements) {
                JCheckBox cb = new JCheckBox(moveName);
                cb.setForeground(Color.WHITE);
                cb.setBackground(new Color(60, 65, 75));
                cb.setFont(new Font("Arial", Font.PLAIN, 13));
                cb.setSelected(selected.contains(moveName));
                cb.addActionListener(e -> {
                    if (cb.isSelected()) {
                        if (selected.size() < 4) {
                            selected.add(moveName);
                        } else {
                            cb.setSelected(false);
                            JOptionPane.showMessageDialog(this,
                                    "Solo puedes seleccionar hasta 4 movimientos.", "Límite alcanzado", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        selected.remove(moveName);
                    }
                    updateConfirmButtonState();
                });
                movesPanel.add(cb);
            }
        }

        panel.add(movesScroll, BorderLayout.SOUTH);
        return panel;
    }

    private void loadPokemonImage(String pokemonName, JLabel label) {
        String[] paths = {
            "resources/ataques/" + pokemonName + ".gif",
            "resources/ataques/" + pokemonName + ".png",
            "resources/pokemon/" + pokemonName + ".gif",
            "resources/pokemon/" + pokemonName + ".png"
        };
        
        for (String path : paths) {
            File f = new File(path);
            if (f.exists()) {
                try {
                    ImageIcon icon = new ImageIcon(f.getAbsolutePath());
                    label.setIcon(icon);
                    return;
                } catch (Exception e) {
                    System.err.println("Error cargando imagen: " + e.getMessage());
                }
            }
        }
        
        // Si no encuentra ninguna imagen, crear un placeholder
        BufferedImage img = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // Fondo circular
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(100, 100, 100));
        g.fillOval(10, 10, 100, 100);
        
        // Simular una silueta Pokémon
        g.setColor(new Color(70, 70, 70));
        g.fillOval(35, 30, 50, 50); // Cabeza
        g.fillRoundRect(45, 65, 30, 30, 10, 10); // Cuerpo
        
        // Poner el nombre
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(pokemonName);
        g.drawString(pokemonName, (120 - textWidth) / 2, 100);
        
        g.dispose();
        label.setIcon(new ImageIcon(img));
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
                if (btn.isEnabled()) {
                    btn.setBackground(color.brighter()); 
                }
            }
            public void mouseExited(MouseEvent e) { 
                btn.setBackground(color); 
            }
        });
        return btn;
    }
    
    // Alterna entre jugador 1 y jugador 2
    private void switchPlayer() {
        isSelectingPlayer1 = !isSelectingPlayer1;
        updatePlayerInfo();
        loadPlayerPokemons();
        updateConfirmButtonState();
    }
    
    // Actualiza la interfaz con la información del jugador actual
    private void updatePlayerInfo() {
        if (isSelectingPlayer1) {
            playerLabel.setText(player1Name);
            playerLabel.setForeground(player1Color);
            switchPlayerButton.setText("Cambiar a " + player2Name);
            instructionLabel.setText("Selecciona hasta 4 movimientos para cada Pokémon de " + player1Name);
        } else {
            playerLabel.setText(player2Name);
            playerLabel.setForeground(player2Color);
            switchPlayerButton.setText("Cambiar a " + player1Name);
            instructionLabel.setText("Selecciona hasta 4 movimientos para cada Pokémon de " + player2Name);
        }
    }
    
    // Actualiza el estado del botón de confirmar según las selecciones
    private void updateConfirmButtonState() {
        // Habilitar el botón de confirmar solo si ambos jugadores han hecho sus selecciones y son válidas
        boolean player1Valid = validatePlayerSelections(playerPokemons, playerSelectedMovements);
        boolean player2Valid = validatePlayerSelections(opponentPokemons, opponentSelectedMovements);
        
        confirmButton.setEnabled(player1Valid && player2Valid);
    }
    
    // Valida que todos los Pokémon de un jugador tengan al menos un movimiento seleccionado
    private boolean validatePlayerSelections(ArrayList<String> pokemonList, HashMap<String, ArrayList<String>> movementsMap) {
        for (String pokemon : pokemonList) {
            ArrayList<String> moves = movementsMap.get(pokemon);
            if (moves == null || moves.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    // Valida todas las selecciones antes de continuar
    private boolean validateSelections() {
        return validatePlayerSelections(playerPokemons, playerSelectedMovements) && 
               validatePlayerSelections(opponentPokemons, opponentSelectedMovements);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Getters para integración con POOBkemonGUI
    public JButton getGoBackButton() { return backButton; }
    public JButton getSelectItemsButton() { return confirmButton; }
    public JButton getSwitchPlayerButton() { return switchPlayerButton; }
    
    // Devuelve el HashMap de movimientos del jugador 1
    public HashMap<String, ArrayList<String>> getPlayerSelectedMovements() {
        return playerSelectedMovements;
    }
    
    // Devuelve el HashMap de movimientos del jugador 2
    public HashMap<String, ArrayList<String>> getOpponentSelectedMovements() {
        return opponentSelectedMovements;
    }
    
    // Método heredado para compatibilidad - devuelve todos los movimientos combinados
    public Map<String, ArrayList<String>> getAllSelectedMovements() { 
        HashMap<String, ArrayList<String>> allMovements = new HashMap<>();
        allMovements.putAll(playerSelectedMovements);
        allMovements.putAll(opponentSelectedMovements);
        return allMovements;
    }
}
