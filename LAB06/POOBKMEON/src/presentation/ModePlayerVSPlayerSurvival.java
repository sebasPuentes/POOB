package presentation;
import domain.POOBkemon;
import domain.PoobkemonException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.*;
public class ModePlayerVSPlayerSurvival extends JPanel {
    protected BattlePanel survivalBatalla;

    protected DatosTwoPlayersSurvival datos;

    private JButton continuar;
    private JButton btnRegresarNormal;
    private CardLayout cardLayout;

    protected String firstName;
    protected String secondName;

    private POOBkemonGUI po;

    public ModePlayerVSPlayerSurvival(POOBkemonGUI newPo){
        po = newPo;
        prepareElements();
    }

    private void prepareElements(){
        cardLayout = new CardLayout();
        btnRegresarNormal = new JButton("Back");
        continuar = new JButton("Continue");
        setLayout(cardLayout);

        datos = new DatosTwoPlayersSurvival(po,this);
        add(datos, "Datos");

        survivalBatalla = new BattlePanel(po);
        add(survivalBatalla, "Battle");
    }

    public void inicializate(String player1Name, Color color1, String player2Name, Color color2){
        po.createTrainer(player1Name, color1);
        po.createTrainer(player2Name, color2);
        try {
            po.domain.generateRandomSelectionPokemon(player1Name);
            po.domain.generateRandomSelectionPokemon(player2Name);
            }catch (Exception i) {
                JOptionPane.showMessageDialog(null, i.getMessage());
                return;
            }
            po.domain.inicializateBattle(player1Name, player2Name);
            survivalBatalla.inicializate(po.domain.inicialTrainerMovements(player1Name));
            po.selectedPokemon.changeImagePvsP();
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
}
