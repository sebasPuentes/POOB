package presentation;
import domain.*;

import javax.management.openmbean.OpenDataException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CityGUI extends JFrame{
    public static final int SIDE=20;

    public final int SIZE;
    private JButton ticTacButton;
    private JPanel  controlPanel;
    private PhotoCity photo;
    private City theCity;
    private JMenuBar menuBar;
    private JMenu menuPrincipal;
    private JMenuItem nuevo;
    private JMenuItem abrir;
    private JMenuItem guardarComo;
    private JMenuItem importar;
    private JMenuItem exportarComo;
    private JMenuItem salir;
    private File file;




    private CityGUI() {
        theCity=new City();
        SIZE=theCity.getSize();
        prepareElements();
        prepareElementsMenu();
        prepareActions();
        prepareActionsMenu();
    }

    private void prepareElements() {
        setTitle("Schelling City");
        photo=new PhotoCity(this);
        ticTacButton=new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo,BorderLayout.NORTH);
        add(ticTacButton,BorderLayout.SOUTH);
        setSize(new Dimension(SIDE*SIZE+15,SIDE*SIZE+72));
        setResizable(false);
        photo.repaint();
    }
    private void prepareElementsMenu(){
        menuBar = new JMenuBar();
        menuPrincipal = new JMenu("Archivo");
        //Opciones
        nuevo = new JMenuItem("Nuevo");
        abrir =  new JMenuItem("Abrir");
        guardarComo = new JMenuItem("Guardar Como");
        importar = new JMenuItem("Importar");
        exportarComo = new JMenuItem("Exportar Como");
        salir = new JMenuItem("Salir");
        //Añadirlas
        menuPrincipal.add(nuevo);
        menuPrincipal.add(abrir);
        menuPrincipal.addSeparator();
        menuPrincipal.add(guardarComo);
        menuPrincipal.add(importar);
        menuPrincipal.add(exportarComo);
        menuPrincipal.addSeparator();
        menuPrincipal.add(salir);
        menuBar.add(menuPrincipal);
        //Set Del Menu
        setJMenuBar(menuBar);
    }

    private void prepareActions(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ticTacButton.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        ticTacButtonAction();
                    }
                });

    }

    private void prepareActionsMenu(){
        nuevo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                optionNew();
            }
        });

        abrir.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {optionOpen();}
        });

        guardarComo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {optionSave();}
        });

        importar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                optionImport();
            }
        });

        exportarComo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                optionExport();
            }
        });
        salir.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                optionExit();
            }
        });
    }

    //Exit Action Menu
    private void optionExit(){
        System.exit(0);
    }
    //-----------------------Con archivos-----------------------
    //Open Archivo
    private void optionOpen() throws CityException{
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                City loadedCity = theCity.open01(selectedFile);
                if (loadedCity != null) {
                    theCity = loadedCity;
                    JOptionPane.showMessageDialog(this, "File opened successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    photo.repaint();
                }
            }catch (CityException e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void optionSave(){
        File file = new File("");
        try {
            theCity.save01(file);
            JOptionPane.showMessageDialog(this, "File saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }catch (CityException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void optionImport(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                theCity.import03(selectedFile);
                JOptionPane.showMessageDialog(this, "File imported successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                photo.repaint();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void optionExport(){
            theCity.export03(new File("analizandoComportamiento2.txt"));
    }

    private void optionNew(){
        theCity = new City();
        photo.repaint();

    }

    private void ticTacButtonAction() {
        theCity.ticTac();
        photo.repaint();
    }

    public City gettheCity(){
        return theCity;
    }

    public static void main(String[] args) {
        CityGUI cg=new CityGUI();
        cg.setVisible(true);
    }
}

class PhotoCity extends JPanel{
    private CityGUI gui;

    public PhotoCity(CityGUI gui) {
        this.gui=gui;
        setBackground(Color.white);
        setPreferredSize(new Dimension(gui.SIDE*gui.SIZE+10, gui.SIDE*gui.SIZE+10));
    }


    public void paintComponent(Graphics g){
        City theCity=gui.gettheCity();
        super.paintComponent(g);

        for (int c=0;c<=theCity.getSize();c++){
            g.drawLine(c*gui.SIDE,0,c*gui.SIDE,theCity.getSize()*gui.SIDE);
        }
        for (int f=0;f<=theCity.getSize();f++){
            g.drawLine(0,f*gui.SIDE,theCity.getSize()*gui.SIDE,f*gui.SIDE);
        }
        for (int f=0;f<theCity.getSize();f++){
            for(int c=0;c<theCity.getSize();c++){
                if (theCity.getItem(f,c)!=null){
                    g.setColor(theCity.getItem(f,c).getColor());
                    if (theCity.getItem(f,c).shape()==Item.SQUARE){
                        if (theCity.getItem(f,c).isActive()){
                            g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);
                        }else{
                            g.drawRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);
                        }
                    }else {
                        if (theCity.getItem(f,c).isActive()){
                            g.fillOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                        } else {
                            g.drawOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                        }
                    }
                    if (theCity.getItem(f,c).isAgent()){
                        g.setColor(Color.red);
                        if (((Agent)theCity.getItem(f,c)).isHappy()){
                            g.drawString("u",gui.SIDE*c+6,gui.SIDE*f+15);
                        } else if (((Agent)theCity.getItem(f,c)).isIndifferent()){
                            g.drawString("_",gui.SIDE*c+7,gui.SIDE*f+10);
                        } else if (((Agent)theCity.getItem(f,c)).isDissatisfied()){
                            g.drawString("~",gui.SIDE*c+6,gui.SIDE*f+17);
                        }
                    }
                }
            }
        }
    }
}