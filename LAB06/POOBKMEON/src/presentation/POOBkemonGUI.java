package presentation;
import domain.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.Flow;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

public class POOBkemonGUI extends JFrame {
    private JMenuItem leave;
    private JMenuItem save;
    private JFileChooser fileChooser;
    protected CardLayout cardLayout;
    protected JPanel panelContenedor;
    private PokedexPanel pokedexPanelPrueba;
    protected ModePlayerVSPlayer playerVSplayerPanel;
    protected ModePlayerVSPlayer playerVSplayerPanelSurvival;
    protected ModePlayerVsMachine playerVsMachinePanel;
    protected BattlePanel panelBattle;
    private ModeMachineVsMachine machineVsMachinePanel;
    protected ListPokemonAvailable listPokemonsPanel;
    protected InventoryPanel panelInvetory;
    protected SelectionPokemon chooser;
    protected ListOfMovementsPanel listMovements;
    protected PanelSelectedPokemon selectedPokemon;
    protected BattlePanelMvsM panelMvsM;
    protected ModePlayerVSPlayerSurvival panelPvsPSurvival;
    private JColorChooser colorChooser;
    
    private JPanel inicio;
    private JPanel panelPrincipal;
    private JPanel modesOfGamePanelP;
    private JPanel modesOfGameNormal;
    private JPanel modesOfGameSurvival;

    protected POOBkemon domain = new POOBkemon();
    protected TreeMap<String,Pokemon> pokemones;
    protected TreeMap<String, Movement> movimientos;


    /**
     * Constructor of POOBkemon
     */
    public POOBkemonGUI(){
        domain = domain.deserializateGame();
        pokemones = domain.getPokedex();
        movimientos = domain.getMovements();
        prepareElements();
        prepareActions();
    }
    
    /*
     * prepare elements of the innermost layer
     */
    private void prepareElements(){
        setTitle("POOBkemon Esmeralda");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)(screenSize.getWidth() / 1.5), (int)(screenSize.getHeight() / 1.5));
        fileChooser = new JFileChooser();
        colorChooser = new JColorChooser();
        prepareElementsMenu();
        prepareElementsModesOfGame();
        add(panelContenedor);
    }

    public void prepareActions(){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                exit();
            }
        });

        leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                saveOpen();
            }
        });

        panelBattle.getFighButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            
                //panelBattle.actualizar(90,20);
            }
        });


        panelBattle.getFighButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Mostrar lista de movimientos ->
            }
        });
        panelBattle.getInventoryButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Mostrar Inventario
            }
        });

        panelBattle.getFighButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                panelBattle.showMovesPanel();               
            }
        });

        panelBattle.getBackOptions().addActionListener(new ActionListener(){ //aqui
            @Override
            public void actionPerformed(ActionEvent e){
                panelBattle.showBattleOptionsPanel();
            }
        });

        prepareMovementActions();
    }

    public void changePanel(String namePanel){
        cardLayout.show(panelContenedor,namePanel);
    }

    private void prepareElementsMenu(){
        JMenuBar menu = new JMenuBar();
        JMenu menuDesplegable = new JMenu("Menu");
        leave = new JMenuItem("Exit");
        save = new JMenuItem("Save");
        menuDesplegable.add(leave);
        menuDesplegable.addSeparator();
        menuDesplegable.add(save);
        menu.add(menuDesplegable);
        setJMenuBar(menu);
    }

    private void prepareElementsModesOfGame(){
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        inicio = inicio();
        panelContenedor.add(inicio,"inicio");

        panelPrincipal = menuPrincipal();
        panelContenedor.add(panelPrincipal,"principal");

        pokedexPanelPrueba = new PokedexPanel(this);
        panelContenedor.add(pokedexPanelPrueba,"pokedex");

        modesOfGamePanelP = modesOfGamePanel();
        panelContenedor.add(modesOfGamePanelP,"modos de juego");

        modesOfGameNormal = modesOfGamePanelNormal();
        panelContenedor.add(modesOfGameNormal,"normal");
        
        modesOfGameSurvival = modesOfGamePanelSurvival();
        panelContenedor.add(modesOfGameSurvival,"survival");

        playerVSplayerPanel = new ModePlayerVSPlayer(this);
        panelContenedor.add(playerVSplayerPanel,"player vs player");
            
        playerVsMachinePanel = new ModePlayerVsMachine(this);
        panelContenedor.add(playerVsMachinePanel,"player vs machine");

        machineVsMachinePanel = new ModeMachineVsMachine(this);
        panelContenedor.add(machineVsMachinePanel,"machine vs machine");

        panelPvsPSurvival = new ModePlayerVSPlayerSurvival(this);
        panelContenedor.add(panelPvsPSurvival,"player vs player survival");

        panelInvetory = new InventoryPanel(this);
        panelContenedor.add(panelInvetory,"inventory");

        listMovements = new ListOfMovementsPanel(this);
        panelContenedor.add(listMovements,"movimientos");

        listPokemonsPanel = new ListPokemonAvailable(this);
        panelContenedor.add(listPokemonsPanel,"pokemon list");

        selectedPokemon = new PanelSelectedPokemon(this);
        panelContenedor.add(selectedPokemon,"select pokemon");

        chooser = new SelectionPokemon(this);
        panelContenedor.add(chooser, "chooser");

        panelBattle = new BattlePanel(this);
        panelContenedor.add(panelBattle,"battle");

        panelMvsM = new BattlePanelMvsM(this);
        panelContenedor.add(panelMvsM,"battle m");

    }

    private JPanel inicio(){
        JPanel inicioPanelPrueba = background("inicio");
        inicioPanelPrueba.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelContenedor,"principal");
            }
        });
        return inicioPanelPrueba;
    }
    private JPanel backgroundMenu(String backgroundImage){
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon back = new ImageIcon(getClass().getResource("/resources/" + backgroundImage + ".GIF"));
                g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
                Image centeredImage = new ImageIcon(getClass().getResource("/resources/" + "title" + ".PNG")).getImage();
                int scaledWidth = getWidth() / 2;
                int scaledHeight = getHeight() / 2;
                int x = (getWidth() - scaledWidth) / 2;
                int y = (getHeight() - scaledHeight) / 2;
                g.drawImage(centeredImage, x, y, scaledWidth, scaledHeight, this);
            }
        };
    }

    private JPanel menuPrincipal(){
        JPanel menuPrincipal = backgroundMenu("download");
        menuPrincipal.setLayout(new BorderLayout());
        JPanel modosDeJuego = invisiblePanelWithOpacity();
        modosDeJuego.setOpaque(false);
        modosDeJuego.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        menuPrincipalButtons(modosDeJuego);
        menuPrincipal.add(modosDeJuego, BorderLayout.SOUTH);
        return menuPrincipal;
    }

    private void menuPrincipalButtons(JPanel modosDeJuego){
        JButton play = new JButton("Play");
        JButton pokedex = new JButton("Pokedex");
        styleButtonExternal(play);
        styleButtonExternal(pokedex);
        modosDeJuego.add(play);
        modosDeJuego.add(pokedex);
        prepareActionsMenuPrincipal(play, pokedex);
    }

    private void prepareActionsMenuPrincipal(JButton play, JButton pokedex){
        pokedex.addActionListener(e -> changePanel("pokedex"));
        play.addActionListener(e -> changePanel("modos de juego"));
    }

    private JPanel modesOfGamePanel(){
        JPanel modesOfGame = background("trainers");
        modesOfGame.setLayout(new BorderLayout());
        modesOfGamePanelButtons(modesOfGame);
        return modesOfGame;
    }

    private void modesOfGamePanelButtons(JPanel modesOfGame){
        JButton back = new JButton("BACK");
        JButton modeNormal = new JButton("MODE NORMAL");
        JButton modeSurvival = new JButton("MODE SURVIVAL");
        styleButtonExternal(back);
        styleButton(modeNormal);
        styleButton(modeSurvival);
        JPanel options = modesOfGames(modeNormal, modeSurvival);
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        centro.add(options);
        modesOfGame.add(centro, BorderLayout.CENTER);
        JPanel buttonPanel = invisiblePanelWithOpacity();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        buttonPanel.add(back);
        modesOfGame.add(buttonPanel,BorderLayout.SOUTH);
        prepareActionsModesOfGamePanel(back, modeNormal, modeSurvival);
    }

    private JPanel modesOfGames(JButton modeNormal, JButton modeSurvival){
        JPanel options = invisiblePanelWithoutOpacity();
        options.setOpaque(false);
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        modeNormal.setAlignmentX(Component.CENTER_ALIGNMENT);
        modeSurvival.setAlignmentX(Component.CENTER_ALIGNMENT);
        options.add(Box.createVerticalStrut(20));
        options.add(modeNormal);
        options.add(Box.createVerticalStrut(15));
        options.add(modeSurvival);

        return options;
    }

    private void prepareActionsModesOfGamePanel(JButton back, JButton normalMode, JButton survivalMode){
        back.addActionListener(e -> changePanel("principal"));
        normalMode.addActionListener(e -> changePanel("normal"));
        survivalMode.addActionListener(e -> changePanel("survival"));
    }

    private JPanel modesOfGamePanelNormal(){
        JPanel modesOfGamePanelNormal = background("trainers");
        modesOfGamePanelNormal.setLayout(new BorderLayout());
        modesOfGamePanelNormalButtons(modesOfGamePanelNormal);
        return modesOfGamePanelNormal;
    }

    private void modesOfGamePanelNormalButtons(JPanel modesOfGamePanelNormal){
        JButton back = new JButton("BACK");
        styleButtonExternal(back);
        JPanel normalMode = modeNormalPanel(back);
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        centro.add(normalMode);
        JPanel buttonPanel = invisiblePanelWithOpacity();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,20,20));
        buttonPanel.add(back);
        modesOfGamePanelNormal.add(buttonPanel,BorderLayout.SOUTH);
        modesOfGamePanelNormal.add(centro,BorderLayout.CENTER);
    }

    private JPanel modeNormalPanel(JButton back){

        JButton playerVsPlayer = new JButton("Player vs Player");
        JButton playerVsMachine = new JButton("Player vs Machine");
        JButton machineVsMachine = new JButton("Machine vs Machine");
        styleButton(playerVsPlayer);
        styleButton(playerVsMachine);
        styleButton(machineVsMachine);
        JPanel options = invisiblePanelWithoutOpacity();
        options.setOpaque(false);
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        playerVsMachine.setAlignmentX(Component.CENTER_ALIGNMENT);
        machineVsMachine.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerVsPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        options.add(Box.createVerticalStrut(20));
        options.add(playerVsPlayer);
        options.add(Box.createVerticalStrut(15));
        options.add(playerVsMachine);
        options.add(Box.createVerticalStrut(15));
        options.add(machineVsMachine);
        prepareActionsModeNormal(back, playerVsPlayer, playerVsMachine, machineVsMachine);

        return options;
    }

    private void prepareActionsModeNormal(JButton back, JButton playerVsPlayer, JButton playerVsMachine, JButton machineVsMachine){
        back.addActionListener(e -> changePanel("modos de juego"));
        playerVsPlayer.addActionListener(e -> changePanel("player vs player"));
        playerVsMachine.addActionListener(e -> changePanel("player vs machine"));
        machineVsMachine.addActionListener(e -> changePanel("machine vs machine"));
    }

    private JPanel modesOfGamePanelSurvival(){
        JPanel modeSurvivalPanel = background("download");
        modeSurvivalPanel.setLayout(new BorderLayout());
        modesOfGamePanelSurvivalButton(modeSurvivalPanel);
        return modeSurvivalPanel;
    }

    private void modesOfGamePanelSurvivalButton(JPanel modeSurvivalPanel){
        JButton back = new JButton("BACK");
        styleButtonExternal(back);
        JPanel modeSurvival = survivalModeMenu(back);
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        centro.add(modeSurvival);
        JPanel buttonPanel = invisiblePanelWithOpacity();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        buttonPanel.add(back);
        modeSurvivalPanel.add(centro,BorderLayout.CENTER);
        modeSurvivalPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel survivalModeMenu(JButton back){
        JButton playerVsPlayerSurvival = new JButton("Player vs Player");
        styleButton(playerVsPlayerSurvival);
        JPanel options = invisiblePanelWithoutOpacity();
        options.setOpaque(false);
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        playerVsPlayerSurvival.setAlignmentX(Component.CENTER_ALIGNMENT);
        options.add(playerVsPlayerSurvival);
        prepareActionsSurvivalMode(back,playerVsPlayerSurvival);
        return options;
    }

    private void prepareActionsSurvivalMode(JButton back, JButton playerVsPlayerSurvival){
        back.addActionListener(e -> changePanel("modos de juego"));
        playerVsPlayerSurvival.addActionListener(e -> changePanel("player vs player survival"));
    }

    public JPanel invisiblePanelWithoutOpacity(){
        return new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 0));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
    }
    public JPanel invisiblePanelWithOpacity(){
        return new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 180));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
    }
    private JPanel background(String backgroundImage){
        return new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon back;
                try {
                    back = new ImageIcon(getClass().getResource("/resources/" + backgroundImage+".GIF"));
                } catch (Exception e) {
                    back = new ImageIcon(getClass().getResource("/resources/download.GIF"));
                }
                g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
    }
    
    public void styleButtonExternal(JButton button) {
        button.setFont(new Font("Times new Roman", Font.BOLD, 14));
        button.setBackground(new Color(30, 30, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(180, 35));
    }

    public void styleButton(JButton button){
        button.setFont(new Font("Times new Roman", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(180, 35));
    }
    /*
     * Do the action of close the window
     */
    private void exit(){
        int option = JOptionPane.showConfirmDialog(this, "Estas seguro de que quieres salir?",
                "Confirmar salida", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
    
    /*
     * Do the action of save open
     */
    private void saveOpen(){
        int choice = fileChooser.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION){
            File archive = fileChooser.getCurrentDirectory();
            JOptionPane.showMessageDialog(this, "Funcionalidad Guardar en construccion, Lugar donde se guarda: "+ archive.getName(), "Informacion ", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public POOBkemon getDomain(){
        return domain;
    }
    private void prepareMovementActions(){
    
        machineVsMachinePanel.getBtnRegresar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(panelContenedor,"normal");
            }
        });

        playerVSplayerPanel.getBtnRegresarNormal().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(panelContenedor,"normal");
            }
        });

        pokedexPanelPrueba.getButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(panelContenedor,"principal");
            }
        });


        chooser.getButtonBack().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                chooser.reset();
                cardLayout.show(panelContenedor,"player vs machine");
                
            }
        });


        playerVSplayerPanel.getButtonContinuar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //cardLayout.show(panelContenedor,"battle");
            }
        });


        playerVSplayerPanel.getBtnRegresarNormal().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(panelContenedor,"normal");
            }
        });

        panelBattle.getRunButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //chooser.reset();
                listMovements.resetPokemonChosen();
                //selectedPokemon.reset();
                //playerVsMachinePanel.reset();
                panelBattle.reset();
                cardLayout.show(panelContenedor,"principal");
            }
        });
        playerVsMachinePanel.getButtonRegresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                playerVsMachinePanel.reset();
                cardLayout.show(panelContenedor,"normal");
            }
        });

        playerVsMachinePanel.getChoserColorNext().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                playerVsMachinePanel.changeColor();
                chooser.setColor();
                listMovements.setColor();
                System.out.println("Se ha seleccionado el boton de color, se ha puesto en la seleccion de poquemones y movimientos");
            }
        });


        panelBattle.getPokemonButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(panelContenedor,"pokemon list");
            }
        });

        panelBattle.getInventoryButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(panelContenedor,"inventory");
            }
        });

        panelInvetory.getButtonBack().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(panelContenedor,"battle");
            }
        });
        
        listPokemonsPanel.getBackButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                listPokemonsPanel.reset();
                cardLayout.show(panelContenedor,"battle");
            }
        });
        

        listMovements.getNextButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                if (!listMovements.isSelectedMovements()) {
                    return;
                }
                System.out.println(listMovements.getPokemonChoosen().toString());
                System.out.println(listMovements.getPokemonChoosen().toString());
                
                selectedPokemon.inicializate(listMovements.getPokemonChoosen(), listMovements.getColor());

                playerVsMachinePanel.pokemonsWithMovs= listMovements.getMovementsMap();
                listMovements.resetPokemonChosen();
                //selectedPokemon.reset();
                cardLayout.show(panelContenedor,"select pokemon");
                System.out.println("Se ha seleccionado seguir y se ha resetiado las listas de movimientos");

            }
        });
        listMovements.getComeButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                listMovements.resetPokemonChosen();
                selectedPokemon.reset();
                cardLayout.show(panelContenedor,"chooser");
            }
        });
        
        selectedPokemon.getBackButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                listMovements.resetPokemonChosen();
                selectedPokemon.reset();
                cardLayout.show(panelContenedor,"player vs machine");
                
                System.out.println("se ha oprimido volver de seleccionar el pokemon inicial y se resetea ese panel y el de lista de movimientos");
            }
        });
        }

/////////////////////////////////////////////////////////////////////////////////////////
        public void createTrainer(String trainerEscogido ,Color color){
            try{
                if (trainerEscogido == null) {
                    JOptionPane.showMessageDialog(null, "Error: Player trainer name not set");
                    return;
                }
                domain.addNewTrainer(trainerEscogido,color);
            }catch(PoobkemonException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        public void addPokemonsToTrainer(String trainerEscogido,HashMap<String, ArrayList<String>> pokemonsWithAll) throws PoobkemonException{
            HashMap<String, ArrayList<String>> lista = pokemonsWithAll;
            for (Map.Entry<String, ArrayList<String>> entry : lista.entrySet()) {
                domain.addNewPokemon(trainerEscogido, entry.getKey(), movimientos.get(entry.getValue().get(0)), movimientos.get(entry.getValue().get(1)), movimientos.get(entry.getValue().get(2)), movimientos.get(entry.getValue().get(3)));   
            }
        }

        public ArrayList<String> addItemsToTrainer(String trainerEscogido,ArrayList<String> itemsEscogidos) throws PoobkemonException{
            ArrayList<String> items = itemsEscogidos;
            for (String i:items){
                domain.getTrainer(trainerEscogido).getInventory().addItem(domain.getItems().get(i));
            }
            return domain.getTrainer(trainerEscogido).getInventory().getItemsArray();
        }
/////////////////////////////////////////////////////////////////////////////////////////


    
    public static void main(String args []){
        POOBkemonGUI kemon = new POOBkemonGUI();
        kemon.setLocationRelativeTo(null);
        kemon.setVisible(true);
    }
}