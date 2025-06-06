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

public class SelectionMovementsTwoPlayers extends JPanel {
    private  String backgroundImage = "emerald";
    private POOBkemonGUI po;
    private JButton come;
    private JButton doneButton; 
    private ModePlayerVSPlayer gameMode;

    private SelectionMovementsPanel selection1;
    private SelectionMovementsPanel selection2;


    public SelectionMovementsTwoPlayers(POOBkemonGUI pooBkemonGUI, ModePlayerVSPlayer father){
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
        selection1 = new SelectionMovementsPanel(po);
        selection2 = new SelectionMovementsPanel(po);

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
                    if (!selection1.isSelectedMovements() || ! selection1.isSelectedMovements()) {
                        JOptionPane.showMessageDialog(SelectionMovementsTwoPlayers.this,
                    "Tienes que escoger todos los movimientos.",
                    "Movimientos no seleccionados.", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                    gameMode.firstPokemonMovs = selection1.getPokemonMovs();
                    gameMode.secondPokemonMovs = selection2.getPokemonMovs();
                    
                   
                   gameMode.inicialPoks.inicializate(selection1.getColor(), selection1.getPokemonChoosen(), selection2.getColor(), selection2.getPokemonChoosen());
                   gameMode.changePanel("Iniciales");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SelectionMovementsTwoPlayers.this, ex.getMessage());
                }
            }
        });

        come.addActionListener(e -> {
            reset();
            gameMode.changePanel("Inventory");
        });
    }

    public JButton getButtonBack(){
        return come;
    }

    public JButton getNextBJButton(){
        return doneButton;
    }

    public void inicializate(Color color1, ArrayList<String> pok1, Color color2, ArrayList<String> pok2){
        selection1.setColor(color1);
        selection2.setColor(color2);
        selection1.infoSelectedPokemons(pok1);
        selection2.infoSelectedPokemons(pok2);
    }
    

    public void reset(){ 
        System.out.println("resetea todo de la seleccion movimientos dos players");
        selection1.resetPokemonChosen();
        selection2.resetPokemonChosen();
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
