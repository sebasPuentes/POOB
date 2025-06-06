package src.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PokedexPanel extends JPanel {
    private JButton backButton;
    private JButton leftButton;
    private JButton rightButton;
    private JLabel pokemonImageLabel;
    private List<String> pokemonImages;
    private ArrayList<JButton> buttons = new ArrayList<>();

    private int currentIndex;

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

    private void setButtonsColor() {
        for (JButton button : buttons) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    private void updatePokemonImage() {
        if (pokemonImages.isEmpty()) {
            pokemonImageLabel.setIcon(null);
            pokemonImageLabel.setText("");
            return;
        }

        String imagePath = "/resources/pokedex/" + pokemonImages.get(currentIndex) + "Front.png";
        URL imageURL = getClass().getResource(imagePath);

        if (imageURL == null) {
            System.err.println("Imagen no encontrada: " + imagePath);
            // Mostrar una imagen de "no disponible" si no se encuentra la imagen
            URL defaultImageURL = getClass().getResource("/resources/pokedex/NoImageAvailable.png");
            if (defaultImageURL != null) {
                pokemonImageLabel.setIcon(new ImageIcon(new ImageIcon(defaultImageURL).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
            } else {
                pokemonImageLabel.setIcon(null);
            }
            pokemonImageLabel.setText("Desconocido");
            return;
        }

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

    private void showPreviousPokemon() {
        if (!pokemonImages.isEmpty()) {
            currentIndex = (currentIndex - 1 + pokemonImages.size()) % pokemonImages.size();
            updatePokemonImage();
        }
    }

    private void showNextPokemon() {
        if (!pokemonImages.isEmpty()) {
            currentIndex = (currentIndex + 1) % pokemonImages.size();
            updatePokemonImage();
        }
    }

    public JButton getBackButton() {
        return backButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaint();
        revalidate();
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/escenas/pokedexFinal.GIF"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}