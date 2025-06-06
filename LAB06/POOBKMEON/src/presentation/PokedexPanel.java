package presentation;

import domain.Pokemon;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PokedexPanel extends JPanel {
    private static final String POKEMONES = "src/resources/";
    private static final String TYPES = "resources/types/";
    private static final Color POKEDEX_GREEN = new Color(32, 160, 32);
    private static final Color POKEDEX_YELLOW = new Color(255, 235, 59);
    private static final Color POKEDEX_SCREEN_BG = new Color(220, 220, 200);

    private JPanel pokedexPanel;
    private POOBkemonGUI po;
    private JLabel imagenLabel;
    private JLabel imagenArriba;
    private JLabel imagenAbajo;
    private JPanel mainScreen;
    private JPanel typesPanel;
    private JPanel pokemonListPanel;
    private JScrollPane listScrollPane;
    private JButton backButton;
    private JButton prevButton;
    private JButton nextButton;


    private ArrayList<String[]> pokemones;
    private final int[] currentIndex = {0};
    private ArrayList<JButton> pokemonButtons = new ArrayList<>();

    public PokedexPanel(POOBkemonGUI newPo) {
        po = newPo;
        setLayout(null);
        backButton = new JButton("BACK") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(197, 36, 126));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        pokedexPanel = new JPanel();
        pokedexPanel.setOpaque(false);
        pokedexPanel.setLayout(null);
        pokedexPanel.setBounds(0, 0, 1000, 500);
        add(pokedexPanel);

        setupPokedexComponents();
        initSampleData();

        addPokedexStartupEffect();
    }

    private void setupPokedexComponents() {
        mainScreen = new JPanel();
        mainScreen.setBackground(POKEDEX_SCREEN_BG);
        mainScreen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        mainScreen.setLayout(null);
        mainScreen.setBounds(255, 80, 230, 320);
        pokedexPanel.add(mainScreen);

        imagenLabel = new JLabel();
        imagenLabel.setBounds(40, 85, 150, 150);
        mainScreen.add(imagenLabel);

        imagenArriba = new JLabel();
        imagenArriba.setBounds(90, 10, 50, 50);
        mainScreen.add(imagenArriba);

        imagenAbajo = new JLabel();
        imagenAbajo.setBounds(90, 250, 50, 50);
        mainScreen.add(imagenAbajo);

        typesPanel = new JPanel();
        typesPanel.setLayout(new BoxLayout(typesPanel, BoxLayout.Y_AXIS));
        typesPanel.setBounds(90, 120, 130, 280);
        typesPanel.setOpaque(false);
        pokedexPanel.add(typesPanel);

        pokemonListPanel = new JPanel();
        pokemonListPanel.setLayout(new BoxLayout(pokemonListPanel, BoxLayout.Y_AXIS));
        pokemonListPanel.setBackground(POKEDEX_YELLOW);

        listScrollPane = new JScrollPane(pokemonListPanel);
        listScrollPane.setBounds(615, 80, 260, 320);
        listScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        pokedexPanel.add(listScrollPane);

        backButton.setBounds(75, 385, 100, 40);
        backButton.setFont(cargarFuentePixel(18));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        pokedexPanel.add(backButton);

        prevButton = new JButton("");
        prevButton.setBounds(345, 45, 40, 40);
        prevButton.setFont(cargarFuentePixel(14));
        prevButton.setFocusPainted(false);
        prevButton.addActionListener(e -> moverSeleccion(-1));
        pokedexPanel.add(prevButton);

        nextButton = new JButton("");
        nextButton.setBounds(345, 400, 40, 40);
        nextButton.setFont(cargarFuentePixel(14));
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(e -> moverSeleccion(1));
        pokedexPanel.add(nextButton);
    }

    private void moverSeleccion(int delta) {
        if (pokemones == null || pokemones.isEmpty()) return;

        int newIndex = currentIndex[0] + delta;
        if (newIndex >= 0 && newIndex < pokemones.size()) {
            currentIndex[0] = newIndex;
            actualizarVista();
            scrollToSelectedPokemon();
        }
    }

    private void actualizarVista() {
        if (pokemones == null || pokemones.isEmpty()) return;

        String[] pokemon = pokemones.get(currentIndex[0]);

        String id = pokemon[0];
        String name = pokemon[1];
        String typeOne = pokemon[2];
        String typeTwo = pokemon[3];

        String imagePath = POKEMONES + id + ".PNG";
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imagenLabel.setIcon(new ImageIcon(img));

        actualizarTiposPaneles(typeOne, typeTwo);
        updatePositionIndicators();

        for (int i = 0; i < pokemonButtons.size(); i++) {
            if (i == currentIndex[0]) {
                pokemonButtons.get(i).setBackground(new Color(255, 200, 0));
                pokemonButtons.get(i).setForeground(Color.BLACK);
                pokemonButtons.get(i).setFont(cargarFuentePixel(14).deriveFont(Font.BOLD));
            } else {
                pokemonButtons.get(i).setBackground(POKEDEX_YELLOW);
                pokemonButtons.get(i).setForeground(Color.BLACK);
                pokemonButtons.get(i).setFont(cargarFuentePixel(14));
            }
        }
    }

    private void actualizarTiposPaneles(String typeOne, String typeTwo) {
        typesPanel.removeAll();
        typesPanel.revalidate();
        typesPanel.repaint();
        if (typeOne != null && !typeOne.isEmpty()) {
            JPanel tipo1Panel = createTypePanel(typeOne);
            typesPanel.add(tipo1Panel);
            typesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        if (typeTwo != null && !typeTwo.isEmpty() && !typeTwo.equals(" ")) {
            JPanel tipo2Panel = createTypePanel(typeTwo);
            typesPanel.add(tipo2Panel);
        }
    }

    private JPanel createTypePanel(String typeName) {
        Color typeColor;
        switch (typeName.toLowerCase()) {
            case "fire": typeColor = new Color(255, 128, 0); break;
            case "water": typeColor = new Color(51, 153, 255); break;
            case "grass": typeColor = new Color(51, 204, 51); break;
            case "electric": typeColor = new Color(255, 204, 0); break;
            case "flying": typeColor = new Color(153, 153, 255); break;
            case "poison": typeColor = new Color(153, 51, 204); break;
            default: typeColor = new Color(153, 153, 153);
        }

        JPanel tipoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(typeColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 15, 15);
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        tipoPanel.setOpaque(false);
        tipoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tipoPanel.setPreferredSize(new Dimension(120, 40));
        tipoPanel.setMaximumSize(new Dimension(120, 40));

        JLabel tipoLabel = new JLabel(typeName.toUpperCase());
        tipoLabel.setFont(cargarFuentePixel(16));
        tipoLabel.setForeground(Color.WHITE);
        tipoPanel.add(tipoLabel);

        return tipoPanel;
    }

    private void updatePositionIndicators() {
        if (currentIndex[0] > 0) {
            String prevId = pokemones.get(currentIndex[0] - 1)[0];
            String prevPath = POKEMONES + prevId + ".PNG";
            File prevFile = new File(prevPath);

            if (prevFile.exists()) {
                ImageIcon prevIcon = new ImageIcon(prevPath);
                Image prevImg = prevIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                imagenArriba.setIcon(new ImageIcon(prevImg));
                imagenArriba.setVisible(true);
            } else {
                imagenArriba.setVisible(false);
            }
        } else {
            imagenArriba.setVisible(false);
        }

        if (currentIndex[0] < pokemones.size() - 1) {
            String nextId = pokemones.get(currentIndex[0] + 1)[0];
            String nextPath = POKEMONES + nextId + ".PNG";
            File nextFile = new File(nextPath);

            if (nextFile.exists()) {
                ImageIcon nextIcon = new ImageIcon(nextPath);
                Image nextImg = nextIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                imagenAbajo.setIcon(new ImageIcon(nextImg));
                imagenAbajo.setVisible(true);
            } else {
                imagenAbajo.setVisible(false);
            }
        } else {
            imagenAbajo.setVisible(false);
        }
    }

    private Font cargarFuentePixel(float tamaño) {
        return new Font("Monospaced", Font.BOLD, (int)tamaño);
    }

    public JButton getButton() {
        return backButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(220,30,30));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void cargarPokemones(ArrayList<String[]> nuevaLista) {
        pokemones = nuevaLista;
        currentIndex[0] = 0;
        actualizarListaPokemones();
        actualizarVista();
    }

    private void initSampleData() {
        if (pokemones == null) {
            pokemones = new ArrayList<>();
        }
        pokemones.add(new String[]{"3", "Venusaur", "Grass", "Poison", "525", "80", "82", "83", "100", "100", "80"});
        pokemones.add(new String[]{"6", "Charizard", "Fire", "Flying", "534", "78", "84", "78", "109", "85", "100"});
        pokemones.add(new String[]{"9", "Blastoise", "Water", "", "530", "79", "83", "100", "85", "105", "78"});
        pokemones.add(new String[]{"26", "Raichu", "Electric", "", "485", "60", "90", "55", "90", "80", "110"});
        pokemones.add(new String[]{"34", "Nidoking", "Poison", "Ground", "505", "81", "102", "77", "85", "75", "85"});
        pokemones.add(new String[]{"36", "Clefable", "Fairy", "", "483", "95", "70", "73", "95", "90", "60"});
        pokemones.add(new String[]{"59", "Arcanine", "Fire", "", "555", "90", "110", "80", "100", "80", "95"});
        pokemones.add(new String[]{"68", "Machamp", "Fighting", "", "505", "90", "130", "80", "65", "85", "55"});
        pokemones.add(new String[]{"80", "Slowbro", "Water", "Psychic", "490", "95", "75", "110", "100", "80", "30"});
        pokemones.add(new String[]{"94", "Gengar", "Ghost", "Poison", "500", "60", "65", "60", "130", "75", "110"});
        pokemones.add(new String[]{"112", "Rhydon", "Ground", "Rock", "485", "105", "130", "120", "45", "45", "40"});
        pokemones.add(new String[]{"130", "Gyarados", "Water", "Flying", "540", "95", "125", "79", "60", "100", "81"});
        pokemones.add(new String[]{"143", "Snorlax", "Normal", "", "540", "160", "110", "65", "65", "110", "30"});
        pokemones.add(new String[]{"146", "Moltres", "Fire", "Flying", "580", "90", "100", "90", "125", "85", "90"});
        pokemones.add(new String[]{"149", "Dragonite", "Dragon", "Flying", "600", "91", "134", "95", "100", "100", "80"});
        pokemones.add(new String[]{"157", "Typhlosion", "Fire", "", "534", "78", "84", "78", "109", "85", "100"});
        pokemones.add(new String[]{"160", "Feraligatr", "Water", "", "530", "85", "105", "100", "79", "83", "78"});
        pokemones.add(new String[]{"176", "Togetic", "Fairy", "Flying", "405", "55", "40", "85", "80", "105", "40"});
        pokemones.add(new String[]{"217", "Ursaring", "Normal", "", "500", "90", "130", "75", "75", "75", "55"});
        pokemones.add(new String[]{"225", "Delibird", "Ice", "Flying", "330", "45", "55", "45", "65", "45", "75"});
        pokemones.add(new String[]{"232", "Donphan", "Ground", "", "500", "90", "120", "120", "60", "60", "50"});
        pokemones.add(new String[]{"248", "Tyranitar", "Rock", "Dark", "600", "100", "134", "110", "95", "100", "61"});
        pokemones.add(new String[]{"257", "Blaziken", "Fire", "Fighting", "530", "80", "120", "70", "110", "70", "80"});
        pokemones.add(new String[]{"282", "Gardevoir", "Psychic", "Fairy", "518", "68", "65", "65", "125", "115", "80"});
        pokemones.add(new String[]{"289", "Slaking", "Normal", "", "670", "150", "160", "100", "95", "65", "100"});
        pokemones.add(new String[]{"376", "Metagross", "Steel", "Psychic", "600", "80", "135", "130", "95", "90", "70"});
        actualizarListaPokemones();
        currentIndex[0] = 0;
        actualizarVista();
    }

    private void actualizarListaPokemones() {
        if (pokemones == null || pokemones.isEmpty()) return;
        pokemonListPanel.removeAll();
        pokemonButtons.clear();
        for (int i = 0; i < pokemones.size(); i++) {
            String[] pokemon = pokemones.get(i);
            final int index = i;
            JButton pokemonButton = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (index == currentIndex[0]) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(new Color(255, 255, 255, 80));
                        g2d.fillRect(0, 0, getWidth(), getHeight());
                    }
                }
            };
            pokemonButton.setText("N°." + String.format("%03d", Integer.parseInt(pokemon[0])) + " " + pokemon[1]);
            pokemonButton.setHorizontalAlignment(SwingConstants.LEFT);
            pokemonButton.setBackground(POKEDEX_YELLOW);
            pokemonButton.setBorderPainted(false);
            pokemonButton.setFocusPainted(false);
            pokemonButton.setFont(cargarFuentePixel(14));
            pokemonButton.setPreferredSize(new Dimension(250, 30));
            pokemonButton.setMaximumSize(new Dimension(250, 30));


            if (i == currentIndex[0]) {
                pokemonButton.setBackground(new Color(255, 200, 0));
                pokemonButton.setFont(cargarFuentePixel(14).deriveFont(Font.BOLD));
            }

            pokemonButton.addActionListener(e -> {
                currentIndex[0] = index;
                actualizarVista();
            });

            pokemonButtons.add(pokemonButton);
            pokemonListPanel.add(pokemonButton);
        }

        pokemonListPanel.revalidate();
        pokemonListPanel.repaint();
    }

    private void scrollToSelectedPokemon() {
        if (currentIndex[0] >= 0 && currentIndex[0] < pokemonButtons.size()) {
            JButton button = pokemonButtons.get(currentIndex[0]);
            Rectangle rect = button.getBounds();
            rect.y = rect.y - 50;
            listScrollPane.getViewport().scrollRectToVisible(rect);
        }
    }

    private void addPokedexStartupEffect() {
        mainScreen.setVisible(true);
        listScrollPane.setVisible(true);
        typesPanel.setVisible(true);
    }
}