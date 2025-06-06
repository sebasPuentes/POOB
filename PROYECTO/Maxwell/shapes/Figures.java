package shapes;
import java.awt.*;
import java.awt.geom.*;


/**
 * Father class that have al the figures of the methods
 * 
 * @author (Julian Lopez && Sebastian Puentes) 
 * @version (03/13/2025)
 */
public abstract class Figures{
    
    protected boolean isVisible;
    protected String color;
    protected int xPosition;
    protected int yPosition;
    
    /**
     * Get the circle Color
     */
    public String getColor(){
        return color;
    }
    
     /**
     * Set the position of the circle 
     * @param XPosition
     * @param YPosition
     */
    public void setPosition(int x, int y){
        xPosition = x;
        yPosition = y;
        
    }
    
    /**
     * Make visible the circle
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make Invisible the circle
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Get the x position
     */
    public int getX(){
        return this.xPosition;
    }
    
    public void moveTo(int newX, int newY){
        erase();
        xPosition = newX;
        yPosition = newY;
        draw();
    }
    
    /**
     * Get the y position
     */
    public int getY(){
        return this.yPosition;
    }
    
    /**
     * Draw the figures
     */
    public abstract void draw();
    
    /**
     * Erase the figures
     */
    public abstract void erase();
    
    /**
     * Move the circle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the circle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the circle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the circle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the circle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the circle vertically
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        }else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
}
