package presentation;
import com.sun.java.accessibility.util.GUIInitializedListener;
import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class DMaxwellGUI extends JFrame {
    private JButton up;
    private JButton down;
    private JButton right;
    private JButton left;
    private JButton color1;
    private JButton color2;

    private Maxwell tablero;
    private DMaxwell maxwell;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JMenuItem cambiarColor1;
    private JMenuItem cambiarColor2;
    private JMenuItem newBoard;

    public DMaxwellGUI() {
        maxwell = new DMaxwell();
        prepareElements();
        prepareActions();
    }

    private void prepareActions() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Boton Salir Ventana OYENTE
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", JOptionPane.QUESTION_MESSAGE, 
                JOptionPane.YES_NO_OPTION);
                JDialog dialog = optionPane.createDialog(DMaxwellGUI.this, "Confirmar Salida");
                dialog.setVisible(true);
                if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
                    System.exit(0);  
                }
            }
        });
        /*/Oyente Boton Subir
        up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maxwell.moveNorth();
            }
        });
        //Oyente Boton Bajar
        down.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maxwell.moveSouth();
            }
        });
        //Oyente Boton Izquierda
        left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maxwell.moveWest();
            }
        });
        //Oyente Boton Derecha
        right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maxwell.moveEast();
            }
        });
        */
        prepareActionMenu();

        
    }

    private void prepareElements() {
        setTitle("Maxwell Discreto");
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(width * 1 / 4, height * 1 / 4);
        setLocationRelativeTo(null);

        prepareElementsBoard();
        prepareElementsMenu();
    }

    private void prepareElementsBoard(){
        setLayout(new GridLayout(2,1));
        tablero = new Maxwell(maxwell.container());
        add(tablero);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));

        JPanel maxPanel = new JPanel(new BorderLayout());
        up = new JButton("↑");
        down = new JButton("↓");
        right = new JButton("→");
        left = new JButton("←");

        maxPanel.add(up,BorderLayout.NORTH);
        maxPanel.add(down,BorderLayout.SOUTH);
        maxPanel.add(left,BorderLayout.WEST);
        maxPanel.add(right,BorderLayout.EAST);
        south.add(maxPanel);
        
        add(south);
        
    }

    private void prepareElementsMenu() {
        JMenuBar classicMenu = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenu opciones = new JMenu("Opciones");
        openItem = new JMenuItem("Abrir");
        saveItem = new JMenuItem("Salvar");
        exitItem = new JMenuItem("Salir");

        cambiarColor1 = new JMenuItem("Cambiar Color Rojas");
        cambiarColor2 = new JMenuItem("Cambiar Color Azules");
        newBoard = new JMenuItem("Nuevo Tablero");
    
        opciones.add(cambiarColor1);
        opciones.addSeparator();
        opciones.add(cambiarColor2);
        opciones.addSeparator();
        opciones.add(newBoard);

        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        classicMenu.add(fileMenu);
        classicMenu.add(opciones);
        setJMenuBar(classicMenu);
    }

    private void prepareActionMenu(){
        //Abrir Oyente
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(DMaxwellGUI.this) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(DMaxwellGUI.this,"Funcionando" + " " + selectedFile.getName(),"Abrir archivo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //Salvar Oyente
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(DMaxwellGUI.this) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(DMaxwellGUI.this,"Funcionando" + " " + selectedFile.getName(),"Salvar archivo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //Salir Oyente
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", 
                JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                JDialog dialog = optionPane.createDialog(DMaxwellGUI.this, "Confirmar Salida");
                dialog.setVisible(true);
                if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
                    System.exit(0); 
                }
            }
        });
    }

    private void refresh(){
        repaint();
    }

    public static void main(String[] args) {
        DMaxwellGUI gui = new DMaxwellGUI();
        gui.pack();
        gui.setVisible(true);
    }
}
