package presentation;
import javax.swing.*;

import domain.PoobkemonException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatosTwoPlayers extends JPanel {
    private String backgroundImage = "fondoAnimado2";
    private JButton btnRegresarNormal;
    private JButton continuar;
    private JButton chooserColorPlayer1;
    private JButton chooserColorPlayer2;
    private POOBkemonGUI po;
    private ModePlayerVSPlayer gameMode;

    private JColorChooser colorChooser;
    private Color colorChosedPlayer1;
    private Color colorChosedPlayer2;

    private JPanel chooseDifficulty;

    private JButton player1;
    private JButton player2;
    private String[] paths;
    private int indexPlayerOne = 0;
    private int indexPlayerTwo = 0;

    private JTextField player1NameField;
    private JTextField player2NameField;
    private String player1Name = "";
    private String player2Name = "";

    private Color choice1;
    private Color choice2;

    public DatosTwoPlayers(POOBkemonGUI newPo, ModePlayerVSPlayer father){
        gameMode = father;
        po = newPo;
        chooserColorPlayer1 = new JButton("Choose Color");
        chooserColorPlayer2 = new JButton("Choose Color");
        prepareElements();
        prepareActions();
    }
    
    private void prepareElements(){
        setLayout(new BorderLayout());
        colorChooser = new JColorChooser();
        paths = new String[]{"/resources/trainers/Trainer1.png","/resources/trainers/Trainer2.png","/resources/trainers/Trainer3.png"};
        
        prepareButtons();
        playerPanel();
    }

    private void prepareButtons(){
        btnRegresarNormal = new JButton("Back");
        continuar = new JButton("CONTINUE");
        po.styleButton(continuar);
        po.styleButton(btnRegresarNormal);
        po.styleButton(chooserColorPlayer1);
        po.styleButton(chooserColorPlayer2);
        
        
        JPanel buttonPanel = po.invisiblePanelWithOpacity();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        buttonPanel.add(btnRegresarNormal);
        buttonPanel.add(continuar);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void prepareActions(){
        continuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    player1Name = player1NameField.getText().trim();
                    player2Name = player2NameField.getText().trim();
                    try{
                        po.domain.isTrainerIsed(player1Name);
                        po.domain.isTrainerIsed(player2Name);
                    }
                    catch(PoobkemonException i){
                        JOptionPane.showMessageDialog(DatosTwoPlayers.this, "Nombre no disponible", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (player1Name.equals(player2Name)){
                        JOptionPane.showMessageDialog(DatosTwoPlayers.this, "No pueden tener el mismo nombre.","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(player1Name.isEmpty() || player2Name.isEmpty()){
                        JOptionPane.showMessageDialog(DatosTwoPlayers.this, "Ambos nombres deben llenarse","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(colorChosedPlayer1 == null || colorChosedPlayer2 == null){
                        JOptionPane.showMessageDialog(DatosTwoPlayers.this,"Ambos colores deben llenarse","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    gameMode.firstName = player1Name;
                    gameMode.secondName = player2Name;

                    gameMode.inventory.inicializate(choice1,choice2);
                    gameMode.changePanel("Inventory");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(DatosTwoPlayers.this, ex.getMessage());
                }
            }
        });

        btnRegresarNormal.addActionListener(e -> {
            po.changePanel("normal");
        });

        chooserColorPlayer1.addActionListener(e -> {
            choice1 = colorChooser.showDialog(this, "Selecciona tu color", Color.BLUE);
            if (choice1 != null) {
                colorChosedPlayer1 = choice1;
                chooserColorPlayer1.setBackground(colorChosedPlayer1);
            }
        });
        
        chooserColorPlayer2.addActionListener(e -> {
            choice2 = colorChooser.showDialog(this, "Selecciona tu color", Color.RED);
            if (choice2 != null) {
                colorChosedPlayer2 = choice2;
                chooserColorPlayer2.setBackground(colorChosedPlayer2);
            }
        });
    }
    

    private void playerPanel() {
        chooseDifficulty = new JPanel();
        chooseDifficulty.setLayout(new GridLayout(1, 2));
        chooseDifficulty.setOpaque(false);
        
        JPanel player1Panel = new JPanel();
        player1Panel.setLayout(new BoxLayout(player1Panel, BoxLayout.Y_AXIS));
        player1Panel.setOpaque(false);
        
        JLabel player1Label = new JLabel("Player One", JLabel.CENTER);
        player1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        player1Label.setFont(new Font("Arial", Font.BOLD, 20));
        
        player1Panel.add(player1Label);
        player1Panel.add(Box.createVerticalStrut(10));
        
        JPanel trainerPanel1 = createPlayerTrainer(1);
        trainerPanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        player1Panel.add(trainerPanel1);
        player1Panel.add(Box.createVerticalStrut(20));
        
        JPanel namePanel1 = new JPanel();
        namePanel1.setLayout(new BoxLayout(namePanel1, BoxLayout.Y_AXIS));
        namePanel1.setOpaque(false);
        namePanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel nameLabel1 = new JLabel("Enter name for Player 1");
        nameLabel1.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel1.add(nameLabel1);
        
        player1NameField = new JTextField(10);
        player1NameField.setFont(new Font("Arial", Font.BOLD, 20));
        player1NameField.setHorizontalAlignment(JTextField.CENTER);
        player1NameField.setMaximumSize(new Dimension(180, 40));
        player1NameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(56,56,56), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        player1NameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        namePanel1.add(Box.createVerticalStrut(5));
        namePanel1.add(player1NameField);
        player1Panel.add(namePanel1);
        player1Panel.add(Box.createVerticalStrut(15));
        
        JPanel colorPanel1 = new JPanel();
        colorPanel1.setOpaque(false);
        colorPanel1.add(chooserColorPlayer1);
        colorPanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        player1Panel.add(colorPanel1);
        
        JPanel player2Panel = new JPanel();
        player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.Y_AXIS));
        player2Panel.setOpaque(false);
        
        JLabel player2Label = new JLabel("Player Two", JLabel.CENTER);
        player2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2Label.setFont(new Font("Arial", Font.BOLD, 20));
        
        player2Panel.add(player2Label);
        player2Panel.add(Box.createVerticalStrut(10));
        
        JPanel trainerPanel2 = createPlayerTrainer(2);
        trainerPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2Panel.add(trainerPanel2);
        player2Panel.add(Box.createVerticalStrut(20));
        
        JPanel namePanel2 = new JPanel();
        namePanel2.setLayout(new BoxLayout(namePanel2, BoxLayout.Y_AXIS));
        namePanel2.setOpaque(false);
        namePanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel nameLabel2 = new JLabel("Enter name for Player 2");
        nameLabel2.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel2.add(nameLabel2);
        
        player2NameField = new JTextField(10);
        player2NameField.setFont(new Font("Arial", Font.BOLD, 20));
        player2NameField.setHorizontalAlignment(JTextField.CENTER);
        player2NameField.setMaximumSize(new Dimension(180, 40));
        player2NameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(56,56,56), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        player2NameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        namePanel2.add(Box.createVerticalStrut(5));
        namePanel2.add(player2NameField);
        player2Panel.add(namePanel2);
        player2Panel.add(Box.createVerticalStrut(15));
        
        JPanel colorPanel2 = new JPanel();
        colorPanel2.setOpaque(false);
        colorPanel2.add(chooserColorPlayer2);
        colorPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2Panel.add(colorPanel2);
        
        chooseDifficulty.add(player1Panel);
        chooseDifficulty.add(player2Panel);
        
        add(chooseDifficulty, BorderLayout.CENTER);
    }

    private JPanel createPlayerTrainer(int trainerNum) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JButton trainerButton;
        if (trainerNum == 1) {
            player1 = createImageButton("Trainer1", paths[0]);
            trainerButton = player1;
            trainerButton.addActionListener(e -> cambiarTrainer(1));
        } else {
            player2 = createImageButton("Trainer2", paths[0]);
            trainerButton = player2;
            trainerButton.addActionListener(e -> cambiarTrainer(2));
        }
        panel.add(trainerButton, BorderLayout.CENTER);
        return panel;
    }

    private void cambiarTrainer(int trainerNum) {
        if (trainerNum == 1) {
            indexPlayerOne = (indexPlayerOne + 1) % paths.length;
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(paths[indexPlayerOne]));
                Image scaledImage = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                player1.setIcon(new ImageIcon(scaledImage));
                player1.setToolTipText("Trainer: " + paths[indexPlayerOne]);
            } catch (Exception e) {
                player1.setText("No imagen??");
                player1.setIcon(null);
            }
        } else {
            indexPlayerTwo = (indexPlayerTwo + 1) % paths.length;
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(paths[indexPlayerTwo]));
                Image scaledImage = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                player2.setIcon(new ImageIcon(scaledImage));
                player2.setToolTipText("Trainer: " + paths[indexPlayerTwo]);
            } catch (Exception e) {
                player2.setText("No imagen??");
                player2.setIcon(null);
            }
        }
    }

    private JButton createImageButton(String name, String imagePath) {
        int width = 70, height = 70;
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

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setToolTipText(name);
        button.setPreferredSize(new Dimension(width, height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    public JButton getBtnRegresarNormal(){
        return btnRegresarNormal;
    }
    
    public JButton getButtonContinuar(){
        return continuar;
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ backgroundImage+".GIF"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}