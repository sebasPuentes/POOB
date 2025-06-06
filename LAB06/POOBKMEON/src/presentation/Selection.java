package presentation;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import domain.Item;
import domain.Pokemon;
import domain.PoobkemonException;

import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class Selection extends JPanel{
    private JLabel texto;
    private POOBkemonGUI pooBkemonGUI;
    private Color color;
    
    private JPanel panelScroll;
    private JPanel potionsScollPanel;
    private ArrayList<String> pokemonesChoosen;
    private ArrayList<String> itemsChoosen;
    private ArrayList<JButton> buttons;
    private ArrayList<JButton> potionButtons;
    private String Trainer;
    public static final int MAX_POKEMONS = 6;
    public static final int MAX_POTIONS = 2;

    public Selection(POOBkemonGUI po, Color newColor){
        pooBkemonGUI = po;
        color = newColor;
        prepareElementsPokemons();
        prepareElementsItems();
    }
    
    private void prepareElementsItems(){
        itemsChoosen = new ArrayList<>();
        potionButtons = new ArrayList<>();

        potionsScollPanel = new JPanel(new GridLayout(0, 1, 1, 1)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        potionsScollPanel.setOpaque(false);
        JScrollPane potionsScroll = new JScrollPane(potionsScollPanel);
        potionsScroll.setBackground(color);
        potionsScroll.setOpaque(false);
        potionsScroll.setPreferredSize(new Dimension(150, 300));
        potionsScroll.getViewport().setOpaque(false);
        potionsScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        potionsScroll.getVerticalScrollBar().setUnitIncrement(15);
                
        createPotionButtons();
                
        JPanel left = (JPanel)getComponent(2);
        left.removeAll();
        left.setLayout(new BorderLayout());
                
        JLabel potionsTitle = new JLabel("Items", SwingConstants.CENTER);
        potionsTitle.setOpaque(true);
        potionsTitle.setBackground(color);
        potionsTitle.setForeground(Color.WHITE);
        potionsTitle.setFont(new Font("Arial", Font.BOLD, 14));
                
        left.add(potionsTitle, BorderLayout.NORTH);
        left.add(potionsScroll, BorderLayout.CENTER);
        left.add(new JLabel(" "), BorderLayout.WEST);
        left.add(new JLabel(" "), BorderLayout.EAST);

    }

    private void createPotionButtons() {
        for (Entry<String, Item> entry : pooBkemonGUI.domain.getItems().entrySet()) {
            String nombre = entry.getKey();
            Item item = entry.getValue();
            String ruta = item.getName() + ".png";
            JButton button = createPotionButton(nombre, ruta);
            button.setToolTipText(item.createPokemonForToolTip());
            ToolTipManager.sharedInstance().setInitialDelay(500);
            potionButtons.add(button);
            button.addActionListener(e -> selectPotion(button));
            potionsScollPanel.add(button);
        }
    }

    private JButton createPotionButton(String name, String imagePath) {
        int width=40, height=40;
        JButton button = new JButton(name);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/" + imagePath));
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
           
        }
        
        button.setToolTipText(name);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 50));
        button.setActionCommand(name);


        return button;
    }

    private void selectPotion(JButton button) {
        String itemName = button.getActionCommand();
        if (itemsChoosen.contains(itemName)) {
            button.setBackground(null);
            button.setOpaque(false);
            itemsChoosen.remove(itemName);
        } else {
            button.setBackground(Color.ORANGE);
            button.setOpaque(true);
            itemsChoosen.add(itemName);
        }
        System.out.println("Pociones seleccionadas: " + itemsChoosen);
    }

    private void prepareElementsPokemons(){
        pokemonesChoosen = new ArrayList<>();
        buttons = new ArrayList<>();

        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel upPanel = new JPanel(new BorderLayout());
        upPanel.setOpaque(false); 
        texto = new JLabel("Player");
        texto.setOpaque(true);
        texto.setBackground(color);
        texto.setHorizontalAlignment(JLabel.CENTER);
        texto.setForeground(color);
        upPanel.add(new JLabel(" "),BorderLayout.CENTER);
        upPanel.add(texto, BorderLayout.NORTH);
        add(upPanel,BorderLayout.NORTH);

        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(false);
        right.add(new JLabel(" "),BorderLayout.WEST);
        right.add(new JLabel(" "),BorderLayout.CENTER);
        right.add(new JLabel(" "),BorderLayout.EAST);
        add(right, BorderLayout.EAST);

        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);
        left.add(new JLabel(" "),BorderLayout.WEST);
        ImageIcon imagen = new ImageIcon(getClass().getResource("/resources/pokeball1.png"));


        left.add(new JLabel(imagen),BorderLayout.CENTER);
        left.add(new JLabel(" "),BorderLayout.EAST);
        add(left, BorderLayout.WEST);

        JPanel down = new JPanel(new BorderLayout());
        down.setOpaque(false);
        //doneButton.setVisible(false);
        down.add(new JLabel(" "),BorderLayout.NORTH);
        down.add(new JLabel(" "),BorderLayout.CENTER);
        JPanel booton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        booton.setOpaque(false);
        down.add(booton,BorderLayout.SOUTH);
        add(down, BorderLayout.SOUTH);

        JPanel centro = new JPanel(new BorderLayout());
        JPanel title = new JPanel(new GridLayout(1,1));
        JLabel pokemonsTitle = new JLabel("Choose POOBKEMONS",SwingConstants.CENTER);
        pokemonsTitle.setOpaque(false);
        pokemonsTitle.setBackground(color);
        pokemonsTitle.setForeground(Color.WHITE);
        pokemonsTitle.setFont(new Font("Arial",Font.BOLD,14));
        title.add(pokemonsTitle);
        title.setBackground(Color.GRAY);
        centro.setOpaque(false);
        panelScroll = new JPanel(new GridLayout(8,2,10,10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        //GridBagConstraints gbc = new GridBagConstraints();
        panelScroll.setOpaque(false);
        panelScroll.setBackground(color);
        


        JScrollPane scrollPane = new JScrollPane(panelScroll);
        scrollPane.setBackground(color);
        scrollPane.setOpaque(false);
	    scrollPane.setPreferredSize(new Dimension(300, 400));
	    scrollPane.getViewport().setOpaque(false);
	    scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	    scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        InputMap inputMap = scrollPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = scrollPane.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up");
	    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down");
	    actionMap.put("up", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JScrollBar vertical = scrollPane.getVerticalScrollBar();
	            vertical.setValue(vertical.getValue() - vertical.getUnitIncrement());
	        }
	    });
	    actionMap.put("down", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JScrollBar vertical = scrollPane.getVerticalScrollBar();
	            vertical.setValue(vertical.getValue() + vertical.getUnitIncrement());
	        }
	    });

        JPanel scrollContainer = new JPanel();
	    scrollContainer.setOpaque(false);
	    scrollContainer.setLayout(new BoxLayout(scrollContainer, BoxLayout.Y_AXIS));
	    scrollContainer.add(Box.createVerticalGlue());
	    scrollContainer.add(scrollPane);
	    scrollContainer.add(Box.createVerticalGlue());
        centro.add(title,BorderLayout.NORTH);
	    centro.add(scrollContainer, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);
        createButtons();
    }

    public Color getColor(){
        return color;
    }
    public void setColor(Color newColor){
        color = newColor;
        texto.setBackground(newColor);
        repaint();

    }


    public void createButtons() {
        for (Entry<String, Pokemon> entry : pooBkemonGUI.domain.getPokedex().entrySet()) {
            Pokemon pokemon = entry.getValue();
            JButton button = createImageButton(pokemon);
            buttons.add(button);
            button.addActionListener(e -> 
            selectionPokemons(button)
            );
            panelScroll.add(button);
            }
    }

    private void selectionPokemons(JButton button){
        String pokemonName = button.getActionCommand();
        if (pokemonesChoosen.contains(pokemonName)) {
            button.setBackground(null);
            button.setOpaque(false);
            pokemonesChoosen.remove(pokemonName);
        }
        else{
            button.setBackground(Color.GREEN);
            button.setOpaque(true);
            pokemonesChoosen.add(pokemonName);
        }
        System.out.println(pokemonesChoosen);
    }
    public ArrayList<String> getPokemonChoosen(){
        return pokemonesChoosen;
    }
    public int sizeChoosen(){
        return pokemonesChoosen.size();
    }
    private JButton createImageButton(String name,String imagePath) {
        int x=1, y=1;
        int width=50, height =30;
        Dimension smallSize = new Dimension(50, 30); 
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/" + imagePath));
            
            if (imagePath.toLowerCase().endsWith(".gif")){
                button.setIcon(icon);
                button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            } 
            else {
                Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));
            }
            //button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }catch (Exception e) {
            e.printStackTrace();
            button.setText("No imagen");
        }
        button.setPreferredSize(smallSize);
        button.setMinimumSize(smallSize);
        button.setMaximumSize(smallSize); 
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setToolTipText(name);
        button.setActionCommand(name);
        
        return button;
    }
    private JButton createImageButton(Pokemon pokemon) {
        String name = pokemon.getName();
        String imagePath = pokemon.getPokedexIndex() + ".png";
        //System.out.println(imagePath);
        JButton button = createImageButton(name, imagePath);
        button.setToolTipText(pokemon.createPokemonForToolTip());
        ToolTipManager.sharedInstance().setInitialDelay(500);
        return button;
    }

    
    public ArrayList<String> getItemsChoosen() {
        return itemsChoosen;
    }

    public String getTrainer(){
        return Trainer;
    }
    public void setTrainer(String tra){
        Trainer = tra;
    }

    public void reset(){ 
        System.out.println("resetea todo de la seleccion");
        Trainer = "";
        pokemonesChoosen.clear();
        itemsChoosen.clear();
        for (JButton button : buttons){
            button.setBackground(null);
            button.setOpaque(false);
            button.revalidate();
            button.repaint();
        }
        for (JButton button : potionButtons){
            button.setBackground(null);
            button.setOpaque(false);
            button.revalidate();
            button.repaint();
        }
        panelScroll.revalidate();
        panelScroll.repaint();
    }
}
