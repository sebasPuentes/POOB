package presentation;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import domain.Pokemon;
import domain.PoobkemonException;
import domain.Trainer;

import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.Map.Entry;

public class PanelSelectedPokemon extends JPanel{
    private String backgroundImage = "emerald";
    private ArrayList<String> pokemonsChosenFight;
    private ArrayList<String> pokemonInicialChosen;
    private ArrayList<JButton> buttons;
    private JPanel panelScroll;
    private JButton doneButton;

    private JPanel upPanel;
    private JScrollPane scrollPane;
    private JPanel centro;

    private Color color;
    private POOBkemonGUI po;
    private JLabel texto;
    private JButton come;
    private static final int MAX_CHANGED = 1;


    public PanelSelectedPokemon(POOBkemonGUI newPo){
        po = newPo;
        
        prepareElements();
        prepareActions();
    }
    private void prepareElements(){
        color = new Color(0, 0, 255);
        come = new JButton("BACK");
        doneButton = new JButton ("Selecciona tu pokemon inicial");
        pokemonsChosenFight= new ArrayList<>();
        pokemonInicialChosen = new ArrayList<>();
        setLayout(new BorderLayout());
        setOpaque(false);
        buttons = new ArrayList<>();
    }

    public void inicializate(ArrayList<String> pokemons, Color color){
        pokemonsChosenFight = pokemons;
        setColor(color);
        prepareElementsToStart();
    }

    private void prepareElementsToStart(){
        po.styleButton(come);
        

        upPanel = new JPanel(new BorderLayout());
        upPanel.setOpaque(false); 
        texto = new JLabel("Player");
        texto.setOpaque(true);
        texto.setBackground(color);
        texto.setHorizontalAlignment(JLabel.CENTER);
        texto.setForeground(Color.BLUE);
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
        
        po.styleButton(doneButton);
        down.add(new JLabel(" "),BorderLayout.NORTH);
        down.add(new JLabel(" "),BorderLayout.CENTER);
        JPanel booton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        booton.setOpaque(false);
        booton.add(come);
        booton.add(doneButton);
        down.add(booton,BorderLayout.SOUTH);
        add(down, BorderLayout.SOUTH);

        centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        //centro.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        panelScroll = new JPanel(new GridLayout(4,4,1,1)) { //GridBagLayout   DEBERIA SER CALCULADO FILAS Y COLUMNAS   de dominio
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        scrollPane = new JScrollPane(panelScroll);
        scrollPane.setBackground(Color.BLUE);
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

        panelScroll.setOpaque(false);
        panelScroll.setBackground(Color.BLUE);

        centro.add(scrollContainer, BorderLayout.CENTER); //tal vez por eso se demora en clock?
        add(centro, BorderLayout.CENTER);

        createButtons();
    }

    private void prepareActions(){
        doneButton.addActionListener(e ->{
            if (sizeChosenPokemon() < 1){
                JOptionPane.showMessageDialog(this, "Debes escoger " + MAX_CHANGED + "pokemon para iniciar la batalla", 
                "Límite excedido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (sizeChosenPokemon() > MAX_CHANGED){
                JOptionPane.showMessageDialog(this, "Solo puedes escoger uno para cambiar " + MAX_CHANGED + "pokemon", 
                "Límite excedido", JOptionPane.WARNING_MESSAGE);

                return;
            }
            
        
        po.createTrainer(po.playerVsMachinePanel.trainerEscogido,color);
        try{
            po.addPokemonsToTrainer(po.playerVsMachinePanel.trainerEscogido,po.playerVsMachinePanel.pokemonsWithMovs);
            System.out.println(po.playerVsMachinePanel.itemsEscogidos + "QUE ESTA PASANDO ACA?????????????");
            System.out.println(po.playerVsMachinePanel.trainerEscogido);
            System.out.println(po.playerVsMachinePanel.pokemonsWithMovs + "ESTO ESTA VACIO?");
            po.addItemsToTrainer(po.playerVsMachinePanel.trainerEscogido,po.playerVsMachinePanel.itemsEscogidos);
            po.domain.inicialTrainerPokemon(po.playerVsMachinePanel.trainerEscogido, pokemonInicialChosen.get(0));
            po.domain.inicialTrainerPokemon(po.playerVsMachinePanel.trainerEscogidoMachine,"professor");
        }
        catch(PoobkemonException i){
            JOptionPane.showMessageDialog(null, i.getMessage());
            return;
        }
        po.domain.inicializateBattle(po.playerVsMachinePanel.trainerEscogido,po.playerVsMachinePanel.trainerEscogidoMachine );
        po.panelBattle.inicializate(po.domain.inicialTrainerMovements(po.playerVsMachinePanel.trainerEscogido));
        changeImage();
        po.cardLayout.show(po.panelContenedor,"battle");
        reset();
        });
    }

    public int sizeChoosen(){
        return pokemonsChosenFight.size();
    }
    public int sizeChosenPokemon(){
        return pokemonInicialChosen.size();
    }

    public void changeImage(){
        int primero = po.domain.getCurrentPokemonPokedexIndex();
        int segundo = po.domain.getOponentPokemonPokedexIndex();
        po.panelBattle.setFirstPokemon(Integer.toString(primero));
        po.panelBattle.setSecondPokemon(Integer.toString(segundo));
        System.out.println(primero + " " + segundo);
    }

    public void changeImageMvsM(){
        int primero = po.domain.getCurrentPokemonPokedexIndex();
        int segundo = po.domain.getOponentPokemonPokedexIndex();
        po.panelMvsM.setFirstPokemon(Integer.toString(primero));
        po.panelMvsM.setSecondPokemon(Integer.toString(segundo));
        System.out.println(primero + " " + segundo);
    }

    public void changeImagePvsP(){
        int primero = po.domain.getCurrentPokemonPokedexIndex();
        int segundo = po.domain.getOponentPokemonPokedexIndex();
        po.panelBattle.setFirstPokemon(Integer.toString(primero));
        po.panelBattle.setSecondPokemon(Integer.toString(segundo));
        System.out.println(primero + " " + segundo);
    }

    private void createButtons(){
        for (String pokemonSelected : pokemonsChosenFight){
            Pokemon po1 = po.pokemones.get(pokemonSelected);
            String nombre = po1.getName();
            String ruta = po1.getPokedexIndex().toString() + ".png";
            JButton button = createImageButton(nombre,ruta);
            buttons.add(button);
            button.addActionListener(e -> 
            selectionPokemons(button));
            panelScroll.add(button); 
        }
    }



    private JButton createImageButton(String name,String imagePath) {
        int x=1, y=1;
        int width=50, height =50;
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
        }catch (Exception e) {
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
        
        return button;
    }
    private void setColor(Color newCorlor){
        color = newCorlor;
    }
    public Color getColor(){
        return color;
    }

    private void selectionPokemons(JButton button){
        
        if (pokemonInicialChosen.contains(button.getToolTipText()) ) {
            button.setBackground(null);
            button.setOpaque(false);
            pokemonInicialChosen.remove(button.getToolTipText());
        }
        else{
            button.setBackground(Color.GREEN);
            button.setOpaque(true);
            pokemonInicialChosen.add(button.getToolTipText());
        }
        System.out.println("Inicial: "+pokemonInicialChosen.toString());
    }

    public JButton getBackButton(){
        return come;
    }
    public String getPokemonChoosed(){
        return pokemonInicialChosen.get(0);
    }

    public JButton getDoneButton(){
        return doneButton;
    }
    public boolean isOneOption(){
        return pokemonInicialChosen.size() ==1;
    }

    public void reset(){
        System.out.println("reset panel de seleccion inicial");
        pokemonsChosenFight.clear();
        pokemonsChosenFight = new ArrayList<>();
        pokemonInicialChosen = new ArrayList<>();
        for (JButton button : buttons){
        button.setBackground(null);
        button.setOpaque(false);
        }
        buttons = new  ArrayList<>();
        removeAll();
        
        revalidate(); 
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ backgroundImage+".JPG"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

}