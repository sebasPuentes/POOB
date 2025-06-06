package src.presentation;

import src.domain.*;

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
 * @author Julian Lopez, David Puentes
 * @version 7.0 (2025-05-07)
 */
public class PokemonSelectionPanel extends JPanel {
    private final int MIN_POKEMON = 1;
    private final int MAX_POKEMON = 6;

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
    private List<String> selectedPlayerPokemons;
    private List<String> selectedOpponentPokemons;
    private boolean selectingPlayerPokemons = true;
    private POOBkemonGUI mainGUI;

    private Image backgroundImage;

    // Colores mejorados para los bordes y efectos
    private final Color PLAYER_SELECTION_COLOR = new Color(30, 144, 255);
    private final Color OPPONENT_SELECTION_COLOR = new Color(220, 20, 60);
    private final Color UNSELECTED_COLOR = new Color(255, 255, 255, 180);
    private final Color HOVER_COLOR = new Color(255, 215, 0, 180);

    // Panel que está actualmente bajo el cursor
    private JPanel hoveredPanel = null;

    /**
     * Constructor para el panel de selección de Pokémon
     */
    public PokemonSelectionPanel() {
        // Cargar imagen de fondo PRIMERO antes de cualquier otra inicialización
        loadBackgroundImage();
        prepareElements();
        prepareActions();
    }

    /**
     * Intenta cargar la imagen de fondo desde varias ubicaciones posibles
     */
    private void loadBackgroundImage() {
        // Usar solo la primera ruta directamente
        String imagePath = "resources/escenas/gimnasio.png";

        try {
            // Intentar cargar con ImageIO
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

    /**
     * Crea una imagen de fondo por defecto cuando no se puede cargar la original
     */
    private void createDefaultBackgroundImage() {
        int width = 800;
        int height = 600;
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) backgroundImage.getGraphics();

        // Dibujar un degradado como fondo
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 128, 0),
                width, height, new Color(0, 64, 0));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);

        // Dibujar líneas de cuadrícula para simular un gimnasio
        g2d.setColor(new Color(255, 255, 255, 50));
        for (int i = 0; i < width; i += 50) {
            g2d.drawLine(i, 0, i, height);
        }
        for (int i = 0; i < height; i += 50) {
            g2d.drawLine(0, i, width, i);
        }

        // Dibujar texto "GIMNASIO POKEMON" en el centro
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        g2d.setColor(new Color(255, 255, 255, 128));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "GIMNASIO POKEMON";
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, (width - textWidth)/2, height/2);

        g2d.dispose();
    }


    /**
     * Prepara los elementos visuales del panel
     */
    private void prepareElements() {
        setLayout(new BorderLayout());

        //Panel Informativo
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        topPanel.setOpaque(false);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        instructionLabel = new JLabel("Selecciona de 1 a 6 Pokémon para el Jugador 1");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        instructionLabel.setForeground(Color.WHITE);

        selectionCountLabel = new JLabel("Pokémon seleccionados: 0/" + MAX_POKEMON);
        selectionCountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        selectionCountLabel.setForeground(Color.WHITE);

        topPanel.add(instructionLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(selectionCountLabel);

        add(topPanel, BorderLayout.NORTH);

        // Panel de cuadrícula para los Pokémon con más espacio entre tarjetas
        selectionGrid = new JPanel(new GridLayout(0, 3, 25, 25)); // 3 columnas, más espaciado
        selectionGrid.setOpaque(false);

        // Cargar las tarjetas de Pokémon
        pokemonCards = new ArrayList<>();
        pokemonNameToCardMap = new HashMap<>();
        selectedPlayerPokemons = new ArrayList<>();
        selectedOpponentPokemons = new ArrayList<>();

        // Panel con scroll para contener la cuadrícula - Hacerlo transparente
        scrollPane = new JScrollPane(selectionGrid);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Mejorar la apariencia del scrollPane
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll más suave
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        // Añadir panel con padding alrededor del grid para mejor visualización
        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setOpaque(false);
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        paddingPanel.add(scrollPane, BorderLayout.CENTER);

        add(paddingPanel, BorderLayout.CENTER);

        // Panel de controles en la parte inferior con fondo semi-transparente
        controlPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        controlPanel.setOpaque(false);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Botones más atractivos
        confirmButton = createStyledButton("Confirmar selección", new Color(34, 139, 34)); // Verde oscuro
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setEnabled(false);

        goToMovements = createStyledButton("Elegir Movimientos", new Color(34, 139, 34)); // Verde oscuro
        goToMovements.setFont(new Font("Arial", Font.BOLD, 16));
        goToMovements.setEnabled(false);

        cancelButton = createStyledButton("Cancelar", new Color(178, 34, 34)); // Rojo oscuro
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));

        controlPanel.add(confirmButton);
        controlPanel.add(cancelButton);
        controlPanel.add(goToMovements);


        add(controlPanel, BorderLayout.SOUTH);

        // Cargar las tarjetas de Pokémon
        loadPokemonCards();
    }

    /**
     * Crea un botón con estilo personalizado
     */
    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                // Gradiente de fondo
                if (isEnabled()) {
                    GradientPaint gradient = new GradientPaint(
                            0, 0, baseColor.brighter(),
                            0, height, baseColor.darker());
                    g2d.setPaint(gradient);
                } else {
                    g2d.setColor(Color.GRAY);
                }

                g2d.fillRoundRect(0, 0, width, height, 15, 15);

                // Borde
                g2d.setColor(isEnabled() ? baseColor.darker() : Color.DARK_GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, width-1, height-1, 15, 15);

                // Texto
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle textRect = fm.getStringBounds(getText(), g2d).getBounds();

                int textX = (width - textRect.width) / 2;
                int textY = (height - textRect.height) / 2 + fm.getAscent();

                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                g2d.drawString(getText(), textX, textY);
            }
        };

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        // Agregar efectos de hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(Cursor.getDefaultCursor());
            }
        });

        return button;
    }

    /**
     * Prepara las acciones para los componentes interactivos
     */
    private void prepareActions() {
        confirmButton.addActionListener(e -> confirmSelection());
    }

    public void updateTrainerNames(String player1Name, String player2Name) {
        if (selectingPlayerPokemons) {
            instructionLabel.setText("Selecciona Pokémon para " + player1Name);
        } else {
            instructionLabel.setText("Selecciona Pokémon para " + player2Name);
        }
    }
    /**
     * Carga las tarjetas de Pokémon con imágenes GIF desde el directorio de recursos
     */
    private void loadPokemonCards() {
        try {
            // Obtener directorio de recursos
            File resourcesDir = new File("resources/ataques");

            if (!resourcesDir.exists() || !resourcesDir.isDirectory()) {
                // Intentar otra ruta
                resourcesDir = new File("POOBkemon/resources/ataques");
                if (!resourcesDir.exists() || !resourcesDir.isDirectory()) {
                    resourcesDir = new File("./resources/ataques");
                    if (!resourcesDir.exists() || !resourcesDir.isDirectory()) {
                        JLabel errorLabel = new JLabel("No se encontró el directorio de recursos", SwingConstants.CENTER);
                        errorLabel.setForeground(Color.WHITE);
                        selectionGrid.add(errorLabel);
                        return;
                    }
                }
            }

            // Filtrar solo archivos GIF
            File[] gifFiles = resourcesDir.listFiles((dir, name) ->
                    name.toLowerCase().endsWith(".gif"));

            if (gifFiles == null || gifFiles.length == 0) {
                JLabel errorLabel = new JLabel("No se encontraron GIFs de Pokémon", SwingConstants.CENTER);
                errorLabel.setForeground(Color.WHITE);
                selectionGrid.add(errorLabel);
                return;
            }
            // Crear tarjetas para cada imagen GIF de Pokémon
            for (File file : gifFiles) {
                String fileName = file.getName();

                // Extraer el nombre del Pokémon del nombre del archivo
                String pokemonName = file.getName().split("\\.")[0];

                // Crear una tarjeta para este Pokémon
                JPanel card = createPokemonCard(file, pokemonName);
                pokemonCards.add(card);
                pokemonNameToCardMap.put(pokemonName, card);

                // Añadir la tarjeta a la cuadrícula
                selectionGrid.add(card);
            }

            if (pokemonCards.isEmpty()) {
                JLabel noImagesLabel = new JLabel("No se encontraron imágenes GIF de Pokémon", SwingConstants.CENTER);
                noImagesLabel.setForeground(Color.WHITE);
                selectionGrid.add(noImagesLabel);
            }

        } catch (Exception e) {
            System.out.println("Error al cargar las imágenes de Pokémon: " + e.getMessage());
            e.printStackTrace();

            JLabel errorLabel = new JLabel("Error al cargar imágenes: " + e.getMessage(), SwingConstants.CENTER);
            errorLabel.setForeground(Color.WHITE);
            selectionGrid.add(errorLabel);
        }
    }

    /**
     * Crea una tarjeta visual para un Pokémon a partir del archivo de imagen
     */
    private JPanel createPokemonCard(File imageFile, String pokemonName) {
        // Crear un panel con efectos visuales mejorados
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Verificar si este panel está bajo el cursor
                boolean isHovered = (this == hoveredPanel);

                // Verificar si está seleccionado
                boolean isSelected = false;
                Color borderColor = UNSELECTED_COLOR;

                if (selectedPlayerPokemons.contains(pokemonName)) {
                    isSelected = true;
                    borderColor = PLAYER_SELECTION_COLOR;
                } else if (selectedOpponentPokemons.contains(pokemonName)) {
                    isSelected = true;
                    borderColor = OPPONENT_SELECTION_COLOR;
                } else if (isHovered) {
                    borderColor = HOVER_COLOR;
                }

                // Fondo del panel con efecto de brillo
                int width = getWidth();
                int height = getHeight();

                // Dibujar fondo semi-transparente con brillo
                if (isSelected) {
                    // Fondo especial para seleccionados
                    Color glowColor = borderColor.brighter();
                    g2d.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 50));
                    g2d.fillRoundRect(0, 0, width, height, 15, 15);
                } else if (isHovered) {
                    // Fondo para hover
                    g2d.setColor(new Color(255, 255, 200, 50));
                    g2d.fillRoundRect(0, 0, width, height, 15, 15);
                } else {
                    // Fondo normal semi-transparente
                    g2d.setColor(new Color(0, 0, 0, 80));
                    g2d.fillRoundRect(0, 0, width, height, 15, 15);
                }

                // Borde con efecto de brillo
                if (isSelected || isHovered) {
                    // Efecto de resplandor
                    int glowSize = isSelected ? 6 : 4;
                    for (int i = glowSize; i > 0; i--) {
                        float alpha = isSelected ? 0.6f - (i * 0.08f) : 0.4f - (i * 0.08f);
                        g2d.setColor(new Color(
                                borderColor.getRed()/255f,
                                borderColor.getGreen()/255f,
                                borderColor.getBlue()/255f,
                                alpha));
                        g2d.setStroke(new BasicStroke(i * 2));
                        g2d.drawRoundRect(glowSize-i, glowSize-i, width-2*(glowSize-i)-1, height-2*(glowSize-i)-1, 15, 15);
                    }
                }

                // Borde principal
                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(isSelected ? 3 : 2));
                g2d.drawRoundRect(0, 0, width-1, height-1, 15, 15);
            }
        };

        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(160, 200)); // Tarjetas más grandes
        card.setOpaque(false);

        try {
            // Cargar la imagen GIF directamente
            ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());

            // Crear panel para la imagen con un halo
            JPanel imagePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // No pintar fondo para mantener transparencia
                }
            };
            imagePanel.setOpaque(false);
            imagePanel.setLayout(new BorderLayout());

            JLabel imageLabel = new JLabel(icon, SwingConstants.CENTER);
            imageLabel.setOpaque(false);
            imagePanel.add(imageLabel, BorderLayout.CENTER);

            card.add(imagePanel, BorderLayout.CENTER);

        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
            errorLabel.setForeground(Color.RED);
            errorLabel.setOpaque(false);
            card.add(errorLabel, BorderLayout.CENTER);
        }

        // Panel para el nombre con efecto de fondo
        JPanel namePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo semi-transparente para el nombre
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                super.paintComponent(g);
            }
        };
        namePanel.setOpaque(false);
        namePanel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel(pokemonName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setOpaque(false);
        namePanel.add(nameLabel, BorderLayout.CENTER);

        card.add(namePanel, BorderLayout.SOUTH);

        // Añadir efectos de hover
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hoveredPanel = card;
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoveredPanel = null;
                card.setCursor(Cursor.getDefaultCursor());
                card.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                togglePokemonSelection(pokemonName, card);
            }
        });

        return card;
    }

    /**
     * Alterna la selección de un Pokémon
     * @param pokemonName Nombre del Pokémon
     * @param card Tarjeta que representa al Pokémon
     */
    private void togglePokemonSelection(String pokemonName, JPanel card) {
        List<String> currentSelection = selectingPlayerPokemons ? selectedPlayerPokemons : selectedOpponentPokemons;

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

        // Repintar la tarjeta para mostrar/ocultar el borde de selección
        card.repaint();
        updateSelectionCount();
        updateConfirmButtonState();
    }

    /**
     * Actualiza el contador de Pokémon seleccionados
     */
    private void updateSelectionCount() {
        List<String> currentSelection = selectingPlayerPokemons ? selectedPlayerPokemons : selectedOpponentPokemons;
        selectionCountLabel.setText("Pokémon seleccionados: " + currentSelection.size() + "/" + MAX_POKEMON);
    }

    /**
     * Actualiza el estado del botón de confirmar según la selección actual
     */
    private void updateConfirmButtonState() {
        List<String> currentSelection = selectingPlayerPokemons ? selectedPlayerPokemons : selectedOpponentPokemons;
        confirmButton.setEnabled(currentSelection.size() >= MIN_POKEMON);

        // Habilitar el botón de movimientos solo cuando ambos jugadores han seleccionado Pokémon
        goToMovements.setEnabled(!selectingPlayerPokemons &&
                !selectedPlayerPokemons.isEmpty() &&
                !selectedOpponentPokemons.isEmpty());
    }

    /**
     * Confirma la selección actual de Pokémon
     */
    private void confirmSelection() {
        if (selectingPlayerPokemons) {
            // Terminar selección del jugador, comenzar selección del oponente
            selectingPlayerPokemons = false;

            // Update label with trainer name if available
            if (mainGUI != null && mainGUI.getTrainer2() != null) {
                instructionLabel.setText("Selecciona Pokémon para " + mainGUI.getTrainer2().getNombre());
            } else {
                instructionLabel.setText("Selecciona Pokémon para el Jugador 2");
            }

            // Desmarcar todas las tarjetas visualmente
            for (JPanel card : pokemonCards) {
                card.repaint();
            }
            // Resetear contador
            selectionCountLabel.setText("Pokémon seleccionados: 0/" + MAX_POKEMON);
            confirmButton.setEnabled(false);
        } else {
            // Ambos jugadores han seleccionado Pokémon
            // Habilitar el botón de elegir movimientos
            goToMovements.setEnabled(true);

            // Mostrar mensaje informativo con nombres de entrenadores
            String player1Name = mainGUI != null && mainGUI.getTrainer1() != null ?
                    mainGUI.getTrainer1().getNombre() : "Jugador 1";
            String player2Name = mainGUI != null && mainGUI.getTrainer2() != null ?
                    mainGUI.getTrainer2().getNombre() : "Jugador 2";

            JOptionPane.showMessageDialog(this,
                    "¡Selección completada!\n\n" +
                            "Entrenador " + player1Name + ": " + selectedPlayerPokemons.size() + " Pokémon\n" +
                            "Entrenador " + player2Name + ": " + selectedOpponentPokemons.size() + " Pokémon\n\n" +
                            "Ahora puedes hacer clic en 'Elegir Movimientos' para asignar movimientos a tus Pokémon.",
                    "Selección completada", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Inicia la batalla con los Pokémon seleccionados
     */
    private void startBattle() {
        JOptionPane.showMessageDialog(this,
                "¡La batalla está lista para comenzar!\nJugador 1: " + selectedPlayerPokemons.size() + " Pokémon\n" +
                        "Jugador 2: " + selectedOpponentPokemons.size() + " Pokémon",
                "Preparando Batalla", JOptionPane.INFORMATION_MESSAGE);

        // Mostrar los nombres seleccionados
        StringBuilder message = new StringBuilder("Pokémon seleccionados:\n\nJugador 1:\n");
        for (String pokemon : selectedPlayerPokemons) {
            message.append("- ").append(pokemon).append("\n");
        }
        message.append("\nJugador 2:\n");
        for (String pokemon : selectedOpponentPokemons) {
            message.append("- ").append(pokemon).append("\n");
        }

        JOptionPane.showMessageDialog(this, message.toString(), "Selección completada", JOptionPane.INFORMATION_MESSAGE);

        if (mainGUI != null) {
            // Aquí iría el código para iniciar la batalla con los Pokémon seleccionados
            // Por ejemplo:
            // mainGUI.setPlayerPokemons(selectedPlayerPokemons);
            // mainGUI.setOpponentPokemons(selectedOpponentPokemons);
            // mainGUI.cardLayout.show(mainGUI.contentPanel, "BATALLA");
        }
    }

    /**
     * Restablece el panel de selección a su estado inicial
     */
    public void resetSelectionPanel() {
        selectingPlayerPokemons = true;
        selectedPlayerPokemons.clear();
        selectedOpponentPokemons.clear();

        // Repintar todas las tarjetas
        for (JPanel card : pokemonCards) {
            card.repaint();
        }

        instructionLabel.setText("Selecciona de 1 a 6 Pokémon para el Jugador 1");
        selectionCountLabel.setText("Pokémon seleccionados: 0/" + MAX_POKEMON);
        confirmButton.setEnabled(false);
    }

    /**
     * Restablece los bordes de todas las tarjetas
     */
    private void resetCardBorders() {
        for (JPanel card : pokemonCards) {
            card.repaint();
        }
    }

    /**
     * Devuelve la lista de Pokémon seleccionados por el jugador
     * @return Lista con los nombres de los Pokémon seleccionados
     */
    public List<String> getPlayerPokemons() {
        return new ArrayList<>(selectedPlayerPokemons);
    }

    /**
     * Devuelve la lista de Pokémon seleccionados por el oponente
     * @return Lista con los nombres de los Pokémon seleccionados
     */
    public List<String> getOpponentPokemons() {
        return new ArrayList<>(selectedOpponentPokemons);
    }

    //Repintar el panel
    public void forceRepaint() {
        revalidate();
        repaint();
    }

    //Get Boton Movimientos
    public JButton getGoToMovementsButton() {
        return goToMovements;
    }
    //Get Boton Confirmar
    public JButton getCancelButton(){
        return cancelButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Dibujar la imagen de fondo
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Si no hay imagen de fondo, dibujar un gradiente
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(0, 0, new Color(41, 128, 185),
                    getWidth(), getHeight(), new Color(44, 62, 80));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}