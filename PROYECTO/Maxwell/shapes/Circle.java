package shapes;
import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0.  (15 July 2000) 
 */

public class Circle extends Figures{

    public static final double PI=3.1416;
    private int diameter;
    
    public Circle(){
        diameter = 15;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
        isVisible = false;
    }
    
     /**
     * Creates a new circle with an specific color and position
     * @param color the new color of the circle
     * @param newXPosition position x
     * @param newXPosition position y
     */
    public Circle(String newColor, int newXPosition, int newYPosition){
        color = newColor;
        xPosition = newXPosition;
        yPosition = newYPosition;
        diameter = 10;
    }
    
    /**
     * Draw the circle
     */
    @Override
    public void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, 
                diameter, diameter));
            canvas.wait(10);
        }
    }
    
    /**
     * Erase the circle
     */
    @Override
    public void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }
}
