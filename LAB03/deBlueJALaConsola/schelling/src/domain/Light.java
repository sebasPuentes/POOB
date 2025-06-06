package domain;
import java.awt.Color;

/**
 * Write a description of class Light here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Light implements Item
{
    private City city;
    private int row,column;
    private Color color;    
    public final static char RED ='r', YELLOW ='y', GREEN ='g';
    private char state;
    private char lastColor;
    
    /**
     * Constructor for objects of class Light.
     */
    public Light(City city, int row, int column){
        this.city=city;
        this.row=row;
        this.column=column;
        this.city.setItem(row,column,this);
        state = RED;
        color = Color.RED;
    }
    
    /**
     * Change Color of the Light.
     */
    @Override
    public void decide(){
        changeColor();
    }
    
    /**
     * Returns color of the item.
     * @return Color color.
     */
    public Color getColor(){
      return color;
    }
    
    /**
     * Change the color and states of the item.
     */
    public void changeColor() {
        if (state == RED && color == Color.red) {
            state = YELLOW;
            color = Color.yellow;
            lastColor = RED;
        } else if (state == YELLOW && color == Color.yellow && lastColor == RED) {
            state = GREEN;
            color = Color.green;
            lastColor = YELLOW;
        } else if (state == GREEN && color == Color.green) {
            state = YELLOW;
            color = Color.yellow;
            lastColor = GREEN;
        }else if (state == YELLOW && color == Color.yellow && lastColor == GREEN){
            state = RED;
            color = Color.red;
            lastColor = YELLOW;
        }
    }
    
    /**
     * Returns LightState
     * @return char state.
     */
    public char getLightState(){
        return state;
    }
}
