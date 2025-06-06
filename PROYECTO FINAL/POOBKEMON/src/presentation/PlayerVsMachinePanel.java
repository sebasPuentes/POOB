package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Panel para configurar partidas entre un jugador humano y una máquina IA.
 * Permite al jugador ingresar su nombre y seleccionar el tipo de máquina contra la que quiere combatir.
 */
public class PlayerVsMachinePanel extends JPanel {
    
    private POOBkemonGUI mainGUI;
    private JTextField playerNameField;
    private ButtonGroup machineTypeGroup;
    private String selectedMachineType = null;
    private JButton startButton;
    private JButton backButton;
    
    // Panel para la selección visual de máquinas
    private JPanel machineCardsPanel;
    private JPanel selectedCard = null;
    
    // Constantes
    private final String[] MACHINE_TYPES = {"Experto", "Defensivo", "Ofensivo", "Changing"};
    private final Color[] MACHINE_COLORS = {
        new Color(255, 215, 0),
        new Color(0, 100, 200),
        new Color(200, 0, 0),
        new Color(0, 150, 0)
    };
    
    // Imagen de fondo
    private Image backgroundImage;
    
    /**
     * Constructor del panel de configuración Jugador vs Máquina
     * @param gui Referencia a la GUI principal
     */
    public PlayerVsMachinePanel(POOBkemonGUI gui) {
        this.mainGUI = gui;
        setLayout(new BorderLayout());
        loadBackgroundImage();
        prepareElements();
        prepareActions();
    }
    
    /**
     * Carga la imagen de fondo del panel
     */
    private void loadBackgroundImage() {
        try {
            File file = new File("src/resources/escenas/seleccionMaquinas.png");
            if (file.exists()) {
                backgroundImage = new ImageIcon(file.getAbsolutePath()).getImage();
            } else {
                System.err.println("No se encontró el archivo de imagen de fondo: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
        }
    }
    
    /**
     * Inicializa los elementos del panel
     */
    private void prepareElements() {
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel superior con título y botón de regreso
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        // Título
        JLabel titleLabel = new JLabel("JUGADOR VS MÁQUINA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        backButton = createStyledButton("Volver", new Color(178, 34, 34));
        backButton.setPreferredSize(new Dimension(100, 40));
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.WEST);
        return panel;
    }
    
    /**
     * Crea el panel principal con entrada de nombre y selección de máquina
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        namePanel.setOpaque(false);
        namePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        JLabel nameLabel = new JLabel("Tu nombre:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        playerNameField = new JTextField(20);
        playerNameField.setFont(new Font("Arial", Font.PLAIN, 18));
        playerNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        namePanel.add(nameLabel);
        namePanel.add(playerNameField);
        JLabel machineLabel = new JLabel("Selecciona tu oponente:", SwingConstants.CENTER);
        machineLabel.setFont(new Font("Arial", Font.BOLD, 18));
        machineLabel.setForeground(Color.WHITE);
        machineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        machineCardsPanel = new JPanel(new GridLayout(1, MACHINE_TYPES.length, 20, 0));
        machineCardsPanel.setOpaque(false);
        machineCardsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        machineCardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        machineTypeGroup = new ButtonGroup();
        for (int i = 0; i < MACHINE_TYPES.length; i++) {
            JPanel card = createMachineCard(MACHINE_TYPES[i], MACHINE_COLORS[i]);
            machineCardsPanel.add(card);
        }
        
        // Descripción del modo de juego
        JTextArea descriptionArea = new JTextArea(
            "En este modo de juego, te enfrentarás a un entrenador controlado por la IA.\n" +
            "Cada tipo de máquina tiene una estrategia diferente:\n" +
            "• Experto: Combina las mejores estrategias de ataque\n" +
            "• Defensivo: Prioriza movimientos defensivos y curativos\n" +
            "• Ofensivo: Prioriza Movimientos que incrementen su ataque.\n" +
            "• Changing: Alterna entre diferentes pokemones"
        );
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Arial", Font.ITALIC, 14));
        descriptionArea.setForeground(Color.WHITE);
        descriptionArea.setBackground(new Color(0, 0, 0, 150));
        descriptionArea.setOpaque(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionArea.setMaximumSize(new Dimension(700, 150));
        panel.add(namePanel);
        panel.add(machineLabel);
        panel.add(machineCardsPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(descriptionArea);
        
        return panel;
    }
    
    /**
     * Crea una tarjeta visual para representar un tipo de máquina
     */
    private JPanel createMachineCard(String machineType, Color baseColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(30, 30, 30, 220));
                g.fillRect(0, 0, getWidth(), getHeight());
                if (machineType.equals(selectedMachineType)) {
                    g.setColor(baseColor);
                    ((Graphics2D)g).setStroke(new BasicStroke(4));
                    g.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
                }
            }
        };
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(130, 160));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        try {
            String imagePath = "src/resources/entrenadores/" + machineType.toLowerCase() + ".png";
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageIcon trainerIcon = new ImageIcon(imageFile.getAbsolutePath());
                Image image = trainerIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
                imageLabel.setOpaque(false);
                card.add(imageLabel, BorderLayout.CENTER);
            } else {
                JPanel colorIcon = createColorIcon(machineType, baseColor);
                card.add(colorIcon, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            JPanel colorIcon = createColorIcon(machineType, baseColor);
            card.add(colorIcon, BorderLayout.CENTER);
        }
        JLabel nameLabel = new JLabel(machineType, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setOpaque(false);
        card.add(nameLabel, BorderLayout.SOUTH);
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedMachineType = machineType;
                for (Component comp : machineCardsPanel.getComponents()) {
                    comp.repaint();
                }
                selectedCard = card;
                card.repaint();
                updateStartButtonState();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(baseColor, 2));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            }
        });
        return card;
    }
    
    /**
     * Crea un icono de color para representar un tipo de máquina
     */
    private JPanel createColorIcon(String machineType, Color color) {
        JPanel colorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
                g.setColor(Color.BLACK);
                g.drawOval(10, 10, getWidth() - 21, getHeight() - 21);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                FontMetrics fm = g.getFontMetrics();
                String initial = machineType.substring(0, 1);
                int textWidth = fm.stringWidth(initial);
                int textHeight = fm.getHeight();
                g.drawString(initial, (getWidth() - textWidth) / 2, 
                            (getHeight() - textHeight) / 2 + fm.getAscent());
            }
        };
        colorPanel.setOpaque(false);
        colorPanel.setPreferredSize(new Dimension(90, 90));
        return colorPanel;
    }
    /**
     * Crea el panel inferior con botones
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        startButton = createStyledButton("¡COMENZAR AVENTURA!", new Color(34, 139, 34));
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setPreferredSize(new Dimension(300, 60));
        startButton.setEnabled(false);
        panel.add(startButton);
        return panel;
    }
    
    /**
     * Crea un botón estilizado con colores personalizados
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
     * Configura las acciones de los componentes
     */
    private void prepareActions() {
        startButton.addActionListener(e -> {
            if (validateInputs()) {
                startGame();
            }
        });
        backButton.addActionListener(e -> {
            mainGUI.cardLayout.show(mainGUI.contentPanel, "JUGAR");
        });
    }
    
    /**
     * Actualiza el estado del botón de inicio según las selecciones
     */
    private void updateStartButtonState() {
        boolean nameEntered = !playerNameField.getText().trim().isEmpty();
        boolean machineSelected = selectedMachineType != null;
        startButton.setEnabled(nameEntered && machineSelected);
    }
    
    /**
     * Valida los datos ingresados
     */
    private boolean validateInputs() {
        String playerName = playerNameField.getText().trim();
        
        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingresa tu nombre para continuar.",
                "Nombre requerido", JOptionPane.WARNING_MESSAGE);
            playerNameField.requestFocus();
            return false;
        }
        if (selectedMachineType == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona un tipo de máquina para enfrentarte.",
                "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Inicia el juego con la configuración seleccionada
     */
    private void startGame() {
        String playerName = playerNameField.getText().trim();
        System.out.print("Nombre del jugador: " + playerName + "\n");
        System.out.print("Tipo de máquina seleccionada: " + selectedMachineType + "\n");
        try {
            mainGUI.player1 = playerName;
            mainGUI.player2 = selectedMachineType;
            mainGUI.createTrainer(playerName,Color.RED);
            mainGUI.prepareQuickPvsMBattle(playerName, selectedMachineType);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al configurar la batalla: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Obtiene el nombre del jugador ingresado
     */
    public String getPlayerName() {
        return playerNameField.getText().trim();
    }
    
    /**
     * Obtiene el tipo de máquina seleccionado
     */
    public String getSelectedMachineType() {
        return selectedMachineType;
    }
    
    /**
     * Obtiene el botón de inicio
     */
    public JButton getStartButton() {
        return startButton;
    }
    
    /**
     * Obtiene el botón de volver
     */
    public JButton getBackButton() {
        return backButton;
    }
    
    /**
     * Pinta el componente con la imagen de fondo
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Fondo de respaldo
            GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 80, 100), 
                                                      getWidth(), getHeight(), new Color(0, 30, 60));
            ((Graphics2D)g).setPaint(gradient);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
    /**
     * Restablece el panel a su estado inicial
     */
    public void reset() {
        playerNameField.setText("");
        selectedMachineType = null;
        selectedCard = null;
        startButton.setEnabled(false);
        
        // Repintar tarjetas de máquinas
        for (Component comp : machineCardsPanel.getComponents()) {
            comp.repaint();
        }
    }
}
