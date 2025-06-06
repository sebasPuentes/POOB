package presentation;
import domain.Movement;
import domain.Pokemon;
import domain.PoobkemonException;
import domain.Trainer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class BattlePanel extends JPanel {
    private String backgroundImage = "battle";
    private String firstPokemon = "6";
    private String secondPokemon = "6";
    private JButton fightButton;
    private JButton inventoryButton;
    private JButton pokemonButton;
    private JButton runButton;
    private POOBkemonGUI po;
    private JPanel movesPanel;
    private JPanel opciones;
    private JPanel battleOptionsPanel;
    private JButton backToOptionsBattle;

    private JPanel playerStatsPanel;
    private JPanel opponentStatsPanel;

    private CustomHealthBar playerHealthBar;
    private CustomHealthBar opponentHealthBar;
    private JLabel info;
    private JPanel panelInfo; 

    private JLabel playerHealthLabel;
    private JLabel opponentHealthLabel;
    private JLabel playerNameLabel;
    private JLabel opponentNameLabel;

    private int xFirst, xSecond;
    private int yFirst, ySecond;
    private Color actualColor;

    private Timer battleTimer;
    private JLabel timerLabel;
    private int timeRemaining = 20;
    private boolean timerRunning = false;

    private Font pokemonFont;
    protected CardLayout cardLayout;

    private ArrayList<String> trainerActualMovements;


    public BattlePanel(POOBkemonGUI newPo) {
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
        initializeTimer();
    }

    public void inicializate(ArrayList<String> movs){
        trainerActualMovements = movs;
        actualColor = po.domain.getCurrentColor();

        String nameCurrent = po.domain.getCurrentPokemonName();
        int psCurrent = po.domain.getCurrentPokemonPs();
        int levelCurrent = po.domain.getCurrentPokemonLevel();
        int maxPs = po.domain.getcurrentMaxPs();
        setFirstPokemon(Integer.toString(po.domain.getCurrentPokemonPokedexIndex()));
        playerStatsPanel = createStatsPanel(nameCurrent, levelCurrent ,psCurrent,maxPs, true);

        String nameOponent = po.domain.getOponentPokemonName();
        int psOponent = po.domain.getOponentPokemonPs();
        int levelOponent = po.domain.getOponentPokemonLevel();
        int maxPsOponent = po.domain.getOponentMaxPs();
        setSecondPokemon(Integer.toString(po.domain.getOponentPokemonPokedexIndex()));
        opponentStatsPanel = createStatsPanel(nameOponent,levelOponent,psOponent,maxPsOponent, false);
        
        add(playerStatsPanel);
        add(opponentStatsPanel);
        prepareMovementButtons();
        actualizarColor();
        
    }

    private void prepareElements() {
        calculatePokemonPositions();

        trainerActualMovements = new ArrayList<>();

        info = new JLabel("Elige tu acción");
        info.setFont(pokemonFont);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setOpaque(true);
        
        JPanel panelIz = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelIz.setOpaque(false);

        panelInfo = new JPanel();
        panelInfo.setOpaque(false);
         
        panelInfo.add(info);
        info.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(actualColor, 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        info.setBackground(actualColor);

        panelInfo.setBorder(BorderFactory.createLineBorder(actualColor, 2));

        add(panelIz, BorderLayout.SOUTH);

        opciones = po.invisiblePanelWithOpacity();
        opciones.setOpaque(false);
        opciones.setPreferredSize(new Dimension(350, 150));
        
        cardLayout = new CardLayout();
        opciones.setLayout(cardLayout);

        panelIz.add(panelInfo);
        panelIz.add(opciones);

        battleOptionsPanel = new JPanel(new GridLayout(2,2,1,1));
        battleOptionsPanel.setOpaque(false);

        fightButton = new JButton("LUCHAR");
        inventoryButton = new JButton("MOCHILA");
        pokemonButton = new JButton("POKéMON");
        runButton = new JButton("HUIR");

        po.styleButton(fightButton);
        po.styleButton(inventoryButton);
        po.styleButton(pokemonButton);
        po.styleButton(runButton);

        fightButton.setPreferredSize(new Dimension(20, 25));
        inventoryButton.setPreferredSize(new Dimension(20, 25));
        pokemonButton.setPreferredSize(new Dimension(20, 25));
        runButton.setPreferredSize(new Dimension(20, 25));

        battleOptionsPanel.add(fightButton);
        battleOptionsPanel.add(inventoryButton);
        battleOptionsPanel.add(pokemonButton);
        battleOptionsPanel.add(runButton);

        opciones.add(battleOptionsPanel, "Opciones");
        
        showBattleOptionsPanel();
    }

    public void prepareMovementButtons() {
        movesPanel = new JPanel(new BorderLayout());
        movesPanel.setOpaque(false);

        JPanel movesButtonsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        movesButtonsPanel.setOpaque(false);

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(248, 248, 248, 220));
        messagePanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(40, 40, 40), 3, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        messagePanel.setLayout(new BorderLayout());

        JLabel moveLabel = new JLabel("¿Qué movimiento debería usar " + po.domain.getCurrentPokemonName() + "?");
        moveLabel.setFont(pokemonFont);
        messagePanel.add(moveLabel, BorderLayout.CENTER);

        for (String move : trainerActualMovements) {
            JButton moveBtn = new JButton(move);
            po.styleButton(moveBtn);
            moveBtn.setFont(pokemonFont);
            moveBtn.setPreferredSize(new Dimension(150, 40));
            moveBtn.setMinimumSize(new Dimension(150, 40));
            try{moveBtn.setToolTipText("PP: "+String.valueOf(po.domain.getPPInBattle(move)));}
            catch(PoobkemonException e){System.out.println(e.getMessage());}

            moveBtn.addActionListener(e -> {
                System.out.println("Selected move: " + move);
                try{
                    gameEnd();
                    int oldIndex = po.domain.getOponentPokemonPokedexIndex();
                    po.domain.movementPerformed(move);

                    JButton button = (JButton) e.getSource();
                    //e -> objeto que se pasa en el action listener
                    //getSource() -> devuelve el componente que genero el evento
                    //devuelve el objeto componente especifico que generó el evento.
                    int currentPP = po.domain.getPPInBattle(move);
                    button.setToolTipText("PP: "+ currentPP);

                    if (!po.domain.isAliveOpponentPokemon() || oldIndex != po.domain.getOponentPokemonPokedexIndex()){
                        int newIndex = po.domain.getOponentPokemonPokedexIndex();
                        setSecondPokemon(Integer.toString(newIndex));
                    }
                    actualizarCreateStatsPanelAfterMove();
                    actualizarColor();
                }
                catch(PoobkemonException h){
                    System.out.println("No se hace el ataque: "+ h.getMessage());
                }
                showBattleOptionsPanel();
            });

            movesButtonsPanel.add(moveBtn);
        }

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButtonPanel.setOpaque(false);
        backToOptionsBattle.setPreferredSize(new Dimension(100, 30));
        backButtonPanel.add(backToOptionsBattle);

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);
        topSection.add(messagePanel, BorderLayout.CENTER);
        topSection.add(backButtonPanel, BorderLayout.SOUTH);

        movesPanel.add(topSection, BorderLayout.NORTH);
        movesPanel.add(movesButtonsPanel, BorderLayout.CENTER);

        JPanel paddingPanel = new JPanel();
        paddingPanel.setOpaque(false);
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        paddingPanel.setLayout(new BorderLayout());
        paddingPanel.add(movesButtonsPanel, BorderLayout.CENTER);

        movesPanel.add(paddingPanel, BorderLayout.CENTER);

        opciones.add(movesPanel, "MovimientosP");

        fightButton.addActionListener(e -> {
            showMovesPanel();
        });

        backToOptionsBattle.addActionListener(e -> {
            showBattleOptionsPanel();
        });

        movesPanel.revalidate();
        movesPanel.repaint();
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
        setSecondPokemon(Integer.toString(po.domain.getOponentPokemonPokedexIndex()));

        String pokemonNameCurrent = po.domain.getCurrentPokemonName();
        int healthCurrent = po.domain.getCurrentPokemonPs();
        int levelCurrent = po.domain.getCurrentPokemonLevel();
        int maxPsCurrent = po.domain.getcurrentMaxPs();
        System.out.println("vida afectada a mi pokemon : "+healthCurrent);
        setFirstPokemon(Integer.toString(po.domain.getCurrentPokemonPokedexIndex()));
        
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

    public void gameEnd(){
        if (po.domain.GameIsOVer()){
            JOptionPane.showMessageDialog(this, "Ha ganado: "+ po.domain.getWinner(),"Se acabo!",JOptionPane.INFORMATION_MESSAGE);
            po.changePanel("inicio");
            reset();
        }
        
    }


    public void showMovesPanel() {
        cardLayout.show(opciones, "MovimientosP");
        startTimer();
        opciones.revalidate();
        opciones.repaint();
    }


    public void actualizarColor(){
        actualColor = po.domain.getCurrentColor();
        info.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(actualColor, 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        info.setBackground(actualColor);
        panelInfo.setBackground(actualColor);
        panelInfo.setBorder(BorderFactory.createLineBorder(actualColor, 2));
    }


    public void showBattleOptionsPanel() {
        stopTimer();
        cardLayout.show(opciones,"Opciones"); 
    }
    public void actualizaInfo(){
        info.setText("");
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






    public JButton getFighButton() {
        return fightButton;
    }

    public JButton getInventoryButton() {
        return inventoryButton;
    }

    public JButton getPokemonButton() {
        return pokemonButton;
    }

    public JButton getRunButton() {
        return runButton;
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

    private void initializeTimer(){
        battleTimer = new javax.swing.Timer(1000, e -> {
            timeRemaining--;
            updateTimerDisplay();

            if (timeRemaining <= 0){
                timerExpired();
            }
        });

        timerLabel = new JLabel("20");
        timerLabel.setFont(pokemonFont);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.BLACK, 2, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        timerLabel.setBackground(new Color(248, 248, 248, 220));
        timerLabel.setOpaque(true);
        timerLabel.setBounds(375, 20, 50, 30);
        add(timerLabel);
    }

    private void updateTimerDisplay() {
        timerLabel.setText(String.valueOf(timeRemaining));
        if (timeRemaining <= 5) {
            timerLabel.setForeground(Color.RED);
        } else {
            timerLabel.setForeground(Color.BLACK);
        }
    }

    private void timerExpired() {
        stopTimer();
        info.setText("¡Se acabó el tiempo!");
        showBattleOptionsPanel();
    }

    private void startTimer(){
        if(!timerRunning){
            timeRemaining = 20;
            updateTimerDisplay();
            battleTimer.start();
            timerRunning = true;
        }
    }

    public void stopTimer(){
        if (timerRunning){
            battleTimer.stop();
            timerRunning = false;
        }
    }

    public void resetTimer(){
        stopTimer();
        timeRemaining = 20;
        updateTimerDisplay();
    }
    public void reset(){
        trainerActualMovements.clear();
        po.panelInvetory.reset();

    }

    public void actualizarListaMovements(){
        trainerActualMovements = po.domain.getMovementsStringCurrent();
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

        timerLabel.setBounds(375,20,50,30);

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