package src.presentation;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Panel for selecting and customizing trainers in Pokémon Emerald style
 * @author Julian Lopez, David Puentes
 * @version 1.4 (2025-05-08)
 */
public class TrainersPanel extends JPanel {

    // Main components
    private JLabel titleLabel;
    private JPanel contentPanel;
    private JPanel controlPanel;
    private Image backgroundImage;

    // Trainer selection components
    private JPanel trainerCardPanel;
    private JPanel trainerInfoPanel;
    private JButton prevTrainerButton;
    private JButton nextTrainerButton;
    private JLabel trainerImageLabel;
    private JTextField trainerNameField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;

    // Region selection
    private JComboBox<String> regionComboBox;
    private String[] regionOptions = {"Kanto", "Johto", "Hoenn", "Sinnoh", "Unova", "Kalos", "Alola", "Galar", "Paldea"};

    // Button components
    private JButton confirmButton;
    private JButton backButton;

    // Two-player support
    public boolean isSelectingPlayer1 = true;
    private String player1Name = "";
    private boolean player1IsMale = true;
    private String player1Region = "Hoenn";
    private int player1SpriteIndex = 0;

    private String player2Name = "";
    private boolean player2IsMale = true;
    private String player2Region = "Hoenn";
    private int player2SpriteIndex = 0;

    // Current selection variables
    private List<String> trainerSprites;
    private int currentTrainerIndex = 0;
    private boolean isMaleSelected = true;

    // Colors inspired by Pokémon Emerald
    private final Color EMERALD_GREEN = new Color(88, 200, 136);
    private final Color EMERALD_DARK_GREEN = new Color(56, 158, 105);
    private final Color EMERALD_LIGHT = new Color(184, 248, 216);
    private final Color EMERALD_BLUE = new Color(104, 176, 176);
    private final Color EMERALD_TEXT = new Color(40, 88, 72);
    /**
     * Constructor for the trainer selection panel
     */
    public TrainersPanel() {
        loadBackgroundImage();
        loadTrainerSprites();
        prepareElements();
        prepareActions();
    }

    /**
     * Loads the background image from various possible paths
     */
    private void loadBackgroundImage() {
        String[] possiblePaths = {
                "resources/escenas/seleccionPersonajes.png",
                "./resources/escenas/seleccionPersonajes.png",
                "resources/escenas/trainer_bg.png",
                "./resources/escenas/trainer_bg.png"
        };

        for (String path : possiblePaths) {
            try {
                File file = new File(path);
                if (file.exists()) {
                    backgroundImage = ImageIO.read(file);
                    return;
                }
            } catch (IOException e) {
                System.out.println("Failed to load background from: " + path);
            }
        }

        // If no image was found, create a default one
        createDefaultBackgroundImage();
    }

    /**
     * Creates a default background image
     */
    private void createDefaultBackgroundImage() {
        int width = 800;
        int height = 600;
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) backgroundImage.getGraphics();

        // Gradient background
        GradientPaint gradient = new GradientPaint(0, 0, new Color(34, 139, 34),
                width, height, new Color(0, 100, 0));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);

        // Grid pattern
        g2d.setColor(new Color(255, 255, 255, 30));
        for (int i = 0; i < width; i += 40) g2d.drawLine(i, 0, i, height);
        for (int i = 0; i < height; i += 40) g2d.drawLine(0, i, width, i);

        // Pokéball decorations
        for (int i = 0; i < 10; i++) {
            int x = (int)(Math.random() * width);
            int y = (int)(Math.random() * height);
            int size = 30 + (int)(Math.random() * 50);

            g2d.setColor(new Color(255, 255, 255, 40));
            g2d.fillOval(x, y, size, size);
            g2d.setColor(new Color(200, 0, 0, 40));
            g2d.fillArc(x, y, size, size, 0, 180);
            g2d.setColor(new Color(255, 255, 255, 40));
            g2d.fillOval(x + size/4, y + size/4, size/2, size/2);
        }

        g2d.dispose();
    }

    /**
     * Loads trainer sprites from resources
     */
    private void loadTrainerSprites() {
        trainerSprites = new ArrayList<>();
        String[] possiblePaths = {
                "resources/entrenadores",
        };

        File spritesDir = null;
        for (String path : possiblePaths) {
            File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {
                spritesDir = dir;
                break;
            }
        }

        if (spritesDir == null) {
            System.out.println("Trainer sprites directory not found. Using defaults.");
            trainerSprites.add("Male_Trainer");
            trainerSprites.add("Female_Trainer");
            return;
        }

        // Load PNG and GIF files
        File[] spriteFiles = spritesDir.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".gif"));

        if (spriteFiles != null && spriteFiles.length > 0) {
            for (File file : spriteFiles) {
                String spriteName = file.getName().split("\\.")[0];
                trainerSprites.add(spriteName);
            }
        } else {
            trainerSprites.add("Male_Trainer");
            trainerSprites.add("Female_Trainer");
        }
    }

    /**
     * Prepares UI elements
     */
    private void prepareElements() {
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        titleLabel = new JLabel("ELIGE ENTRENADOR - JUGADOR 1");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Main content panel
        contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Trainer card panel (left)
        trainerCardPanel = createStyledPanel();
        trainerCardPanel.setLayout(new BorderLayout());
        trainerCardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EMERALD_DARK_GREEN, 3, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Trainer image panel
        JPanel trainerImagePanel = new JPanel(new BorderLayout(15, 0));
        trainerImagePanel.setOpaque(false);

        prevTrainerButton = createStyledButton("<", EMERALD_DARK_GREEN);
        nextTrainerButton = createStyledButton(">", EMERALD_DARK_GREEN);

        trainerImageLabel = new JLabel("Loading sprites...", SwingConstants.CENTER);
        trainerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        trainerImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        trainerImageLabel.setPreferredSize(new Dimension(300, 300));
        trainerImageLabel.setMinimumSize(new Dimension(300, 300));
        trainerImageLabel.setBorder(BorderFactory.createLineBorder(EMERALD_BLUE, 2));
        trainerImageLabel.setBackground(new Color(255, 255, 255, 100));// Fully transparent background
        trainerImageLabel.setOpaque(false);

        trainerImagePanel.add(prevTrainerButton, BorderLayout.WEST);
        trainerImagePanel.add(trainerImageLabel, BorderLayout.CENTER);
        trainerImagePanel.add(nextTrainerButton, BorderLayout.EAST);

        trainerCardPanel.add(trainerImagePanel, BorderLayout.CENTER);

        // Trainer info panel (right)
        trainerInfoPanel = createStyledPanel();
        trainerInfoPanel.setLayout(new BoxLayout(trainerInfoPanel, BoxLayout.Y_AXIS));
        trainerInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EMERALD_DARK_GREEN, 3, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Player indicator
        JLabel playerLabel = new JLabel("JUGADOR 1");
        playerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerLabel.setForeground(new Color(0, 100, 200)); // Blue for player 1
        playerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Trainer name field
        JLabel nameLabel = new JLabel("NOMBRE:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(EMERALD_TEXT);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        trainerNameField = new JTextField(15);
        trainerNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        trainerNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        trainerNameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Gender selection
        JLabel genderLabel = new JLabel("GÉNERO:");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 16));
        genderLabel.setForeground(EMERALD_TEXT);
        genderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        maleRadioButton = new JRadioButton("Masculino");
        maleRadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
        maleRadioButton.setForeground(EMERALD_TEXT);
        maleRadioButton.setOpaque(false);
        maleRadioButton.setSelected(true);

        femaleRadioButton = new JRadioButton("Femenino");
        femaleRadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
        femaleRadioButton.setForeground(EMERALD_TEXT);
        femaleRadioButton.setOpaque(false);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        genderPanel.setOpaque(false);
        genderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);

        // Region selection
        JLabel regionLabel = new JLabel("REGIÓN:");
        regionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        regionLabel.setForeground(EMERALD_TEXT);
        regionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        regionComboBox = new JComboBox<>(regionOptions);
        regionComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        regionComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        regionComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        regionComboBox.setSelectedIndex(2); // Default to Hoenn

        // Custom renderer for regions
        regionComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JLabel) {
                    if (isSelected) {
                        setBackground(EMERALD_GREEN);
                        setForeground(Color.WHITE);
                    } else {
                        setBackground(Color.WHITE);
                        setForeground(EMERALD_TEXT);
                    }
                    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                }
                return c;
            }
        });

        // Add components to info panel
        trainerInfoPanel.add(playerLabel);
        trainerInfoPanel.add(Box.createVerticalStrut(15));
        trainerInfoPanel.add(nameLabel);
        trainerInfoPanel.add(Box.createVerticalStrut(5));
        trainerInfoPanel.add(trainerNameField);
        trainerInfoPanel.add(Box.createVerticalStrut(20));
        trainerInfoPanel.add(genderLabel);
        trainerInfoPanel.add(Box.createVerticalStrut(5));
        trainerInfoPanel.add(genderPanel);
        trainerInfoPanel.add(Box.createVerticalStrut(20));
        trainerInfoPanel.add(regionLabel);
        trainerInfoPanel.add(Box.createVerticalStrut(5));
        trainerInfoPanel.add(regionComboBox);
        trainerInfoPanel.add(Box.createVerticalStrut(20));

        // Create split pane
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                trainerCardPanel,
                trainerInfoPanel);
        splitPane.setOpaque(false);
        splitPane.setDividerLocation(250);
        splitPane.setEnabled(false);

        contentPanel.add(splitPane, BorderLayout.CENTER);

        // Control panel
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        controlPanel.setOpaque(false);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        confirmButton = createStyledButton("Confirmar", EMERALD_DARK_GREEN);
        confirmButton.setPreferredSize(new Dimension(150, 40));
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));

        backButton = createStyledButton("Volver", new Color(178, 34, 34));
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));

        controlPanel.add(confirmButton);
        controlPanel.add(backButton);

        // Assemble main layout
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Update the trainer display
        updateTrainerDisplay();
    }

    /**
     * Creates a styled panel with emerald theme
     */
    private JPanel createStyledPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, EMERALD_LIGHT,
                        0, getHeight(), new Color(255, 255, 255));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Creates a styled button with emerald theme
     */
    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                // Gradient background
                if (isEnabled()) {
                    GradientPaint gradient = new GradientPaint(
                            0, 0, baseColor.brighter(),
                            0, height, baseColor.darker());
                    g2d.setPaint(gradient);
                } else {
                    g2d.setColor(Color.GRAY);
                }
                g2d.fillRoundRect(0, 0, width, height, 15, 15);

                // Border
                g2d.setColor(isEnabled() ? baseColor.darker() : Color.DARK_GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, width-1, height-1, 15, 15);

                // Text
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

        // Add hover effect
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
     * Sets up action listeners
     */
    private void prepareActions() {
        prevTrainerButton.addActionListener(e -> {
            currentTrainerIndex = (currentTrainerIndex - 1 + trainerSprites.size()) % trainerSprites.size();
            updateTrainerDisplay();
        });

        nextTrainerButton.addActionListener(e -> {
            currentTrainerIndex = (currentTrainerIndex + 1) % trainerSprites.size();
            updateTrainerDisplay();
        });

        maleRadioButton.addActionListener(e -> isMaleSelected = true);
        femaleRadioButton.addActionListener(e -> isMaleSelected = false);
        confirmButton.addActionListener(e -> confirmTrainerSelection());

        // Enter key to confirm
        trainerNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    confirmTrainerSelection();
                }
            }
        });
    }

    /**
     * Updates trainer sprite display
     */
    private void updateTrainerDisplay() {
        try {
            if (trainerSprites.isEmpty()) {
                trainerImageLabel.setText("No sprites available");
                return;
            }

            // Get the current sprite name
            String currentSprite = trainerSprites.get(currentTrainerIndex);

            // Look for the GIF file directly in resources/entrenadores
            File gifFile = new File("resources/entrenadores/" + currentSprite + ".gif");

            if (gifFile.exists() && gifFile.isFile()) {

                Icon gifIcon = new ImageIcon(gifFile.getAbsolutePath());
                trainerImageLabel.setIcon(gifIcon);
                trainerImageLabel.setText("");

            }


        } catch (Exception e) {
            System.out.println("Error displaying trainer: " + e.getMessage());
            e.printStackTrace();
            trainerImageLabel.setIcon(null);
            trainerImageLabel.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Switch from player 1 to player 2
     */
    private void switchToPlayer2() {
        // Save player 1 data
        player1Name = trainerNameField.getText().trim();
        player1IsMale = isMaleSelected;
        player1Region = (String) regionComboBox.getSelectedItem();
        player1SpriteIndex = currentTrainerIndex;

        // Update UI for player 2
        isSelectingPlayer1 = false;
        titleLabel.setText("ELIGE ENTRENADOR - JUGADOR 2");

        // Update player label
        for (Component c : trainerInfoPanel.getComponents()) {
            if (c instanceof JLabel && "JUGADOR 1".equals(((JLabel)c).getText())) {
                ((JLabel)c).setText("JUGADOR 2");
                ((JLabel)c).setForeground(new Color(200, 50, 50)); // Red for player 2
                break;
            }
        }

        // Reset fields for new input
        trainerNameField.setText("");
        maleRadioButton.setSelected(true);
        isMaleSelected = true;
        regionComboBox.setSelectedIndex(2);
        currentTrainerIndex = 0;
        updateTrainerDisplay();
    }

    /**
     * Confirms trainer selection
     */
    private void confirmTrainerSelection() {
        String trainerName = trainerNameField.getText().trim();

        // First validation - stop if name is empty
        if (trainerName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, introduce un nombre para tu entrenador.",
                    "Nombre requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (isSelectingPlayer1) {
            // Switch to player 2 selection (Player 1 is valid)
            player1Name = trainerNameField.getText().trim();
            player1IsMale = isMaleSelected;
            player1Region = (String) regionComboBox.getSelectedItem();
            player1SpriteIndex = currentTrainerIndex;

            // Switch to Player 2 selection
            switchToPlayer2();
        } else {
            // Save player 2 data (final validation)
            player2Name = trainerNameField.getText().trim();
            player2IsMale = isMaleSelected;
            player2Region = (String) regionComboBox.getSelectedItem();
            player2SpriteIndex = currentTrainerIndex;

            // Both players have been selected, show confirmation
            JOptionPane.showMessageDialog(this,
                    "¡Entrenadores registrados con éxito!\n\n" +
                            "Jugador 1: " + player1Name + " (" + (player1IsMale ? "Masculino" : "Femenino") + ", " + player1Region + ")\n" +
                            "Jugador 2: " + player2Name + " (" + (player2IsMale ? "Masculino" : "Femenino") + ", " + player2Region + ")\n\n" +
                            "¡Comienza tu aventura Pokémon Elige Tus Pokemones!",
                    "Entrenadores registrados", JOptionPane.INFORMATION_MESSAGE);

            // The confirmButton's ActionListener in POOBkemonGUI will handle navigation
        }
    }

    /**
     * Resets the panel to initial state
     */
    public void resetSelectionPanel() {
        // Reset trainer data
        player1Name = "";
        player2Name = "";
        player1IsMale = true;
        player2IsMale = true;
        player1Region = "Hoenn";
        player2Region = "Hoenn";
        player1SpriteIndex = 0;
        player2SpriteIndex = 0;

        // Reset UI to player 1
        isSelectingPlayer1 = true;
        titleLabel.setText("ELIGE ENTRENADOR - JUGADOR 1");
        trainerNameField.setText("");
        maleRadioButton.setSelected(true);
        isMaleSelected = true;
        regionComboBox.setSelectedIndex(2);
        currentTrainerIndex = 0;

        // Reset player label
        for (Component c : trainerInfoPanel.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel)c;
                if ("JUGADOR 1".equals(label.getText()) || "JUGADOR 2".equals(label.getText())) {
                    label.setText("JUGADOR 1");
                    label.setForeground(new Color(0, 100, 200));
                    break;
                }
            }
        }

        updateTrainerDisplay();
        revalidate();
        repaint();
    }

    /**
     * Draw the background image
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            super.paintComponent(g);
        }
    }

    /**
     * Gets the confirm button
     */
    public JButton getConfirmButton() {
        return confirmButton;
    }

    /**
     * Gets the back button
     */
    public JButton getBackButton() {
        return backButton;
    }

    /**
     * Gets player 1's name
     */
    public String getPlayer1Name() {
        return player1Name;
    }

    /**
     * Gets player 2's name
     */
    public String getPlayer2Name() {
        return player2Name;
    }
}