package Maxwell;
import shapes.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Class Hole
 * 
 * @author (Julian Camilo Lopez Barrero && Juan Sebastian Puentes Julio) 
 * @version (a version number or a date)
 */
public class Hole
{
    public final static String MOVIL = "Movil", NORMAL = "Normal";
    protected String type;
    protected Rectangle hole;
    protected int width;
    protected int height;
    protected int xPosition;
    protected int yPosition;
    protected int maxParticles;
    protected int eatenParticles;
    protected int size = 15;
    
    /**
     * Constructor for objects of class Hole
     */
    public Hole(int xPosition, int yPosition, int height, int width,int maxParticles) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.maxParticles = maxParticles;
        eatenParticles = 0;
        hole = new Rectangle();  
        hole.changeSize(height, width);  
        hole.setPosition(xPosition,yPosition);  
        hole.changeColor("black");  
        type = NORMAL;
    }
    
    /**
     * Returns the type of the Hole.
     * @return String type.
     */
    public String getType(){
        return type;
    }
    
    /**
     * Make invisible the hole
     */
    public void makeInvisibleHole(){
        hole.makeInvisible();
    }
    
    /**
     * Make invisible the hole
     */
    public void makeVisibleHole(){
        hole.makeVisible();
    }
    
    /**
    * Get position in x
    */
    public int getX(){
        return this.xPosition;
    }
    
    /**
     * Get position in y
     */
    public int getY(){
        return this.yPosition;
    }
    
    /**
     * Get Hole width.
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Get Hole Height.
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Get Max Amount of particles that can be eaten.
     */
    public int getAmountParticles(){
        return maxParticles;
    }
    
    /**
     * Verifys if holes can eat more particles.
     */
    public boolean canEat() {
        return eatenParticles < maxParticles;
    }

    /**
     * Increases eatenParticles variable if hole can eat.
     */
    public void eatParticle() {
        if (canEat()) {
            eatenParticles++;
        }
    }
    
    /**
     * Returns eaten particles.
     * @return int eatenParticles.
     */
    public int getEatenParticles(){
        return eatenParticles;
    }

    /**
     * Moves hole.
     */
    public void move(){
        return;
    }
    
    /**
     * Checks Hole Collision.
     * @param boolean isInLeft.
     * @param int minX.
     * @param int maxXLeftBoard.
     * @param int minXRightBoard.
     * @param int maxXRightBoard.
     * @param int minY.
     * @param int maxY.
     */
    public void checkHoleCollision(boolean isInLeft,int minX, int maxXLeftBoard, int minXRightBoard, int maxXRightBoard, int minY, int maxY) {
        return;
    }
}
