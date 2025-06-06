package src.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.awt.image.BufferedImage;

/**
 * Panel simplificado para selección de ítems
 */
public class ItemsPanel extends JPanel {
    // Constantes
    private final int MAX_ITEMS = 3;
    
    // Referencias
    private POOBkemonGUI gui;
    
    // Componentes UI
    private JPanel itemsGridPanel;
    private JLabel titleLabel;
    private JLabel playerTurnLabel;
    private JLabel selectedItemsLabel;
    private JButton confirmButton;
    private JButton cancelButton;
    private JButton switchPlayerButton;
    // Datos
    private ArrayList<String> availableItems = new ArrayList<>();
    private ArrayList<String> player1Items = new ArrayList<>();
    private ArrayList<String> player2Items = new ArrayList<>();
    private boolean isPlayer1Turn = true;
    private String player1Name = "Jugador 1";
    private String player2Name = "Jugador 2";
    
    // Colores
    private final Color PLAYER1_COLOR = new Color(30, 144, 255);  // Azul
    private final Color PLAYER2_COLOR = new Color(220, 20, 60);   // Rojo
    private final Color SELECTED_COLOR = new Color(46, 204, 113); // Verde

    // Constructor
    public ItemsPanel(POOBkemonGUI gui) {
        this.gui = gui;
        setLayout(new BorderLayout(10, 10));
        prepareElements();
        prepareActions();
    }

    // Inicializar componentes
    private void prepareElements() {
        // Panel superior con título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        titleLabel = new JLabel("SELECCIÓN DE ÍTEMS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        playerTurnLabel = new JLabel("Turno de " + player1Name + " - Selecciona hasta 3 ítems", SwingConstants.CENTER);
        playerTurnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerTurnLabel.setForeground(PLAYER1_COLOR);

        selectedItemsLabel = new JLabel("Ítems seleccionados: 0/" + MAX_ITEMS, SwingConstants.CENTER);
        selectedItemsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectedItemsLabel.setForeground(Color.WHITE);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setOpaque(false);
        labelPanel.add(titleLabel);
        labelPanel.add(Box.createVerticalStrut(10));
        labelPanel.add(playerTurnLabel);
        labelPanel.add(Box.createVerticalStrut(5));
        labelPanel.add(selectedItemsLabel);

        topPanel.add(labelPanel, BorderLayout.CENTER);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        // Panel central con grid de ítems
        itemsGridPanel = new JPanel(new GridLayout(0, 4, 15, 15));
        itemsGridPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(itemsGridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Panel inferior con botones de control
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        bottomPanel.setOpaque(false);

        cancelButton = createStyledButton("Cancelar", new Color(178, 34, 34));
        switchPlayerButton = createStyledButton("Cambiar a " + player2Name, new Color(155, 89, 182));
        confirmButton = createStyledButton("Confirmar", new Color(34, 139, 34));
        confirmButton.setEnabled(false);

        bottomPanel.add(cancelButton);
        bottomPanel.add(switchPlayerButton);
        bottomPanel.add(confirmButton);

        // Agregar paneles al layout principal
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Color de fondo
        setBackground(new Color(44, 62, 80));
    }

    // Preparar acciones de botones
    private void prepareActions() {
        confirmButton.addActionListener(e -> confirmSelection());
    }

    // Cargar ítems disponibles
    private void loadItems() {
        itemsGridPanel.removeAll();

        if (gui != null) {
            availableItems = gui.getItemsNames();
        } else {
            // Ítems por defecto si no hay GUI
            availableItems = new ArrayList<>(Arrays.asList(
                "Poción", "Súper Poción", "Híper Poción", "Revivir"
            ));
        }

        for (String itemName : availableItems) {
            JButton itemButton = createItemButton(itemName);
            itemsGridPanel.add(itemButton);
        }

        itemsGridPanel.revalidate();
        itemsGridPanel.repaint();
    }

    // Crear un botón de ítem
    private JButton createItemButton(String itemName) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        // Cargar imagen
        ImageIcon icon = loadItemImage(itemName);
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Etiqueta de nombre
        JLabel nameLabel = new JLabel(itemName, SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));

        button.add(imageLabel, BorderLayout.CENTER);
        button.add(nameLabel, BorderLayout.SOUTH);

        // Estilo del botón
        button.setBackground(new Color(70, 70, 80));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 120));

        // Verificar si el ítem ya está seleccionado
        ArrayList<String> currentItems = isPlayer1Turn ? player1Items : player2Items;
        if (currentItems.contains(itemName)) {
            button.setBackground(SELECTED_COLOR);
        }

        // Acción al hacer clic
        button.addActionListener(e -> {
            toggleItemSelection(itemName, button);
        });

        // Efectos de hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!button.getBackground().equals(SELECTED_COLOR)) {
                    button.setBackground(new Color(90, 90, 100));
                }
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ArrayList<String> currentItems = isPlayer1Turn ? player1Items : player2Items;
                if (currentItems.contains(itemName)) {
                    button.setBackground(SELECTED_COLOR);
                } else {
                    button.setBackground(new Color(70, 70, 80));
                }
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });

        return button;
    }

    // Cargar imagen para un ítem
    private ImageIcon loadItemImage(String itemName) {
        String[] possiblePaths = {
            "resources/pociones/" + itemName + ".png",
            "resources/pociones/" + itemName + ".png",
            "resources/pociones/" + itemName + ".png",
            "resources/pociones/" + itemName + ".png"
        };

        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {
                try {
                    ImageIcon originalIcon = new ImageIcon(file.getAbsolutePath());
                    Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImage);
                } catch (Exception e) {
                    System.err.println("Error cargando imagen: " + e.getMessage());
                }
            }
        }

        // Si no se encuentra la imagen, crear un icono predeterminado
        return createDefaultItemIcon(itemName);
    }

    // Crear un icono predeterminado
    private ImageIcon createDefaultItemIcon(String itemName) {
        int size = 80;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Color según tipo de ítem
        Color itemColor;
        if (itemName.toLowerCase().contains("poción") || itemName.toLowerCase().contains("pocion")) {
            itemColor = new Color(135, 206, 250); // Azul para pociones
        } else if (itemName.toLowerCase().contains("revivir")) {
            itemColor = new Color(255, 215, 0);   // Dorado para revivir
        } else {
            itemColor = new Color(144, 238, 144); // Verde para otros
        }
        // Dibujar frasco
        g2d.setColor(itemColor);
        g2d.fillRoundRect(15, 10, 50, 60, 15, 15);
        // Borde
        g2d.setColor(itemColor.darker());
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(15, 10, 50, 60, 15, 15);
        // Cuello del frasco
        g2d.setColor(itemColor);
        g2d.fillRect(30, 5, 20, 10);
        g2d.setColor(itemColor.darker());
        g2d.drawRect(30, 5, 20, 10);
        // Tapa
        g2d.setColor(new Color(100, 50, 50));
        g2d.fillRoundRect(27, 0, 26, 8, 5, 5);
        g2d.setColor(new Color(80, 40, 40));
        g2d.drawRoundRect(27, 0, 26, 8, 5, 5);
        // Contenido (líquido)
        g2d.setColor(itemColor.brighter());
        g2d.fillRoundRect(20, 40, 40, 25, 10, 10);
        g2d.dispose();
        return new ImageIcon(image);
    }

    // Alternar selección de ítem
    private void toggleItemSelection(String itemName, JButton button) {
        ArrayList<String> currentItems = isPlayer1Turn ? player1Items : player2Items;
        if (currentItems.contains(itemName)) {
            currentItems.remove(itemName);
            button.setBackground(new Color(70, 70, 80));
        } else {
            if (currentItems.size() < MAX_ITEMS) {
                currentItems.add(itemName);
                button.setBackground(SELECTED_COLOR);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Máximo de " + MAX_ITEMS + " ítems alcanzado.",
                        "Límite alcanzado", JOptionPane.WARNING_MESSAGE);
            }
        }
        updateSelectedItemsLabel();
        updateConfirmButtonState();
    }

    // Actualizar etiqueta de ítems seleccionados
    private void updateSelectedItemsLabel() {
        ArrayList<String> currentItems = isPlayer1Turn ? player1Items : player2Items;
        selectedItemsLabel.setText("Ítems seleccionados: " + currentItems.size() + "/" + MAX_ITEMS);
    }

    // Actualizar estado del botón confirmar
    private void updateConfirmButtonState() {
        // Habilitar confirmar solo si el jugador 2 ya seleccionó
        confirmButton.setEnabled(!isPlayer1Turn || !player2Items.isEmpty());
    }

    // Cambiar de jugador
    public void switchPlayer() {
        isPlayer1Turn = !isPlayer1Turn;
        if (isPlayer1Turn) {
            playerTurnLabel.setText("Turno de " + player1Name + " - Selecciona hasta 3 ítems");
            playerTurnLabel.setForeground(PLAYER1_COLOR);
            switchPlayerButton.setText("Cambiar a " + player2Name);
        } else {
            playerTurnLabel.setText("Turno de " + player2Name + " - Selecciona hasta 3 ítems");
            playerTurnLabel.setForeground(PLAYER2_COLOR);
            switchPlayerButton.setText("Cambiar a " + player1Name);
        }
        // Actualizar la interfaz para mostrar los ítems seleccionados por el jugador actual
        updateItemSelectionDisplay();
        updateSelectedItemsLabel();
    }

    // Actualizar la visualización de los ítems seleccionados
    private void updateItemSelectionDisplay() {
        for (Component comp : itemsGridPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                String itemName = ((JLabel) button.getComponent(1)).getText();

                ArrayList<String> currentItems = isPlayer1Turn ? player1Items : player2Items;
                if (currentItems.contains(itemName)) {
                    button.setBackground(SELECTED_COLOR);
                } else {
                    button.setBackground(new Color(70, 70, 80));
                }
            }
        }
    }

    // Manejar cancelación
    public void handleCancel() {
        int option = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas cancelar la selección?",
                "Confirmar cancelación", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION && gui != null) {
            resetPanel();
            gui.cardLayout.show(gui.contentPanel, "MOVIMIENTOS");
        }
    }

    // Confirmar selección
    public void confirmSelection() {
        StringBuilder summary = new StringBuilder("<html><h2>Ítems seleccionados</h2>");
        summary.append("<h3 style='color:#3498db'>" + player1Name + "</h3>");
        if (player1Items.isEmpty()) {
            summary.append("<p>No se seleccionaron ítems</p>");
        } else {
            summary.append("<ul>");
            for (String item : player1Items) {
                summary.append("<li>" + item + "</li>");
            }
            summary.append("</ul>");
        }

        summary.append("<h3 style='color:#e74c3c'>" + player2Name + "</h3>");
        if (player2Items.isEmpty()) {
            summary.append("<p>No se seleccionaron ítems</p>");
        } else {
            summary.append("<ul>");
            for (String item : player2Items) {
                summary.append("<li>" + item + "</li>");
            }
            summary.append("</ul>");
        }
        summary.append("</html>");
        JOptionPane.showMessageDialog(this, summary.toString(), "Selección completada", JOptionPane.INFORMATION_MESSAGE);
        // Preparar para la batalla
        gui.prepareForBattle();
        gui.cardLayout.show(gui.contentPanel, "BATALLA");

    }

    // Reiniciar panel
    public void resetPanel() {
        player1Items.clear();
        player2Items.clear();
        isPlayer1Turn = true;

        playerTurnLabel.setText("Turno de " + player1Name + " - Selecciona hasta 3 ítems");
        playerTurnLabel.setForeground(PLAYER1_COLOR);
        switchPlayerButton.setText("Cambiar a " + player2Name);

        updateSelectedItemsLabel();
        updateItemSelectionDisplay();
        confirmButton.setEnabled(false);
    }

    // Inicializar con la GUI principal
    public void initialize(POOBkemonGUI gui) {
        this.gui = gui;

        if (gui != null && gui.getTrainer1() != null && gui.getTrainer2() != null) {
            player1Name = gui.getTrainer1().getNombre();
            player2Name = gui.getTrainer2().getNombre();
            playerTurnLabel.setText("Turno de " + player1Name + " - Selecciona hasta 3 ítems");
            switchPlayerButton.setText("Cambiar a " + player2Name);
        }

        loadItems();
        resetPanel();
    }

    // Crear botón con estilo
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setPreferredSize(new Dimension(150, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(color.brighter());
                    button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });

        return button;
    }

    // Fondo personalizado
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Gradiente de fondo
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(44, 62, 80),
                0, height, new Color(52, 73, 94));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }
    
    // Getters
    public JButton getConfirmButton() {
        return confirmButton;
    }
    
    public JButton getCancelButton() {
        return cancelButton;
    }
    
    public JButton getSwitchTrainerButton() {
        return switchPlayerButton;
    }
    
    public ArrayList<String> getPlayerItems() {
        return player1Items;
    }
    
    public ArrayList<String> getOpponentItems() {
        return player2Items;
    }
}
