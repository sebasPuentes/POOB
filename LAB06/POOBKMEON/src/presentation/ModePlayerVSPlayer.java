package presentation;
import javax.swing.*;

import domain.PoobkemonException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ModePlayerVSPlayer extends JPanel {
    private String backgroundImage = "fondoAnimado2";
    private JButton btnRegresarNormal;
    private JButton continuar;
    private CardLayout cardLayout;
    protected DatosTwoPlayers datos;
    protected SelectionPokemonItemPlayers inventory;
    protected SelectionMovementsTwoPlayers movements;
    protected InicialPokemonsPlayers inicialPoks;
    protected BattlePanel batalla;

    protected String firstName;
    protected String secondName;
    protected ArrayList<String> firstItems;
    protected ArrayList<String> secondItems;
    protected HashMap<String, ArrayList<String>> firstPokemonMovs;
    protected HashMap<String, ArrayList<String>> secondPokemonMovs;

    private POOBkemonGUI po;


    public ModePlayerVSPlayer(POOBkemonGUI newPo){
        po = newPo;
        prepareElements();
        prepareActions();
    }
    
    private void prepareElements(){
        cardLayout = new CardLayout();
        btnRegresarNormal = new JButton("Back");
        continuar = new JButton("Continuar");
        setLayout(cardLayout);

        

        datos = new DatosTwoPlayers(po,this);
        add(datos, "Datos");

        inventory = new SelectionPokemonItemPlayers(po,this);
        add(inventory, "Inventory");

        movements = new SelectionMovementsTwoPlayers(po, this);
        add(movements,"Movimientos");
        
        inicialPoks = new InicialPokemonsPlayers(po, this);
        add(inicialPoks, "Iniciales");

        batalla = new BattlePanel(po);
        add(batalla, "Battle");
    }

    private void prepareActions(){

    }
    
    public JButton getBtnRegresarNormal(){
        return btnRegresarNormal;
    }
    
    public JButton getButtonContinuar(){
        return continuar;
    }
    public void changePanel(String namePanel){
        cardLayout.show(this,namePanel);
    }

    public void inicializateBattle(Color color1, Color color2, String pok1, String pok2){
        po.createTrainer(firstName,color1);
        po.createTrainer(secondName,color2);
        try{
            po.addPokemonsToTrainer(firstName,firstPokemonMovs);
            po.addPokemonsToTrainer(secondName,secondPokemonMovs);

            po.addItemsToTrainer(firstName,firstItems);
            po.addItemsToTrainer(secondName,secondItems);

            po.domain.inicialTrainerPokemon(firstName,pok1);
            po.domain.inicialTrainerPokemon(secondName,pok2);
            }
        catch(PoobkemonException i){
            JOptionPane.showMessageDialog(null, i.getMessage());
            return;
        }
        po.domain.inicializateBattle(firstName,secondName);
        batalla.inicializate(po.domain.inicialTrainerMovements(firstName));
        po.selectedPokemon.changeImagePvsP();
    }
    


}