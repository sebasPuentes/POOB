package domain;
import java.awt.Color;


/**
 *  Schelling.
 * 
 * @author (Julian Camilo Lopez y Juan Sebastian Puentes Julio) 
 * @version (a version number or a date)
 */
public class Schelling extends Person
{
    /**
     * Constructor for objects of class Schelling.
     */
    public Schelling(City city, int row, int column)
    {
        super(city,row,column);
        color = Color.yellow;
    }
    

    /**
     * Verifys and moves Schelling.
     */
    @Override
    public void decide(){
        int similarNeighbors = city.neighborsEquals(row,column);
        int neighbors = city.neighbors(row,column);
        int neighborsDiff = neighbors - similarNeighbors;
        if(neighbors == 0 || neighbors == similarNeighbors){
            state = INDIFFERENT;
        }
        if(similarNeighbors <= neighbors/3){
            state = DISSATISFIED;
        }
        if(similarNeighbors > neighbors/3 && similarNeighbors != neighbors){
            state = HAPPY;
        }
        
        if(state == DISSATISFIED){
            if(city.isEmpty(row,column-1)){
                city.changeItemPosition(row,column,row,column-1,(Item)this);
                column --;
            }else if(city.isEmpty(row-1,column)){
               city.changeItemPosition(row,column,row-1,column,(Item)this); 
               row--;
            }else if(city.isEmpty(row+1,column)){
               city.changeItemPosition(row,column,row+1,column,(Item)this); 
               row++;
            }else if(city.isEmpty(row,column+1)){
               city.changeItemPosition(row,column,row,column+1,(Item)this); 
               column++;
            }
        }
    }
    
}

