package src.presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ModePvsPPanel extends JPanel {
    private JLabel titulo;
    private JButton backButton;
    private JButton normalButton;
    private JButton survivalButton;
    private ArrayList<JButton> buttons = new ArrayList<>();
    public ModePvsPPanel() {
        prepareElements();
    }
    private void prepareElements() {
        setLayout(new BorderLayout());

        titulo = new JLabel("¡Selecciona la Modalidad!");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.GRAY);
        add(titulo, BorderLayout.NORTH);

        JPanel modePanel = new JPanel(new GridLayout(4, 1, 0, 15));
        modePanel.setOpaque(false);
        modePanel.setPreferredSize(new Dimension(250, 200));

        normalButton = new JButton("Normal");
        normalButton.setContentAreaFilled(false);
        survivalButton = new JButton("Supervivencia");
        survivalButton.setContentAreaFilled(false);
        backButton = new JButton("Volver");
        backButton.setContentAreaFilled(false);

        modePanel.add(normalButton);
        modePanel.add(survivalButton);
        modePanel.add(backButton);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(modePanel);
        add(centerPanel, BorderLayout.SOUTH);

        buttons.add(normalButton);
        buttons.add(survivalButton);
        buttons.add(backButton);

        setButtonsColor();
    }

    private void setButtonsColor() {
        for (JButton button : buttons) {
            button.setOpaque(false);
            button.setContentAreaFilled(true);
            button.setBackground(new Color(255, 255, 255, 150));
            button.setForeground(Color.BLACK); // Texto negro
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }
    }
    public JButton getBackButton(){
        return backButton;
    }
    public JButton getNormalMode() {
        return normalButton;
    }
    public JButton getSurvivalMode() {
        return survivalButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaint();
        revalidate();
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ "elegirModo"+".png"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}