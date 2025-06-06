package presentation;
import domain.*;

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
   
    
    private CityGUI() {
        theCity=new City();
        SIZE=theCity.getSize();
        prepareElements();
        prepareActions();
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

    private void prepareActions(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        ticTacButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    ticTacButtonAction();
                }
            });

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