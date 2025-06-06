package shapes;
import java.awt.*;
import java.util.ArrayList;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 1.0  (15 July 2000)()
 */


 
public class Rectangle extends Figures{

    public static int EDGES = 4;
    private int height;
    private int width;

    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle(){
        height = 30;
        width = 40;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
        isVisible = false;
    }
    
    public Rectangle(int h,int w){
        height = h;
        width = w;
        xPosition = 100;
        yPosition = 100;
        color = "white";
        isVisible = false;
    }
    
    public void showCenter(){
        Canvas canvas = Canvas.getCanvas();
        canvas.drawCartesianPlane();
    }
    
    /**
     * Get the widht position of the rectangle
     */
    public int getWidth(){
        return width;
    }
    
    
    /**
     * get the height position of the rectangle
     */
    public int getHeight(){
            return height;
    }
    
    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidth must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }   
    
    /**
     * Change the position y of the rectangle
     * @param the yPosition
     */
    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }
    
    /*
     * Draw the rectangle with current specifications on screen.
     */
    @Override
    public void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, 
                                       width, height));
            canvas.wait(10);
        }
    }
    

    /*
     * Erase the rectangle on screen.
     */
    @Override
    public void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
}

