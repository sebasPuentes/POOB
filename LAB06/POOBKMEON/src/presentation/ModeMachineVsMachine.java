package presentation;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

public class ModeMachineVsMachine extends JPanel {
    private String backgroundImage = "PZ4ODE";
    private JButton btnRegresar;
    private JButton continueButton;
    private POOBkemonGUI po;
    private TreeMap<String, String> gameModes;
    private ArrayList<String> gameModeChoosen1;
    private ArrayList<String> gameModeChoosen2;
    private ArrayList<JButton> buttons1;
    private ArrayList<JButton> buttons2;
    private JPanel chooseDifficulty;

    private JButton buttonTrainer1;
    private JButton buttonTrainer2;
    protected String machineTrainerFirst;
    protected String machineTrainerSecond;
    private String[] paths;
    private int indexPath1 = 0;
    private int indexPath2 = 0;

    public ModeMachineVsMachine(POOBkemonGUI newPo){
        po = newPo;
        prepareElements();
        prepareActions();
    }

    private void prepareElements(){
        setLayout(new BorderLayout());
        gameModes = new TreeMap<>(){{
            put("Defensive", "/resources/Defensive.jpeg");
            put("Expert", "/resources/Expert.jpeg");
            put("Changing", "/resources/Changing.jpeg");
            put("Attacking", "/resources/Attacking.jpeg");
        }};

        paths = new String[]{"/resources/trainers/Trainer1.png","/resources/trainers/Trainer2.png","/resources/trainers/Trainer3.png"};

        gameModeChoosen1 = new ArrayList<>();
        gameModeChoosen2 = new ArrayList<>();
        buttons1 = new ArrayList<>();
        buttons2 = new ArrayList<>();

        continueButton = new JButton("CONTINUE");
        po.styleButton(continueButton);

        prepareButtons();
        chooseDifficulty();
    }

    private void prepareButtons(){
        btnRegresar = new JButton("BACK");
        po.styleButton(btnRegresar);

        JPanel buttonPanel = po.invisiblePanelWithOpacity();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(btnRegresar);
        buttonPanel.add(continueButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void prepareActions(){
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmMachinesInfo();
            }
        });

        btnRegresar.addActionListener(e -> {
            po.changePanel("modos de juego");
        });
    }

    private void confirmMachinesInfo(){
        if (gameModeChoosen1.size() != 1 || gameModeChoosen2.size() != 1) {
            JOptionPane.showMessageDialog(ModeMachineVsMachine.this,
                            "Error",
                            "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            machineTrainerFirst = gameModeChoosen1.get(0);
            machineTrainerSecond = gameModeChoosen2.get(0);
            po.domain.inicialTrainerPokemon(machineTrainerFirst, "machine one");
            po.domain.inicialTrainerPokemon(machineTrainerSecond, "machine two");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ModeMachineVsMachine.this, ex.getMessage());
        }
        po.domain.inicializateBattle(machineTrainerFirst, machineTrainerSecond);
        po.panelMvsM.inicializate(po.domain.inicialTrainerMovements(machineTrainerFirst));
        po.selectedPokemon.changeImageMvsM();
        po.cardLayout.show(po.panelContenedor, "battle m");
    }

    private void chooseDifficulty(){
        chooseDifficulty = new JPanel();
        chooseDifficulty.setLayout(new GridLayout(1, 2));
        chooseDifficulty.setOpaque(false);

        JPanel trainer1Panel = new JPanel();
        trainer1Panel.setLayout(new BorderLayout());
        trainer1Panel.setOpaque(false);

        JPanel trainer2Panel = new JPanel();
        trainer2Panel.setLayout(new BorderLayout());
        trainer2Panel.setOpaque(false);

        JPanel trainerSelector1 = createTrainerSelector(1);
        JPanel trainerSelector2 = createTrainerSelector(2);

        JPanel gameModePanel1 = createGameModePanel(1);
        JPanel gameModePanel2 = createGameModePanel(2);

        trainer1Panel.add(new JLabel("Machine Trainer 1", JLabel.CENTER), BorderLayout.NORTH);
        trainer1Panel.add(trainerSelector1, BorderLayout.CENTER);
        trainer1Panel.add(gameModePanel1, BorderLayout.SOUTH);

        trainer2Panel.add(new JLabel("Machine Trainer 2", JLabel.CENTER), BorderLayout.NORTH);
        trainer2Panel.add(trainerSelector2, BorderLayout.CENTER);
        trainer2Panel.add(gameModePanel2, BorderLayout.SOUTH);

        chooseDifficulty.add(trainer1Panel);
        chooseDifficulty.add(trainer2Panel);

        add(chooseDifficulty, BorderLayout.CENTER);
    }

    private JPanel createTrainerSelector(int trainerNum) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        JButton trainerButton;
        if (trainerNum == 1) {
            buttonTrainer1 = createImageButton("Trainer1", paths[0]);
            trainerButton = buttonTrainer1;
            trainerButton.addActionListener(e -> cambiarTrainer(1));
        } else {
            buttonTrainer2 = createImageButton("Trainer2", paths[0]);
            trainerButton = buttonTrainer2;
            trainerButton.addActionListener(e -> cambiarTrainer(2));
        }

        panel.add(trainerButton, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createGameModePanel(int trainerNum) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder("Select Game Mode"));

        for (Map.Entry<String, String> entry: gameModes.entrySet()) {
            JButton button = createImageButton(entry.getKey(), entry.getValue());
            po.styleButton(button);

            if (trainerNum == 1) {
                buttons1.add(button);
                button.addActionListener(e -> selectionGameMode(button, 1));
            } else {
                buttons2.add(button);
                button.addActionListener(e -> selectionGameMode(button, 2));
            }

            panel.add(button);
        }

        return panel;
    }

    private void cambiarTrainer(int trainerNum) {
        if (trainerNum == 1) {
            indexPath1 = (indexPath1 + 1) % paths.length;
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(paths[indexPath1]));
                Image scaledImage = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                buttonTrainer1.setIcon(new ImageIcon(scaledImage));
                buttonTrainer1.setToolTipText("Trainer: " + paths[indexPath1]);
            } catch (Exception e) {
                buttonTrainer1.setText("No imagen??");
                buttonTrainer1.setIcon(null);
            }
        } else {
            indexPath2 = (indexPath2 + 1) % paths.length;
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(paths[indexPath2]));
                Image scaledImage = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                buttonTrainer2.setIcon(new ImageIcon(scaledImage));
                buttonTrainer2.setToolTipText("Trainer: " + paths[indexPath2]);
            } catch (Exception e) {
                buttonTrainer2.setText("No imagen??");
                buttonTrainer2.setIcon(null);
            }
        }
    }

    private JButton createImageButton(String name, String imagePath) {
        int width = 70, height = 70;
        Dimension smallSize = new Dimension(50, 30);
        JButton button = new JButton();

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));

            if (imagePath.toLowerCase().endsWith(".gif")) {
                button.setIcon(icon);
                button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            } else {
                Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));
            }
        } catch (Exception e) {
            button.setText("No imagen, intenta de nuevo.");
        }

        button.setPreferredSize(smallSize);
        button.setMinimumSize(smallSize);
        button.setMaximumSize(smallSize);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setToolTipText(name);
        button.setPreferredSize(new Dimension(200, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    private void selectionGameMode(JButton button, int trainerNum) {
        ArrayList<String> gameModeChoosen = (trainerNum == 1) ? gameModeChoosen1 : gameModeChoosen2;
        ArrayList<JButton> buttons = (trainerNum == 1) ? buttons1 : buttons2;
        for (JButton b : buttons) {
            b.setBackground(null);
            b.setOpaque(false);
        }
        gameModeChoosen.clear();
        button.setBackground(Color.GREEN);
        button.setOpaque(true);
        gameModeChoosen.add(button.getToolTipText());
    }

    public JButton getBtnRegresar(){
        return btnRegresar;
    }

    public JButton getContinueButton() {
        return continueButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ backgroundImage+".GIF"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}