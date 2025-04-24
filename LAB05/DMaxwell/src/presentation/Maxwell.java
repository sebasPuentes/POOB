package presentation;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Maxwell extends JPanel {

    private JPanel Panel;
    private static Color color1 = Color.RED;
    private static Color color2 = Color.BLUE;
    private static final Color Hole = Color.GRAY;

    private int h;
    private int w;

    
    private  int[] hoyos;
    private  int[] particulasRed;
    private  int[] ParticulasBlue;
    private  int[] midWall;

    public Maxwell(int newH, int newW,int [][] info ){
        this(newH,newW);
        setVariables(info);
        paintComponents();
    }

    public Maxwell(int newH, int newW){
        h = newH;
        w = newW;
        setLayout(new GridLayout(1,1));
        setSize(getWidth(), getHeight());
        setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        prepareElements();
    }

    public Maxwell(int[][] info){
        this(11,20);
        setVariables(info);
        paintComponents();
    }

    private void setVariables(int[][] info){
        ParticulasBlue = info[0];
        particulasRed = info[1];
        hoyos = info[2];
        midWall = info[3];
    }

    private void prepareElements(){
        Panel = new JPanel(new GridLayout(h, (2*w)+1));
        for (int i = 0; i < h * ((2*w)+1); i++) {
            JPanel celd = new JPanel();
            Panel.add(celd);
        }
        Panel.setBorder(getBorder());
        add(Panel);
    }

    public void paintComponents(){
        paintCenter();
        for (int num:ParticulasBlue){
            Panel.getComponent(num).setBackground(color2);
        }
        for(int num:particulasRed){
            Panel.getComponent(num).setBackground(color1);
        }
        for(int num: hoyos){
            Panel.getComponent(num).setBackground(Hole);
        }
        
    }

    private void paintCenter(){ //451
        int variable = h*((2*w) +1);
        int centroPar = (h/2) * (2 * w + 1) + w;
        for(int i: midWall){
            Panel.getComponent(i).setBackground(Color.BLACK);
            if((int) variable/2 == i){
                Panel.getComponent(i).setBackground(Color.GRAY);
            }
            if (i == centroPar){
                Panel.getComponent(i).setBackground(Color.GRAY);
            }
            else if (i == centroPar){
                Panel.getComponent(i).setBackground(Color.GRAY);
            }
        }
    }
    

}
