package domain;

/**
 * Write a description of class Walker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Walker extends Person
{
    
    /**
     * Constructor for objects of class Walker
     */
    public Walker(City city,int row,int column){
        super(city,row,column);
        color = color.green;
        state = INDIFFERENT;
    }
    
    /**
     * Returns the Walker Shape.
     * @return SQUARE int.
     */
    @Override
    public int shape(){
        return SQUARE;
    }
    
    /**
     * Checks items around and then moves.
     */
    @Override
    public void decide(){
        checkItemsAround();
        move();
    }
    

    /**
     * Checks if there exists items around the walker. If exists then his state turns happy.
     */
    public void checkItemsAround(){
        for(int dr=-1; dr<2;dr++){
            for (int dc=-1; dc<2;dc++){
                if( dr == 0 && dc == 0){
                    continue;
                }
                int checkRow = row + dr;
                int checkColumn = column + dc;
                if(city.inLocations(checkRow,checkColumn) && city.getItem(checkRow,checkColumn) != null){
                    state = HAPPY;
                }
            }
        }
    }
    
    /**
     * Moves the walker only to the north.
     */
    public void move(){
        if (row > 0) { 
            int newRow = row - 1;
            if (city.isEmpty(newRow, column)) { 
                city.setItem(row, column, null); 
                row = newRow; 
                city.setItem(row, column, this);
            }else{
                state = DISSATISFIED;
            }
        }
    }
}
