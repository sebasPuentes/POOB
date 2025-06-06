package presentation;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Maxwell extends JPanel {

    private JPanel Panel;
    public static Color color1 = Color.RED;  
    public static Color color2 = Color.BLUE; 
    private static final Color HOLE = Color.GRAY; 

    private int height;  
    private int width;   

    private int[] holes;        
    private int[] redParticles; 
    private int[] blueParticles; 
    private int[] midWall;      

    /**
     * Constructor for creating a Maxwell object with specified grid size and data.
     * @param newH The height of the grid.
     * @param newW The width of the grid.
     * @param data A 2D array containing the particle, hole, and wall data.
     */
    public Maxwell(int newH, int newW, int[][] data) {
        this(newH, newW);
        setData(data);
        paintComponents();
    }

    /**
     * Constructor for creating a Maxwell object with specified grid size.
     * @param newH The height of the grid.
     * @param newW The width of the grid.
     */
    public Maxwell(int newH, int newW) {
        height = newH;
        width = newW;
        setLayout(new GridLayout(1, 1));
        setSize(getWidth(), getHeight());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        prepareElements();
    }

    /**
     * Constructor for creating a Maxwell object 
     * @param data A 2D array containing the particle, hole, and wall data.
     */
    public Maxwell(int[][] data) {
        this(11, 20);
        setData(data);
        paintComponents();
    }

    /**
     * Sets the data for the particles, holes, and mid-wall positions.
     * @param data A 2D array containing the particle, hole, and wall data.
     */
    private void setData(int[][] data) {
        blueParticles = data[0];
        redParticles = data[1];
        holes = data[2];
        midWall = data[3];
    }

    /**
     * Prepares the elements of the grid
     */
    private void prepareElements() {
        Panel = new JPanel(new GridLayout(height, (2 * width) + 1));
        for (int i = 0; i < height * ((2 * width) + 1); i++) {
            JPanel celda = new JPanel();
            Panel.add(celda);
        }
        Panel.setBorder(getBorder());
        add(Panel);
    }

    /**
     * Paints the components
     */
    public void paintComponents() {
        paintMidWall();
        for (int num : blueParticles) {
            Panel.getComponent(num).setBackground(color2);
        }
        for (int num : redParticles) {
            Panel.getComponent(num).setBackground(color1);
        }
        for (int num : holes) {
            Panel.getComponent(num).setBackground(HOLE);
        }
    }

    /**
     * Repaints the components
     */
    public void rePaintComponents() {
        for (int i = 0; i < Panel.getComponentCount(); i++) {
            Panel.getComponent(i).setBackground(Color.WHITE);
        }

        paintMidWall();

        for (int num : blueParticles) {
            Panel.getComponent(num).setBackground(color2);
        }

        for (int num : redParticles) {
            Panel.getComponent(num).setBackground(color1);
        }

        for (int num : holes) {
            Panel.getComponent(num).setBackground(HOLE);
        }
    }

    /**
     * Paints the mid-wall 
     */
    private void paintMidWall() {
        int totalCells = height * (2 * width + 1);
        int centerIndex = (height / 2) * (2 * width + 1) + width;
        for (int i : midWall) {
            if (i >= 0 && i < totalCells) {
                if (i == centerIndex) {
                    Panel.getComponent(i).setBackground(Color.GRAY);
                } else {
                    Panel.getComponent(i).setBackground(Color.BLACK);
                }
            }
        }
    }

    /**
     * Sets the background color of all cells in the grid to white.
     */
    private void setBackground() {
        Component[] componentesDer = Panel.getComponents();
        for (int i = 0; i < componentesDer.length; i++) {
            componentesDer[i].setBackground(Color.WHITE);
        }
    }

    /**
     * Refreshes the grid with new data
     * @param info A 2D array containing the updated particle, hole, and wall data.
     */
    public void refresh(int[][] info) {
        setBackground();
        setData(info);
        paintMidWall();
        paintComponents();
        Panel.revalidate();
        Panel.repaint();
        revalidate();
        repaint();
    }

    /**
     * Resets the particle colors to the default 
     */
    public void resetColors() {
        color1 = Color.RED;
        color2 = Color.BLUE;
    }
}
