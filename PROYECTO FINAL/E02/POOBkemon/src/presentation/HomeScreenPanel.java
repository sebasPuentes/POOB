package src.presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HomeScreenPanel extends JPanel {
    private JLabel titulo;
    private JButton playButton;
    private JButton exitButton;
    private Image backgroundImage;
    private ArrayList<JButton> buttons = new ArrayList<>();

    public HomeScreenPanel() {
        prepareElements();
    }

    private void prepareElements() {
        setLayout(new BorderLayout());
        playButton = new JButton("Jugar");
        exitButton = new JButton("Salir");
        buttons.add(playButton);
        buttons.add(exitButton);

        // Panel para centrar los botones y colocarlos en línea
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(playButton);
        buttonPanel.add(exitButton);
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.SOUTH);
        setButtonsColor();

    }

    private void setButtonsColor() {
        for (JButton button : buttons) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaint();
        revalidate();
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/escenas/" + "inicio" + ".PNG"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}