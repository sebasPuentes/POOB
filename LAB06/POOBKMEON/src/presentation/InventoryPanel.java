package presentation;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

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
public class InventoryPanel extends JPanel {
    private  String backgroundImage = "emerald";
    private JLabel texto;
    private POOBkemonGUI pooBkemonGUI;
    private Color color;
    private JButton come;
    private JButton doneButton; 
    private JPanel panelScroll;
    private ArrayList<String> itemsSelected;
    private ArrayList<JButton> buttons;
    private final int MAX_ITEM_SELECT = 1;

    public InventoryPanel(POOBkemonGUI po){
        pooBkemonGUI = po;
        color = new Color(85, 85, 85, 100);
        come = new JButton("Back To Battle!");
        doneButton = new JButton ("Use Item");
        texto = new JLabel("Player");
    }

    public void inicializate(ArrayList<String> items){
        itemsSelected = items;
        System.out.println(itemsSelected + "PANEL INVENTORY?");
        prepareElements();
        prepareActions();
    }


    private JPanel upPanel(){
        JPanel up = new JPanel(new BorderLayout());
        up.setOpaque(false);
        texto.setOpaque(true);
        texto.setBackground(color);
        texto.setHorizontalAlignment(JLabel.CENTER);
        texto.setForeground(color);
        up.add(new JLabel(" "),BorderLayout.CENTER);
        up.add(texto, BorderLayout.NORTH);
        return up;
    }

    private JPanel rightPanel(){
        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(false);
        right.add(new JLabel(" "),BorderLayout.WEST);
        right.add(new JLabel(" "),BorderLayout.CENTER);
        right.add(new JLabel(" "),BorderLayout.EAST);
        return right;
    }

    private JPanel leftPanel(){
        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);
        left.add(new JLabel(" "),BorderLayout.WEST);
        ImageIcon imagen = new ImageIcon(getClass().getResource("/resources/pokeball1.png"));
        left.add(new JLabel(imagen),BorderLayout.CENTER);
        left.add(new JLabel(" "),BorderLayout.EAST);
        return left;
    }

    private JPanel downPanel(){
        JPanel down = new JPanel(new BorderLayout());
        down.setOpaque(false);
        //doneButton.setVisible(false);
        down.add(new JLabel(" "),BorderLayout.NORTH);
        down.add(new JLabel(" "),BorderLayout.CENTER);
        JPanel booton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        booton.setOpaque(false);
        booton.add(come);
        booton.add(doneButton);
        down.add(booton,BorderLayout.SOUTH);
        return down;
    }

    private void prepareElements(){
        buttons = new ArrayList<>();
        setLayout(new BorderLayout());
        setOpaque(false);
        pooBkemonGUI.styleButton(come);
        pooBkemonGUI.styleButton(doneButton);
        JPanel upPanel = upPanel();
        JPanel right = rightPanel();
        JPanel left = leftPanel();
        JPanel down = downPanel();
        add(upPanel,BorderLayout.NORTH);
        add(right, BorderLayout.EAST);
        add(left, BorderLayout.WEST);
        add(down, BorderLayout.SOUTH);
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        //centro.setBackground(color);

        panelScroll = new JPanel(new GridLayout(4,4,1,1)) { //GridBagLayout   DEBERIA SER CALCULADO FILAS Y COLUMNAS   de dominio
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        //GridBagConstraints gbc = new GridBagConstraints();
        panelScroll.setOpaque(false);
        panelScroll.setBackground(color);
        


        JScrollPane scrollPane = new JScrollPane(panelScroll);
        scrollPane.setBackground(color);
        scrollPane.setOpaque(false);
	    scrollPane.setPreferredSize(new Dimension(300, 400));
	    scrollPane.getViewport().setOpaque(false);
	    scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	    scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        InputMap inputMap = scrollPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = scrollPane.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up");
	    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down");
	    actionMap.put("up", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JScrollBar vertical = scrollPane.getVerticalScrollBar();
	            vertical.setValue(vertical.getValue() - vertical.getUnitIncrement());
	        }
	    });
	    actionMap.put("down", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JScrollBar vertical = scrollPane.getVerticalScrollBar();
	            vertical.setValue(vertical.getValue() + vertical.getUnitIncrement());
	        }
	    });

        JPanel scrollContainer = new JPanel();
	    scrollContainer.setOpaque(false);
	    scrollContainer.setLayout(new BoxLayout(scrollContainer, BoxLayout.Y_AXIS));
	    scrollContainer.add(Box.createVerticalGlue());
	    scrollContainer.add(scrollPane);
	    scrollContainer.add(Box.createVerticalGlue());

	    centro.add(scrollContainer, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        //scrollContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        //upPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        //down.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        //left.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        //right.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        createButtons();
    }
    public void setColor(Color newColor){
        color = newColor;
        texto.setBackground(color);
    }
    public JButton getButtonBack(){
        return come;
    }
    public JButton getNextBJButton(){
        return doneButton;
    }
    public void createButtons() {
        System.err.println(itemsSelected.toString());
        for (String itemSelected : itemsSelected) {
            Item po1 = pooBkemonGUI.domain.getItems().get(itemSelected);
            String nombre = po1.getName();
            String ruta = po1.getName() +".png";
            JButton button = createImageButton(nombre, ruta);
            buttons.add(button);
            button.addActionListener(e -> 
            selectionItems(button)
            );
            panelScroll.add(button);
            //button.setBackground(Color.GREEN);  //??? :(
            //button.setBackground(Color.RED);
            }
    }
    
    private void selectionItems(JButton button){
        if (itemsSelected.contains(button.getToolTipText())) {
            button.setBackground(null);
            button.setOpaque(false);
            itemsSelected.remove(button.getToolTipText());
        }
        else{
            button.setBackground(Color.GREEN);
            button.setOpaque(true);
            itemsSelected.add(button.getToolTipText());
        }
        //System.out.println(pokemonesChoosen);
    }
    public ArrayList<String> getItemsChoosen(){
        return itemsSelected;
    }
    public int sizeChoosen(){
        return itemsSelected.size();
    }
    private JButton createImageButton(String name,String imagePath) {
        int x=1, y=1;
        int width=50, height =50;
        Dimension smallSize = new Dimension(50, 30); 
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/" + imagePath));
            if (imagePath.toLowerCase().endsWith(".gif")){
                button.setIcon(icon);
                button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            } 
            else {
                Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));
            }
            //button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }catch (Exception e) {
            button.setText("No imagen");
        }
        editButton(button, smallSize, name);
        return button;
    }

    private JButton editButton(JButton button, Dimension smallSize,String name){
        button.setPreferredSize(smallSize);
        button.setMinimumSize(smallSize);
        button.setMaximumSize(smallSize); 
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setToolTipText(name);

        return button;
    }
    /*
    private void prepareActions(){
        doneButton.addActionListener(e -> {
        if (sizeChoosen() < 2) {
            JOptionPane.showMessageDialog(this, 
                "Selecciona al menos 2 Items para la batalla!", 
                "Incompleta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (sizeChoosen() > MAX_POKEMONS) {
            JOptionPane.showMessageDialog(this,
                "Solo puedes seleccionar máximo " + MAX_POKEMONS + " pokemones",
                "Límite excedido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        //INICIALIZAR POTIONS.
        //pooBkemonGUI.listMovements.infoSelectedPokemons(pokemonesChoosen);
        //pooBkemonGUI.listPokemonsPanel.inicializate(pokemonesChoosen);
        pooBkemonGUI.cardLayout.show(pooBkemonGUI.panelContenedor,"chooser");
        reset();
        
        });
    }
    */
    private void prepareActions(){
        doneButton.addActionListener(e ->{
            if (itemsSelected.size() == 1){
                try{
                    System.out.println(pooBkemonGUI.domain.getCurrentPokemonPs());
                    System.out.println(itemsSelected.get(0));
                    pooBkemonGUI.domain.actionuseItem(itemsSelected.get(0));
                    System.out.println(pooBkemonGUI.domain.getCurrentPokemonPs());
                    pooBkemonGUI.panelBattle.actualizarCreateStatsPanelAfterMove();
                }catch(PoobkemonException h){
                    System.out.println(h.getMessage());
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Solo puedes utilizar una poción por pókemon", "Límite excedido", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public void reset(){
        itemsSelected.clear();
        for (JButton button : buttons){
            button.setBackground(null);
            button.setOpaque(false);
        }
        revalidate();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ backgroundImage+".JPG"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}

