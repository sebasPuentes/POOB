package Maxwell;
import shapes.*;
import java.util.ArrayList;

/**
 * Class Demon 
 * 
 * @author (Julian Camilo Lopez Barrero && Juan Sebastian Puentes Julio) 
 * @version (07/03/2025)
 */
public class Demon
{
    public final static String WEAK = "Weak", NORMAL = "Normal", BLUE = "Blue";
    protected String type;
    protected int x;
    protected int y;
    protected int size = 15;
    protected Rectangle demon = new Rectangle();
    
    /**
     * Constructor for objects of class Demon.
     */
    public Demon(int xPos,int yPos){ 
        x = xPos;
        y = yPos;
        demon.changeSize(20, 20);
        demon.changeColor("red");
        type = NORMAL;
    }
    
    /**
     * Changes Demon Color.
     */
    public void changeDemonColor(String color){
        demon.changeColor(color);
    }
    
    /**
     * Returns demosn type.
     * @return String type.
     */
    public String getType(){
        return type;
    }
    
    /**
     * Verifys for right particles the access for left chamber.
     * @param p Particles.
     * @return boolean.
     */
    public boolean isInRight(Particles p){
        return Math.abs(p.getX() - getX()) <= size + 10 && 
                Math.abs(p.getY() - getY()) <= size  
                && !p.getColor().equals("red");
    }
    
    /**
     * Verifys for left particles the access for right chamber.
     * @param p Particles.
     * @return boolean.
     */
    public boolean isInLeft(Particles p) {
        return (Math.abs(p.getX()- getX()) <= size &&
                Math.abs(p.getY() - getY()) <= size &&  
                !p.getColor().equals("blue"));
    }
    
    /**
     * Moves demon to an specific position.
     * @param int x.
     * @param int y.
     */
    public void moveDemonTo(int x ,int y){
        demon.moveTo(x,y);
    }
    
    /**
     * Sets demons y position.
     * @param y.
     */
    public void setYPosition(int y){
        this.y = y;
    }

    /**
     * Sets demons x position.
     * @param x.
     */
    public void setXPosition(int x){
        this.x = x;
    }
    
    /**
     * Get the X position
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Get the Y position
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Make Visible the demon
     */
    public void makeVisibleDemon(){
        demon.makeVisible();
    }
    
    /**
     * Make InVisible the demon
     */
    public void makeInvisibleDemon(){
        demon.makeInvisible();
    }

}
