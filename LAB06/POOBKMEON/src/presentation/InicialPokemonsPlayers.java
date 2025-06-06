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

public class InicialPokemonsPlayers extends JPanel {
    private  String backgroundImage = "emerald";
    private POOBkemonGUI po;
    private JButton come;
    private JButton doneButton; 
    private ModePlayerVSPlayer gameMode;

    private SelectionInicialPokemons selection1;
    private SelectionInicialPokemons selection2;


    public InicialPokemonsPlayers(POOBkemonGUI pooBkemonGUI, ModePlayerVSPlayer father){
        gameMode = father;
        po = pooBkemonGUI;
        prepareElements();
        prepareActions();
    }
    

    private void prepareElements(){
        setLayout(new BorderLayout());
        setOpaque(false);
        JPanel temp = new JPanel(new GridLayout(1,2));
        temp.setOpaque(false);
        selection1 = new SelectionInicialPokemons(po);
        selection2 = new SelectionInicialPokemons(po);

        temp.add(selection1);
        temp.add(selection2);

        add(temp, BorderLayout.CENTER);

        JPanel down = new JPanel(new BorderLayout());
        down.setOpaque(false);
        doneButton = new JButton ("Done!");
        come = new JButton("Back..");
        //doneButton.setVisible(false);
        po.styleButton(doneButton);
        po.styleButton(come);
        JPanel booton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        booton.setOpaque(false);
        booton.add(come);
        booton.add(doneButton);
        down.add(booton,BorderLayout.SOUTH);
        add(down, BorderLayout.SOUTH);
    }

    private void prepareActions(){
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (selection1.sizeChosenPokemon() < 1 || selection2.sizeChosenPokemon() < 1){
                        JOptionPane.showMessageDialog(InicialPokemonsPlayers.this, "Debes escoger " + selection1.MAX_CHANGED + "pokemon para iniciar la batalla", 
                        "Límite excedido", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (selection1.sizeChosenPokemon() > selection1.MAX_CHANGED || selection2.sizeChosenPokemon() > selection2.MAX_CHANGED){
                        JOptionPane.showMessageDialog(InicialPokemonsPlayers.this, "Solo puedes escoger uno para cambiar " + selection1.MAX_CHANGED + "pokemon", 
                        "Límite excedido", JOptionPane.WARNING_MESSAGE);

                        return;
                    }
                    
                    gameMode.inicializateBattle(selection1.getColor(),selection2.getColor(), selection1.getPokemonChoosed(), selection2.getPokemonChoosed());
                    
                   gameMode.changePanel("Battle");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(InicialPokemonsPlayers.this, ex.getMessage());
                }
            }
        });

        come.addActionListener(e -> {
            reset();
            gameMode.changePanel("Movimientos");
        });
    }

    public JButton getButtonBack(){
        return come;
    }

    public JButton getNextBJButton(){
        return doneButton;
    }

    public void inicializate(Color color1, ArrayList<String> pok1, Color color2, ArrayList<String> pok2){
        selection1.inicializate(pok1, color1);
        selection2.inicializate(pok2, color2);

    }
    

    public void reset(){ 
        System.out.println("resetea todo de la seleccion movimientos dos players");
        selection1.reset();
        selection2.reset();
        repaint();
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ backgroundImage+".JPG"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
