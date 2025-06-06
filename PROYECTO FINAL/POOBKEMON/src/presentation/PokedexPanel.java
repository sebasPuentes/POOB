package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel that displays Pokémon in a Pokédex-like interface
 */
public class PokedexPanel extends JPanel {

    //==========================================================================
    // 1. DECLARACIÓN DE VARIABLES Y CONSTANTES
    //==========================================================================

    // UI Components
    private JButton backButton;
    private JButton leftButton;
    private JButton rightButton;
    private JLabel pokemonImageLabel;
    private ArrayList<JButton> buttons = new ArrayList<>();

    // Data
    private List<String> pokemonImages;
    private int currentIndex;

    //==========================================================================
    // 2. CONSTRUCTOR E INICIALIZACIÓN
    //==========================================================================

    /**
     * Constructor with list of Pokémon to display
     * @param pokemonImages List of Pokémon image names
     */
    public PokedexPanel(List<String> pokemonImages) {
        this.pokemonImages = pokemonImages;
        this.currentIndex = 0;
        prepareElements();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                updatePokemonImage();
            }

            @Override
            public void componentResized(ComponentEvent e) {
                updatePokemonImage();
            }
        });
    }

    //==========================================================================
    // 3. PREPARACIÓN DE ELEMENTOS DE INTERFAZ
    //==========================================================================

    /**
     * Prepares UI elements
     */
    private void prepareElements() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 4));
        bottomPanel.setOpaque(false);

        backButton = new JButton("Volver");
        bottomPanel.add(backButton);
        buttons.add(backButton);

        leftButton = new JButton("←");
        leftButton.addActionListener(e -> showPreviousPokemon());
        bottomPanel.add(leftButton);
        buttons.add(leftButton);

        rightButton = new JButton("→");
        rightButton.addActionListener(e -> showNextPokemon());
        bottomPanel.add(rightButton);
        buttons.add(rightButton);

        add(bottomPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 30));
        centerPanel.setOpaque(false);

        pokemonImageLabel = new JLabel("", SwingConstants.CENTER);
        pokemonImageLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        pokemonImageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        pokemonImageLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        pokemonImageLabel.setForeground(Color.BLACK);

        centerPanel.add(pokemonImageLabel);
        add(centerPanel, BorderLayout.CENTER);

        setButtonsColor();
    }

    /**
     * Sets cursor style for buttons
     */
    private void setButtonsColor() {
        for (JButton button : buttons) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    //==========================================================================
    // 4. NAVEGACIÓN Y VISUALIZACIÓN DE POKÉMON
    //==========================================================================

    /**
     * Updates the current Pokémon image
     */
    private void updatePokemonImage() {
        if (pokemonImages.isEmpty()) {
            pokemonImageLabel.setIcon(null);
            pokemonImageLabel.setText("");
            return;
        }

        String imagePath = "/resources/pokedex/" + pokemonImages.get(currentIndex) + "Front.png";
        URL imageURL = getClass().getResource(imagePath);


        ImageIcon pokemonImage = new ImageIcon(imageURL);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int width = panelWidth > 0 ? panelWidth / 2 : 200;
        int height = panelHeight > 0 ? panelHeight / 2 : 200;

        Image scaledImage = pokemonImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        pokemonImageLabel.setIcon(new ImageIcon(scaledImage));

        String pokemonName = pokemonImages.get(currentIndex);
        pokemonImageLabel.setText(pokemonName);
    }

    /**
     * Shows the previous Pokémon in the list
     */
    private void showPreviousPokemon() {
        if (!pokemonImages.isEmpty()) {
            currentIndex = (currentIndex - 1 + pokemonImages.size()) % pokemonImages.size();
            updatePokemonImage();
        }
    }

    /**
     * Shows the next Pokémon in the list
     */
    private void showNextPokemon() {
        if (!pokemonImages.isEmpty()) {
            currentIndex = (currentIndex + 1) % pokemonImages.size();
            updatePokemonImage();
        }
    }

    //==========================================================================
    // 5. RENDERIZADO Y ACCESORES
    //==========================================================================

    /**
     * Paints the background image
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaint();
        revalidate();
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/escenas/pokedexFinal.GIF"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Gets the back button
     * @return Back button
     */
    public JButton getBackButton() {
        return backButton;
    }
}