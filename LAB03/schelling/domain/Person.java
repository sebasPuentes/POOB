package domain;

import java.awt.Color;

/**Information about a person<br>
<b>(city,row,column,color)</b><br>
<br>
 */
public class Person extends Agent implements Item{
    protected City city;
    protected int row,column;    
    protected Color color;
    
    /**
     * Create a new person (<b>row,column</b>) in the city <b>ac</b>..
     * @param city 
     * @param row 
     * @param column 
     */
    public Person(City city,int row, int column){
        this.city=city;
        this.row=row;
        this.column=column;
        this.city.setItem(row,column,(Item)this);    
        color=Color.blue;
    }
    
    /**
     * Returns the row
     * @return row int
     */
    public final int getRow(){
        return row;
    }
    
    /**
     * Returns the column
     * @return column int
     */
    public final int getColumn(){
        return column;
    }

    /**
     * Returns the color
     * @return color.
     */
    public final Color getColor(){
        return color;
    }

    /**
     * Sets the Persons state.
     */
    public void decide(){
         state=(getSteps() % 3 == 0 ? Agent.HAPPY: (getSteps() % 3 == 1 ? Agent.INDIFFERENT: Agent.DISSATISFIED));
    }

    /**
     * Change its actual state
     */
    public final void change(){
        step();
    }
    
}
