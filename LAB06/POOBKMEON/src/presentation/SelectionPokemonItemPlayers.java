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

public class SelectionPokemonItemPlayers extends JPanel{
    private  String backgroundImage = "emerald";
    private POOBkemonGUI po;
    private JButton come;
    private JButton doneButton; 
    private ModePlayerVSPlayer gameMode;

    private Selection selection1;
    private Selection selection2;


    public SelectionPokemonItemPlayers(POOBkemonGUI pooBkemonGUI, ModePlayerVSPlayer father){
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
        selection1 = new Selection(po, new Color(1,2,4,100));
        selection2 = new Selection(po, new Color(30,100,30,100));
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
                    if (selection1.getPokemonChoosen().size()<1|| selection1.getItemsChoosen().size()<1 ||selection2.getPokemonChoosen().size()<1|| selection2.getItemsChoosen().size()<1 ){
                        JOptionPane.showMessageDialog(SelectionPokemonItemPlayers.this, 
                            "Selecciona al menos 1 Pokémon para la batalla y dos pociones! ",
                            "Incompleta", JOptionPane.WARNING_MESSAGE);
                    return;
                    }

                   if(selection1.getPokemonChoosen().size()> selection1.MAX_POKEMONS|| selection1.getItemsChoosen().size()> selection1.MAX_POTIONS 
                   ||selection2.getPokemonChoosen().size()> selection1.MAX_POKEMONS|| selection2.getItemsChoosen().size()> selection1.MAX_POTIONS ){
                        JOptionPane.showMessageDialog(SelectionPokemonItemPlayers.this,
                            "Solo puedes seleccionar máximo " + selection1.MAX_POKEMONS + " pokemones y " + selection1.MAX_POTIONS + " pociones",
                            "Límite excedido", JOptionPane.WARNING_MESSAGE);
                        return;
                   }
                   gameMode.firstItems = selection1.getItemsChoosen();
                   gameMode.secondItems = selection2.getItemsChoosen();
                   gameMode.movements.inicializate(selection1.getColor(), selection1.getPokemonChoosen(), selection2.getColor(), selection2.getPokemonChoosen());
                   
                   gameMode.changePanel("Movimientos");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SelectionPokemonItemPlayers.this, ex.getMessage());
                }
            }
        });

        come.addActionListener(e -> {
            gameMode.changePanel("Datos");
        });
    }

    public JButton getButtonBack(){
        return come;
    }

    public JButton getNextBJButton(){
        return doneButton;
    }

    public void inicializate(Color color1, Color color2){
        selection1.setColor(color1);
        selection2.setColor(color2);
    }
    

    public void reset(){ 
        System.out.println("resetea todo de la seleccion players");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ backgroundImage+".JPG"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
