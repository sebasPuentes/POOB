package presentation;

import java.util.ArrayList;

import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

import domain.PoobkemonException;

import javax.swing.*;
import java.awt.*;


public class BattlePanelMvsM extends JPanel {
    private String backgroundImage = "battle";
    private String firstPokemon = "6";
    private String secondPokemon = "6";
    private POOBkemonGUI po;
    private JPanel movesPanel;
    private JPanel opciones;
    private JPanel battleOptionsPanel;
    private JButton backToOptionsBattle;

    private JPanel playerStatsPanel;
    private JPanel opponentStatsPanel;

    private CustomHealthBar playerHealthBar;
    private CustomHealthBar opponentHealthBar;
    private JPanel panelInfo; 

    private JLabel playerHealthLabel;
    private JLabel opponentHealthLabel;
    private JLabel playerNameLabel;
    private JLabel opponentNameLabel;

    private int xFirst, xSecond;
    private int yFirst, ySecond;
    private Color actualColor;
    private int actualIndex;


    private ArrayList<String> movMachine;

    private Font pokemonFont;
    protected CardLayout cardLayout;

    private JButton ticTac;



    public BattlePanelMvsM(POOBkemonGUI newPo) {
        po = newPo;
        actualColor = new Color(100,100,100,100);

        backToOptionsBattle = new JButton("Back");
        po.styleButton(backToOptionsBattle);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        try {
            pokemonFont = new Font("Monospaced", Font.BOLD, 14);
        } catch (Exception e) {
            pokemonFont = new Font("SansSerif", Font.BOLD, 14);
            e.printStackTrace();
        }
        
        prepareElements();
        prepareActions();
    }

    public void inicializate(ArrayList<String> movs){

        movMachine = movs;

        String nameCurrent = po.domain.getCurrentPokemonName();
        int psCurrent = po.domain.getCurrentPokemonPs();
        int levelCurrent = po.domain.getCurrentPokemonLevel();
        int maxPs = po.domain.getcurrentMaxPs();
        playerStatsPanel = createStatsPanel(nameCurrent, levelCurrent ,psCurrent,maxPs, true);
        String nameOponent = po.domain.getOponentPokemonName();
        int psOponent = po.domain.getOponentPokemonPs();
        int levelOponent = po.domain.getOponentPokemonLevel();
        int maxPsOponent = po.domain.getOponentMaxPs();

        opponentStatsPanel = createStatsPanel(nameOponent,levelOponent,psOponent,maxPsOponent, false);
        
        add(playerStatsPanel);
        add(opponentStatsPanel);
        actualizarColor();
        
    }

    private void prepareElements() {
        calculatePokemonPositions();
        movMachine = new ArrayList<>();
        ticTac = new JButton("Tic Tac");
        po.styleButton(ticTac);
        
        JPanel panelIz = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelIz.setOpaque(false);

        panelInfo = new JPanel();
        panelInfo.setOpaque(false);
        
        panelInfo.setBorder(BorderFactory.createLineBorder(actualColor, 2));

        add(panelIz, BorderLayout.SOUTH);

        opciones = po.invisiblePanelWithoutOpacity();
        opciones.setOpaque(false);
        opciones.setPreferredSize(new Dimension(350, 150));
        
        cardLayout = new CardLayout();
        opciones.setLayout(cardLayout);

        panelIz.add(panelInfo);
        panelIz.add(opciones);

        battleOptionsPanel = new JPanel(new GridLayout(2,2,1,1));
        battleOptionsPanel.setOpaque(false);
        battleOptionsPanel.add(ticTac);

        opciones.add(battleOptionsPanel, "Opciones");
        
    }

    private void prepareActions(){
        ticTac.addActionListener(e ->{
            try{
                String randomMove = "";
                gameEnd();
                int oldIndex = po.domain.getOponentPokemonPokedexIndex();
                actualIndex = po.domain.getCurrentPokemonPokedexIndex();
                po.domain.movementPerformed(randomMove);
                if (!po.domain.isAliveOpponentPokemon() || oldIndex != po.domain.getOponentPokemonPokedexIndex()){
                    int newIndex = po.domain.getOponentPokemonPokedexIndex();
                    setSecondPokemon(Integer.toString(newIndex));
                }
                actualizarCreateStatsPanelAfterMove();
            }
            catch(PoobkemonException h){
                System.out.println(h.getMessage());
            }
            });
    }

    public void removeMovement(){
        opciones.remove(movesPanel);
        movesPanel = null;
    }

    public void actualizarCreateStatsPanelAfterMove(){
        actualizarColor();

        String pokemonName = po.domain.getOponentPokemonName();
        int health = po.domain.getOponentPokemonPs();
        int level = po.domain.getOponentPokemonLevel();
        int maxPs = po.domain.getOponentMaxPs();
        
        if (actualIndex != po.domain.getCurrentPokemonPokedexIndex()){
            setFirstPokemon(Integer.toString(po.domain.getCurrentPokemonPokedexIndex()));
        }

        String pokemonNameCurrent = po.domain.getCurrentPokemonName();
        int healthCurrent = po.domain.getCurrentPokemonPs();
        int levelCurrent = po.domain.getCurrentPokemonLevel();
        int maxPsCurrent = po.domain.getcurrentMaxPs();
        System.out.println("vida afectada a mi pokemon : "+healthCurrent);

        playerHealthBar.setValue(healthCurrent);
        opponentHealthBar.setValue(health);

        playerHealthLabel.setText(healthCurrent + "/" + maxPsCurrent);
        opponentHealthLabel.setText(health + "/" + maxPs);

        playerNameLabel.setText(pokemonNameCurrent + " Nv." + levelCurrent);
        opponentNameLabel.setText(pokemonName + " Nv. "+ level);

        playerStatsPanel.repaint();
        opponentStatsPanel.repaint();
        gameEnd();
    }

    public void actualizarListaMovements(){
        movMachine = po.domain.getMovementsStringCurrent();
    }

    public void gameEnd(){
        if (po.domain.GameIsOVer()){
            JOptionPane.showMessageDialog(this, "Ha ganado: "+ po.domain.getWinner(),"Se acabo!",JOptionPane.INFORMATION_MESSAGE);
            po.changePanel("inicio");
        }
        
    }
    public void actualizarColor(){
        actualColor = po.domain.getCurrentColor();
        panelInfo.setBorder(BorderFactory.createLineBorder(actualColor, 2));
    }
    public JPanel createStatsPanel(String pokemonName, int level, int health,int maxHealth, boolean isPlayer) {

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(null);
        statsPanel.setPreferredSize(new Dimension(300, 100));
        
        statsPanel.setOpaque(false);
        
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(null);
        innerPanel.setBounds(0, 0, 280, 80);
        innerPanel.setBackground(new Color(248, 248, 248, 220));
        innerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(40, 40, 40), 3, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JLabel nameLabel = new JLabel(pokemonName + " Nv." + level);
        nameLabel.setFont(pokemonFont);
        nameLabel.setBounds(20, 10, 200, 20);
        innerPanel.add(nameLabel);
        
        CustomHealthBar healthBar = new CustomHealthBar(0, health, isPlayer);
        healthBar.setValue(health);
        healthBar.setBounds(60, 40, 180, 15);
        innerPanel.add(healthBar);
        
        JLabel psLabel = new JLabel("PS");
        psLabel.setFont(pokemonFont);
        psLabel.setBounds(20, 38, 30, 20);
        innerPanel.add(psLabel);
        
        JLabel healthLabel = new JLabel(health + "/" +maxHealth);
        healthLabel.setFont(new Font(pokemonFont.getName(), Font.PLAIN, 12));
        healthLabel.setBounds(180, 55, 80, 20);
        healthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        innerPanel.add(healthLabel);
        
        statsPanel.add(innerPanel);
        
        if (isPlayer) {
            System.out.println("Jugador: " + isPlayer);
            playerHealthBar = healthBar;
            playerHealthLabel = healthLabel;
            playerNameLabel = nameLabel;
        } else {
            System.out.println("EMaquina: " + health);
            opponentHealthBar = healthBar;
            opponentHealthLabel = healthLabel;
            opponentNameLabel = nameLabel;
        }
        
        return statsPanel;
    }

    public JButton getBackOptions() {
        return backToOptionsBattle;
    }

    public void actualizarHealt(int health1,int health1Max, int health2,int health2Max) {
        playerHealthBar.setValue(health1);
        opponentHealthBar.setValue(health2);
        playerHealthLabel.setText(health1 + "/" + health1Max);
        opponentHealthLabel.setText(health2 + "/" + health2Max);
        
        repaint();
    }
    
    public void setPokemonNames(String playerPokemonName, String opponentPokemonName, int playerLevel, int opponentLevel) {
        playerNameLabel.setText(playerPokemonName + " Nv." + playerLevel);
        opponentNameLabel.setText(opponentPokemonName + " Nv." + opponentLevel);
    }

    public void setFirstPokemon(String pokemonImageName) {
        firstPokemon = pokemonImageName;
        repaint();
    }

    public void setSecondPokemon(String pokemonImageName) {
        secondPokemon = pokemonImageName;
        repaint();
    }

    private void calculatePokemonPositions() {
        int scaledWidth = getWidth() / 3;
        int scaledHeight = getHeight() / 3;

        xFirst = (getWidth() - scaledWidth) / 2 - scaledWidth + scaledWidth / 4;
        yFirst = (getHeight() - scaledHeight) / 2 + scaledHeight / 2 + scaledHeight / 3;

        xSecond = (getWidth() - scaledWidth) / 2 + scaledWidth / 2 + scaledWidth / 3;
        ySecond = (getHeight() - scaledHeight) / 2 - scaledHeight / 3;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculatePokemonPositions();
        playerStatsPanel.setBounds(xFirst - 30, yFirst - 100, 300, 100);
        opponentStatsPanel.setBounds(xSecond - 30, ySecond - 80, 300, 100);


        playerStatsPanel.revalidate();
        playerStatsPanel.repaint();

        opponentStatsPanel.revalidate();
        opponentStatsPanel.repaint();

        ImageIcon back = new ImageIcon(getClass().getResource("/resources/" + backgroundImage + ".JPG"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
        int scaledWidth = getWidth() / 3;
        int scaledHeight = getHeight() / 3;

        if (firstPokemon != null) {
            ImageIcon pokemonOne = new ImageIcon(getClass().getResource("/resources/battle/back/" + firstPokemon + ".PNG"));
            g.drawImage(pokemonOne.getImage(), xFirst, yFirst + 50, scaledWidth, scaledHeight, this);
        }

        if (secondPokemon != null) {
            ImageIcon pokemonTwo = new ImageIcon(getClass().getResource("/resources/battle/frente/" + secondPokemon + ".PNG"));
            g.drawImage(pokemonTwo.getImage(), xSecond - 15, ySecond + 50, scaledWidth, scaledHeight, this);
        }
    }
    private class CustomHealthBar extends JProgressBar {
        private boolean isPlayer;

        public CustomHealthBar(int min, int max, boolean isPlayer) {
            super(min, max);
            this.isPlayer = isPlayer;
            setUI(new PokemonHealthBarUI());
            setBorderPainted(false);
            setStringPainted(false);
            setOpaque(false);
        }

        private class PokemonHealthBarUI extends BasicProgressBarUI {
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();

        playerStatsPanel.setBounds(xFirst - 30, yFirst - 100, 300, 100);
        opponentStatsPanel.setBounds(xSecond - 30, ySecond - 80, 300, 100);

        battleOptionsPanel.revalidate();
        battleOptionsPanel.repaint();
                
        int width = c.getWidth();
        int height = c.getHeight();
                
        g2d.setColor(new Color(40, 40, 40));
        g2d.fillRect(0, 0, width, height);
                
        int max = getModel().getMaximum();
        int min = getModel().getMinimum();
        int value = getModel().getValue();
                
        double progress = 0.0;
        if (max != min) {
            progress = (double)(value - min) / (double)(max - min);
        }
                
        int fillWidth = (int)(width * progress);
            
        Color healthColor;
                
        if (value > max * 0.5) {
            healthColor = new Color(88, 208, 120);
        } else if (value > max * 0.2) {
            healthColor = new Color(248, 208, 48);
        } else {
            healthColor = new Color(248, 88, 56);  
        }    
        g2d.setColor(healthColor);
        g2d.fillRect(0, 0, fillWidth, height);
        g2d.dispose();
            }
        }
    }
}
