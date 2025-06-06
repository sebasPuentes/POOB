package src.presentation;

import src.domain.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Panel para configurar batallas entre máquinas IA
 */
public class MachineVsMachinePanel extends JPanel {
    // Referencias y componentes principales
    private POOBkemonGUI mainGUI;
    private JButton backButton;
    private JButton startBattleButton;
    // Paneles para la selección de entrenadores
    private JPanel trainer1Panel;
    private JPanel trainer2Panel;
    // Variables de control de selección
    private String selectedTrainer1Type = null;
    private String selectedTrainer2Type = null;
    // Imagen de fondo y elementos visuales
    private Image backgroundImage;
    private JPanel hoveredPanel = null;
    // Tipos de entrenadores disponibles
    private final String[] TRAINER_TYPES = {"Experto", "Defensivo", "Ofensivo", "Changing"};
    private final Color SELECTED_COLOR_1 = new Color(30, 144, 255, 180); // Azul para primera máquina
    private final Color SELECTED_COLOR_2 = new Color(220, 20, 60, 180);  // Rojo para segunda máquina
    private final Color HOVER_COLOR = new Color(255, 215, 0, 180);       // Dorado para hover
    
    /**
     * Constructor del panel Machine vs Machine
     */
    public MachineVsMachinePanel(POOBkemonGUI gui) {
        this.mainGUI = gui;
        loadBackgroundImage();
        initializeComponents();
        addListeners();
    }
    /**
     * Carga la imagen de fondo para el panel
     */
    private void loadBackgroundImage() {
        String imagePath = "resources/escenas/estadio.png";
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                backgroundImage = ImageIO.read(file);
            } else {
                createDefaultBackgroundImage();
            }
        } catch (Exception e) {
            createDefaultBackgroundImage();
        }
    }
    
    /**
     * Crea una imagen de fondo por defecto si no se encuentra el archivo
     */
    private void createDefaultBackgroundImage() {
        int width = 800;
        int height = 600;
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) backgroundImage.getGraphics();
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 80, 100), width, height, new Color(0, 30, 60));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
    }
    
    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Panel de título y botón de regreso
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Panel principal con los selectores de entrenador
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Crear paneles para cada entrenador
        trainer1Panel = createTrainerSelectionPanel("Entrenador Máquina 1", 1);
        trainer2Panel = createTrainerSelectionPanel("Entrenador Máquina 2", 2);
        
        mainPanel.add(trainer1Panel);
        mainPanel.add(trainer2Panel);
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de control inferior con botón para iniciar batalla
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel superior con título y botón de regreso
     * @return Panel configurado
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Título
        JLabel titleLabel = new JLabel("BATALLA MÁQUINA VS MÁQUINA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        
        // Botón de regreso
        backButton = createStyledButton("Volver", new Color(178, 34, 34));
        backButton.setPreferredSize(new Dimension(100, 40));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    /**
     * Crea un panel para seleccionar un tipo de entrenador
     * @param title Título del panel
     * @param panelNum Número del panel (1 para el primer entrenador, 2 para el segundo)
     * @return Panel configurado
     */
    private JPanel createTrainerSelectionPanel(String title, int panelNum) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Título del panel
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        // Panel de tarjetas para tipos de entrenador
        JPanel cardsPanel = new JPanel(new GridLayout(1, TRAINER_TYPES.length, 15, 0));
        cardsPanel.setOpaque(false);
        
        // Crear tarjetas para cada tipo de entrenador
        for (String trainerType : TRAINER_TYPES) {
            JPanel card = createTrainerTypeCard(trainerType, panelNum);
            cardsPanel.add(card);
        }
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(cardsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel inferior con botón para iniciar batalla
     * @return Panel configurado
     */
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setOpaque(false);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        startBattleButton = createStyledButton("¡INICIAR BATALLA!", new Color(34, 139, 34));
        startBattleButton.setFont(new Font("Arial", Font.BOLD, 18));
        startBattleButton.setPreferredSize(new Dimension(250, 50));
        startBattleButton.setEnabled(false);
        
        controlPanel.add(startBattleButton);
        
        return controlPanel;
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
     * Crea una tarjeta para representar un tipo de entrenador
     * @param trainerType Tipo de entrenador
     * @param panelNum Número del panel (1 o 2) para diferenciar selecciones
     * @return Panel de tarjeta configurado
     */
    private JPanel createTrainerTypeCard(String trainerType, int panelNum) {
        // Panel con pintura personalizada para mostrar selección
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Determinar si esta tarjeta está seleccionada
                boolean isSelected = (panelNum == 1) ? 
                    trainerType.equals(selectedTrainer1Type) : 
                    trainerType.equals(selectedTrainer2Type);
                
                // Color de borde según el panel al que pertenece
                Color borderColor = (panelNum == 1) ? SELECTED_COLOR_1 : SELECTED_COLOR_2;
                
                // Dibujar borde de selección si está seleccionado
                if (isSelected) {
                    g.setColor(borderColor);
                    ((Graphics2D)g).setStroke(new BasicStroke(4));
                    g.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
                }
                
                // Efecto hover
                if (this == hoveredPanel) {
                    g.setColor(HOVER_COLOR);
                    ((Graphics2D)g).setStroke(new BasicStroke(2));
                    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                }
            }
        };
        
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(120, 120));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Cargar imagen del entrenador
        try {
            String imagePath = "resources/entrenadores/" + trainerType.toLowerCase() + ".png";
            File imageFile = new File(imagePath);
            
            if (imageFile.exists()) {
                // Cargar y escalar la imagen
                ImageIcon trainerIcon = new ImageIcon(imageFile.getAbsolutePath());
                Image image = trainerIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
                card.add(imageLabel, BorderLayout.CENTER);
            } else {
                // Si no hay imagen, mostrar un círculo de color
                JPanel colorIcon = createColorIcon(trainerType);
                card.add(colorIcon, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            JPanel colorIcon = createColorIcon(trainerType);
            card.add(colorIcon, BorderLayout.CENTER);
        }
        
        // Etiqueta con el nombre del tipo de entrenador
        JLabel nameLabel = new JLabel(trainerType, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        nameLabel.setForeground(Color.WHITE);
        card.add(nameLabel, BorderLayout.SOUTH);
        
        // Eventos del mouse para la selección
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Actualizar la selección según el panel
                if (panelNum == 1) {
                    selectedTrainer1Type = trainerType;
                    repaintTrainerPanelCards(trainer1Panel);
                } else {
                    selectedTrainer2Type = trainerType;
                    repaintTrainerPanelCards(trainer2Panel);
                }
                
                // Habilitar el botón de inicio si ambos tipos están seleccionados
                updateStartButtonState();
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
    
    /**
     * Crea un icono de color para representar el tipo de entrenador cuando no hay imagen
     * @param trainerType Tipo de entrenador
     * @return Panel con un círculo de color según el tipo
     */
    private JPanel createColorIcon(String trainerType) {
        JPanel colorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Color según el tipo de entrenador
                Color typeColor = getColorForTrainerType(trainerType);
                g.setColor(typeColor);
                g.fillOval(0, 0, getWidth(), getHeight());
                g.setColor(Color.BLACK);
                g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        colorPanel.setOpaque(false);
        colorPanel.setPreferredSize(new Dimension(80, 80));
        return colorPanel;
    }
    /**
     * Asigna un color según el tipo de entrenador
     * @param type Tipo de entrenador
     * @return Color correspondiente
     */
    private Color getColorForTrainerType(String type) {
        switch(type) {
            case "Experto": return new Color(255, 215, 0); // Dorado
            case "Defensivo": return new Color(0, 100, 200); // Azul
            case "Ofensivo": return new Color(200, 0, 0); // Rojo
            case "Changing": return new Color(0, 150, 0); // Verde
            default: return Color.GRAY;
        }
    }
    /**
     * Repinta todas las tarjetas en un panel de entrenador
     * @param panel Panel que contiene las tarjetas a repintar
     */
    private void repaintTrainerPanelCards(JPanel panel) {
        // Buscar el panel de tarjetas dentro del panel del entrenador
        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel innerPanel = (JPanel) component;
                if (innerPanel.getLayout() instanceof GridLayout) {
                    // Repintar todas las tarjetas
                    for (Component card : innerPanel.getComponents()) {
                        card.repaint();
                    }
                    return;
                }
            }
        }
    }
    
    /**
     * Actualiza el estado del botón de inicio de batalla
     */
    private void updateStartButtonState() {
        startBattleButton.setEnabled(selectedTrainer1Type != null && selectedTrainer2Type != null);
    }
    
    /**
     * Configura los listeners de eventos para los componentes
     */
    private void addListeners() {
        startBattleButton.addActionListener(e -> startMachineVsMachineBattle());
    }
    
    /**
     * Inicia la batalla entre máquinas con los tipos seleccionados
     */
    private void startMachineVsMachineBattle() {
        if (mainGUI == null || selectedTrainer1Type == null || selectedTrainer2Type == null) {
            JOptionPane.showMessageDialog(this,
                "Error: Selecciona ambos entrenadores",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String trainer1Name = selectedTrainer1Type;
            String trainer2Name = selectedTrainer2Type;
            if (trainer1Name != null && trainer2Name != null) {
                mainGUI.player1 = trainer1Name;
                mainGUI.player2 = trainer2Name;
                mainGUI.prepareForBattleMachines();
            } else {
                throw new Exception("Error al crear los entrenadores");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al iniciar la batalla: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    /**
     * Renderiza el fondo del panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
    /**
     * Devuelve el botón de regreso
     * @return Botón de regreso
     */
    public JButton getBackButton() {
        return backButton;
    }
    
    /**
     * Devuelve el botón para iniciar la batalla
     * @return Botón de inicio de batalla
     */
    public JButton getStartBattleButton() {
        return startBattleButton;
    }
    
    /**
     * Obtiene el tipo de entrenador seleccionado para la máquina 1
     * @return Nombre del tipo de entrenador seleccionado
     */
    public String getSelectedTrainer1Type() {
        return selectedTrainer1Type;
    }
    
    /**
     * Obtiene el tipo de entrenador seleccionado para la máquina 2
     * @return Nombre del tipo de entrenador seleccionado
     */
    public String getSelectedTrainer2Type() {
        return selectedTrainer2Type;
    }
}
