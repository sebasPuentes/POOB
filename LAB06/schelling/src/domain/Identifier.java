package domain;
import java.awt.Color;
import java.io.Serializable;


/**
 * Write a description of class Identifier here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Identifier implements Item, Serializable {
    private int row,column;
    private City city;
    public final static char RED ='r', GREEN ='g', GRAY = 'y';
    private char state;
    private Color color;
    
    /**
     * Constructor for objects of class Identifier
     */
    public Identifier(City city, int row, int column){
        this.city=city;
        this.row=row;
        this.column=column;
        this.city.setItem(row,column,this);
        state = GRAY;
        color = Color.gray;
    }
    
    /**
     * If items around then state is GREEN, otherwise RED.
     */
    public void decide(){
        if(itemsAround()){
            state = GREEN;
            color = Color.green;
        }else{
            state = RED;
            color = Color.red;
        }
    }
    
    /**
     * Returns color of the item.
     */
    public Color getColor(){
        return color;
    }
    
    /**
     * Returns the state of the item.
     */
    public char getIdentifierState(){
        return state;
    }
    
    /**
     * Checks if the are items around.
     */
    public boolean itemsAround(){
        for(int dr=-1; dr<2;dr++){
            for (int dc=-1; dc<2;dc++){
                if( dr == 0 && dc == 0){
                    continue;
                }
                int checkRow = row + dr;
                int checkColumn = column + dc;
                if(city.inLocations(checkRow,checkColumn) && city.getItem(checkRow,checkColumn) != null){
                    return true;
                }
            }
        }
        return false;
    }
}
