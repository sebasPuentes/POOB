package presentation;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import domain.Pokemon;
import domain.PoobkemonException;

import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class ListPokemonAvailable extends JPanel{
    private String backgroundImage = "emerald";
    private ArrayList<String> pokemonsChosenFight;
    private ArrayList<JButton> buttons;
    private JPanel panelScroll;
    private JButton doneButton;
    private Color color;
    private POOBkemonGUI po;
    private JLabel texto;
    private JButton come;
    private int MAX_CHANGED = 1;

    public ListPokemonAvailable(POOBkemonGUI newPo){
        po = newPo;
        color = new Color(0, 0, 255);
        come = new JButton("Back");
        doneButton = new JButton ("Change");
        prepareActions();
    }
    public void inicializate(ArrayList<String> pokemons, Color color){
        pokemonsChosenFight = pokemons;
        setColor(color);
        prepareElements();
       
    }
    private void prepareElements(){
        
        po.styleButton(come);
        buttons = new ArrayList<>();

        setLayout(new BorderLayout());
        setOpaque(false);


        JPanel upPanel = new JPanel(new BorderLayout());
        upPanel.setOpaque(false); 
        texto = new JLabel("Player");
        texto.setOpaque(true);
        texto.setBackground(new Color(51, 50, 50));
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

        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);

        panelScroll = new JPanel(new GridLayout(4,4,1,1)) { //GridBagLayout   DEBERIA SER CALCULADO FILAS Y COLUMNAS   de dominio
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JScrollPane scrollPane = new JScrollPane(panelScroll);
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

        centro.add(scrollContainer, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        createButtons();
    }

    private void prepareActions(){
        doneButton.addActionListener(e -> {
            if (sizeChoosen() < 1){
                return;
            }
            if (sizeChoosen() > MAX_CHANGED){
                JOptionPane.showMessageDialog(this, "Solo puedes escoger uno para cambiar " + MAX_CHANGED + "pokemon", 
                "Límite excedido", JOptionPane.WARNING_MESSAGE);

                return;
            }
            if (sizeChoosen() == 1){
                changeImage();
                //pokemons
            }
        System.out.println("se ha comfirmado un pokemon a cambiar ");
        reset();
        po.cardLayout.show(po.panelContenedor,"battle");
        });
    }

    public int sizeChoosen(){
        return pokemonsChosenFight.size();
    }

    private void changeImage() {
        System.out.println(pokemonsChosenFight.size());
        String pokemonName = pokemonsChosenFight.get(0);
        System.out.println(pokemonsChosenFight);
        try{po.domain.actionCambiar(pokemonName);}
        catch(PoobkemonException e){System.out.println(e.getMessage());}
        System.out.println(pokemonName);

        po.panelBattle.setFirstPokemon(String.valueOf(po.domain.getCurrentPokemonPokedexIndex()));
        po.panelBattle.actualizarCreateStatsPanelAfterMove();
        po.panelBattle.actualizarListaMovements();
        po.panelBattle.removeMovement();
        po.panelBattle.prepareMovementButtons();
    }

    private void createButtons(){
        System.err.println(pokemonsChosenFight.toString());
        for (String pokemonSelected : pokemonsChosenFight){
            Pokemon po1 = po.pokemones.get(pokemonSelected);
            String nombre = po1.getName();
            String ruta = po1.getPokedexIndex().toString() + ".png";
            JButton button = createImageButton(nombre,ruta);
            buttons.add(button);
            button.addActionListener(e -> selectionPokemons(button));
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

    private void selectionPokemons(JButton button){
        if (pokemonsChosenFight.contains(button.getToolTipText()) ) {//&& pokemonsChosenFight.size()==1
            button.setBackground(null);
            button.setOpaque(false);
            pokemonsChosenFight.remove(button.getToolTipText());
        }
        else{
            button.setBackground(Color.GREEN);
            button.setOpaque(true);
            pokemonsChosenFight.add(button.getToolTipText());
        }
        System.out.println(pokemonsChosenFight.toString());
    }

    public JButton getBackButton(){
        return come;
    }

    public JButton getDoneButton(){
        return doneButton;
    }
    public void reset(){
        System.out.println("resetea todo del cambio de pokemon");
        pokemonsChosenFight.clear();
        for (JButton button : buttons){
            button.setBackground(null);
            button.setOpaque(false);
            button.revalidate();
            button.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ backgroundImage+".JPG"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

}